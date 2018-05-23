package security;

import java.io.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExcutorFile {
    private String algorithm;
    private String inFile;
    private String outFile;
    private int cores;
    private boolean flag;

    /**
     * 在给定cpu核数情况下，对文件执行加密或者hash算法
     * @param algorithm 算法名
     * @param inFile 输入文件路径
     * @param outFile 输出文件路径
     * @param cores 核数
     * @param flag flag == true:运行加密算法， flag == false: 运行hash算法
     */

    public ExcutorFile(String algorithm, String inFile,String outFile, int cores, boolean flag){
        this.algorithm = algorithm;
        this.inFile = inFile;
        this.outFile = outFile;
        this.cores = cores;
        this.flag = flag;
    }
    static final int MAX_BYTE_SIZE = Integer.MAX_VALUE / 2 + Integer.MAX_VALUE / 4;

    public void excutorFile() throws Exception{
        ExecutorService service = Executors.newFixedThreadPool(cores);
        CountDownLatch latch = new CountDownLatch(cores);
        long start = System.currentTimeMillis();
        RandomAccessFile accessFile = new RandomAccessFile(inFile, "rw");
        int size= (int)(accessFile.length() / cores);
        if (size >= MAX_BYTE_SIZE){
            System.out.println("文件太大,每次读取的字节大小：" + MAX_BYTE_SIZE);
            size = MAX_BYTE_SIZE;
        }
        byte[] data = new byte[size];
        for(int i = 0; i < cores; i++){
            accessFile.read(data);
            EncryptRunable runable = new EncryptRunable(algorithm, data,flag);
            service.execute(runable);
            accessFile.seek(size);
        }
        service.shutdown();
        while (true){
            if (service.isTerminated()){
                long end = System.currentTimeMillis();
                BufferedWriter bw = new BufferedWriter(new FileWriter(outFile, true));
                StringBuilder sb = new StringBuilder();
                sb.append(algorithm + ",");
                sb.append(inFile + ",");
                sb.append(cores + ",");
                sb.append(flag + ",");
                sb.append((end - start) +"\n");
                System.out.println(sb.toString());
                bw.write(sb.toString());
                bw.flush();
                bw.close();
                break;
            }
        }
    }
}

package encrypt.security;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author liu
 */
public class ExcutorMain {

    private static Lock lock = new ReentrantLock();

     private  void excutor(String algorithm, String inFile,String outFile, int cores, boolean flag) throws Exception{

        new ExcutorFile(algorithm, inFile, outFile, cores, flag).excutorFile();
    }

    public static void main(String[] args){
        String configure = args[0];
        String inPath = args[1];
        String outFile = args[2];


        try{
            BufferedReader br = new BufferedReader(new FileReader(configure));
            String line;
            // 参数
            String algorithm, inFile;
            int cores;
            boolean flag;
            while ((line = br.readLine()) != null && line.length() > 0){
                lock.lock();
                String[] params = line.split(",");
                algorithm = params[0];
                inFile = inPath + params[1] + ".mkv";
                cores = Integer.valueOf(params[2]);
                flag = Integer.valueOf(params[3]) == 1 ;
                new ExcutorMain().excutor(algorithm, inFile, outFile, cores, flag);
                lock.unlock();
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}

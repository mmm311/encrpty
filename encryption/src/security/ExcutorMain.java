package security;

import java.io.BufferedReader;
import java.io.FileReader;

public class ExcutorMain {
    public static  void excutor(String algorithm, String inFile,String outFile, int cores, boolean flag) throws Exception{
        new ExcutorFile(algorithm, inFile, outFile, cores, flag).excutorFile();
    }

    public static void main(String[] args){
        String configure = "D:\\entrcy\\configure.txt";
        String outFile = "D:\\entrcy\\result.txt";
        String inPath = "E:\\data\\test\\part";
        try{
            BufferedReader br = new BufferedReader(new FileReader(configure));
            String line = null;
            // 参数
            String algorithm = null;
            String inFile = null;
            int cores = 1;
            boolean flag = true;
            while ((line = br.readLine()) != null){
                String[] params = line.split(",");
                algorithm = params[0];
                inFile = inPath + params[1] + "mkv";
                cores = Integer.valueOf(params[2]);
                flag = Integer.valueOf(params[3]) == 1? true : false;
                excutor(algorithm, inFile, outFile, cores, flag);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

package security;

public class EncryptRunable implements Runnable {
    String algorithm = null;
    byte[] data = null;
    // true ：运行加密算法， false: 运行hash算法
    boolean flag;

    public EncryptRunable(String algorithm, byte[] data, boolean flag){
        this.algorithm = algorithm;
        this.data = data;
        this.flag = flag;
    }

    @Override
    public void run() {
        try{
            EncryptHash encryptHash = new EncryptHash(algorithm, data);
            if (flag) {
                encryptHash.encrpy();

            }else{
                encryptHash.hash();

            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}

package encrypt.security;



/**
 * @author liu
 */
public class EncryptRunable implements Runnable {
    private String algorithm = null;
    private byte[] data = null;

    /**
     * flag == true:运行加密算法, flag == fasle: 运行hash算法
     */
    private boolean flag;

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

package security;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.security.MessageDigest;
import java.security.Security;

/**
 * @author liu
 */
public class EncryptHash {
    /**
     * algorithm 算法名
     */
    private String algorithm;
    /**
     * data 传入数据
     */
    private byte[] data;

    public EncryptHash(String algorithm, byte[] data){
        this.algorithm = algorithm;
        this.data = data;
    }

    /**
     * @param size key的长度
     * @return key的字符数组
     * @throws Exception 异常
     */
    private byte[] getKey(int size) throws Exception{
        Security.addProvider(new BouncyCastleProvider());
        KeyGenerator keyGenerator = KeyGenerator.getInstance(algorithm, "BC");
        keyGenerator.getProvider();
        keyGenerator.init(size);
        SecretKey key = keyGenerator.generateKey();
        return key.getEncoded();
    }


    /**
     * @param keyByte 字节数组转换为key对象
     * @return keyByte的key对象
     */
    private Key toKey( byte[] keyByte){
        return new SecretKeySpec(keyByte, algorithm);
    }

    public synchronized void  encrpy() throws Exception{
        // key的长度 默认为128位 DES的key长度64位
        int keySize = 128;

        // 拼接cipherAlgorithm 字符串
        String cipherAlgorithm;
        if ("ARC4".equals(algorithm)){
            cipherAlgorithm = algorithm;
        }else{
            cipherAlgorithm = algorithm + "/ECB/PKCS5Padding";
        }

        if ("DES".equals(algorithm)){
            keySize = 64;
        }

        // 获取加密密钥
        byte[] keyByte = getKey( keySize);
        Key key = toKey(keyByte);

        Cipher  cipher = Cipher.getInstance(cipherAlgorithm);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] result = cipher.doFinal(data);
        cipher.init(Cipher.DECRYPT_MODE, key);
        cipher.doFinal(result);
    }

    public synchronized void hash() throws Exception{
        Security.addProvider(new BouncyCastleProvider());
        MessageDigest md = MessageDigest.getInstance(algorithm);
        md.digest(data);
    }

}

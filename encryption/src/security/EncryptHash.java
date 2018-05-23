package security;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.security.MessageDigest;
import java.security.Security;

public class EncryptHash {
    // 加密算法名
    private String algorithm;
    // 数据
    private byte[] data;

    public EncryptHash(){}


    public EncryptHash(String algorithm, byte[] data){
        this.algorithm = algorithm;
        this.data = data;
    }
    // 获取key 的字节数组
    public byte[] getKey(String algorithm, int size) throws Exception{
        Security.addProvider(new BouncyCastleProvider());
        KeyGenerator keyGenerator = KeyGenerator.getInstance(algorithm, "BC");
        keyGenerator.getProvider();
        keyGenerator.init(size);
        SecretKey key = keyGenerator.generateKey();
        return key.getEncoded();
    }

    // 将key 字节数组转换为key对象
    public Key toKey(String algorithm, byte[] keyByte){
        return new SecretKeySpec(keyByte, algorithm);
    }

    public synchronized void  encrpy() throws Exception{
        // key的长度 默认为128位 DES的key长度64位
        int keySize = 128;

        // 拼接cipherAlgorithm 字符串
        String cipherAlgorithm = null;
        if (algorithm.equals("ARC4")){
            cipherAlgorithm = algorithm;
        }else{
            cipherAlgorithm = algorithm + "/ECB/PKCS5Padding";
        }

        if (algorithm.equals("DES")){
            keySize = 64;
        }

        // 获取加密密钥
        byte[] keyByte = getKey(algorithm, keySize);
        Key key = toKey(algorithm, keyByte);

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

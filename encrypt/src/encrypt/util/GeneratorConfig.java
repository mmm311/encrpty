package encrypt.util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class GeneratorConfig {
    public static void main(String[] args) throws IOException {
        String[] encrptions = new String[]{"IDEA", "DES", "AES", "Blowfish","ARC4"};
        String[] hashs = new String[]{"Tiger", "RIpeMD160", "SHA1", "RipeMD128", "MD5"};

        BufferedWriter br = new BufferedWriter(new FileWriter("configure4.txt", true));

        int encryptions_length = encrptions.length;
        for (int i = 0; i < encryptions_length; i++){
            for(int j = 2; j <= 20; j = j + 2) {
                for(int k = 1; k <= 8; k++) {
                    String str = encrptions[i] + "," + j + "," + k + "," + 1 + "\n";
                    br.write(str);
                }
            }
        }

        int hash_length = hashs.length;
        for (int i = 0; i < hash_length; i++){
            for(int j = 2; j <= 20; j = j + 2) {
                for(int k = 1; k <= 8; k++) {
                    String str = hashs[i] + "," + j + "," + k + "," + 0 + "\n";
                    br.write(str);
                }
            }
        }
        br.close();

    }
}

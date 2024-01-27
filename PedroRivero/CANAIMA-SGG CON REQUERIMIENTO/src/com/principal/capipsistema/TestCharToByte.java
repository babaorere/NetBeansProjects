/*
 * Todos los derechos reservados por CAPIP Sistemas C.A., Venezuela
 * RIF J-407111787
 * capipsistemas@gmail.com, baba.orere@gmail.com
 * @2016, 2017, 2018
 */
package capipsistema;

import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.JOptionPane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author Baba
 */
public class TestCharToByte {

    /**
     * @param instrToEncrypt
     * @return
     */
    public static String encrypt(final byte[] instrToEncrypt) {
        try {
            final Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(charToByte(Arrays.copyOf("J407111787+1".toCharArray(), 16)), "AES"));
            return Base64.getEncoder().encodeToString(cipher.doFinal(instrToEncrypt));
        } catch (Exception inex) {
            JOptionPane.showMessageDialog(null, "Error al tratar de cifrar" + System.getProperty("line.separator") + inex);
            logger.error(inex);
        }
        return null;
    }

    /**
     * @param chars
     * @return
     */
    private static byte[] charToByte(char[] chars) {
        CharBuffer charBuffer = CharBuffer.wrap(chars);
        java.nio.ByteBuffer byteBuffer = Charset.forName("UTF-8").encode(charBuffer);
        byte[] bytes = Arrays.copyOfRange(byteBuffer.array(), byteBuffer.position(), byteBuffer.limit());
        // clear sensitive data
        Arrays.fill(charBuffer.array(), '\u0000');
        // clear sensitive data
        Arrays.fill(byteBuffer.array(), (byte) 0);
        return bytes;
    }

    private static String toStr(byte[] inin) {
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < inin.length; i++) {
            sb.append(String.valueOf(inin[i]));
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.println(toStr(charToByte("123".toCharArray())));
        System.out.println(toStr("123".getBytes()));
        System.out.println(toStr("-pj407111787+1".getBytes()));
        System.out.println(encrypt("J407111787+1".getBytes()));
        System.out.println("J407111787+1".toCharArray().length);
    }

    private static final Logger logger = LogManager.getLogger(TestCharToByte.class);
}

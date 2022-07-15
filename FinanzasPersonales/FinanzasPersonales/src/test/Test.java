package test;

import java.security.MessageDigest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.xml.bind.DatatypeConverter;

/**
 *
 */
public class Test {

    public static void main(String[] args) {

        try {

            String hash = "35454B055CC325EA1AF2126E27707052";
            String password = "ILoveJava";

            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(password.getBytes("UTF-8"));
            byte[] digest = md.digest();

            String myHash = DatatypeConverter.printHexBinary(digest).toUpperCase();

            if (myHash.equals(hash)) {
                System.out.println("Comparacion= TRUE");
            } else {
                System.out.println("Comparacion= FALSE");
            }

            LocalDate date = LocalDate.parse("01-01-2001", DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            System.out.println(date);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime dateTime = LocalDateTime.parse("2019-03-27 00:00:00", formatter);
            System.out.println(dateTime);

        } catch (Exception ex) {
            System.out.println("Error\n" + ex.toString());
        }

    }

}

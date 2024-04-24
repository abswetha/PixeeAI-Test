import io.github.pixee.security.ObjectInputFilters;
import io.github.pixee.security.ZipSecurity;
import java.io.*;
import java.net.*;
import java.sql.*;

public class VulnerableSQLExample {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/mydatabase";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "password";

    public static void main(String[] args) {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream("serializedData.ser"));
            ObjectInputFilters.enableObjectFilterIfUnprotected(ois);
            Object obj = ois.readObject();
            System.out.println(obj.toString());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            URL url = new URL("https://example.com");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            int[] array = new int[5];
            System.out.println(array[10]);
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        }
        try {
            FileInputStream fis = new FileInputStream("evil.zip");
            ZipInputStream zis = ZipSecurity.createHardenedInputStream(fis);
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                byte[] buffer = new byte[1024];
                File outFile = new File(entry.getName());
                FileOutputStream fos = new FileOutputStream(outFile);
                int length;
                while ((length = zis.read(buffer)) > 0) {
                    fos.write(buffer, 0, length);
                }
                fos.close();
            }
            zis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Encoding {

    private final static int BUFFER_SIZE = 1000;

    public static void main(String[] args) throws IOException {
        try (final FileInputStream fileInputStream = new FileInputStream(args[0]);
             final InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "windows-1251");
             final FileOutputStream fileOutputStream = new FileOutputStream(args[1]);
             final OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream, "utf-8")) {
            final char[] buffer = new char[BUFFER_SIZE];
            int readed;
            while ((readed = inputStreamReader.read(buffer, 0, BUFFER_SIZE)) > 0)
                outputStreamWriter.write(buffer, 0, readed);
        }
    }
}
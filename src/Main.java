import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Objects;
import java.util.Vector;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;


// Но что делать, если мы не знаем, что содержится в jar-архиве? Подумав немного над этим вопросом,
// я ознакомился с классом ZipFile. Ведь JarFile является его наследником. У класса ZipFile есть метод entries(),
// который возвращает объект интерфейса Enumeration, содержащий имена всех файлов, входящих в архив.
// Но так как пользоваться этим объектом,
// мягко говоря, неудобно, то имеет смысл перенести всё содержимое в объект класса Vector.


public class Main {

    public static void main(String[] args) throws IOException {


        JarFile jarFile = new JarFile("vulnlab-0.0.5.jar");


        System.out.println(jarFile.size());

        Enumeration<JarEntry> entries = jarFile.entries();
        System.out.println(entries.hasMoreElements());


        Vector<JarEntry> vect = new Vector<>();
// Vector capacity – количество  элементов в векторе v. Почему-то, метод v.capacity() выдаёт большее число,
//  чем на самом деле. Разбираться с этим не стал
        int vc = 0;


        while (Objects.requireNonNull(entries).hasMoreElements()) {
            vect.add(entries.nextElement());
            vc++;
        }


        File tmpfile;
        JarEntry tmpentry;
        FileOutputStream out;
        InputStream in;
        int temp;
        try {
            for (int i = 0; i < vc; i++) {
                tmpentry = vect.get(i);
                tmpfile = new File(tmpentry.getName());
                if (tmpentry.isDirectory()) {
                    if (!tmpfile.mkdir()) {
                        System.out.println("Can't create directory: " + tmpfile.getName());
                        return;
                    }
                } else {
                    in = Objects.requireNonNull(jarFile).getInputStream(tmpentry);
                    out = new FileOutputStream(tmpfile);
                    while ((temp = in.read()) != -1)
                        out.write(temp);
                    out.close();
                    in.close();
                }
            }
        } catch (
                IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}



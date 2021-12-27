import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class Main {

    public static void main(String[] args) {

        openZip("H://JAVA/Games/savegames/zip_save.zip", "H://JAVA/Games/savegames/");
        openProgress("H://JAVA/Games/savegames", ".dat");

    }

    public static void openZip(String zip, String directoryName) {
        try (ZipInputStream zin = new ZipInputStream(new FileInputStream(zip))) {
            ZipEntry entry;
            String name;
            while ((entry = zin.getNextEntry()) != null) {
                name = entry.getName();
                FileOutputStream fout = new FileOutputStream(directoryName + name);
                for (int c = zin.read(); c != -1; c = zin.read()) {
                    fout.write(c);
                }
                fout.flush();
                zin.closeEntry();
                fout.close();
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

    }

    public static void openProgress(String directoryName, String expansion) {
        File dir = new File(directoryName);
        for (File item : dir.listFiles()) {
            // проверим, является ли объект каталогом
            if (!item.isDirectory() && item.getName().contains(expansion)) {

                // String name = fileName + i + ".txt";
                GameProgress gameProgress = null;
                // откроем входной поток для чтения файла
                try (FileInputStream fis = new FileInputStream(directoryName + "/" + item.getName());
                     ObjectInputStream ois = new ObjectInputStream(fis)) {
                    // десериализуем объект и скастим его в класс
                    gameProgress = (GameProgress) ois.readObject();
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
                System.out.println(gameProgress);
            }

        }
    }

}

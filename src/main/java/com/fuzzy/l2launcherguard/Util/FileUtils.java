package com.fuzzy.l2launcherguard.Util;

//import org.apache.commons.lang3.RandomStringUtils;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;

public class FileUtils {

    public static void ensureDirectories(Path dir) throws IOException {
        try {
            com.fuzzy.l2launcherguard.utils.FileUtils.ensureDirectories(dir);
        } catch (IOException e) {
            throw new IOException(e);
        } catch (SecurityException e) {

        }
    }

    /**
     * Создает временный файл который забираем из uri.
     * <br>
     * Вернет готовый Path для работы.
     * <br>
     * За удаление временных файлов отвечает ОС или вызывающая данный метод сторона.
     *
     * Адрес файла в файловой системе.
     * @return вернет Path файла который обработан для работы при многосерверной развертке системы.
     *  Может вернуть {@link (IOException)} если файл в процессе работы был удален или перемещен.
     */
//    public static Path copyFileToTempDir(@NonNull Component component, @NonNull URI uri) throws PlatformException {
//        try {
//            final Path temp = Files.createTempFile("temp_", "");
//            final ClusterFile clusterFile = new ClusterFile(component, uri);
//            clusterFile.copyTo(temp, StandardCopyOption.REPLACE_EXISTING);
//            return temp;
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    public static void saveToFile(byte[] data, Path path) {
        if (data == null) throw new IllegalArgumentException();
        try (OutputStream outputStream = Files.newOutputStream(path, StandardOpenOption.CREATE, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING)) {
            outputStream.write(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Path saveToFileIntoSubsystemsTempDir(byte[] data, String fileName, Path path) {
        saveToFile(data, path);
        return path;
    }

    public static Path saveToFileIntoSubsystemsTempDir(byte[] data, Path tempDir) {
        Path path = null;
//        do {
//            String fileName = RandomStringUtils.randomAlphanumeric(20, 21);
//            path = tempDir.resolve(fileName);
//        } while (Files.exists(path));
//        saveToFile(data, path);
        return path;
    }

    public static byte[] readBytesFromFile(Path path){
        try (InputStream inputStream = Files.newInputStream(path, StandardOpenOption.READ)) {
            final ByteArrayOutputStream output = new ByteArrayOutputStream();
            inputStream.transferTo(output);
            return output.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}

package lt.asprogramuoju;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Will write and read object to file.
 *
 * @author pavel.vrublevskij
 * @version 1.0
 */
@SuppressWarnings("all")
public class FileUtil {

    private FileUtil(){}

    public static <T> boolean writeObjectToFile(File file, T obj) throws Exception {
        List<T> listOfObjects = readAllFromFile(file);
        listOfObjects.add(obj);

        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(file))) {
            listOfObjects.forEach(t -> {
                try {
                    objectOutputStream.writeObject(t);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            objectOutputStream.flush();
        } catch (IOException e) {
            throw new IOException(e);
        }
        return true;
    }

    /**
     * Read objects from file and return list. Call this method you have to know which domain object used in the file
     *
     * @param file
     * @param <T> any domain/bean class
     * @return ArrayList
     * @throws Exception
     */
    public static <T> List<T> readAllFromFile(File file) throws Exception {
        List<T> values = new ArrayList<>();
        file.createNewFile(); // if file already exists will do nothing
        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(file))) {
            while (true) {
                values.add((T) objectInputStream.readObject());
            }
        } catch (EOFException e) {
            return values;
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

}

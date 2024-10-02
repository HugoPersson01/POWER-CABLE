
package se.kth.adamfhhugoper.labb3b.io;

import se.kth.adamfhhugoper.labb3b.model.Project;

import java.io.*;
import java.util.List;

/**
 * Hints on how to implement serialization and deserialization
 * of lists of projects and users.
 */


public class ProjectsFileIO {

    /**
     * Call this method before the application exits, to store the users and projects,
     * in serialized form.
     */

    public static void serializeToFile( File file, List<Project> data) throws IOException {

        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file))) {
            out.writeObject(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Call this method at startup of the application, to deserialize the users and
     * from file the specified file.
     */

    @SuppressWarnings("unchecked")
    public static List<Project> deSerializeFromFile (File file) throws IOException, ClassNotFoundException {

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
            List<Project> data = (List<Project>) in.readObject();
            return data;
        }


    }

    private ProjectsFileIO() {}
}



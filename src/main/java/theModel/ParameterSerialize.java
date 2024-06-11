package theModel;

import java.io.*;
import java.util.ArrayList;

/**
 * The ParameterSerialize class handles the serialization and deserialization of parameter data.
 * This class is used to save and load a list of strings to and from a file.
 * The file is named parameterSerialize.ser and is located in the root directory of the project.
 * The class uses the Java serialization API to save and load data.
 *
 * Here are the attributes of the class:
 * <ul>
 *     <li>{@code fileText} String: the name of the file to save/load data</li>
 * </ul>
 *
 * @see java.io.Serializable
 * @see java.io.FileOutputStream
 * @see java.io.ObjectOutputStream
 * @see java.io.FileInputStream
 *
 */
public class ParameterSerialize {
    private final String fileText = "parameterSerialize.ser"; // The file to save/load data

    /**
     * Saves the given data to a file.
     *
     * @param data the data to save
     * @throws IOException if an I/O error occurs during saving
     */
    public void saveData(ArrayList<String> data) throws IOException {
        FileOutputStream fileOut = new FileOutputStream(fileText);
        ObjectOutputStream out = new ObjectOutputStream(fileOut);
        out.writeObject(data);
        System.out.printf("Data saved to file %s \n", fileText);
        out.close();
    }

    /**
     * Loads data from a file.
     *
     * @return the data loaded from the file
     * @throws IOException            if an I/O error occurs during loading
     * @throws ClassNotFoundException if the class for the serialized object cannot be found
     */
    public ArrayList<String> loadData() throws IOException, ClassNotFoundException {
        File file = new File(fileText);
        if (!file.exists()) {
            System.out.printf("The file %s does not exist. \n", fileText);
            return new ArrayList<>();
        }
        FileInputStream fileIn = new FileInputStream(fileText);
        ObjectInputStream in = new ObjectInputStream(fileIn);
        ArrayList<String> data = (ArrayList<String>) in.readObject();
        in.close();
        return data;
    }
}

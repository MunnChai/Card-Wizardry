package persistence;

import model.Writable;
import org.json.*;
import java.io.*;



// Represents a writer that writes JSON representation of user to file
public class JsonWriter {
    private static final int TAB = 4;
    private PrintWriter writer;
    private String destination;

    public JsonWriter(String destination) {
        this.destination = destination;
    }

    // MODIFIES: this
    // EFFECTS: Opens this writer, throws FileNotFoundException if destination is not a valid file that can be opened
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(destination));
    }

    // MODIFIES: this
    // EFFECTS: Writes user into destination file in json form
    public void write(Writable thingToWrite) {
        JSONObject json = thingToWrite.toJson();
        writer.print(json.toString(TAB));
    }

    // MODIFIES: this
    // EFFECTS: Closes this writer
    public void close() {
        writer.close();
    }
}

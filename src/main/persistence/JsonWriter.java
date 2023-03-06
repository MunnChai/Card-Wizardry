package persistence;

import model.User;
import org.json.*;
import java.io.*;



// Represents a writer that writes JSON representation of workroom to file
public class JsonWriter {
    private static final int TAB = 4;
    private PrintWriter writer;
    private String destination;

    public JsonWriter(String destination) {
        this.destination = destination;
    }

    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(destination));
    }

    public void write(User user) {
        JSONObject json = user.toJson();
        writer.print(json.toString(TAB));
    }

    public void close() {
        writer.close();
    }
}

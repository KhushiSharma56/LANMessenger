package chat.core;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MessageUtils {

    public static String getTimestamp() {
        return new SimpleDateFormat("HH:mm:ss").format(new Date());
    }

    public static synchronized void saveMessageToLog(String message) {
        try (FileWriter fw = new FileWriter("chat-log.txt", true)) {
            fw.write(message + System.lineSeparator());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

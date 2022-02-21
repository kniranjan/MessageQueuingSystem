package queue;

import consumer.JSONConsumer;
import org.json.JSONObject;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class JSONQueueTest {

    @Test(expected = InvalidMessageFormatException.class)
    public void addMessageTestInvalidJSON() throws UnPublishedMessageException, InvalidMessageFormatException {
        Queue queue = new JSONQueue("test-queue-1");
        queue.addMessage("message");
    }

    @Test
    public void addMessageValidJSON() throws IOException, UnPublishedMessageException, InvalidMessageFormatException {
        Path path = Paths.get("src/test/resources/json_message_1.json");
        JSONConsumer consumer = new JSONConsumer("test-consumer-1");
        StringBuilder sb = new StringBuilder();
        for(String line : Files.readAllLines(path)) {
            sb.append(line);
        }
        String expectedMessage = sb.toString();
        JSONObject expectedJson = new JSONObject(expectedMessage);
        Queue queue = new JSONQueue("test-queue-1");
        queue.addMessage(expectedJson);


    }
}

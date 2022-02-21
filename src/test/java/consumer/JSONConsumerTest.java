package consumer;

import consumer.JSONConsumer;
import org.json.JSONObject;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class JSONConsumerTest {

    @Test
    public void testConsumeMessage() throws IOException {
        Path path = Paths.get("src/test/resources/json_message_1.json");
        JSONConsumer consumer = new JSONConsumer("test-consumer-1");
        StringBuilder sb = new StringBuilder();
        for(String line : Files.readAllLines(path)) {
            sb.append(line);
        }
        String message = sb.toString();
        System.out.println(message);
        JSONObject jsonObject = new JSONObject(message);
        consumer.consumeMessage(jsonObject);

    }
}

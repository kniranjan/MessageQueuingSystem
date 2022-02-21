package producer;

import consumer.JSONConsumer;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import queue.NullMessageException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class JSONProducerTest {

    @Test
    public void produceMessageTest() throws NullMessageException, IOException {
        Producer p = new JSONProducer("test-producer-1");
        JSONObject jsonObject = (JSONObject) p.produceMessage();

        Path path = Paths.get("src/test/resources/json_message_1.json");
        JSONConsumer consumer = new JSONConsumer("test-consumer-1");
        StringBuilder sb = new StringBuilder();
        for(String line : Files.readAllLines(path)) {
            sb.append(line);
        }
        String expectedMessage = sb.toString();
        JSONObject expectedJson = new JSONObject(expectedMessage);
        Assert.assertTrue(jsonObject.toString().equals(expectedJson.toString()));


    }
}

package producer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import publishing.service.PublishingService;
import publishing.service.UnPublishedPublisherException;
import queue.InvalidMessageFormatException;
import queue.NullMessageException;
import queue.UnPublishedMessageException;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class JSONProducer implements Producer{

    private static final Logger logger = LogManager.getLogger(JSONProducer.class.getCanonicalName());
    String name;
    public JSONProducer(String name) {
        this.name = name;
    }
    @Override
    public JSONObject produceMessage() throws NullMessageException {
        Path path = Paths.get("src/main/resources/json_message_1.json");
        try {
            StringBuilder sb = new StringBuilder();
            for(String line : Files.readAllLines(path)) {
                sb.append(line);
            }
            String message = sb.toString();
            JSONObject jsonObject = new JSONObject(message);
            return jsonObject;

        } catch (IOException e) {
            logger.error("Exception while reading file ",e);
            throw new NullMessageException();
        }

    }

    @Override
    public void putMessage() throws UnPublishedPublisherException, UnPublishedMessageException, NullMessageException, InvalidMessageFormatException {
        JSONObject message = produceMessage();
        PublishingService.PUBLISHING_SERVICE.putMessage(name,message);
    }

    @Override
    public String getName() {
        return this.name;
    }
}

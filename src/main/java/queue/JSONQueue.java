package queue;

import org.json.JSONObject;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class JSONQueue implements Queue{

    private static final Logger logger = LogManager.getLogger(JSONQueue.class.getCanonicalName());
    BlockingQueue<JSONObject> queue;
    String name;

    public JSONQueue(String name) {
        queue = new LinkedBlockingQueue<>();
        this.name = name;
    }

    @Override
    public void addMessage(Object message) throws UnPublishedMessageException, InvalidMessageFormatException {
        try {
            JSONObject jsonMessage = (JSONObject)message;
            queue.put(jsonMessage);
            logger.info("Added message , queue size {}", queue.size());
        } catch (InterruptedException e) {
            logger.error("Interrupted while adding message ",e);
            throw new UnPublishedMessageException();
        } catch (ClassCastException ex) {
            logger.error("Message not a valid json",ex);
            throw new InvalidMessageFormatException();
        }


    }

    @Override
    public boolean equals(Object obj) {
        JSONQueue queue = (JSONQueue) obj;
        if(queue.getName().equals(this.name))
            return true;

        return false;
    }

    @Override
    public int hashCode() {
        return this.name.hashCode();
    }

    public String getName() {
        return this.name;
    }

    @Override
    public JSONObject getMessage() throws NullMessageException {
        try {
            return queue.take();
        }catch (InterruptedException e) {
            logger.error("Interrupted while adding message ",e);
        }
        throw new NullMessageException();
    }


}

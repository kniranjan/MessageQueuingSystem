package consumer;

import org.json.JSONObject;

import java.util.concurrent.atomic.AtomicInteger;

public class JSONConsumer implements Consumer{
    String name;
    AtomicInteger count = new AtomicInteger(0);
    public JSONConsumer(String name) {
        this.name = name;
    }
    @Override
    public void consumeMessage(Object message) {
        JSONObject jsonMessage = (JSONObject)message;
        System.out.println("consumer named "+name+" consumed given message "+jsonMessage.toString()+
                " messages consumed till now :"+count.incrementAndGet());


    }

    @Override
    public String getName() {
        return this.name;
    }

    public int countMessagesConsumed() {
        return count.get();
    }
}

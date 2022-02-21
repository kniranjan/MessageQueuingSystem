package consumer;

public interface Consumer {

    void consumeMessage(Object message);

    String getName();

    int countMessagesConsumed();
}

package queue;

public interface Queue {

    void addMessage(Object message) throws UnPublishedMessageException, InvalidMessageFormatException;

    Object getName();

    Object getMessage() throws NullMessageException;
}

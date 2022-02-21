package producer;

import publishing.service.UnPublishedPublisherException;
import queue.InvalidMessageFormatException;
import queue.NullMessageException;
import queue.UnPublishedMessageException;

public interface Producer {

    Object produceMessage() throws NullMessageException;

    void putMessage() throws UnPublishedPublisherException, UnPublishedMessageException, NullMessageException, InvalidMessageFormatException;

    String getName();
}

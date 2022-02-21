package notification.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import queue.NullMessageException;
import queue.Queue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import consumer.Consumer;
import queue.QueueAlreadyExistsException;

public enum NotificationService {

    NOTIFICATION_SERVICE;
    private static final Logger logger = LogManager.getLogger(NotificationService.class.getCanonicalName());
    HashMap<Queue, List<Consumer>> queueToConsumersMap;
    HashMap<String, Queue> queueNameMap;

    NotificationService() {
        queueToConsumersMap = new HashMap<>();
        queueNameMap = new HashMap<>();
    }

    public void subscribeToQueue(Queue queue, Consumer consumer) throws QueueNotExistsException {
        if(!queueToConsumersMap.containsKey(queue))
            throw new QueueNotExistsException();

        List consumers = queueToConsumersMap.get(queue);
        consumers.add(consumer);
    }

    public void deSubscribeFromQueue(Queue queue, Consumer consumer) throws QueueNotExistsException {
        if(!queueToConsumersMap.containsKey(queue))
            throw new QueueNotExistsException();

        List consumers = queueToConsumersMap.get(queue);
        consumers.remove(consumer);
    }

    public void addQueueForNotification(Queue queue) throws QueueAlreadyExistsException {
        if(queueToConsumersMap.containsKey(queue))
            throw new QueueAlreadyExistsException();

        queueToConsumersMap.put(queue, new ArrayList<>());
    }

    public synchronized void notifyConsumers(Queue queue) {
        if(!queueToConsumersMap.containsKey(queue)) {
            logger.error("Queue with name {} does not exist",queue.getName());
            return;
        }

        List<Consumer> consumers = queueToConsumersMap.get(queue);
        Object message = null;
        try {
            message = queue.getMessage();
            for(Consumer consumer: consumers) {
                consumer.consumeMessage(message);
            }
        } catch (NullMessageException e) {
            logger.error("Null message consumed from queue");
        }

    }


}

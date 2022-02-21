package publishing.service;

import java.util.HashMap;

import notification.service.NotificationService;
import queue.InvalidMessageFormatException;
import queue.Queue;
import queue.QueueAlreadyExistsException;
import queue.UnPublishedMessageException;

public enum PublishingService {

    PUBLISHING_SERVICE;

    HashMap<String, Queue> producerToQueueMapping;
    HashMap<Queue,String> queueToProducerMapping;


    PublishingService() {
        producerToQueueMapping = new HashMap<>();
        queueToProducerMapping = new HashMap<>();
    }

    public void putMessage(String producerName, Object message) throws UnPublishedPublisherException, UnPublishedMessageException, InvalidMessageFormatException {

        if(!producerToQueueMapping.containsKey(producerName))
            throw new UnPublishedPublisherException();

        Queue queue = producerToQueueMapping.get(producerName);
        queue.addMessage(message);
        NotificationService.NOTIFICATION_SERVICE.notifyConsumers(queue);
    }

    public synchronized void registerProducerAndQueue(String name, Queue queue) throws ProducerAlreadyExistsException, QueueAlreadyExistsException {
        if(producerToQueueMapping.containsKey(name))
            throw new ProducerAlreadyExistsException();

        if(queueToProducerMapping.containsKey(queue))
            throw new QueueAlreadyExistsException();

        producerToQueueMapping.put(name,queue);
        queueToProducerMapping.put(queue,name);

    }


}

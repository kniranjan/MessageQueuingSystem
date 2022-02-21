package notification.service;

import consumer.Consumer;
import consumer.JSONConsumer;
import org.junit.Assert;
import org.junit.Test;
import queue.JSONQueue;
import queue.Queue;
import queue.QueueAlreadyExistsException;

public class NotificationServiceTest {

    @Test(expected = QueueNotExistsException.class)
    public void subscribeToQueueTestUnregisteredQueue() throws QueueNotExistsException {
        Queue queue = new JSONQueue("test-queue-1");
        Consumer consumer = new JSONConsumer("test-json-consumer-1");
        NotificationService.NOTIFICATION_SERVICE.subscribeToQueue(queue,consumer);

    }

    @Test
    public void subscribeToQueueTest() throws QueueNotExistsException, QueueAlreadyExistsException {
        Queue queue = new JSONQueue("test-queue");
        Consumer consumer = new JSONConsumer("test-json-consumer");
        NotificationService.NOTIFICATION_SERVICE.addQueueForNotification(queue);
        NotificationService.NOTIFICATION_SERVICE.subscribeToQueue(queue,consumer);

    }

    @Test
    public void registerAlreadyRegisteredQueue() throws QueueAlreadyExistsException {
        Queue queue = new JSONQueue("test-queue-2");
        NotificationService.NOTIFICATION_SERVICE.addQueueForNotification(queue);
        try {
            NotificationService.NOTIFICATION_SERVICE.addQueueForNotification(queue);
        } catch (QueueAlreadyExistsException ex) {
            Assert.assertTrue(true);
            return;
        }
        Assert.assertTrue(false);
    }

}

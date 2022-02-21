import consumer.Consumer;
import consumer.JSONConsumer;
import notification.service.NotificationService;
import notification.service.QueueNotExistsException;
import producer.JSONProducer;
import producer.Producer;
import publishing.service.ProducerAlreadyExistsException;
import publishing.service.PublishingService;
import publishing.service.UnPublishedPublisherException;
import queue.*;

public class App {

    public static void main(String[] args) throws QueueAlreadyExistsException, ProducerAlreadyExistsException, QueueNotExistsException, NullMessageException, UnPublishedPublisherException, UnPublishedMessageException, InvalidMessageFormatException {
        Queue queue = new JSONQueue("jsonQueue1");
        Producer producer1 = new JSONProducer("producer1");
        PublishingService.PUBLISHING_SERVICE.registerProducerAndQueue(producer1.getName(),queue);
        NotificationService.NOTIFICATION_SERVICE.addQueueForNotification(queue);

        Consumer consumer1 = new JSONConsumer("consumer-1");
        NotificationService.NOTIFICATION_SERVICE.subscribeToQueue(queue,consumer1);

        producer1.putMessage();

        Consumer consumer2 = new JSONConsumer("consumer-2");
        NotificationService.NOTIFICATION_SERVICE.subscribeToQueue(queue,consumer2);

        producer1.putMessage();

        NotificationService.NOTIFICATION_SERVICE.deSubscribeFromQueue(queue,consumer2);

        producer1.putMessage();

    }
}

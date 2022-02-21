package publishing.service;

import consumer.Consumer;
import consumer.JSONConsumer;
import notification.service.NotificationService;
import notification.service.QueueNotExistsException;
import org.junit.Assert;
import org.junit.Test;
import producer.JSONProducer;
import producer.Producer;
import queue.*;

import java.util.concurrent.*;

public class PublishingServiceTest {

    @Test
    public void noMessageLossTest() throws QueueAlreadyExistsException, ProducerAlreadyExistsException, QueueNotExistsException, InterruptedException {
        Queue queue = new JSONQueue("jsonQueue1");
        Producer producer1 = new JSONProducer("producer1");
        PublishingService.PUBLISHING_SERVICE.registerProducerAndQueue(producer1.getName(),queue);
        NotificationService.NOTIFICATION_SERVICE.addQueueForNotification(queue);

        Consumer consumer1 = new JSONConsumer("consumer-1");
        NotificationService.NOTIFICATION_SERVICE.subscribeToQueue(queue,consumer1);

        int numThreads = 50;
        CountDownLatch latch = new CountDownLatch(numThreads);
        ExecutorService es = Executors.newFixedThreadPool(numThreads);


        for(int i=0; i<50;i++) {

            es.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        producer1.putMessage();
                        //System.out.println("added message "+Thread.currentThread().getName());
                        latch.countDown();
                    }
                    catch (NullMessageException e) {
                        e.printStackTrace();
                    } catch (UnPublishedMessageException e) {
                        e.printStackTrace();
                    } catch (InvalidMessageFormatException e) {
                        e.printStackTrace();
                    } catch (UnPublishedPublisherException e) {
                        e.printStackTrace();
                    }

                }
            });


        }

        latch.await();
        es.shutdown();
        Assert.assertEquals(numThreads,consumer1.countMessagesConsumed());
    }

    @Test(expected = UnPublishedPublisherException.class)
    public void testPutMessageUnPublishedProducer() throws UnPublishedPublisherException, UnPublishedMessageException, InvalidMessageFormatException {
        PublishingService.PUBLISHING_SERVICE.putMessage("test-producer-sample","message");
    }

    @Test
    public void registerProducerAndQueueTestExsitingQueue() throws QueueAlreadyExistsException, ProducerAlreadyExistsException {
        Queue queue = new JSONQueue("test-publishing-service-queue-1");
        PublishingService.PUBLISHING_SERVICE.registerProducerAndQueue("test-publishing-service-producer-1",queue);

        try{
            PublishingService.PUBLISHING_SERVICE.registerProducerAndQueue("test-publishing-service-producer-2",queue);
        } catch (QueueAlreadyExistsException ex) {
            Assert.assertTrue(true);
            return;
        }
        Assert.assertTrue(false);
    }

    @Test
    public void registerProducerAndQueueTestDuplicateProducer() throws QueueAlreadyExistsException, ProducerAlreadyExistsException {
        Queue queue = new JSONQueue("test-publishing-service-queue-2");
        PublishingService.PUBLISHING_SERVICE.registerProducerAndQueue("test-publishing-service-producer-3",queue);

        try{
            PublishingService.PUBLISHING_SERVICE.registerProducerAndQueue("test-publishing-service-producer-3",new JSONQueue("queue-2"));
        } catch (ProducerAlreadyExistsException ex) {
            Assert.assertTrue(true);
            return;
        }
        Assert.assertTrue(false);
    }
}

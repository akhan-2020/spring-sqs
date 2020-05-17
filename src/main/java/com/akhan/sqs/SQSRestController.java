package com.akhan.sqs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.cloud.aws.messaging.listener.SqsMessageDeletionPolicy;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class SQSRestController {

    private static final Logger logger = LoggerFactory.getLogger(SQSRestController.class);

    /* AWS Fifo Queue */
    private static final String SQS_QUEUE = "testqueue.fifo";

    @Autowired
    QueueMessagingTemplate queueMessagingTemplate;


    @PostMapping("/customer")
    public String index(@RequestBody Customer customer) {
        send(customer);
        return "Message was sent succesfully}";
    }

    /**
     * writes customer message into FIFO Queue
     *
     * @param customer
     */
    public void send(Customer customer) {
        /* de-duplication ID ensures no duplicates will be added to the FIFO queue */
        Map<String, Object> headers = new HashMap<>();
        headers.put("message-group-id", "TEST_GROUP");
        headers.put("message-deduplication-id", customer.getCustomerId());
        queueMessagingTemplate.convertAndSend(SQS_QUEUE, customer, headers);
    }


    /**
     * listener receives customer message and auto deletes on successful message completion,
     * throwing an exception will keep the message in the queue
     *
     * @param customer
     */
    @SqsListener(value = SQS_QUEUE, deletionPolicy = SqsMessageDeletionPolicy.ON_SUCCESS)
    public void receivePerson(Customer customer) {
        logger.info("Received customer : {}, having MessageID: {}", customer.getName(), customer.getCustomerId());
    }

}

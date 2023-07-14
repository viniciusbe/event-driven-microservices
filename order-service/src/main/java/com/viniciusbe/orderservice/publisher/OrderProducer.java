package com.viniciusbe.orderservice.publisher;


import com.viniciusbe.orderservice.dto.OrderEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class OrderProducer {

    private Logger LOGGER = LoggerFactory.getLogger(OrderProducer.class);

    @Value("${rabbitmq.exchange.name}")
    private String exchangeName;

    @Value("${rabbitmq.order.routing.key}")
    private String orderRoutingKey;

    @Value("${rabbitmq.email.routing.key}")
    private String emailRoutingKey;

    private RabbitTemplate rabbitTemplate;

    public OrderProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMessage(OrderEvent orderEvent) {
        LOGGER.info(String.format("Order event send -> %s", orderEvent.toString()));
        rabbitTemplate.convertAndSend(exchangeName, orderRoutingKey, orderEvent);
        rabbitTemplate.convertAndSend(exchangeName, emailRoutingKey, orderEvent);
    }
}

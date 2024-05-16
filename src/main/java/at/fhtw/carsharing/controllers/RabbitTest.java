package at.fhtw.carsharing.controllers;

import java.nio.charset.StandardCharsets;
import com.rabbitmq.client.DeliverCallback;

public class RabbitTest {
    public static void main(String[] args) {
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String (delivery.getBody(), StandardCharsets.UTF_8);
            System.out.println("blala" + message);
        };



    }


}

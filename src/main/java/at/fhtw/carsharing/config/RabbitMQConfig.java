package at.fhtw.carsharing.config;

import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.RabbitListenerContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String UPDATE_STATUS_QUEUE_NAME = "UpdateStatus";
    public static final String EMERGENCY_QUEUE_NAME = "Emergency";
    public static final String CREATE_INVOICE_QUEUE_NAME = "CreateInvoice";
    public static final String IN_QUEUE_NAME = "InQueue";
    public static final String EXCHANGE ="";
    public static final String VEHICLE_ID = "VehicleID";
    public static final String USER_ID = "UserID";


    @Bean
    public Queue updateStatusQueue() {
        return new Queue(UPDATE_STATUS_QUEUE_NAME, false);
    }

    @Bean
    public Queue emergencyQueue() {return new Queue(EMERGENCY_QUEUE_NAME, false);
    }

    @Bean Queue createInvoiceQueue() {
        return new Queue(CREATE_INVOICE_QUEUE_NAME, false);
    }


    @Bean
    public CachingConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory("localhost");
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        return connectionFactory;
    }

    @Bean
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory());
        rabbitTemplate.setDefaultReceiveQueue(IN_QUEUE_NAME);
        return rabbitTemplate;

    }



}

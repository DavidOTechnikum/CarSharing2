package at.fhtw.carsharing.controllers;

import at.fhtw.carsharing.config.RabbitMQConfig;
import at.fhtw.carsharing.persistence.entity.User;
import at.fhtw.carsharing.persistence.entity.UsernameToken;
import at.fhtw.carsharing.persistence.repository.UserList;
import at.fhtw.carsharing.security.WebSecurityConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping("/api/invoices")
public class InvoicesController {
    List<User> userList = UserList.getUserList();
    RabbitMQConfig config = new RabbitMQConfig();
    RabbitTemplate rabbitTemplate = config.rabbitTemplate();
    final AtomicInteger messageCount = new AtomicInteger();

    @PostMapping("/{user-id}")
public HttpStatus createInvoice(@PathVariable("user-id") int id,
                                @RequestBody UsernameToken usernameToken) {

        int count = 0;
        for (User u : userList) {
            if (u.getId() == id) {
                count++;
            }
        }
        if (count > 0 && WebSecurityConfig.TokenVerifierManager(usernameToken.getJwtToken(), usernameToken.getUsername())){
            rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE, RabbitMQConfig.CREATE_INVOICE_QUEUE_NAME, id ,message1 -> {
                message1.getMessageProperties().getHeaders().put(RabbitMQConfig.MESSAGE_COUNT_PROPERTY_NAME, messageCount.get());
                return message1;
            });

            return HttpStatus.OK;
        }
        return HttpStatus.FORBIDDEN;
    }
}

package at.fhtw.carsharing.controllers;

import at.fhtw.carsharing.config.RabbitMQConfig;
import at.fhtw.carsharing.persistence.entity.Emergency;
import at.fhtw.carsharing.persistence.entity.Vehicle;
import at.fhtw.carsharing.persistence.entity.VehicleStatus;
import at.fhtw.carsharing.persistence.repository.VehicleList;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping("/api/devices")
public class VehicleStatusController {

    List<Vehicle> vehicleList = VehicleList.getVehicleList();
    final String vehicleToken = "t0k3n";
    RabbitMQConfig config = new RabbitMQConfig();
    RabbitTemplate rabbitTemplate = config.rabbitTemplate();
    final AtomicInteger messageCount = new AtomicInteger();
    ObjectMapper objectMapper = new ObjectMapper();

    @PostMapping("/{vehicle-id}/status")
    public HttpStatus statusUpdate(@PathVariable("vehicle-id") int id,
                                   @RequestHeader String token,
                                   @RequestBody VehicleStatus vehicleStatus) throws JsonProcessingException {
        String statusMessage = objectMapper.writeValueAsString(vehicleStatus);


        if (token.equals(vehicleToken)) {
            rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE, RabbitMQConfig.UPDATE_STATUS_QUEUE_NAME, statusMessage, message -> {
                message.getMessageProperties().getHeaders();
                return message;
            });

            return HttpStatus.OK;
        } else
        return HttpStatus.FORBIDDEN;
    }

    @PostMapping("/{vehicle-id}/alarm")
    public HttpStatus emergencyUpdate(@PathVariable("vehicle-id") int id,
                                      @RequestHeader String token,
                                      @RequestBody Emergency emergency) throws JsonProcessingException {

        String emergencyMessage = objectMapper.writeValueAsString(emergency);
        if (token.equals(vehicleToken)) {
            rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE, RabbitMQConfig.EMERGENCY_QUEUE_NAME, emergencyMessage, message -> {
                message.getMessageProperties().getHeaders();
                return message;
            });
        }

        return HttpStatus.OK;
    }






}

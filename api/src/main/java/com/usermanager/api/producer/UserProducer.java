package com.usermanager.api.producer;

import com.usermanager.api.domain.User;
import com.usermanager.api.dto.EmailDTO;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class UserProducer {
    @Autowired
    RabbitTemplate rabbitTemplate;
    @Value("${broker.queue.email.name}")
    private String routingKey;

    public void publishRegistrationEmail(User user){
        EmailDTO emailDTO = new EmailDTO(user.getId(),
                user.getEmail(), "User registered successfully", "Thanks for your registration!");

        rabbitTemplate.convertAndSend("", routingKey, emailDTO);
    }
}
package com.flipkart.notification_service.Service;


import com.flipkart.notification_service.Dto.OrderCreatedEvent;
import com.flipkart.notification_service.Repository.OrderEventRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationService {
    private final JavaMailSender javaMailSender;



    public void sendOrderCreatedNotification(OrderCreatedEvent event){

        String message = """
        =========================================
        Order Created Notification
        =========================================

        Dear %s,

        Your order with orderNumber: %s has been created successfully.


        Thanks,
        BookStore Team
        =========================================
        """
                .formatted(
                        event.getCustomer().getName(),
                        event.getOrderNumber()
                );


        log.info("message-{}",message);
        send(event.getCustomer().getEmail(),"Order Created Notification ",message);


    }

    public void sendOrderDeliveredNotification(OrderCreatedEvent event){

        String message = """
        =========================================
        Order Delivered Notification
        =========================================

        Dear %s,

        Your order with orderNumber: %s has been delivered successfully.


        Thanks,
        BookStore Team
        =========================================
        """
                .formatted(
                        event.getCustomer().getName(),
                        event.getOrderNumber()
                );


        log.info("message-{}",message);
        send(event.getCustomer().getEmail(),"Order Delivered Notification",message);


    }

    public void send(String to, String subject, String message) {

        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setTo(to);
        mailMessage.setSubject(subject);
        mailMessage.setText(message);

        javaMailSender.send(mailMessage);

        log.info("Email sent successfully to {}", to);
    }
}

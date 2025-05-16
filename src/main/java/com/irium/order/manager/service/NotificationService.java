package com.irium.order.manager.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

  private static final Logger logger = LoggerFactory.getLogger(NotificationService.class);

  private final JavaMailSender mailSender;

  public NotificationService(JavaMailSender mailSender) {
	this.mailSender = mailSender;
  }

  @Async
  public void sendEmail(String email, Long orderId) {
	try {
	  SimpleMailMessage message = new SimpleMailMessage();
	  message.setTo(email);
	  message.setSubject("Order Completed!");
	  message.setText("Your order #" + orderId + " has been completed.");
	  mailSender.send(message);
	  logger.info("Order email was sent to {}", email);
	} catch (Exception exception) {
	  logger.info("Error sending email to {} : {}", email, exception.getMessage(), exception);
	}
  }

}

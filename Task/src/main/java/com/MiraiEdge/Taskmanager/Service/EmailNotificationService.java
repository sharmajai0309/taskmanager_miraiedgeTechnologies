package com.MiraiEdge.Taskmanager.Service;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.MiraiEdge.Taskmanager.model.EmailTask;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailNotificationService {
    private final JavaMailSender mailSender;
    private final RedisTemplate<String, EmailTask> redisTemplate;
    private static final String EMAIL_QUEUE = "email_queue";

    public void queueEmail(String to, String subject, String body) {
        EmailTask emailTask = new EmailTask(to, subject, body);
        redisTemplate.opsForList().rightPush(EMAIL_QUEUE, emailTask);
    }

    @Scheduled(fixedDelay = 5000) // Process queue every 5 seconds
    public void processEmailQueue() {
        EmailTask task = redisTemplate.opsForList().leftPop(EMAIL_QUEUE);
        if (task != null) {
            sendEmail(task);
        }
    }

    
    private void sendEmail(EmailTask task) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(task.getTo());
            message.setSubject(task.getSubject());
            message.setText(task.getBody());
            mailSender.send(message);
            log.info("Email sent to: {}", task.getTo());
        } catch (MailException e) {
            log.error("Failed to send email to {}: {}", task.getTo(), e.getMessage());
            // Requeue the failed task
            redisTemplate.opsForList().rightPush(EMAIL_QUEUE, task);
        }
    }

    
}

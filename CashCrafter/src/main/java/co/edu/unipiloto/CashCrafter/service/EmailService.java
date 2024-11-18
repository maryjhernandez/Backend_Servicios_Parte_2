package co.edu.unipiloto.CashCrafter.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    @Autowired
    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void enviarEmailVerificacion(String destinatario, String token) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(destinatario);
        message.setSubject("Verifica tu cuenta de CashCrafter");
        message.setText("Para verificar tu cuenta, haz clic en el siguiente enlace: " +
                       "http://localhost:8080/api/usuarios/verificar?token=" + token);
        
        mailSender.send(message);
    }
}
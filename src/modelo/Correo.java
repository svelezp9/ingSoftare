
package modelo;

import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.JOptionPane;
import static vista.login.frmReg;
import modelo.usuarios;

public class Correo extends Conexion {
    
    public boolean enviar(usuarios usr) {
        
        try {
            Properties props = new Properties();
            props.setProperty("mail.smtp.host", "smtp.gmail.com");
            props.setProperty("mail.smtp.starttls.enable", "true");
            props.setProperty("mail.smtp.port", "587");
            props.setProperty("mail.smtp.auth", "true");
            
            Session session = Session.getDefaultInstance(props);
            String correoRemitente = "paradoxengineapp@gmail.com";
            String passwordRemitente = "A1B2C3D4E5-";
            String correoReceptor = usr.getCorreo();
            //String correoReceptor = frmReg.txtCorreo.getText();
            
            String asunto = "Bienvenido a Paradox Engine APP";
            String mensaje = "Te damos la bienvenida a Paradox Engine APP, una aplicación para guardar tus libros.";
            
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(correoRemitente));
            
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(correoReceptor));
            message.setSubject(asunto);
            message.setText(mensaje);
            
            Transport t = session.getTransport("smtp");
            t.connect(correoRemitente, passwordRemitente);
            t.sendMessage(message, message.getRecipients(Message.RecipientType.TO));
            t.close();
            
            JOptionPane.showMessageDialog(null, "Correo electrónico enviado.");
            
            return true;
        } catch (AddressException ex) {
            Logger.getLogger(Correo.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } catch (MessagingException ex) {
            Logger.getLogger(Correo.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        
    } 
    public boolean solicitud(usuarios usrP, usuarios usrR, String titulo) {
        
        try {
            Properties props = new Properties();
            props.setProperty("mail.smtp.host", "smtp.gmail.com");
            props.setProperty("mail.smtp.starttls.enable", "true");
            props.setProperty("mail.smtp.port", "587");
            props.setProperty("mail.smtp.auth", "true");
            
            Session session = Session.getDefaultInstance(props);
            String correoRemitente = "paradoxengineapp@gmail.com";
            String passwordRemitente = "A1B2C3D4E5-";
            String correoReceptor = usrP.getCorreo();
            //String correoReceptor = frmReg.txtCorreo.getText();
            
            String asunto = "Solicitud de préstamo - Paradox Engine APP";
            String nombre = usrR.getNombre();
            String correo = usrR.getCorreo();
            String mensaje = "El usuario "+nombre+" te envía una solicitud de préstamo para el libro: "+titulo;
            mensaje = mensaje+".\nSi deseas prestar el libro te podrás comunicar con el usuario al siguiente correo: " +correo;
            mensaje = mensaje+"\n\n\n Gracias por utilizar Paradox Engine APP";
            
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(correoRemitente));
            
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(correoReceptor));
            message.setSubject(asunto);
            message.setText(mensaje);
            
            Transport t = session.getTransport("smtp");
            t.connect(correoRemitente, passwordRemitente);
            t.sendMessage(message, message.getRecipients(Message.RecipientType.TO));
            t.close();
            JOptionPane.showMessageDialog(null, "Solicitud de préstamo enviada.");
            
            return true;
        } catch (AddressException ex) {
            Logger.getLogger(Correo.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } catch (MessagingException ex) {
            Logger.getLogger(Correo.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        
    }
    
}

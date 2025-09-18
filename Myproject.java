

import java.util.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.io.UnsupportedEncodingException;

import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

public class Myproject {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to CA Onboarding");

        System.out.print("Enter your full name: ");
        String name = scanner.nextLine();

        System.out.print("Enter your email: ");
        String email = scanner.nextLine();

        System.out.print("Enter your college: ");
        String college = scanner.nextLine();

        String utmLink = generateUTMLink(name, email);

        // Send email
        boolean mailStatus = sendEmail(email, name, utmLink);

        if (mailStatus) {
            System.out.println("\n Onboarding Successful!");
            System.out.println("UTM Link sent to: " + email);
        } else {
            System.out.println("\n Failed to send email.");
        }

        scanner.close();
    }

    public static String generateUTMLink(String name, String email) {
        String baseURL = "https://www.industryacademiacommunity.org/newjoinee";
        try {
            String encodedName = URLEncoder.encode(name.trim(), StandardCharsets.UTF_8.toString());
            String encodedEmail = URLEncoder.encode(email.trim(), StandardCharsets.UTF_8.toString());
            return baseURL + "?utm_source=CA_Onboarding&utm_name=" + encodedName + "&utm_email=" + encodedEmail;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return baseURL;
        }
    }

    public static boolean sendEmail(String to, String name, String utmLink) {
        final String from = "arpitaamarbhat@gmail.com"; // Your Gmail
        final String password = "lzzffkjljbvlppfo"; // App password (not Gmail password)


        // Gmail SMTP setup
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(
                    Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject("Welcome to IAC, " + name + "!");

            String msg = "Hello " + name + ",\n\nWelcome to the CA Program!\nHere is your UTM link:\n" + utmLink + "\n\nBest of luck!";

            message.setText(msg);
            Transport.send(message);
            return true;

        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }
    }
}

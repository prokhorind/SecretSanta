package com.telegram.bot;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by kleba on 06.11.2017.
 */

public class Sender {

    private Properties props;
    private Properties accountProps;


    public Sender(){}

    public boolean readProperties(){


        props = new Properties();
        accountProps = new Properties();

        try {

            InputStream is=getClass().getClassLoader().getResourceAsStream("message.properties");
            InputStream isAccount=getClass().getClassLoader().getResourceAsStream("emailAccount.properties");

            props.load(is);
            accountProps.load(isAccount);

            return  true;


        } catch (IOException e) {
            System.err.println("ОШИБКА: Файл свойств отсуствует!");
            return  false;
        }



    }


    public Session getPasswordAuthentication(){
        Session session=null;
        try {
            session= Session.getInstance(props, new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {

                    return new PasswordAuthentication(accountProps.getProperty("username"), accountProps.getProperty("password"));
                }

            });
        }catch (NullPointerException e){

        }
        return session;

    }


    public boolean send(String subject, String text,String to){


        try {


            Message message = new MimeMessage(getPasswordAuthentication());

            //кому
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            //Заголовок письма
            message.setSubject(subject);
            //Содержимое
            message.setText(text);
            //Отправляем сообщение
            Transport.send(message);
            return true;

        }
        catch (NullPointerException e){
            return false;
        }
        catch (MessagingException e ) {


            return false;

        }
    }
}
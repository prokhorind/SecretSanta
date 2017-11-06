package com.telegram.bot;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MyBot extends TelegramLongPollingBot {


    public List<Person>  personList = new ArrayList<>();

    @Override
    public String getBotUsername() {
        // Return bot username
        // If bot username is @MyBot, it must return 'MyBot'
        return "try1111_bot";
    }

    @Override
    public String getBotToken() {
        // Return bot token from BotFather
        return "462663994:AAFNCamGl7T3rs4qhS5-cTh8Rg5QZu93FuI";
    }


    @Override
    public void onUpdateReceived(Update update) {

        // We check if the update has a message and the message has text
        if (update.hasMessage() && update.getMessage().hasText()) {
            // Set variables
            String message_text = update.getMessage().getText();
            long chat_id = update.getMessage().getChatId();


            createMemberList(message_text,update,chat_id);
          /*  SendMessage message = new SendMessage() // Create a message object object
                    .setChatId(chat_id)
                    .setText(message_text);
            try {
                execute(message); // Sending our message object to user
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }*/
        }
    }

    public List<Person> createMemberList(String message,Update update, long chat_id){

            String[] words=  message.split(" ");
            String url= words[0];

            switch(url){

                case "/reg":{

                        String email=words[1];

                        String firstName= update.getMessage().getFrom().getFirstName();
                        String lastName=update.getMessage().getFrom().getLastName();
                        System.out.println("email="+email);
                        System.out.println("firstname="+firstName);
                        System.out.println("lastname="+lastName);
                        Person person= new Person(firstName,lastName,email);
                        personList.add(person);
                        break;
                }

                        case "/send":{


                            if(personList.size()==0) {
                                SendMessage mg = new SendMessage() // Create a message object object
                                    .setChatId(chat_id)
                                    .setText("/reg first");
                             try {
                                    execute(mg); // Sending our message object to user
                             } catch (TelegramApiException e) {
                                    e.printStackTrace();
                             }
                            }
                            else {
                                 sort();
                                sendMessage();
                                personList.clear();
                            }
                        }

            }


    return personList;
    }

    public void sort(){
        Collections.shuffle(personList);



    }

    public void sendMessage(){
        Sender sender= new Sender();
        sender.readProperties();
        sender.getPasswordAuthentication();

        String subject="Secret Santa";

        int lastElement=personList.size()-1;
        for(int i=0;i<personList.size();i++){

            if(i==lastElement){

               String message= (personList.get(i).firstName+ " "+personList.get(i).lastName  +" u are secret Santa for "+personList.get(0).firstName+ " "+personList.get(0).lastName);
                sender.send(subject,message,personList.get(i).email);
            }
            else {
                String message=  (personList.get(i).firstName + " "+personList.get(i).lastName + " u are secret Santa for " + personList.get(i + 1).firstName + " "+personList.get(i+1).lastName);
                sender.send(subject,message,personList.get(i).email);
            }

        }

    }

    public void cleanList(){

        personList.clear();
    }
}
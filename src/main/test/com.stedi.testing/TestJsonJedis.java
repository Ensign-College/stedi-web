package com.stedi.testing;

import com.getsimplex.steptimer.datarepository.GenericRepository;
import com.getsimplex.steptimer.model.Customer;
import com.getsimplex.steptimer.model.EmailMessage;
import com.getsimplex.steptimer.utils.JedisClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class TestJsonJedis {

    public static void main (String[] args) throws Exception{

        GenericRepository messageRepository = new GenericRepository<EmailMessage>();
        //JedisClient jedisClient = new JedisClient();

        Customer customer = new Customer();
        customer.setCustomerName("John Smith");
        customer.setPhone("8015551212");
        customer.setEmail("johnsmith@gmail.com");
        customer.setGender("male");
        customer.setBirthDay("01/01/2001");

        EmailMessage emailMessage = new EmailMessage();

        emailMessage.setMessageText("Hi");
        emailMessage.setName("John Doe");
        emailMessage.setSubject("Test");
        emailMessage.setToAddress("johndoe@gmail.com");

        //jedisClient.jsonArrayAdd("TestCustomersArray","$" ,customer);

        messageRepository.addToArrayAtKey(emailMessage.getToAddress(),emailMessage);//we a

        ArrayList<EmailMessage> emailsToJohn = messageRepository.getArrayAtKey("johndoe@gmail.com");

//        for(Object object:jsonArray){
//            JSONObject jsonObject = (JSONObject) object;
//            System.out.println(jsonObject.toString());
//        }

        emailsToJohn.forEach((EmailMessage message)->System.out.println(emailMessage.getMessageText()));
    }
}

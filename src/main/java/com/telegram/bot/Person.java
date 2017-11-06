package com.telegram.bot;

/**
 * Created by kleba on 06.11.2017.
 */
public class Person {
    String firstName;
    String lastName;
    String email;


    Person(String firstName,String lastName,  String email){

      this.firstName=firstName;
        this.lastName=lastName;
        this.email=email;
    }



    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }


    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLastName() {
        return lastName;
    }
}

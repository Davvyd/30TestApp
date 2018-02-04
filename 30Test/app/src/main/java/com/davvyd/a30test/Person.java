package com.davvyd.a30test;

import org.json.simple.*;

/**
 * Created by Admin on 04-Feb-18.
 */

public class Person {
    private String firstName;
    private String surname;
    private String gender;
    private int id;
    private int age;
    private int[] friends;

    public Person(String firstName, String surname, String gender, int id, int age, int[] friends) {
        this.firstName = firstName;
        this.surname = surname;
        this.gender = gender;
        this.id = id;
        this.age = age;
        this.friends = friends;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSurname() {
        return surname;
    }

    public String getGender() {
        return gender;
    }

    public int getAge() {
        return age;
    }

    public int[] getFriends() {
        return friends;
    }

    public int getId(){
        return id;
    }

    public static Person jsonToPerson(JSONObject object){
        System.out.println(object.toString());

            String firstName = (String) object.get("firstName");
            String surname = (String) object.get("surname");
            String gender = (String) object.get("gender");
            int id = (int) (long) object.get("id");

            Object ageObject = object.get("age");
            int age = (int) (long) (ageObject != null ? ageObject : 0L);

            JSONArray arr = (JSONArray) object.get("friends");
            int[] friends = new int[arr.size()];

            for (int i = 0; i < friends.length; i++)
                friends[i] = (int)(long) arr.get(i);

            return new Person(firstName,surname,gender,id,age,friends);

        /*}catch (JSONException e){
            e.printStackTrace();
        }*/
    }


    @Override
    public String toString() {
        return id+") "+firstName+" "+surname+" ("+gender+") age: "+age;
    }
}

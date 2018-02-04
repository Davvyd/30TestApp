package com.davvyd.a30test;

import android.app.Application;
import android.content.Context;

import org.json.simple.*;
import org.json.simple.parser.*;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by Admin on 04-Feb-18.
 */

public class SocialNetwork {
    private final String fileName = "data.json";

    private LinkedList<Person> group = new LinkedList<>();

    private static Context appContext=null;
    public static SocialNetwork instance=null;

    private SocialNetwork(){
        JSONParser parser=new JSONParser();
        try {
            JSONArray json = (JSONArray) parser.parse(readJsonStream());
            for (Object o : json) {
                //System.out.println(o.toString());
                group.add(Person.jsonToPerson((JSONObject) o));
            }
        }catch (ParseException e) {
            e.printStackTrace();
        }
    }
    public static SocialNetwork getInstance(){
        if(instance!=null)
            return instance;

        instance = new SocialNetwork();
        return instance;
    }

    public static void setAppContext(Context con){
        appContext=con;
    }
/*
    public static void print(){
        SocialNetwork.getInstance().group.forEach(System.out::println);
    }*/
    public String readJsonStream(){

        InputStreamReader stream = new InputStreamReader(appContext.getResources().openRawResource(R.raw.data));
        BufferedReader reader = new BufferedReader(stream);
        String jsonString="";
        String line="";
        try {
            while((line=reader.readLine())!=null){
                jsonString+=line+"\n";
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonString;
    }

    public Person[] getPeople(){
        Person[] people = new Person[group.size()];
        int cout=0;
        for (Person p: group)
            people[cout++]=p;
        return people;
    }
    public Person getPerson(int id){
        return group.get(id-1);
    }
    public Respond personInfo(int id){
        if(group.size()<id || id<0)
            return null;

        Person selected = group.get(id-1);
        int[] friends = selected.getFriends();
        ArrayList<Integer> friendsOfFriends = new ArrayList<>();
        Set<Integer> suggested = new TreeSet<>();

        //iterating friends
        for(int f:friends) {
            Person friend = group.get(f - 1);
            //iter through friends of friend
            //if is he already in list that means we have 2 links to that person so we add him to suggestion list
            for (int ff : friend.getFriends())
                if(friendsOfFriends.contains(ff))

                    suggested.add(ff);
                else
                    friendsOfFriends.add(ff);
        }

        //removing collision
        friendsOfFriends.remove(new Integer(id));
        suggested.remove(new Integer(id));

        for(int fr:friends) {
            friendsOfFriends.remove(new Integer(fr));
            suggested.remove(fr);
        }

        //Preparing data
        Collections.sort(friendsOfFriends);
        int[] fof = new int[friendsOfFriends.size()];
        int cout=0;
        for (int i:friendsOfFriends)
            fof[cout++]=i;

        int[] sug = new int[suggested.size()];
        cout=0;
        for(int i:suggested)
            sug[cout++]=i;

        return new Respond(id,friends,fof,sug);
    }
    public class Respond{
        public int person;
        public int[] friends;
        public int[] friendOfFriends;
        public int[] suggested;

        public Respond(int person, int[] friends, int[] friendOfFriends, int[] suggested) {
            this.person = person;
            this.friends = friends;
            this.friendOfFriends = friendOfFriends;
            this.suggested = suggested;
        }

        @Override
        public String toString() {
            return person+") friends "+ Arrays.toString(friends) +" "+" fof "+
                    Arrays.toString(friendOfFriends)+" suggested: "+Arrays.toString(suggested);
        }
    }
}

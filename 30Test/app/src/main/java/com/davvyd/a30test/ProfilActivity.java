package com.davvyd.a30test;

import android.graphics.drawable.GradientDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.Arrays;

public class ProfilActivity extends AppCompatActivity {
    Person selected;
    Person[] friends;
    Person[] friendsOf;
    Person[] suggested;

    RecyclerView rvFriends;
    RecyclerView rvFriendsOf;
    RecyclerView rvSuggested;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.profil_layout);
        getProfilInfo();
        init();


    }
    //initialize main viewGroup with selected person
    //and all RecyclerView with corresponding data
    void init(){
        ViewGroup root =(ViewGroup)findViewById(R.id.selected);
        View view = LayoutInflater.from(this).inflate(R.layout.person_layout,root,true);

        ((TextView)view.findViewById(R.id.name)).setText(selected.getFirstName());
        ((TextView)view.findViewById(R.id.surname)).setText(selected.getSurname());
        ((TextView)view.findViewById(R.id.age)).setText(""+selected.getAge());
        ((TextView)view.findViewById(R.id.id)).setText(""+selected.getId());
        if(selected.getGender().equals("male"))
            view.setBackgroundColor(PersonAdapter.maleColor);
        else
            view.setBackgroundColor(PersonAdapter.femaleColor);

        rvFriends=(RecyclerView)findViewById(R.id.rv_friends);
        rvFriendsOf=(RecyclerView)findViewById(R.id.rv_friendsOf);
        rvSuggested=(RecyclerView)findViewById(R.id.rv_suggested);

        rvFriends.setHasFixedSize(true);
        rvFriendsOf.setHasFixedSize(true);
        rvSuggested.setHasFixedSize(true);

        rvFriends.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        rvFriendsOf.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        rvSuggested.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));

        rvFriends.setAdapter(new PersonAdapter(this,R.layout.person_vertical_layout,friends));
        rvFriendsOf.setAdapter(new PersonAdapter(this,R.layout.person_vertical_layout,friendsOf));
        rvSuggested.setAdapter(new PersonAdapter(this,R.layout.person_vertical_layout,suggested));


    }
    //get all profile data for selected Person
    void getProfilInfo(){
        int selectedId = getIntent().getIntExtra("SelectedId",0);

        SocialNetwork network = SocialNetwork.getInstance();
        SocialNetwork.Respond respond = network.personInfo(selectedId);
        selected = network.getPerson(selectedId);

        friends = new Person[respond.friends.length];
        for(int i =0;i<respond.friends.length;i++)
            friends[i]=network.getPerson(respond.friends[i]);

        friendsOf =  new Person[respond.friendOfFriends.length];
        for(int i =0;i<respond.friendOfFriends.length;i++)
            friendsOf[i]=network.getPerson(respond.friendOfFriends[i]);

        suggested = new Person[respond.suggested.length];
        for(int i =0;i<respond.suggested.length;i++)
            suggested[i]=network.getPerson(respond.suggested[i]);
        System.out.println(Arrays.toString(suggested));

    }
}

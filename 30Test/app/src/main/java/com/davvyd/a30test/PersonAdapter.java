package com.davvyd.a30test;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.zip.Inflater;

/**
 * Created by Admin on 04-Feb-18.
 */

public class PersonAdapter extends RecyclerView.Adapter<PersonAdapter.ViewHolder> {
    private Person[] people;
    private Context appContext;
    private int layout;

    public PersonAdapter(Context context,int Layout,Person[] source){
        appContext=context;
        people = source;
        layout=Layout;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layout,parent,false);
        return new ViewHolder(view);
    }
    public static int maleColor =Color.argb(255,66, 134, 244);
    public static int femaleColor = Color.argb(255,255, 91, 143);
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Person p = people[position];
        holder.name.setText(p.getFirstName());
        holder.surname.setText(p.getSurname());
        holder.age.setText(""+p.getAge());
        holder.id.setText(""+p.getId());
        if(p.getGender().equals("male"))
            holder.parent.setBackgroundColor(maleColor);
        else
            holder.parent.setBackgroundColor(femaleColor);

    }

    @Override
    public int getItemCount() {
        return people.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView name;
        public TextView surname;
        public TextView age;
        public TextView id;
        public ConstraintLayout parent;
        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView)itemView.findViewById(R.id.name);
            surname = (TextView)itemView.findViewById(R.id.surname);
            age = (TextView)itemView.findViewById(R.id.age);
            id = (TextView)itemView.findViewById(R.id.id);
            parent=(ConstraintLayout) itemView;
            parent.setOnClickListener(this);

            if(layout==R.layout.person_layout){
                name.setTextSize(32);
                surname.setTextSize(32);
            }
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(appContext,ProfilActivity.class);
            intent.putExtra("SelectedId",Integer.parseInt(""+id.getText()));
            //if we are using vertical lay out that means we are already on profil activity and
            // we want to close old profil acitvity
            if(layout==R.layout.person_vertical_layout)
                ((ProfilActivity)appContext).finish();
            appContext.startActivity(intent);
        }
    }
}

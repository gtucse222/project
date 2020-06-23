package com.example.a222latest;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeSet;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ProfileHolder> implements Filterable {
    private boolean group;
    // private TreeSet<UserC> names;
    //private TreeSet<UserC> namesAll;

    private RedBlackTree<UserC> names;
    private RedBlackTree<UserC> namesAll;
    //private Iterator<UserC> iterForName;
    private Iterator<BinaryTree.Node> iterForName;
    private OnNoteListener mOnNoteListener;
    private ArrayList<String> emailss=new ArrayList<>();
    Context context;

    // public ContactAdapter(TreeSet<UserC> names,boolean group,OnNoteListener onNoteListener)

    public ContactAdapter(RedBlackTree<UserC> names,boolean group,OnNoteListener onNoteListener) {

        this.names = names;
        this.mOnNoteListener=onNoteListener;
        this.group=group;
        //this.namesAll=new TreeSet<>();
        this.namesAll=new RedBlackTree<>();
        iterForName=names.iterator();
        while (true){
            if (!iterForName.hasNext())break;
                //else namesAll.add(iterForName.next());
            else namesAll.add((UserC) iterForName.next().data);
        }
        iterForName=names.iterator();

        
    }

    @NonNull
    @Override
    public ProfileHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater =LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.rehber_row,parent,false);
        return new ProfileHolder(view,mOnNoteListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ProfileHolder holder, int position) {
        if(iterForName.hasNext()){

            // UserC temp=iterForName.next();
            UserC temp= (UserC) iterForName.next().data;
            final String b=temp.geteMail();
            final String a=temp.getName();
            System.out.println(a+"  "+b);
            holder.name.setText(a);
            holder.email.setText(b);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(group==false){
                        emailss.clear();
                        context=v.getContext();
                        passData(b);
                    }
                    else if(group==true){
                        context=v.getContext();
                        if(!emailss.contains(b)) {
                            Toast.makeText(v.getContext(),"Added",Toast.LENGTH_SHORT).show();
                            emailss.add(b);
                            holder.name.setTextColor(Color.RED);
                            holder.email.setTextColor(Color.RED);
                        }else{
                            Toast.makeText(v.getContext(), "Member already added",Toast.LENGTH_SHORT).show();
                        }
                    }

                }
            });

            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (holder.name.getCurrentTextColor()==Color.RED){
                        Toast.makeText(v.getContext(),"Removed From Group ",Toast.LENGTH_SHORT).show();
                        holder.name.setTextColor(-1979711488);
                        holder.email.setTextColor(-1979711488);

                        emailss.remove(holder.email.getText());

                    }
                    return true;
                }
            });


        }

    }

    @Override
    public int getItemCount() {
        return names.size();
    }

    private void passData(String item){
        System.out.println("Personal Intent"+item);  //Item = mail
        //Intent intent=new Intent(context,Message.class);
        //intent.putExtra("receiverName",item);
        //context.startActivity(intent);

    }
    public void sendGroup(){
        //  Intent intent=new Intent(context,Message.class);       MessageActivity send
        // intent.putStringArrayListExtra("mails",emailss);
        group=false;
        //context.startActivity(intent);
        //emailss.clear();
    }
    public ArrayList<String> getGroup(){
        return new ArrayList<>(emailss);
    }

    @Override
    public Filter getFilter() {
        return filter;
    }
    Filter filter=new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            //TreeSet<UserC> filteredList=new TreeSet<>();
            RedBlackTree<UserC> filteredList=new RedBlackTree<>();
            //Iterator<UserC> iterForNameAll;
            Iterator<BinaryTree.Node> iterForNameAll;
            if(constraint.toString().isEmpty()){
                iterForNameAll =namesAll.iterator();
                UserC temp;
                while (true){
                    if (!iterForNameAll.hasNext()) break;
                    else{
                        //temp= iterForNameAll.next();
                        temp= (UserC) iterForNameAll.next().data;
                        filteredList.add(temp);
                    }
                }
            }else{
                iterForNameAll =namesAll.iterator();

                String a;
                UserC temp;
                while(true){
                    if (!iterForNameAll.hasNext())break;
                    else{
                        // temp= iterForNameAll.next();
                        temp= (UserC) iterForNameAll.next().data;
                        a=temp.getName();
                        if (a.toLowerCase().contains(constraint.toString().toLowerCase())){

                            filteredList.add(temp);
                        }
                    }
                }

            }
            FilterResults filterResults=new FilterResults();
            filterResults.values=filteredList;

            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            names.clear();

            // TreeSet<UserC> tempp= (TreeSet<UserC>) results.values;
            RedBlackTree<UserC> tempp= (RedBlackTree<UserC>) results.values;
            System.out.println(tempp.size());

            //Iterator<UserC> tempIter=tempp.iterator();
            Iterator<BinaryTree.Node> tempIter=tempp.iterator();
            UserC temp2;
            while(true){
                if (!tempIter.hasNext())break;
                else{
                    // temp2=tempIter.next();
                    temp2= (UserC) tempIter.next().data;
                    //System.out.println(temp2.getName());
                    names.add(temp2);

                }
            }
            iterForName=names.iterator();

            notifyDataSetChanged();


        }
    };



    class ProfileHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView name;
        TextView email;

        OnNoteListener onNoteListener;
        public ProfileHolder(@NonNull View itemView, final OnNoteListener onNoteListener) {
            super(itemView);
            name=itemView.findViewById(R.id.newname);
            email=itemView.findViewById(R.id.newmail);
            this.onNoteListener=onNoteListener;
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    onNoteListener.OnLongClick(getAdapterPosition());
                    return true;
                }
            });
        }

        @Override
        public void onClick(View v) {
            onNoteListener.OnNoteClick(getAdapterPosition());
        }
    }

    public interface OnNoteListener{
        void OnNoteClick(int position);
        void OnLongClick(int position);
    }

}

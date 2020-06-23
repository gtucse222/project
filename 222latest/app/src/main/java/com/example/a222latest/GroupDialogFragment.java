package com.example.a222latest;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;


public class GroupDialogFragment extends DialogFragment {

    EditText editText;
    TextView textView;
    String groupName;
    DatabaseReference groupRef;
    DatabaseReference memberGroupRef;
    ArrayList<String> mails;
    String grpId;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.groupdialog_fragment,container,false);
        editText=(EditText)view.findViewById(R.id.editText);
        textView=(TextView)view.findViewById(R.id.textView2);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                groupName=editText.getText().toString();
                if (!groupName.equals("")){
                    Toast.makeText(getActivity(),"Group created",Toast.LENGTH_SHORT).show();


                    groupRef=FirebaseDatabase.getInstance().getReference("Groups");
                    memberGroupRef=FirebaseDatabase.getInstance().getReference("Members");
                    groupRef.child(groupName).child("GroupName").push().setValue(groupName);
                    Message msg=new Message();
                    Message msg2=new Message();
                    Message msg3=new Message();
                    msg.setText("deneme msg1");
                    msg2.setText("deneme msg2");
                    msg3.setText("deneme msg3");
                    groupRef.child(groupName).child("Messages").push().setValue(msg);
                    groupRef.child(groupName).child("Messages").push().setValue(msg2);
                    groupRef.child(groupName).child("Messages").push().setValue(msg3);

                    for (int i=0;i<mails.size();i++){
                        groupRef.child(groupName).child("members").push().setValue(mails.get(i));
                        String chl=mails.get(i).substring(0,mails.get(i).indexOf("@")).replace(".","");
                        HashMap<Object,String> hashMap=new HashMap<>();
                        hashMap.put("name",groupName);
                        memberGroupRef.child(chl).child("group").child(groupName).setValue(hashMap);
                    }
                    getDialog().dismiss();
                }else{
                    Toast.makeText(getActivity(),"Invalid Group Name",Toast.LENGTH_SHORT).show();
                }

            }
        });


        return view;
    }
    public void setMails(ArrayList<String> myList){
        mails=myList;
    }
    public void setGroupId(String grpId){this.grpId=grpId;}

}

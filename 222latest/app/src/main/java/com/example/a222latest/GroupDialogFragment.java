package com.example.a222latest;

import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;


public class GroupDialogFragment extends DialogFragment {

    EditText editText;
    TextView textView;
    String groupName;
    DatabaseReference groupRef;
    DatabaseReference memberGroupRef;
    private FirebaseUser mAuth;
    ArrayList<String> mails;
    String groupId;
    Context x;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.groupdialog_fragment, container, false);
        editText = (EditText) view.findViewById(R.id.editText);
        textView = (TextView) view.findViewById(R.id.textView2);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                groupName = editText.getText().toString();
                if ((!groupName.equals(""))) {
                    Toast.makeText(getActivity(), "Group created", Toast.LENGTH_SHORT).show();


                    groupRef = FirebaseDatabase.getInstance().getReference("Groups");
                    memberGroupRef = FirebaseDatabase.getInstance().getReference("Members");

                    groupRef.child(groupId).child("groupName").setValue(groupName);

                    for (int i = 0; i < mails.size(); i++) {
                        groupRef.child(groupId).child("members").child("name" + (i + 1)).setValue(mails.get(i));
                        String chl = mails.get(i).substring(0, mails.get(i).indexOf("@")).replace(".", "");
                        memberGroupRef.child(chl).child("group").child(groupId).setValue(groupName);
                    }

                    mAuth = FirebaseAuth.getInstance().getCurrentUser();
                    groupRef.child(groupId).child("members").child("name" + (mails.size() + 1)).setValue(mAuth.getEmail());
                    memberGroupRef.child((mAuth.getEmail().substring(0, mAuth.getEmail().indexOf("@")).replace(".", "")))
                            .child("group").child(groupId).setValue(groupName);
                    getDialog().dismiss();


                    Intent intent = new Intent(getActivity(), GroupMessagingActivity.class);    //GROUP INTENT
                    intent.putExtra("groupId", groupId);
                    intent.putExtra("groupName", groupName);
                    startActivity(intent);

                } else {
                    Toast.makeText(getActivity(), "Invalid Group Name", Toast.LENGTH_SHORT).show();
                }

            }
        });


        return view;
    }

    public void setMails(ArrayList<String> myList) {
        mails = myList;
    }

    public void setGroupId(String grpId) {
        this.groupId = grpId;
    }

    public void setContext(Context x) {
        this.x = x;
    }

    public String getGroupName() {
        return groupName;
    }
}

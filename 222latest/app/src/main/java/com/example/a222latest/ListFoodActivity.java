package com.example.a222latest;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.Map;

public class ListFoodActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;
    ImageView imageView;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_food);

        System.out.println("listfoodin");

        imageView = findViewById(R.id.imageView);
        textView = findViewById(R.id.textView2);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        getData();

    }

    public void getData(){
        //System.out.println("ingetdata");
        CollectionReference collectionReference = firebaseFirestore.collection("imagePost");

        collectionReference.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                //System.out.println(e.getMessage());
                if( e != null){
                    System.out.println("HATAA!!" + e.getMessage());
                }
                System.out.println("if ici");
                if (queryDocumentSnapshots != null){
                    //System.out.println("if query");
                    for (DocumentSnapshot snapshot : queryDocumentSnapshots.getDocuments() ){

                        //System.out.println("info");
                        Map<String,Object> data = snapshot.getData();
                        String downloadUrl = (String) data.get("downloadUrl");
                        System.out.println("url : " + downloadUrl);

                        textView.setText("Menu");
                        //resmi bas
                        Picasso.get().load(downloadUrl).into(imageView);
                        //System.out.println("picasso");
                    }
                }
            }
        });
    }
}

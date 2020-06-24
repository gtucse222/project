package com.example.a222latest;

import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

public class GuestSeminarFragment extends Fragment {
    FirebaseAuth firebaseAuth;

    RecyclerView recyclerView;
    PriorityQueue<ModelPost> postList;
    List<ModelPost>queueToList= new LinkedList<>();
    AdapterPost adapterPost;
    ModelPost postTemp;
    private SearchView searchView = null;
    private SearchView.OnQueryTextListener queryTextListener;

    public GuestSeminarFragment() {
        // Required empty public constructor
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

//        logIn();
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_seminar, container, false);

        //init
        firebaseAuth = FirebaseAuth.getInstance();

        //recycler view and its properties
        postList = new PriorityQueue<ModelPost>();
        adapterPost = new AdapterPost(getActivity(), queueToList);
        recyclerView = view.findViewById(R.id.postRecview);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        //show newest post first, for this from load last
        layoutManager.setReverseLayout(true);
        recyclerView.setAdapter(adapterPost);
        //layoutManager.setReverseLayout(true);

        //setlayout to recycler
        //init post list

        loadPosts("seminar");
        return view;
    }

    private void loadPosts(String searchQuery) {
        //path of all posts
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Posts");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                postList.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    ModelPost modelPost = ds.getValue(ModelPost.class);

                    if (modelPost.getpTitle().toLowerCase().contains(searchQuery.toLowerCase())) {
                        postList.add(modelPost);
                    }
                    adapterPost.notifyDataSetChanged();

                    //adapter
                    //set adapter to recycler
                }
                Iterator<ModelPost> iter = postList.iterator();
                while (iter.hasNext()){
                    postTemp=iter.next();
                    queueToList.add(postTemp);
                    recyclerView.smoothScrollToPosition(recyclerView.getAdapter().getItemCount());
                }
                System.out.println(postList+"hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh");
                adapterPost.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //if there is an error
                Toast.makeText(getActivity(), "" + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }



    //Check user status



//
//    private void logIn() {
//        String email = "deneme@gmail.com";
//        String password = "123456";
//
//
//        firebaseAuth = FirebaseAuth.getInstance();
//
//        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
//            if (task.isSuccessful()) {
//                // Toast.makeText(this,"Loggedin",Toast.LENGTH_SHORT).show();
//            }
//            //else
//            //Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();
//        });
//    }
}

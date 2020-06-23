package com.example.a222latest;

import android.app.SearchManager;
//import android.content.Context;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.PriorityQueue;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    FirebaseAuth firebaseAuth;

    RecyclerView recyclerView;
    PriorityQueue<ModelPost> postList;
    List<ModelPost> queueToList=new LinkedList<ModelPost>();
    AdapterPost adapterPost;
    ModelPost postTemp;
    private SearchView searchView = null;
    private SearchView.OnQueryTextListener queryTextListener;

    public HomeFragment() {
        // Required empty public constructor
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //logIn();
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        //init
        firebaseAuth = FirebaseAuth.getInstance();
        //init post list
        postList = new PriorityQueue<ModelPost>();
        adapterPost = new AdapterPost(getActivity(), queueToList);
        //recycler view and its properties
        recyclerView = view.findViewById(R.id.postRecyclerview);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        //set adapter to recycler
        recyclerView.setAdapter(adapterPost);
        //show newest post first, for this from load last
        layoutManager.setReverseLayout(true);

        //setlayout to recycler


        loadPosts();
        layoutManager.setReverseLayout(true);
        return view;
    }

    private void loadPosts() {
        //path of all posts
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Posts");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                postList.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    ModelPost modelPost = ds.getValue(ModelPost.class);
                    postList.add(modelPost);
                    //adapter
                    recyclerView.smoothScrollToPosition(recyclerView.getAdapter().getItemCount());
                }
                Iterator<ModelPost> iter = postList.iterator();
                while (iter.hasNext()){
                    postTemp=iter.next();
                    queueToList.add(postTemp);
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

    public void searchPost(String searchQuery) {
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
                    recyclerView.smoothScrollToPosition(recyclerView.getAdapter().getItemCount());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //if there is an error
                Toast.makeText(getActivity(), "" + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    //Check user status


//     @RequiresApi(api = Build.VERSION_CODES.M)
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        //inflater.inflate(R.menu.menu_post, menu);
        MenuItem item = menu.findItem(R.id.post_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (!TextUtils.isEmpty(query.trim())) {
                    searchPost(query);
                } else {
                    loadPosts();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (!TextUtils.isEmpty(newText.trim())) {
                    searchPost(newText);
                } else {
                    loadPosts();
                }
                return false;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // int id = item.getItemId();
        //if (id == R.id.post_search) {
        // Toast.makeText(getActivity(), "", Toast.LENGTH_LONG).show();
        //  return true;
        //}
        // searchView.setOnQueryTextListener(queryTextListener);
        return super.onOptionsItemSelected(item);
    }


}

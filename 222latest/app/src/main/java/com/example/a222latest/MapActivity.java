package com.example.a222latest;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;

public class MapActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private GTUCampusMap GTUMap = new GTUCampusMap();
    protected Button location_button;
    protected TextView location_info;
    protected Spinner from;
    protected Spinner to;
    protected ImageView map_image;
    protected int to_;
    protected int from_;
    protected LocationAdapter adapter_location;

    @SuppressLint({"WrongViewCast", "ClickableViewAccessibility", "ResourceType"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        adapter_location = new LocationAdapter();
        from = findViewById(R.id.from);
        to = findViewById(R.id.to);
        location_button = (Button) findViewById(R.id.location_button);
        location_info = (TextView) findViewById(R.id.direction_info);
        map_image = (ImageView) findViewById(R.id.campus_map);
        map_image.setImageResource(R.drawable.gtu_map);

        ArrayAdapter<CharSequence> adapter =
                ArrayAdapter.createFromResource(this, R.array.numbersForGraph, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        from.setAdapter(adapter);
        to.setAdapter(adapter);

        from.setOnItemSelectedListener(this);
        to.setOnItemSelectedListener(this);

        location_info.setVisibility(View.INVISIBLE);

        location_button.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {


                to_= adapter_location.get_vertex_loca(to.getSelectedItem().toString());
                from_ = adapter_location.get_vertex_loca(from.getSelectedItem().toString());

                if(to_ != from_)
                    location_info.setText(GTUMap.direction_BFS(to_, from_));
                else
                    location_info.setText("You are already here.");

                location_info.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}

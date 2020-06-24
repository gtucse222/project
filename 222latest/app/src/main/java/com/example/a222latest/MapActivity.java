package com.example.a222latest;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
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
    protected Bitmap map;
    protected Bitmap map_copy;
    private static final int THICKNESS = 40;

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

        map = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.gtu_map);
        map_copy = map.copy(Bitmap.Config.ARGB_8888, true);

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

                if(to_ != from_) {

                    StringBuilder dir_str = new StringBuilder();
                    String[] dir_path =  GTUMap.direction_BFS(to_, from_).split("\\s+");

                    dir_str.append(dir_path[0]);
                    dir_str.append(" ");
                    for(int i = 1 ; i < dir_path.length ; ++i) {
                        dir_str.append(dir_path[i]);
                        dir_str.append(" ");

                        Point2D start = adapter_location.get_vertex_pixel(dir_path[i-1]);
                        Point2D finish = adapter_location.get_vertex_pixel(dir_path[i]);

                        drawLine(map_copy, THICKNESS, start.getScaleX(), start.getScaleY(), finish.getScaleX(), finish.getScaleY());
                    }

                    location_info.setText(dir_str.toString());
                    map_image.setImageBitmap(map_copy);
                }
                else
                    location_info.setText("You are already here.");

                location_info.setVisibility(View.VISIBLE);
            }
        });
    }

    private void drawLine(Bitmap map, int thickness ,int x1, int y1, int x2, int y2) {

        if(x1 <= x2) {

            for(int t = 0 ; t < thickness/2 ; ++t) {

                for(int x = x1 ; x <= x2 ; ++x) {

                    int pixel_y = get_point_vector_scaleY(x, x1, y1, x2, y2);
                    map.setPixel(x, pixel_y + t, Color.RED);
                }
            }

            for(int t = 0 ; t < thickness/2 ; ++t) {

                for(int x = x1 ; x <= x2 ; ++x) {

                    int pixel_y = get_point_vector_scaleY(x, x1, y1, x2, y2);
                    map.setPixel(x, pixel_y - t, Color.RED);
                }
            }
        }

        else {

            for( int t = 0 ; t < thickness/2 ; ++t) {

                for(int x = x2 ; x < x1 ; ++x) {

                    int pixel_y = get_point_vector_scaleY(x, x1, y1, x2, y2);
                    map.setPixel(x, pixel_y + t, Color.RED);
                }
            }

            for( int t = 0 ; t < thickness/2 ; ++t) {

                for(int x = x2 ; x < x1 ; ++x) {

                    int pixel_y = get_point_vector_scaleY(x, x1, y1, x2, y2);
                    map.setPixel(x, pixel_y - t, Color.RED);
                }
            }
        }
    }

    private double slope(int x1, int y1, int x2, int y2) {

        int diff_y = y2 - y1;
        int diff_x = x2 - x1;

        return (double) (diff_y / diff_x);
    }

    private int get_point_vector_scaleY(int x, int x1, int y1, int x2, int y2) {

        return (int)(slope(x1, y1, x2, y2)*(x-x1)) + y1;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}

package com.example.a222latest;

import android.annotation.SuppressLint;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
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

public class ListFoodActivity extends AppCompatActivity implements View.OnTouchListener{
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;
    ImageView imageView;
    TextView textView;


    /**
     * touch tag
     */
    private static final String TAG = "Touch";

    /**
     * Zoom scale
     */
    @SuppressWarnings("unused")
    private static final float MIN_ZOOM = 1f,MAX_ZOOM = 1f;

    /**
     * These matrices will be used to scale points of the image
     */
    protected Matrix matrix = new Matrix();

    /**
     * These matrices will be saved to scale points of the image
     */
    protected Matrix savedMatrix = new Matrix();

    /**
     * none touch
     */
    protected static final int NONE = 0;

    /**
     * if the user drag
     */
    protected static final int DRAG = 1;

    /**
     * if the user zoom
     */
    protected static final int ZOOM = 2;

    /**
     * current mode NONE
     */
    protected int mode = NONE;

    /**
     * PointF object for dragging or zooming
     */
    protected PointF start = new PointF();

    /**
     * PointF object for dragging or zooming
     */
    protected PointF mid = new PointF();

    /**
     * PointF object keeps old distance after dragging or zooming
     */
    protected float oldDist = 1f;

    @SuppressLint("ClickableViewAccessibility")
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

        imageView.setOnTouchListener((View.OnTouchListener) this);
    }

    public void getData() {
        //System.out.println("ingetdata");
        CollectionReference collectionReference = firebaseFirestore.collection("imagePost");

        collectionReference.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                //System.out.println(e.getMessage());
                if (e != null) {
                    System.out.println("HATAA!!" + e.getMessage());
                }
                System.out.println("if ici");
                if (queryDocumentSnapshots != null) {
                    //System.out.println("if query");
                    for (DocumentSnapshot snapshot : queryDocumentSnapshots.getDocuments()) {

                        //System.out.println("info");
                        Map<String, Object> data = snapshot.getData();
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

    /**
     * On touch method for image zoom or dragging
     * @param v, image view
     * @param event, NONE, ZOOM or DRAG
     * @return true
     */
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouch(View v, MotionEvent event)
    {
        ImageView view = (ImageView) v;
        view.setScaleType(ImageView.ScaleType.MATRIX);
        float scale;

        dumpEvent(event);

        switch (event.getAction() & MotionEvent.ACTION_MASK)
        {
            case MotionEvent.ACTION_DOWN:
                savedMatrix.set(matrix);
                start.set(event.getX(), event.getY());
                Log.d(TAG, "mode=DRAG");
                mode = DRAG;
                break;

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:

                mode = NONE;
                Log.d(TAG, "mode=NONE");
                break;

            case MotionEvent.ACTION_POINTER_DOWN:
                oldDist = spacing(event);
                Log.d(TAG, "oldDist=" + oldDist);
                if (oldDist > 5f) {
                    savedMatrix.set(matrix);
                    midPoint(mid, event);
                    mode = ZOOM;
                    Log.d(TAG, "mode=ZOOM");
                }
                break;

            case MotionEvent.ACTION_MOVE:

                if (mode == DRAG)
                {
                    matrix.set(savedMatrix);
                    matrix.postTranslate(event.getX() - start.x, event.getY() - start.y);
                }
                else if (mode == ZOOM)
                {
                    float newDist = spacing(event);
                    Log.d(TAG, "newDist=" + newDist);
                    if (newDist > 5f)
                    {
                        matrix.set(savedMatrix);
                        scale = newDist / oldDist;
                        matrix.postScale(scale, scale, mid.x, mid.y);
                    }
                }
                break;
        }

        view.setImageMatrix(matrix);

        return true;
    }

    /**
     * It allows the points to change according to a certain scale in any movement.
     * @param event, NONE,ZOOM or DRAG
     * @return float new spacing
     */
    private float spacing(MotionEvent event)
    {
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return (float) Math.sqrt(x * x + y * y);
    }

    /**
     * It allows the points to change according to a certain scale in any movement.
     * @param point, new point
     * @param event, NONE ZOOM or DRAG
     */
    private void midPoint(PointF point, MotionEvent event)
    {
        float x = event.getX(0) + event.getX(1);
        float y = event.getY(0) + event.getY(1);
        point.set(x / 2, y / 2);
    }

    /**
     * Show an event in the LogCat view, for debugging
     * */
    private void dumpEvent(MotionEvent event)
    {
        String[] names = { "DOWN", "UP", "MOVE", "CANCEL", "OUTSIDE","POINTER_DOWN", "POINTER_UP", "7?", "8?", "9?" };
        StringBuilder sb = new StringBuilder();
        int action = event.getAction();
        int actionCode = action & MotionEvent.ACTION_MASK;
        sb.append("event ACTION_").append(names[actionCode]);

        if (actionCode == MotionEvent.ACTION_POINTER_DOWN || actionCode == MotionEvent.ACTION_POINTER_UP)
        {
            sb.append("(pid ").append(action >> MotionEvent.ACTION_POINTER_ID_SHIFT);
            sb.append(")");
        }

        sb.append("[");
        for (int i = 0; i < event.getPointerCount(); i++)
        {
            sb.append("#").append(i);
            sb.append("(pid ").append(event.getPointerId(i));
            sb.append(")=").append((int) event.getX(i));
            sb.append(",").append((int) event.getY(i));
            if (i + 1 < event.getPointerCount())
                sb.append(";");
        }

        sb.append("]");
        Log.d("Touch Events ---------", sb.toString());
    }
}


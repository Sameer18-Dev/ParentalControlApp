package parentalcontrolapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.parentalcontrolapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Viewattendance extends AppCompatActivity {
    private RecyclerView mrecyclerView;
    private List<modelviewattendance> SDataList = new ArrayList<modelviewattendance>();

   public static final String FILE_NAME = "my_file" ;
    public static final String KEY_EMAIL ="email" ;
    public static final String Role_KEY ="role" ;
    public static final String KEY_Faculty ="FacultyID" ;
    private String FacultyID = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewattendance);

        SharedPreferences preferences = getSharedPreferences(FILE_NAME, MODE_PRIVATE);
        FacultyID = preferences.getString(KEY_Faculty, "");

        mrecyclerView = findViewById(R.id.recyclerView);

        LinearLayoutManager manager = new LinearLayoutManager(this);
        mrecyclerView.setLayoutManager(manager);

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://myandroidappstesting.000webhostapp.com/ParentalControlApp/ViewAttendance.php";

// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Intent getStdID = getIntent();
                        String  StdID = String.valueOf(getStdID.getIntExtra("StdID", 0));

//                      Toast.makeText(Viewattendance.this, String.valueOf(StdID.length()), Toast.LENGTH_SHORT).show();

                        Log.d("TAG", "onResponse: " + response);

//                        Toast.makeText(Viewattendance.this, StdID, Toast.LENGTH_LONG).show();
                        try {
                            JSONArray jsonArray = new JSONArray(response);

                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject obj = jsonArray.getJSONObject(i);

                                modelviewattendance view = new modelviewattendance();

                                view.setFacultyid(obj.getString("facultyid"));
                                view.setStudentid(obj.getString("studentid"));
                                view.setStdname(obj.getString("studentname"));
                                view.setDate(obj.getString("Date"));
                                view.setTime(obj.getString("Time"));
                                view.setStatus(obj.getString("status"));

                                if(obj.getString("studentid").equals(StdID)){

                                    SDataList.add(view);
                                }
                                if(StdID.equals("0")) {

                                    if (obj.getString("facultyid").equals(FacultyID)) {

                                        SDataList.add(view);
                                    }
                                }

                              //if (obj.getString("Faculty").equals(FacultyID))
                              //{

                              //}

                            }
                            AdapterViewAttendance adapterViewAttendance =new AdapterViewAttendance(SDataList,Viewattendance.this);
                            mrecyclerView.setAdapter(adapterViewAttendance);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {


            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Viewattendance.this, "Error!", Toast.LENGTH_SHORT).show();
            }
        });

// Add the request to the RequestQueue.
        queue.add(stringRequest);
    }


}

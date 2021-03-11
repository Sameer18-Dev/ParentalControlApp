package parentalcontrolapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class updateAttendance extends AppCompatActivity {

    private List<modelattendance> SDataList = new ArrayList<modelattendance>();
    private List<String> AttendanceID = new ArrayList<>();

    private RecyclerView mrecyclerView;

    public static final String FILE_NAME = "my_file" ;
    public static final String KEY_Faculty ="FacultyID";
    private String FacultyID = "";
    private String Admin = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_attendance);


        SharedPreferences preferences = getSharedPreferences(FILE_NAME, MODE_PRIVATE);
        FacultyID = preferences.getString(KEY_Faculty, "");

        Toast.makeText(this, "Here!", Toast.LENGTH_SHORT).show();

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

                        Log.d("TAG", "onResponse: " + response);
                        Toast.makeText(updateAttendance.this, response, Toast.LENGTH_LONG).show();
                        try {
                            JSONArray jsonArray = new JSONArray(response);

                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject obj = jsonArray.getJSONObject(i);

                                modelattendance view = new modelattendance();

                                view.setFacultyid(obj.getString("facultyid"));
                                view.setStudentid(obj.getString("studentid"));
                                view.setStdname(obj.getString("studentname"));
                                view.setDate(obj.getString("Date"));
                                view.setTime(obj.getString("Time"));
                                view.setStatus(obj.getString("status"));


                                if(obj.getString("facultyid").equals(FacultyID)) {
                                    AttendanceID.add(obj.getString("attendanceid"));
                                    SDataList.add(view);
                                }

                            }

                            AdapterAttendance adapterAttendance = new AdapterAttendance(SDataList, updateAttendance.this);
                            mrecyclerView.setAdapter(adapterAttendance);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {


            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(updateAttendance.this, "Error!", Toast.LENGTH_SHORT).show();
            }
        });

// Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    private void submitAttendance(String Attendanceid, String FacultyID, String StudentName, String StudentID, String Status) {

        RequestQueue queue = Volley.newRequestQueue(updateAttendance.this);
        String url = "https://myandroidappstesting.000webhostapp.com/ParentalControlApp/updateAttendance.php";


        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("TAG", "onResponse: " + response);
                        Log.d("Values", "onsubmit: "+response);
                        Toast.makeText(updateAttendance.this, response, Toast.LENGTH_LONG).show();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(updateAttendance.this, "Error!", Toast.LENGTH_SHORT).show();
            }

        }) {
            @Override
            public Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();

                map.put("attendanceid", Attendanceid);
                map.put("facultyid", FacultyID);
                map.put("studentid", StudentID);
                map.put("studentname", StudentName);
                map.put("status", Status);

                //map.put("status",Selectedcheckbox.getText().toString());

                return map;
            }
        };


// Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    public void onsubmit(View view) {

        SharedPreferences preferences = getSharedPreferences(FILE_NAME, MODE_PRIVATE);
        String FacultyID = preferences.getString(KEY_Faculty, "");


//        Toast.makeText(this, String.valueOf(StudentName.size()), Toast.LENGTH_SHORT).show();
        for (int i = 0; i < SDataList.size(); i++) {
            View row = mrecyclerView.getChildAt(i); // This will give you entire row(child) from RecyclerView
            if (row != null) {
                CheckBox check = row.findViewById(R.id.status);

//                check.setChecked(true);

                TextView stdid = row.findViewById(R.id.studentid);
                TextView stdname = row.findViewById(R.id.studentname);
                if (check.isChecked()) {


//                    Log.d("Row", "onsubmit: " + stdname.getText().toString());
                    Log.d("Values", "onsubmit: "+AttendanceID.get(i));
                    Log.d("Values", "onsubmit: "+FacultyID);
                    Log.d("Values", "onsubmit: "+stdname.getText().toString());
                    Log.d("Values", "onsubmit: "+stdid.getText().toString());

                    submitAttendance(AttendanceID.get(i), FacultyID, stdname.getText().toString(), stdid.getText().toString(), "P");


                } else {

                    submitAttendance(AttendanceID.get(i), FacultyID, stdname.getText().toString(), stdid.getText().toString(), "A");
                }

            }
        }
    }

}
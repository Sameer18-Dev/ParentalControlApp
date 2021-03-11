package parentalcontrolapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
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

public class ViewStudent extends AppCompatActivity {

     private RecyclerView  sRecyclerView;
    private List<modelStudent> SDataList = new ArrayList<>();
    //private List<modelStudent> StudentName =new ArrayList<>();
   // private List<String> Student_id = new ArrayList<>();

    public static final String FILE_NAME = "my_file" ;
    public static final String KEY_EMAIL ="email" ;
    public static final String Role_KEY ="role" ;
    public static final String KEY_Faculty ="FacultyID" ;
    private String FacultyID = "";
    private String Admin = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_student);

        SharedPreferences preferences = getSharedPreferences(FILE_NAME, MODE_PRIVATE);
        FacultyID = preferences.getString(KEY_Faculty, "");

        Admin = preferences.getString(Role_KEY, "");

        Toast.makeText(this, FacultyID, Toast.LENGTH_SHORT).show();

        Log.d("FcaultyID", "onCreate: " + FacultyID);

        sRecyclerView = findViewById(R.id.student);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        sRecyclerView.setLayoutManager(manager);

        ViewStudentsNew();

        if (ContextCompat.checkSelfPermission(ViewStudent.this, Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            // Ask for permision
            ActivityCompat.requestPermissions(ViewStudent.this,new String[] { Manifest.permission.SEND_SMS}, 1);
        }

    }


    public void ViewStudentsNew() {

//        Log.d("test", "Here!");

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://myandroidappstesting.000webhostapp.com/ParentalControlApp/ViewStudent.php";

// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("TAGNEW", "onResponse: " + response);

//                        Toast.makeText(ViewStudent.this, response, Toast.LENGTH_LONG).show();

                        try {

                            JSONArray jsonArray = new JSONArray(response);

                            Log.d("here", "onResponse: "+jsonArray.length());

                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject obj = jsonArray.getJSONObject(i);

//                                Toast.makeText(ViewStudent.this, String.valueOf(obj.getString("name")), Toast.LENGTH_SHORT).show();

                                modelStudent student = new modelStudent();

                                student.setStudentName(obj.getString("name"));
                                student.setStudent_id(obj.getString("student_id"));
                                student.setParentphone(obj.getString("parent_phone"));

                                if (obj.getString("Faculty").equals(FacultyID)) {

                                    Log.d("See", "onResponse: "+String.valueOf(obj.getString("name")));

                                    SDataList.add(student);

                                }else if(Admin.equals("1")){

                                    SDataList.add(student);
                                }
                            }

                            studentAdapter adapter = new studentAdapter(SDataList, ViewStudent.this);
                            sRecyclerView.setAdapter(adapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {


            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ViewStudent.this, "Error!", Toast.LENGTH_SHORT).show();
            }
        });

// Add the request to the RequestQueue.
        queue.add(stringRequest);

    }

}
package parentalcontrolapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
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
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Attendance extends AppCompatActivity{



    EditText txtDate, txtTime;

    private String SelectedTime ="";
    private  String SelectedDate ="";
    private  String Selectedcheckbox="";
    private Button btn;
    private int mYear, mMonth, mDay, mHour, mMinute;


    private RecyclerView sRecyclerView;
    private List<modelStudent> StudentName =new ArrayList<modelStudent>();

    public static final String FILE_NAME = "my_file" ;
    public static final String KEY_EMAIL ="email" ;
    public static final String Role_KEY ="role" ;
    public static final String KEY_Faculty ="FacultyID" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);

        btn=findViewById(R.id.button8);
        txtDate=findViewById(R.id.in_date);
        txtTime=findViewById(R.id.in_time);



        ViewStudents();

    }

    private void submitAttendance(String FacultyID, String StudentName, String StudentID, String Date, String Time, String Status) {

                RequestQueue queue = Volley.newRequestQueue(Attendance.this);
                String url = "https://myandroidappstesting.000webhostapp.com/ParentalControlApp/Attendance.php";




                // Request a string response from the provided URL.
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                Log.d("TAG", "onResponse: " + response);
                                Toast.makeText(Attendance.this, response, Toast.LENGTH_LONG).show();

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Attendance.this, "Error!", Toast.LENGTH_SHORT).show();
                    }

                }) {
                    @Override
                    public Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> map = new HashMap<>();

                        map.put("facultyid",FacultyID);
                        map.put("studentid",StudentID);
                        map.put("studentname",StudentName);
                        map.put("Date",Date);
                        map.put("Time",Time);
                        map.put("status",Status);

                        //map.put("status",Selectedcheckbox.getText().toString());

                        return map;
                    }
                };


// Add the request to the RequestQueue.
                queue.add(stringRequest);
            }


    public void ViewStudents() {




        SharedPreferences preferences = getSharedPreferences(FILE_NAME, MODE_PRIVATE);
        String FacultyID =preferences.getString(KEY_Faculty,"");

        sRecyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        sRecyclerView.setLayoutManager(manager);


        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://myandroidappstesting.000webhostapp.com/ParentalControlApp/ViewStudent.php";

// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("TAG", "onResponse: " + response);

//                        Toast.makeText(ViewStudent.this, response, Toast.LENGTH_LONG).show();

                        try {

                            JSONArray jsonArray = new JSONArray(response);

                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject obj = jsonArray.getJSONObject(i);

                                modelStudent student = new modelStudent();

                                student.setStudentName(obj.getString("name"));
                                student.setStudent_id(obj.getString("student_id"));

                                //                              student.setStudent_id(obj.getString("student_id"));

//                                Toast.makeText(ViewStudent.this, String.valueOf(obj.getString("name")), Toast.LENGTH_SHORT).show();

                                if(obj.getString("Faculty").equals(FacultyID)) {
                                    StudentName.add(student);
                                }



//                                address.add(obj.getString("address"));
//                                Email.add(obj.getString("email_address"));
//                                contact_number.add(obj.getString("contact_number"));
//                                parent_phone.add(obj.getString("qualification"));
//                                password.add(obj.getString("password"));

                            }

                            //Toast.makeText(Attendance.this, "Hello", Toast.LENGTH_SHORT).show();

                            modelStudent view = StudentName.get(0);

                            Toast.makeText(Attendance.this, String.valueOf(view.getStudentName()), Toast.LENGTH_SHORT).show();

                            studentcheckboxAdapter adapter = new studentcheckboxAdapter(StudentName, Attendance.this);
                            sRecyclerView.setAdapter(adapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {


            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Attendance.this, "Error!", Toast.LENGTH_SHORT).show();
            }
        });

// Add the request to the RequestQueue.
        queue.add(stringRequest);

    }

    public void onsubmit(View view) {

        SharedPreferences preferences = getSharedPreferences(FILE_NAME, MODE_PRIVATE);
        String FacultyID =preferences.getString(KEY_Faculty,"");



//        Toast.makeText(this, String.valueOf(StudentName.size()), Toast.LENGTH_SHORT).show();
        for(int i=0;i<StudentName.size();i++)
        {
            View row=sRecyclerView.getChildAt(i); // This will give you entire row(child) from RecyclerView
            if(row!=null)
            {
                CheckBox check=  row.findViewById(R.id.checkBox);

//                check.setChecked(true);

                TextView stdid = row.findViewById(R.id.TextViewid);
                TextView stdname = row.findViewById(R.id.txt_student);
                if(check.isChecked()) {


                    Log.d("Row", "onsubmit: " + stdname.getText().toString());

                    submitAttendance(FacultyID, stdname.getText().toString(), stdid.getText().toString(),txtDate.getText().toString(), txtTime.getText().toString(), "P");


                }else{

                    submitAttendance(FacultyID, stdname.getText().toString(), stdid.getText().toString(),txtDate.getText().toString(), txtTime.getText().toString(), "A");
                }

            }
        }
    }

    public void ondateselect(View view) {


        // Get Current Date
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        txtDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();


    }

    public void ontimeselect(View view) {


        // Get Current Time
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {

                        txtTime.setText(hourOfDay + ":" + minute);
                    }
                }, mHour, mMinute, true);
        timePickerDialog.show();

    }
}
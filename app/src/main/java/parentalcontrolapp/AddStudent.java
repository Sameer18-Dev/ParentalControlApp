package parentalcontrolapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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

public class AddStudent extends AppCompatActivity {

    private Spinner spin;
    private Button btn,btn2;
    private EditText  name , email, contact, address, parents, password;
    private List<String> facultiesName = new ArrayList<>();
    private List<String> facultiesID = new ArrayList<>();
    private String SelectedFaculty = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);

        btn = findViewById(R.id.Submit);
        name =findViewById(R.id.student);
        email =findViewById(R.id.email);
        address =findViewById(R.id.address);
        contact =findViewById(R.id.course);
        parents =findViewById(R.id.parent);
        password =findViewById(R.id.password);
        //btn2=findViewById(R.id.ViewStudent);




//        facultiesName.add("val1");
//
//        facultiesName.add("val2");


        spin = findViewById(R.id.faculty);

//        spin.setOnItemSelectedListener(this);



        GetFaculty();
        SendStudent();

    }


    public void GetFaculty(){

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://myandroidappstesting.000webhostapp.com/ParentalControlApp/ViewFaculty.php";


        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("TAG", "onResponse: " + response);
                        Toast.makeText(AddStudent.this, response, Toast.LENGTH_LONG).show();

                        try {
                            JSONArray jsonArray = new JSONArray(response);


                            for(int i = 0; i<jsonArray.length(); i++) {

                                JSONObject obj = jsonArray.getJSONObject(i);


                                facultiesName.add(obj.getString("faculty_name"));
                                facultiesID.add(obj.getString("faculty_id"));
                            }



                            ArrayAdapter adapter = new ArrayAdapter(AddStudent.this, android.R.layout.simple_spinner_item, facultiesName);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


                            spin.setAdapter(adapter);

//        adapter.notifyDataSetChanged();

                            spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(AddStudent.this, facultiesID.get(position), Toast.LENGTH_SHORT).show();
                SelectedFaculty = facultiesID.get(position);
//                                    Log.d("TAG", "onItemSelected");
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(AddStudent.this, "Error!", Toast.LENGTH_SHORT).show();
            }

        });


// Add the request to the RequestQueue.
        queue.add(stringRequest);
    }



    public void SendStudent(){






        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestQueue queue = Volley.newRequestQueue(AddStudent.this);
                String url = "https://myandroidappstesting.000webhostapp.com/ParentalControlApp/AddStudent.php";


                // Request a string response from the provided URL.
               StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                Log.d("TAG", "onResponse: " + response);
                                Toast.makeText(AddStudent.this, response, Toast.LENGTH_LONG).show();
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(AddStudent.this, "Error!", Toast.LENGTH_SHORT).show();
                    }

                }) {
                    @Override
                    public Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> map = new HashMap<>();

                        map.put("name",name.getText().toString());
                        map.put("address",address.getText().toString());
                        map.put("email",email.getText().toString());
                        map.put("contact_number",contact.getText().toString());
                        map.put("parent_phone",parents.getText().toString());
                        map.put("faculty",SelectedFaculty);
                        map.put("password",password.getText().toString());


                        return map;
                    }
                };


// Add the request to the RequestQueue.
                queue.add(stringRequest);
            }
        });
            }










}
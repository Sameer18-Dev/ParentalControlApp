package parentalcontrolapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.parentalcontrolapp.R;

import java.util.HashMap;
import java.util.Map;

public class Add_faculty extends AppCompatActivity {

    private EditText Faculty, Address, Email, number, qualification, password, experience;
    private Button Submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_faculty);

        Faculty = findViewById(R.id.faculty);
        Address = findViewById(R.id.Address);
        Email = findViewById(R.id.Email);
        number = findViewById(R.id.number);
        qualification = findViewById(R.id.qualification);
        password = findViewById(R.id.password);
        experience = findViewById(R.id.experience);
        Submit = findViewById(R.id.Submit);
        //btn =findViewById(R.id.button5);




        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestQueue queue = Volley.newRequestQueue(Add_faculty.this);
                String url = "https://myandroidappstesting.000webhostapp.com/ParentalControlApp/AddFaculty.php";


                // Request a string response from the provided URL.
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                Log.d("TAG", "onResponse: " + response);
                                Toast.makeText(Add_faculty.this, response, Toast.LENGTH_LONG).show();
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Add_faculty.this, "Error!", Toast.LENGTH_SHORT).show();
                    }

                }) {
                    @Override
                    public Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> map = new HashMap<>();

                        map.put("faculty_name", Faculty.getText().toString());
                        map.put("address", Address.getText().toString());
                        map.put("email_address", Email.getText().toString());
                        map.put("contact_number", number.getText().toString());
                        map.put("qualification", qualification.getText().toString());
                        map.put("password", password.getText().toString());
                        map.put("experience", experience.getText().toString());

                        return map;
                    }
                };


// Add the request to the RequestQueue.
                queue.add(stringRequest);
            }
        });

    }
}
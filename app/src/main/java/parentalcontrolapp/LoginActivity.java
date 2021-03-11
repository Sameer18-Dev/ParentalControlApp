package parentalcontrolapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private Button btn, btn2 ;
    private EditText User_Name, T_pass;
    String mEmail;
    String mPass;


    public static final String FILE_NAME = "my_file" ;
    public static final String KEY_EMAIL ="email" ;
    public static final String Role_KEY ="role" ;
    public static final String KEY_Faculty ="FacultyID" ;

    private boolean validateEmailAddress(){
        mEmail =User_Name.getText().toString().trim();
        if (mEmail.isEmpty() ) {
            User_Name.setError("Email is Required");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(mEmail).matches()){
            User_Name.setError(" Invalid Email. Enter Valid Email Address ");
            return false;
        }

        else {
            User_Name.setError(null);
            return true;
        }

    }

    private boolean validatePassword() {
        mPass =T_pass.getText().toString().trim();
        if (mPass.isEmpty()) {
            T_pass.setError("Password is required");
            return false;
        } else {
            T_pass.setError(null);
            return true;
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences preferences = getSharedPreferences(FILE_NAME, MODE_PRIVATE);
        String extrarole =preferences.getString(Role_KEY,"");

        if(!extrarole.isEmpty()){
            if (extrarole.equals("1")) {
                Intent intent = new Intent(LoginActivity.this, Dashboard.class);
                startActivity(intent);
                finish();
            }else{
                Intent intent = new Intent(LoginActivity.this, Dashboard_faculty.class);
                startActivity(intent);
                finish();
            }
        }
        setContentView(R.layout.activity_login);




        User_Name = findViewById(R.id.txtEmail);
        T_pass = findViewById(R.id.txtPassword);
        btn = findViewById(R.id.btnLogin);


//        btn2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent =new Intent(LoginActivity.this,MainActivity.class);
//                startActivity(intent);
//            }
//        });


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mEmail = User_Name.getText().toString().trim();
                mPass = T_pass.getText().toString().trim();


               if (!validateEmailAddress() | !validatePassword()) {
                    return;
                } else {

                }
                    // Instantiate the RequestQueue.
                    RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);

                    String url = "https://myandroidappstesting.000webhostapp.com/ParentalControlApp/login.php";

// Request a string response from the provided URL.
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    Log.d("TAG", "onResponse: " + response);

                                    String role = "";
                                    String FacultyID = "";

                                    try {
                                        JSONObject data = new JSONObject(response);
                                        role = data.getString("role");
                                        FacultyID = data.getString("id");

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }


                                    Toast.makeText(LoginActivity.this, role, Toast.LENGTH_SHORT).show();

                                    if (role.equals("1")) {


                                        SharedPreferences.Editor editor = getSharedPreferences(FILE_NAME, MODE_PRIVATE).edit();
                                        editor.putString(KEY_EMAIL, mEmail);
                                        editor.putString(Role_KEY, role);
                                        editor.apply();

                                        Intent intent = new Intent(LoginActivity.this, Dashboard.class);
                                        intent.putExtra(KEY_EMAIL, mEmail);
                                        intent.putExtra(Role_KEY, role);
                                        startActivity(intent);
                                        finish();

                                    }
                                    else if(role.equals("2")){

                                        SharedPreferences.Editor editor = getSharedPreferences(FILE_NAME, MODE_PRIVATE).edit();
                                        editor.putString(KEY_Faculty, FacultyID);
                                        editor.putString(KEY_EMAIL, mEmail);
                                        editor.putString(Role_KEY, role);
                                        editor.apply();

                                        Intent intent = new Intent(LoginActivity.this, Dashboard_faculty.class);
                                        intent.putExtra("Email", mEmail);
                                        startActivity(intent);
                                        finish();
                                    }else{

                                        Toast.makeText(LoginActivity.this, response, Toast.LENGTH_SHORT).show();
                                    }
                                    

                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(LoginActivity.this, "Error" + error.toString(), Toast.LENGTH_LONG).show();
                        }


                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> map = new HashMap<>();
                            map.put("Email", mEmail);
                            map.put("password", mPass);
                            return map;
                        }
                    };

// Add the request to the RequestQueue.
                    queue.add(stringRequest);
                }
        });



    }

}
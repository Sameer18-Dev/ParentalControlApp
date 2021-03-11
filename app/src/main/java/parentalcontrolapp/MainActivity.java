package parentalcontrolapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.parentalcontrolapp.R;
import com.google.android.material.textfield.TextInputLayout;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private Button btn;

    private TextInputLayout t_name, t_email, t_password;
    String mEmail;
    String mPass;
    String Name;

    private boolean validateUsername() {
      Name = t_name.getEditText().getText().toString().trim();
        if (Name.isEmpty()) {
            t_name.setError("UserName is Required");
            return false;
        }
        else {
            t_name.setError(null);
            return true;
        }

    }

    private boolean validateEmailAddress() {
        mEmail =t_email.getEditText().getText().toString().trim();
        if (mEmail.isEmpty() ) {
            t_email.setError("Email is Required");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(mEmail).matches()){
            t_email.setError(" Invalid Email. Enter Valid Email Address ");
            return false;
        }

        else {
            t_email.setError(null);
            return true;
        }

    }

    private boolean validatePassword() {
        mPass =t_password.getEditText().getText().toString().trim();
        if (mPass.isEmpty()) {
            t_password.setError("Email is Required");
            return false;
        } else if (mPass.length() < 8) {
            t_password.setError("password is too shot . 8 characters are required ");
            return false;
        } else {
            t_password.setError(null);
            return true;
        }

    }


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = findViewById(R.id.button);
        t_name = findViewById(R.id.Name);
        t_email = findViewById(R.id.Email);
        t_password = findViewById(R.id.password);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

             Name =t_name.getEditText().getText().toString().trim();
                mEmail =t_email.getEditText().getText().toString().trim();
                mPass =t_password.getEditText().getText().toString().trim();

                if ( !validateUsername()| !validateEmailAddress()| !validatePassword()){
                 return;
                }else {
                    Register(Name,mEmail,mPass);
                }
            }


        });
    }
    private void Register(String Name, String mEmail, String mPass) {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        String url ="https://myandroidappstesting.000webhostapp.com/ParentalControlApp/Register.php";

// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("TAG", "onResponse: "+response);
                        Toast.makeText(MainActivity.this,response,Toast.LENGTH_LONG).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Error!", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            public Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();

                map.put("Name",Name);
                map.put("Email",mEmail);
                map.put("password",mPass);
                return map;
            }
        };

// Add the request to the RequestQueue.
        queue.add(stringRequest);
    }
}

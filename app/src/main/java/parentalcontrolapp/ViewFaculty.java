package parentalcontrolapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

public class ViewFaculty extends AppCompatActivity {

    private RecyclerView mRecyclerview;
    private List<model> facultiesName = new ArrayList<model>();


    public static final String FILE_NAME = "my_file" ;
    public static final String KEY_EMAIL ="email" ;
    public static final String KEY_Faculty ="FacultyID" ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_faculty);

        mRecyclerview =findViewById(R.id.View);

        LinearLayoutManager manager = new LinearLayoutManager(this);
        mRecyclerview.setLayoutManager(manager);

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://myandroidappstesting.000webhostapp.com/ParentalControlApp/ViewFaculty.php";

// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("TAG", "onResponse: " + response);
                        Toast.makeText(ViewFaculty.this, response, Toast.LENGTH_LONG).show();
                        try {
                            JSONArray jsonArray = new JSONArray(response);



                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject obj = jsonArray.getJSONObject(i);


                                model faculty = new model();
                                faculty.setName(obj.getString("faculty_name"));


                                facultiesName.add(faculty);




                            }

                            facultyAdapter adapter = new facultyAdapter(facultiesName, ViewFaculty.this);
                            mRecyclerview.setAdapter(adapter);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    }, new Response.ErrorListener() {


            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ViewFaculty.this, "Error!", Toast.LENGTH_SHORT).show();
            }
        });

// Add the request to the RequestQueue.
        queue.add(stringRequest);
    }
}
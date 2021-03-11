package parentalcontrolapp;

import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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

import java.util.List;



public class studentAdapter extends RecyclerView.Adapter<studentAdapter.myViewHolder> {

    private List<modelStudent> SDataList;
    private Context context;

    private int TotalClasses = 0, TotalPresents = 0, TotalAbsents = 0;


    public studentAdapter(java.util.List<modelStudent> SDataList, Context context) {
        this.SDataList = SDataList;
        this.context = context;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_student_layout, parent, false);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        modelStudent view =SDataList.get(position);

        Log.d("StudentName", "onBindViewHolder: "+ view.getStudentName());

        holder.stdname.setText(view.getStudentName());
       holder.stdid.setText(view.getStudent_id());
       holder.sendreport.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               // Instantiate the RequestQueue.
               RequestQueue queue = Volley.newRequestQueue(context);
               String url = "https://myandroidappstesting.000webhostapp.com/ParentalControlApp/ViewAttendance.php";

// Request a string response from the provided URL.
               StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                       new Response.Listener<String>() {
                           @Override
                           public void onResponse(String response) {
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

                                       if (obj.getString("studentid").equals(view.getStudentid())) {
                                           TotalClasses++;

                                           if(obj.getString("status").equals("P")){
                                               TotalPresents++;
                                           }else if(obj.getString("status").equals("A")){
                                               TotalAbsents++;
                                           }

                                       }


                                   }

                               } catch (JSONException e) {
                                   e.printStackTrace();
                               }

                               SmsManager smsManager = SmsManager.getDefault();
                               Log.d("sms", "onClick: "+view.getParentphone());
                               smsManager.sendTextMessage(String.valueOf(view.getParentphone()), null, "Your Student Report \n"+"Student Name : "+view.getStudentName()+"\n"+" TotalPresents : "+TotalPresents+"\n"+"TotalAbsents : "+TotalAbsents , null, null);

                           }
                       }, new Response.ErrorListener() {


                   @Override
                   public void onErrorResponse(VolleyError error) {
//                       Toast.makeText(Viewattendance.this, "Error!", Toast.LENGTH_SHORT).show();
                   }
               });

// Add the request to the RequestQueue.
               queue.add(stringRequest);
           }
       });
        holder.viewreport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nav = new Intent(context, Viewattendance.class);
                nav.putExtra("StdID", Integer.parseInt(view.getStudent_id()));
                context.startActivity(nav);
            }
        });


    }

    @Override
    public int getItemCount() {
        return SDataList.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder{

        TextView stdname, stdid;
        Button sendreport, viewreport;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            stdname = itemView.findViewById(R.id.txt_student);
            stdid =itemView.findViewById(R.id.TextViewid);
            sendreport =itemView.findViewById(R.id.sendreport);
            viewreport =itemView.findViewById(R.id.viewreport);


        }
    }
}

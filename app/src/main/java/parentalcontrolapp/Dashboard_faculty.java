package parentalcontrolapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.parentalcontrolapp.R;

public class Dashboard_faculty extends AppCompatActivity {
    public static final String FILE_NAME = "my_file" ;
    public static final String KEY_EMAIL ="email" ;
    public static final String Role_KEY ="role" ;



    private TextView attendance,viewattendance,update,viewstudent;
    String extraEmail = "";

    @Override
    public void onBackPressed() {
        if(!extraEmail.isEmpty()) {
            finish();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case  R.id.logout:
                SharedPreferences preferences = getSharedPreferences(FILE_NAME, MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.remove(Role_KEY);
                editor.apply();
                startActivity(new Intent(Dashboard_faculty.this, LoginActivity.class));
                finish();
                break;
            case  R.id.setting:
                Toast.makeText(Dashboard_faculty.this,"Setting",Toast.LENGTH_LONG ).show();
                break;
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_faculty);


        attendance =findViewById(R.id.Attendance);
        viewattendance=findViewById(R.id.viewattendance);
        viewstudent =findViewById(R.id.viewstudent);
        update=findViewById(R.id.update);


        viewattendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3 = new Intent(Dashboard_faculty.this, Viewattendance.class);
                startActivity(intent3);
            }
        });


     update.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             Intent intent2 = new Intent(Dashboard_faculty.this, updateAttendance.class);
             startActivity(intent2);
         }
     });

    attendance.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent1 = new Intent(Dashboard_faculty.this, Attendance.class);
            startActivity(intent1);
        }
    });

    viewstudent.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(Dashboard_faculty.this, ViewStudent.class);
            startActivity(intent);
        }


            SharedPreferences preferences = getSharedPreferences(FILE_NAME, MODE_PRIVATE);
           String extraEmail =preferences.getString(KEY_EMAIL,"");

        });




    }
}
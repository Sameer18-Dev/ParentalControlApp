package parentalcontrolapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.parentalcontrolapp.R;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class Dashboard extends AppCompatActivity {

    public static final String FILE_NAME = "my_file" ;
    public static final String KEY_EMAIL ="email" ;
    public static final String Role_KEY ="role" ;
    public static final String KEY_Faculty ="FacultyID" ;


    private Button btn, btn1, btn2;
    private TextView textEmail,addteacher,addstudent,viewteacher,viewstudent;
    String extraEmail = "";

    @Override
    public void onBackPressed() {
        if(!extraEmail.isEmpty()) {
            finish();
        }
        }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

       addteacher =findViewById(R.id.Addteacher);
       addstudent =findViewById(R.id.Addstudent);
       viewteacher=findViewById(R.id.Viewteacher);
       viewstudent=findViewById(R.id.viewstudent);


       viewstudent.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent=new Intent(Dashboard.this, ViewStudent.class);
               startActivity(intent);
           }
       });

       viewteacher.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent =new Intent(Dashboard.this, ViewFaculty.class);
               startActivity(intent);
           }
       });

        addteacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboard.this, Add_faculty.class);
                startActivity(intent);
            }
        });


       addstudent.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent =new Intent(Dashboard.this, AddStudent.class);
               startActivity(intent);
           }
       });


        SharedPreferences preferences = getSharedPreferences(FILE_NAME, MODE_PRIVATE);
        extraEmail =preferences.getString(KEY_EMAIL,"");




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
                editor.remove(KEY_EMAIL);
                editor.remove(Role_KEY);
                editor.remove(KEY_Faculty);

                editor.apply();
                startActivity(new Intent(Dashboard.this, LoginActivity.class));
                finish();
                break;
            case  R.id.setting:
                Toast.makeText(Dashboard.this,"Setting",Toast.LENGTH_LONG ).show();
                break;
        }
        return true;
    }
}
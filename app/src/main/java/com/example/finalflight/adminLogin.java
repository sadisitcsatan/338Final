package com.example.finalflight;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.finalflight.DB.MainDatabase;
import com.example.finalflight.DB.UserDao;

public class adminLogin extends AppCompatActivity {
    UserDao mUserDao;
    EditText mUsername;
    EditText mPassword;
    TextView mResullt;
    Button mLogin;
    User mUse;
    int wrong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);
        wrong =(int) 0;
        mResullt = findViewById(R.id.ress);
        mUserDao = Room.databaseBuilder(this, MainDatabase.class, MainDatabase.dbName)
                .allowMainThreadQueries()
                .build()
                .getUserDao();
        mUsername =(EditText) findViewById(R.id.username);
        mPassword =(EditText) findViewById(R.id.password);
        mLogin =(Button) findViewById(R.id.button3);

    }
    public void enter(View view){
        String username = mUsername.getText().toString();
        String password = mPassword.getText().toString();
        if (!username.equals("") && !password.equals("")) {
            mUse = mUserDao.getUser(username);
        }

        if (mUse != null) {
            //mResullt.setText("mUse is not null");
            //mResullt.setText(mUse.toString());
            if (mUse.getPassword().equals(password) && mUse.isAdmin()){
                SharedPreferences pref = this.getSharedPreferences("MyPref",0);
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("Username",username);
                editor.putBoolean("Logged_In",true);
                editor.commit();
                String output = "Successfully Logged In";
                Toast.makeText(this,output,Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(adminLogin.this,AdminHome.class);
                startActivity(intent);
                return;
            }else{
                mResullt.setText("Invalid Username or password");
                wrong++;
            }
        }else{
            wrong++;
            mResullt.setText("Invalid Username or password");
        }
        if (wrong >= 3){
            Intent intent = new Intent(adminLogin.this,MainActivity.class);
            startActivity(intent);
            return;
        }
    }
}

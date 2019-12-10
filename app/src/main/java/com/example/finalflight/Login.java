package com.example.finalflight;

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

public class Login extends AppCompatActivity {
    UserDao mUserDao;
    EditText mUsername;
    EditText mPassword;
    TextView mResullt;
    Button mLogin;
    User mUse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mResullt = findViewById(R.id.resullt);
        mUserDao = Room.databaseBuilder(this, MainDatabase.class, MainDatabase.dbName)
                .allowMainThreadQueries()
                .build()
                .getUserDao();
        mUsername =(EditText) findViewById(R.id.username);
        mPassword =(EditText) findViewById(R.id.password);
        mLogin =(Button) findViewById(R.id.login);

    }
    public void submit(View view){
        String username = mUsername.getText().toString();
        String password = mPassword.getText().toString();
        mUse = mUserDao.getUser(username);

        if (mUse != null) {
            //mResullt.setText("mUse is not null");
            //mResullt.setText(mUse.toString());
            if (mUse.getPassword().equals(password) && mUse.isAdmin()){
                //cahnge intent
                SharedPreferences pref = this.getSharedPreferences("MyPref",0);
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("Username",username);
                editor.putBoolean("Logged_In",true);
                editor.commit();
                String output = "Successfully Logged In";
                Toast.makeText(this,output,Toast.LENGTH_SHORT).show();
            }else {
                mResullt.setText("Invalid Username or password");
            }
        }else{
            mResullt.setText("Invalid Username or password");
        }
    }
}

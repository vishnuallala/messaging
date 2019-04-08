package com.vishnu.twitter;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class login extends AppCompatActivity {
    private EditText login_mail,login_pass;
    private Button login_login,login_signup;
    private ImageView login_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login_mail=findViewById(R.id.login_mail);
        login_pass=findViewById(R.id.login_pass);
        login_login=findViewById(R.id.login_login);
        login_signup=findViewById(R.id.login_signup);
        login_image=findViewById(R.id.login_image);
        if(ParseUser.getCurrentUser()!=null){
            Intent intent=new Intent(login.this,twitterusers.class);
            startActivity(intent);
        }
        login_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(login.this,signup.class);
                startActivity(intent);

            }
        });
        login_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog progressDialog=new ProgressDialog(login.this);
                progressDialog.setMessage("logging in...");
                progressDialog.show();
                if(login_mail.getText().toString().compareTo("")!=0 && login_pass.getText().toString().compareTo("")!=0){
                    ParseUser.logInInBackground(login_mail.getText().toString(), login_pass.getText().toString(), new LogInCallback() {
                        @Override
                        public void done(ParseUser user, ParseException e) {
                            if(user!=null && e==null){
                                Intent intent=new Intent(login.this,twitterusers.class);
                                startActivity(intent);
                                progressDialog.dismiss();
                                finish();
                            }
                        }
                    });
                }
            }
        });
    }
}

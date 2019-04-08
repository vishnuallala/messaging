package com.vishnu.twitter;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;

public class signup extends AppCompatActivity{
    private EditText signup_mail,signup_pass,signup_user;
    private Button signup_signup,signup_login;
    private ImageView signup_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        signup_mail=findViewById(R.id.signup_mail);
        signup_pass=findViewById(R.id.signup_pass);
        signup_user=findViewById(R.id.signup_user);
        signup_signup=findViewById(R.id.signup_signup);
        signup_login=findViewById(R.id.signup_login);
        signup_image=findViewById(R.id.signup_image);
        if(ParseUser.getCurrentUser()!=null){
            Intent intent=new Intent(signup.this,twitterusers.class);
            startActivity(intent);
            finish();
        }
        signup_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (signup_mail.getText().toString().compareTo("") != 0 && signup_pass.getText().toString().compareTo("") != 0) {
                    ParseUser parseUser = new ParseUser();
                    parseUser.setPassword(signup_pass.getText().toString());
                    parseUser.setEmail(signup_mail.getText().toString());
                    parseUser.setUsername(signup_user.getText().toString());
                    final ProgressDialog progressDialog=new ProgressDialog(signup.this);
                    progressDialog.setMessage("signing up.....");
                    progressDialog.show();
                    parseUser.signUpInBackground(new SignUpCallback() {
                        @Override
                        public void done(ParseException e) {
                            if(e==null){
                                progressDialog.dismiss();
                                Intent intent=new Intent(signup.this,twitterusers.class);
                                startActivity(intent);
                                finish();
                            }
                        }
                    });
                }
            }
        });
        signup_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(signup.this,login.class);
                startActivity(intent);
            }
        });
    }
}

package com.vishnu.twitter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class tweet extends AppCompatActivity {
    private EditText tweet_msg;
    private Button send,button;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tweet);
        tweet_msg=findViewById(R.id.tweet_msg);
        send=findViewById(R.id.send);
        button=findViewById(R.id.button2);
        listView=findViewById(R.id.listview);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ArrayList<HashMap<String,String>> tweetlist=new ArrayList<>();
                final SimpleAdapter simpleAdapter=new SimpleAdapter(tweet.this,tweetlist,android.R.layout.simple_list_item_2,new String[]{"key","value"},new int[]{android.R.id.text1,android.R.id.text2});
                ParseQuery<ParseObject> parseQuery=ParseQuery.getQuery("tweets");
                parseQuery.whereContainedIn("user",ParseUser.getCurrentUser().getList("followers"));
                parseQuery.findInBackground(new FindCallback<ParseObject>() {
                    @Override
                    public void done(List<ParseObject> objects, ParseException e) {
                        if(objects.size()>0 && e==null){
                            for(ParseObject x:objects){
                                HashMap<String,String> mesg=new HashMap<>();
                                mesg.put("key",x.getString("user"));
                                mesg.put("value",x.getString("message"));
                                tweetlist.add(mesg);
                            }
                            listView.setAdapter(simpleAdapter);
                        }
                    }
                });

            }
        });
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tweet_msg.getText().toString().compareTo("")!=0){
                    ParseObject parseObject=new ParseObject("tweets");
                    parseObject.put("message",tweet_msg.getText().toString());
                    parseObject.put("user", ParseUser.getCurrentUser().getUsername());
                    parseObject.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if(e==null){
                                Toast.makeText(tweet.this,"saved",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }
}

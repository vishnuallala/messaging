package com.vishnu.twitter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.LogOutCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;

public class twitterusers extends AppCompatActivity {
    private ListView listView;
    private ArrayAdapter arrayAdapter;
    private ArrayList<String> arrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_twitterusers);
        listView=findViewById(R.id.listview);
        arrayList=new ArrayList<>();
        arrayAdapter=new ArrayAdapter<>(twitterusers.this,android.R.layout.simple_list_item_checked,arrayList);
        listView.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CheckedTextView checkedTextView=(CheckedTextView) view;
                if(checkedTextView.isChecked()){
                    Toast.makeText(twitterusers.this,arrayList.get(position)+" is followed",Toast.LENGTH_SHORT).show();
                    ParseUser.getCurrentUser().add("followers",arrayList.get(position));
                }
                else{
                    Toast.makeText(twitterusers.this,arrayList.get(position)+" is unfollowed",Toast.LENGTH_SHORT).show();
                    ParseUser.getCurrentUser().getList("followers").remove(arrayList.get(position));
                    List currentList=ParseUser.getCurrentUser().getList("followers");
                    ParseUser.getCurrentUser().remove("followers");
                    ParseUser.getCurrentUser().put("followers",currentList);
                }
                ParseUser.getCurrentUser().saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if(e==null){
                            Toast.makeText(twitterusers.this,"saved",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        final ParseQuery<ParseUser> parseQuery=new ParseUser().getQuery();
        parseQuery.whereNotEqualTo("username",ParseUser.getCurrentUser().getUsername());
        parseQuery.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> objects, ParseException e) {
                if(objects.size()>0 && e==null) {
                    for (ParseUser name : objects) {
                        arrayList.add(name.getUsername());
                    }
                    listView.setAdapter(arrayAdapter);
                   for (String users : arrayList) {
                        if (ParseUser.getCurrentUser().getList("followers")!=null && ParseUser.getCurrentUser().getList("followers").contains(users)) {
                            listView.setItemChecked(arrayList.indexOf(users), true);
                        }
                    }
               }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.send){
            Intent intent=new Intent(twitterusers.this,tweet.class);
            startActivity(intent);
        }
        if(item.getItemId()==R.id.logout){
            ParseUser.getCurrentUser().logOutInBackground(new LogOutCallback() {
                @Override
                public void done(ParseException e) {
                    Intent intent=new Intent(twitterusers.this,login.class);
                    startActivity(intent);
                    finish();
                }
            });
        }
        return super.onOptionsItemSelected(item);
    }
}

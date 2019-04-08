package com.vishnu.twitter;
import com.parse.Parse;
import android.app.Application;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("IYvaUe2EO7ZS6Vjjh7Q2pWXUViV7BQdNfypNdLKF")
                // if defined
                .clientKey("15HaokYzn743GylqveX4iLA5iU5ahDYebSJLiHGG")
                .server("https://parseapi.back4app.com/")
                .build()
        );
    }
}

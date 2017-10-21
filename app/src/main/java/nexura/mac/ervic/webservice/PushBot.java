package nexura.mac.ervic.webservice;

import android.app.Application;

import com.pushbots.push.Pushbots;

/**
 * Created by ervic on 20/10/17.
 */

public class PushBot extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Pushbots.sharedInstance().init(this);
    }
}

package nexura.mac.ervic.webservice;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.pushbots.push.Pushbots;

public class MainActivity extends AppCompatActivity {
    Button sube, muestra, base;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Pushbots.sharedInstance().registerForRemoteNotifications();
        muestra = (Button)findViewById(R.id.muestra);
        sube = (Button)findViewById(R.id.sube);
        base = (Button)findViewById(R.id.base);

        muestra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,Muestra.class);
                startActivity(intent);
            }
        });
    }
}

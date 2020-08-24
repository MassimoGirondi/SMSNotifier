package net.girondi.smsnotifier;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        PreferencesManager pm =new PreferencesManager(this);
        ((EditText) findViewById(R.id.sender)).setText(pm.get("sender"));
        ((EditText) findViewById(R.id.recipient)).setText(pm.get("recipient"));
        ((EditText) findViewById(R.id.server)).setText(pm.get("server") == "" ? "smtp.gmail.com" : pm.get("server"));
        ((EditText) findViewById(R.id.server_port)).setText(pm.get("port") == "" ? "465" : pm.get("port"));
        ((EditText) findViewById(R.id.password)).setText(pm.get("password"));



        ((Button) findViewById(R.id.save_btn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                PreferencesManager pm =new PreferencesManager(MainActivity.this);
                pm.set("recipient", ((EditText) MainActivity.this.findViewById(R.id.recipient)).getText().toString());
                pm.set("sender", ((EditText) MainActivity.this.findViewById(R.id.sender)).getText().toString());
                pm.set("server", ((EditText) MainActivity.this.findViewById(R.id.server)).getText().toString());
                pm.set("port", ((EditText) MainActivity.this.findViewById(R.id.server_port)).getText().toString());
                pm.set("password", ((EditText) MainActivity.this.findViewById(R.id.password)).getText().toString());

            }
        });

        ((Button) findViewById(R.id.test_btn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                PreferencesManager pm =new PreferencesManager(MainActivity.this);


                SendMail sm = new SendMail(MainActivity.this,
                        pm.get("sender"), pm.get("recipient"),
                        "Test message", "This is a message from your notifier!",
                        pm.get("server"),
                        pm.get("password"),
                        Integer.parseInt(pm.get("port")));


                sm.execute();
            }
        });






    }


}
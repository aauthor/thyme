package space.aauthor.thyme;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.os.Vibrator;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private String state;
    private FloatingActionButton fab;
    private TextView statusView;

    String ON = "ON";
    String OFF = "OFF";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        fab = (FloatingActionButton) findViewById(R.id.fab);
        statusView = (TextView) findViewById(R.id.statusView);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Vibrator v = (Vibrator) getSystemService(VIBRATOR_SERVICE);
                // Vibrate for 500 milliseconds
                v.vibrate(500);

                updateState();
                if (state == ON) {
                    showOn();
                } else {
                    showOff();
                }

            }
        });
    }

    public void updateState() {
        if (state == ON) {
            state = OFF;
        } else {
            state = ON;
        }
    }

    public void showOff() {
        statusView.setText("Off");
    }

    public void showOn() {
        statusView.setText("On");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

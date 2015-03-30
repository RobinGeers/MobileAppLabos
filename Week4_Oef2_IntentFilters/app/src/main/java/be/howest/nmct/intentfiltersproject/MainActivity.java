package be.howest.nmct.intentfiltersproject;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.util.List;


public class MainActivity extends Activity {

    private Button btnLaunch, btnOnderzoek4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Attribuut koppelen aan control-view
        btnLaunch = (Button) findViewById(R.id.btnLaunch);
        btnOnderzoek4 = (Button) findViewById(R.id.btnOnderzoek4);

        btnLaunch.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                launchWithAction(v);
            }
        });

        btnOnderzoek4.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                onderzoek4();
            }
        });
    }

    private void onderzoek4() {
        Intent intent = new Intent(Constants.ACTION_IMPLY, Uri.parse("xtp:///somedata"));
        startActivity(intent);
    }

    private void launchWithAction(View v) {

        // intent -> Geeft de apps terug die naar deze action luisteren
        Intent intent = new Intent(Constants.ACTION_IMPLY);
        //intent.addCategory(Intent.CATEGORY_CAR_DOCK);

        // Controleren of er een app beschikbaar is -> Anders crasht app als er geen aanwezig is
        // Krijg een lijst van alle apps die luisteren naar de intent 'IMPLY'
        PackageManager packageManager = getPackageManager();
        List activities = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        boolean isIntentSafe = activities.size() > 0;

        // Als er een app bestaat die luistert naar de intent 'IMPLY' -> start die app
        if (isIntentSafe) {
            startActivity(intent);
        }
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

package be.howest.nmct.implicitintents;

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

    private Button btnBrowse, btnMakeCall, btnDialNumber, btnGeolocation, btnContacts, btnEdit, btnCalculateBMI;
    public static final String ACTION_BMI = "be.howest.nmct.week2_oef2_bmi.intent.action.BMI";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Koppel attributen aan control-views (buttons)
        btnBrowse = (Button) findViewById(R.id.btnBrowse);
        btnMakeCall = (Button) findViewById(R.id.btnMakeCall);
        btnDialNumber = (Button) findViewById(R.id.btnDialNumber);
        btnGeolocation = (Button) findViewById(R.id.btnGeolocation);
        btnContacts = (Button) findViewById(R.id.btnContacts);
        btnEdit = (Button) findViewById(R.id.btnEdit);
        btnCalculateBMI = (Button) findViewById(R.id.btnCalculate);

        btnBrowse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openBrowser();
            }
        });
        btnMakeCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeCall();
            }
        });
        btnDialNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialNumber();
            }
        });
        btnGeolocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLongtitudeAndLatitude();
            }
        });
        btnContacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showContacts();
            }
        });
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editFirstContact();
            }
        });
        btnCalculateBMI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateBMI();
            }
        });

    }

    private void calculateBMI() {

        // intent -> Geeft de apps terug die naar deze action luisteren
        Intent intent = new Intent(ACTION_BMI);

        // Controleren of er een app beschikbaar is -> Anders crasht app als er geen aanwezig is
        // Krijg een lijst van alle apps die luisteren naar de intent 'BMI'
        PackageManager packageManager = this.getPackageManager();
        List activities = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        boolean isIntentSafe = activities.size() > 0;

        // Als er een app bestaat die luistert naar de intent 'BMI' -> start die app
        if (isIntentSafe) {
            startActivity(intent);
        }
    }

    private void editFirstContact() {
        Intent intent = new Intent(Intent.ACTION_EDIT, Uri.parse("content://contacts/people/1"));
        startActivity(intent);
    }

    private void showContacts() {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("content://contacts/people"));
        startActivity(intent);
    }

    private void showLongtitudeAndLatitude() {
        Intent intent = new Intent(MainActivity.this, LocationActivity.class);
        startActivity(intent);
    }

    private void dialNumber() {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:0488562351"));
        startActivity(intent);
    }

    // Eerst permissie toestaan in AndroidManifest.xml
    private void makeCall() {
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:0488562351"));
        startActivity(intent);
    }

    private void openBrowser() {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.nmct.be"));
        startActivity(intent);
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

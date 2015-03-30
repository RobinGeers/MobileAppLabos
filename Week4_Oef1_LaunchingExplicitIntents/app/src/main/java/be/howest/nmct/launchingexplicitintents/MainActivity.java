package be.howest.nmct.launchingexplicitintents;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends Activity {

    private Button btnStartExplicit, btnDialog;
    public static final int REQUEST_CODE_EXPLICIT = 1; // Het request om de 'Explicit Activity' op te starten krijgt id 1 toegewezen
    // De 2 'Extra info' variabelen maken we hier aan
    // --> Omdat we ze in deze activity willen weergeven
    public static final String EXTRA_INFO_LASTNAME = "Achternaam";
    public static final String EXTRA_INFO_AGE = "Leeftijd";
    private EditText editTextScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Koppel attribuut aan control-view
        btnStartExplicit = (Button) findViewById(R.id.btnStartExplicit);
        btnDialog = (Button)  findViewById(R.id.btnDialog);

        btnStartExplicit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchSecondActivity();
            }
        });
        
        btnDialog.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
    }

    // Maak in de map 'layout' een nieuwe layout resource file: score.xml (Design van dialog venster)
    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();

        // Declareer de view 'Score'
        View viewScore = inflater.inflate(R.layout.score, null);

        // Stel het design van 'score.xml' in als dialog venster
        builder.setView(viewScore);

        // Koppel attribuut aan control-view waar de score ingegeven wordt
        editTextScore = (EditText) viewScore.findViewById(R.id.editTextScore); // BELANGRIJK!!!!! viewScore. --> MOET voor findViewById staan

        // Voeg knoppen toe aan het dialog venster
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                    String score = editTextScore.getText().toString();
                    Toast.makeText(MainActivity.this, "Score: " + score, Toast.LENGTH_SHORT).show();
            }
        })
        .setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        // Toon dialog venster
        builder.create();
        builder.show();
    }

    private void launchSecondActivity() {
        // Start de tweede activity (Geef extra info mee -> putExtra)
        Intent intent = new Intent(MainActivity.this, ExplicitActivity.class);
        intent.putExtra(ExplicitActivity.EXTRA_INFO, "2NMCT");
        //startActivity(intent);
        // Resultaat ontvangen. REQUEST_CODE -> Identificeert je request
        startActivityForResult(intent, REQUEST_CODE_EXPLICIT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


        // Controleer op welke vraag (request) we een antwoord (result) hebben gekregen
        switch (requestCode) {
            case REQUEST_CODE_EXPLICIT:
                // Welk antwoord (result) hebben we gekregen?
                switch (resultCode) {
                    case RESULT_CANCELED:
                        Toast.makeText(MainActivity.this, "User selects cancel", Toast.LENGTH_SHORT).show();
                        break;
                    case RESULT_OK:
                        Toast.makeText(MainActivity.this, "User selects OK", Toast.LENGTH_SHORT).show();
                    case ExplicitActivity.RESULT_CODE_CUSTOM:
                        // Haal gegevens uit data parameter -> Toon ze
                        String lastname = data.getStringExtra(MainActivity.EXTRA_INFO_LASTNAME);
                        int age = data.getIntExtra(MainActivity.EXTRA_INFO_AGE, 0);
                        Toast.makeText(MainActivity.this, "Achternaam: " + lastname + ", leeftijd: " + age, Toast.LENGTH_SHORT).show();
                }
            default:  super.onActivityResult(requestCode, resultCode, data);
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

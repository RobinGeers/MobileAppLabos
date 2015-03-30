package be.howest.nmct;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends Activity {

    // Attributen declareren
    private Button btnSelecteerGeboortejaar, btnSelecteerHoroscoop;
    private TextView textViewResultGeboortejaar;
    private ImageView imageViewResultHoroscoop;

    public static final String EXTRA_BIRTHDAY = "be.howest.nmct.BIRTHDAY";
    public static final String EXTRA_HOROSCOPE = "be.howest.nmct.HOROSCOPE";
    private final int REQUEST_CODE_BIRTHDAY = 1; // Het request om SelectGeboortejaarActivity te openen krijgt id 1 toegewezen
    private final int REQUEST_CODE_HOROSCOPE = 2; // Het request om SelectGeboortejaarActivity te openen krijgt id 1 toegewezen
    public int gekozenHoroscoop = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Attributen koppelen aan control-views
        textViewResultGeboortejaar = (TextView) findViewById(R.id.textViewGeboortejaar);
        btnSelecteerGeboortejaar = (Button) findViewById(R.id.buttonSelecteerGeboortejaar);
        btnSelecteerHoroscoop = (Button) findViewById(R.id.buttonSelecteerHoroscoop);
        imageViewResultHoroscoop = (ImageView) findViewById(R.id.imageViewResultaatHoroscoop);

        // Click events
        btnSelecteerGeboortejaar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selecteerGeboortejaar();
            }
        });

        btnSelecteerHoroscoop.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                selecteerHoroscoop();
            }
        });

        // Indien het geboortejaar en horoscoop waren opgeslagen -> Vul ze in
        if (savedInstanceState != null) {
            String gekozenGeboortejaar = savedInstanceState.getString(EXTRA_BIRTHDAY);
            textViewResultGeboortejaar.setText(gekozenGeboortejaar);

            gekozenHoroscoop = savedInstanceState.getInt(EXTRA_HOROSCOPE);
            imageViewResultHoroscoop.setImageResource(getResourceId(gekozenHoroscoop)); // Niet vergeten afbeelding toe te wijzen!!
        }
    }

    private void selecteerGeboortejaar() {
        Intent intent = new Intent(MainActivity.this, SelectGeboortejaarActivity.class);
        startActivityForResult(intent, REQUEST_CODE_BIRTHDAY); // Start Activity en verwacht een result terug
    }

    private void selecteerHoroscoop() {
        Intent intent = new Intent(MainActivity.this, HoroscoopActivity.class);
        startActivityForResult(intent, REQUEST_CODE_HOROSCOPE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        // Controleer op welke vraag (request) we een antwoord (result) hebben gekregen
        switch (requestCode) {
            case REQUEST_CODE_BIRTHDAY:
                switch (resultCode) {  // Welk antwoord (result) hebben we gekregen?
                    case RESULT_CANCELED: Toast.makeText(MainActivity.this, "Er werd geen geboortejaar geselecteerd", Toast.LENGTH_SHORT).show();
                        break;
                    case RESULT_OK:
                        String geboorteJaar = data.getStringExtra(EXTRA_BIRTHDAY);
                        textViewResultGeboortejaar.setText("Geboortejaar: " + geboorteJaar);
                        break;
                }
                break;
           case REQUEST_CODE_HOROSCOPE:
                switch (resultCode) { // Welk antwoord (result) hebben we gekregen?
                    case RESULT_CANCELED: Toast.makeText(MainActivity.this, "Er werd geen horoscoop geselecteerd", Toast.LENGTH_SHORT).show();
                        break;
                    case RESULT_OK:
                        gekozenHoroscoop = data.getIntExtra(EXTRA_HOROSCOPE, 0);
                        if (gekozenHoroscoop > 0)
                        imageViewResultHoroscoop.setImageResource(getResourceId(gekozenHoroscoop));
                        break;
                }
               break;
        }
    }

    private int getResourceId(int horoscoop) {
        switch (horoscoop) {
            case 0: return R.drawable.waterman;
            case 1: return R.drawable.vissen;
            case 2: return R.drawable.ram;
            case 3: return R.drawable.stier;
            case 4: return R.drawable.tweeling;
            case 5: return R.drawable.kreeft;
            case 6: return R.drawable.leeuw;
            case 7: return R.drawable.maagd;
            case 8: return R.drawable.weegschaal;
            case 9: return R.drawable.schorpioen;
            case 10: return R.drawable.boogschutter;
            case 11: return R.drawable.steenbok;
            default: return -1;
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // Sla het gekozen Geboortejaar en gekozen horoscoop op
        outState.putString(EXTRA_BIRTHDAY, textViewResultGeboortejaar.getText().toString());
        outState.putInt(EXTRA_HOROSCOPE, gekozenHoroscoop);
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

package be.howest.nmct;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends Activity implements ChangeFragment.onChangeFragmentListener, BitcoinRateFragment.onBitcoinRateFragmentListener {

    private float currentWisselkoers = 20.0f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Ipv new ChangeFragment, gebruik newInstance
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, ChangeFragment.newInstance(currentWisselkoers), "changeFragment") // Derde parameter: Geeft tag aan fragment (Belangrijk om fragment op te vragen/tonen!!)
                    .commit();
        }
    }

    private void showFragmentChange(float newWisselkoers) {
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.popBackStack(); // Verwijdert de transactie van de backstack

        // Vraag het bestaande fragment op ipv een nieuwe te maken (Tag meegegeven bij het creëren ervan)
        ChangeFragment fragment = (ChangeFragment) getFragmentManager().findFragmentByTag("changeFragment");
        fragment.setCurrentRateBitcoinInEuro(newWisselkoers);

        // Zorgt ervoor dat je fragment opent met de nieuwe wisselkoers (die is ingevuld)
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        // Wat er ook getoond wordt op het scherm -> Vervang het door dit fragment
        fragmentTransaction.replace(R.id.container, fragment, "changeFragment"); // Derde parameter: Geeft tag aan fragment (Belangrijk om fragment op te vragen/tonen!!)

        fragmentTransaction.commit();
    }

    private void showFragmentBitcoinRate(float oldwisselKoers) {

        // Zorgt ervoor dat je fragment opent met de recentste wisselkoers (die is ingevuld)
        Fragment bitcoinRateFragment = BitcoinRateFragment.newInstance(oldwisselKoers);
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();

        // Wat er ook getoond wordt op het scherm -> Vervang het door dit fragment
        fragmentTransaction.replace(R.id.container, bitcoinRateFragment, "bitcoinRateFragment"); // Derde parameter: Geeft tag aan fragment (Belangrijk om fragment op te vragen/tonen!!)
        fragmentTransaction.addToBackStack("changeFragment"); // Plaats het vorige fragment (changeFragment) in de Backstack

        BitcoinRateFragment.newInstance(currentWisselkoers); // Geef de recente wisselkoers mee aan het bitcoinRateFragement

        fragmentTransaction.commit();
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

    // Methode geimplementeerd van ChangeFragment interface
    @Override
    public void onWijzigKoers(float wisselkoers) {
        showFragmentBitcoinRate(wisselkoers);
    }

    // Methode geimplementeerd van BitcoinRateFragment interface
    @Override
    public void onWijzig(float wisselkoers) {
        showFragmentChange(wisselkoers);
    }
}

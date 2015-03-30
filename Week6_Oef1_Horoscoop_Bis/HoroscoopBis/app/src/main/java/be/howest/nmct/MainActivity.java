package be.howest.nmct;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;


public class MainActivity extends Activity implements MainFragment.onChangeFragmentListener, SelectGeboortejaarFragment.onSelecteerGeboortejaarFragmentListener, HoroscoopFragment.onHoroscoopListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new MainFragment())
                    .commit();
        }
    }

    @Override
    public void onSelecteerGeboortejaar() {
        showFragmentSelecteerGeboortejaar();
    }

    @Override
    public void onSelecteerHoroscoop() {
        showFragmentSelecteerHoroscoop();
    }

    @Override
    public void onNieuwGeboortejaarGekozen(String geboortejaar) {
        showFragmentMain(geboortejaar);
    }

    @Override
    public void onNieuwHoroscoop(int horoscoopSelected) {
        showFragmentMainHoroscope(horoscoopSelected);
    }

    private void showFragmentSelecteerGeboortejaar() {

        Fragment geboortejaarFragment = SelectGeboortejaarFragment.newInstance();
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();

        // Wat er ook getoond wordt op het scherm -> Vervang het door dit fragment
        fragmentTransaction.replace(R.id.container, geboortejaarFragment);
        fragmentTransaction.addToBackStack("MainFragment");

        fragmentTransaction.commit();
    }


    private void showFragmentSelecteerHoroscoop() {
        Fragment horoscoopFragment = HoroscoopFragment.newInstance();
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();

        // Wat er ook getoond wordt op het scherm -> Vervang het door dit fragment
        fragmentTransaction.replace(R.id.container, horoscoopFragment);
        fragmentTransaction.addToBackStack("MainFragment");

        fragmentTransaction.commit();
    }

    private void showFragmentMain(String gekozenGeboortejaar) {
        Fragment mainFragment = MainFragment.newInstance(gekozenGeboortejaar); // Geef het gekozen geboortejaar mee aan MainFragment
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();

        // Wat er ook getoond wordt op het scherm -> Vervang het door dit fragment
        fragmentTransaction.replace(R.id.container, mainFragment);
        fragmentTransaction.addToBackStack("SelectGeboortejaarFragment");

        fragmentTransaction.commit();
    }

    private void showFragmentMainHoroscope(int horoscoopSelected) {
        Fragment mainFragment = MainFragment.newInstance(horoscoopSelected); // Geef het gekozen geboortejaar mee aan MainFragment
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();

        // Wat er ook getoond wordt op het scherm -> Vervang het door dit fragment
        fragmentTransaction.replace(R.id.container, mainFragment);
        fragmentTransaction.addToBackStack("HoroscoopFragment");

        fragmentTransaction.commit();
    }


    /*@Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // Sla het gekozen Geboortejaar en gekozen horoscoop op
        outState.putString(MainFragment.EXTRA_BIRTHDAY, MainFragment.gekozenGeboorteJaar);
        outState.putInt(MainFragment.EXTRA_HOROSCOPE, MainFragment.gekozenHoroscoop);
    }*/

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

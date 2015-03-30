package be.howest.nmct;


import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class BitcoinRateFragment extends Fragment {

    // Attributen declareren
    private EditText editTextRate;
    private Button btnWijzigBitcoinRate;

    private float rate1BitcoinInEuros; // De wisselkoers die is ingevuld

    static final String BITCOIN_RATE = "be.howest.nmct.NEW_BITCOIN_RATE";

    private onBitcoinRateFragmentListener bitcoinRateListener;

    public static final String PREFS_NAME = "MyPrefsFile";

    // Lege constructor
    public BitcoinRateFragment() {
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // Controleren of de Activity de interface implementeerd -> Zodat Activity met fragment kan communiceren
        try {
            bitcoinRateListener = (onBitcoinRateFragmentListener) activity;
        }
        catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement ChangeFragmentListener");
        }
    }

    // Data ontvangen uit Activity
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Indien er een oorspronkelijke waarde is -> Steek die in de ingevulde wisselkoers
        if (getArguments() != null) {
            rate1BitcoinInEuros = getArguments().getFloat(BITCOIN_RATE);
        }

        // Opgeslagen data terugplaatsen
        // -> Haal waardes uit SharedPreferences
        SharedPreferences data = getActivity().getSharedPreferences(PREFS_NAME, 0);
        float wisselkoers = data.getFloat("Wisselkoers", 0);

        // Als er iets in zit plaats het in de variabele (daarna wordt de variabele in onCreateView in editText geplaatst)
        if (wisselkoers != 0) {
            rate1BitcoinInEuros = wisselkoers;
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View viewBitcoinRate = inflater.inflate(R.layout.fragment_bitcoin_rate, container, false);

        // Attributen koppelen aan control-views
        editTextRate = (EditText) viewBitcoinRate.findViewById(R.id.editTextRate);
        btnWijzigBitcoinRate = (Button) viewBitcoinRate.findViewById(R.id.btnWijzigBitcoinRate);

        btnWijzigBitcoinRate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                getBitcoinRate(); // Mag pas uitgevoerd worden als er op geklikt is
                bitcoinRateListener.onWijzig(rate1BitcoinInEuros);
            }
        });

        setBitcoinRate();

        return viewBitcoinRate;
    }

    @Override
    public void onStop() {
        super.onStop();

        // Bewaar wisselkoers als app stopt in SharedPreferences (key, value)
        SharedPreferences data = getActivity().getSharedPreferences(PREFS_NAME, 0); //(0 -> private mode)
        SharedPreferences.Editor editor = data.edit();
        editor.putFloat("Wisselkoers", Float.parseFloat(String.valueOf(rate1BitcoinInEuros)));
        editor.commit();
        // Data ophalen in onCreate
    }

    private void setBitcoinRate() {
        // Toont de oorspronkelijke of laatst aangepaste waarde
        editTextRate.setText(String.valueOf(getArguments().getFloat(BITCOIN_RATE, 100))); // Oorspronkelijk = 100
    }

    private void getBitcoinRate() {
        // Als er iets is ingevuld
        if (!editTextRate.getText().toString().equals("")) {
            rate1BitcoinInEuros = Float.parseFloat(editTextRate.getText().toString());
        }
    }

    // Data van Activity sturen naar een Fragment
    // -> newInstance levert object van Fragment af
    public static BitcoinRateFragment newInstance(float bitcoinrate) {
        BitcoinRateFragment fragment = new BitcoinRateFragment();

        // Steek de waarde van de ingevulde wisselkoers in Bundle (mandje)
        Bundle args = new Bundle();
        args.putFloat(BITCOIN_RATE, bitcoinrate);
        fragment.setArguments(args);

        return fragment;
    }

    // Interface maken -> Staat een Fragment toe om te communiceren met zijn Activity
    public interface onBitcoinRateFragmentListener {
        public void onWijzig(float wisselkoers);
    }
}

package be.howest.nmct;


import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class ChangeFragment extends Fragment {

    // Attributen declareren
    private EditText editTextEuro, editTextBitcoin;
    private Button btnNaarBitcoin, btnNaarEuro, btnWijzigKoers;
    private TextView textViewResult;

    private float currentRateBitcoinInEuro = 100.0f; // Houdt wisselkoers van 1 bitcoin -> euro's bij

    private onChangeFragmentListener changeListener;

    // Lege constructor
    public ChangeFragment() {

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // Controleren of de Activity de interface implementeerd -> Zodat Activity met fragment kan communiceren
        try {
            changeListener = (onChangeFragmentListener) activity;
        }
        catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement ChangeFragmentListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Indien er een oorspronkelijke waarde is -> Steek die in de ingevulde wisselkoers
        if (getArguments() != null) {
            currentRateBitcoinInEuro = getArguments().getFloat(BitcoinRateFragment.BITCOIN_RATE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View viewChange = inflater.inflate(R.layout.fragment_change, container, false);

        // Attributen koppelen aan control-views
        editTextEuro = (EditText) viewChange.findViewById(R.id.editTextEuro);
        editTextBitcoin = (EditText) viewChange.findViewById(R.id.editTextBitcoin);
        btnNaarBitcoin = (Button) viewChange.findViewById(R.id.btnNaarBitcoin);
        btnNaarEuro = (Button) viewChange.findViewById(R.id.btnNaarEuro);
        btnWijzigKoers = (Button) viewChange.findViewById(R.id.btnWijzigKoers);
        textViewResult = (TextView) viewChange.findViewById(R.id.textViewResult);

        // Click events
        btnNaarBitcoin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                changeToBitcoin();
            }
        });

        btnNaarEuro.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
             changeToEuro();
            }
        });

        btnWijzigKoers.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                changeListener.onWijzigKoers(currentRateBitcoinInEuro);
            }
        });

        showWisselkoersResult();

        return viewChange;
    }

    private void showWisselkoersResult() {
        textViewResult.setText(String.valueOf("1 Bitcoin = €" + currentRateBitcoinInEuro));
    }

    private void changeToEuro() {
        // Bereken de waarde in Euro door: x aantal Bitcoins * de wisselkoers van 1 bitcoin
        float valueBitcoin = Float.parseFloat(editTextBitcoin.getText().toString());
        float result = valueBitcoin * currentRateBitcoinInEuro;

        editTextEuro.setText(String.valueOf(result));
    }

    private void changeToBitcoin() {
        // Bereken de waarde in Bitcoin door: x aantal Euro's / de wisselkoers van 1 bitcoin
        float valueEuro = Float.parseFloat(editTextEuro.getText().toString());
        float result = valueEuro / currentRateBitcoinInEuro;

        editTextBitcoin.setText(String.valueOf(result));
    }

    // Data van Activity sturen naar een Fragment
    // -> newInstance levert object van Fragment af
    public static ChangeFragment newInstance(float bitcoinrate) {
        ChangeFragment fragment = new ChangeFragment();

        // Verkrijg de waarde van de ingevulde wisselkoers die in Bundle (mandje) zit
        //float ingevuldeWisselkoers = getArguments().getFloat(BitcoinRateFragment.BITCOIN_RATE, bitcoinrate);
        Bundle args = new Bundle();
        args.putFloat(BitcoinRateFragment.BITCOIN_RATE, bitcoinrate);
        fragment.setArguments(args);

        return fragment;
    }

    // Interface maken -> Staat een Fragment toe om te communiceren met zijn Activity
    public interface onChangeFragmentListener {
        public void onWijzigKoers(float wisselkoers); // Deze methode wordt ook uitgevoerd in Activity -> In die methode open je bitCoinFragment
    }
}

package be.howest.nmct.week2_oef1_stopafstand;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by robin_000 on 28/02/2015.
 */
public class StopafstandFragment extends Fragment {

    // Plaats in 'onCreate' van StopafstandActivity 'new StopafstandFragment'

    // Declareer Control-views als attributen
    private EditText editTextSnelheid, editTextReactietijd;
    private TextView textViewResultaat;
    private RadioButton radioButtonDroog, radioButtonNat;
    private Button buttonBereken;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // View aanmaken die verwijst naar fragment & daarna returnen
        View viewStopafstand = inflater.inflate(R.layout.fragment_stopafstand, container, false);

        // Attributen koppelen aan Control-views
        this.editTextSnelheid = (EditText) viewStopafstand.findViewById(R.id.editTextSnelheid);
        this.editTextReactietijd = (EditText) viewStopafstand.findViewById(R.id.editTextReactietijd);
        this.radioButtonDroog = (RadioButton) viewStopafstand.findViewById(R.id.rdbDroog);
        this.radioButtonNat = (RadioButton) viewStopafstand.findViewById(R.id.rdbNat);
        this.buttonBereken = (Button) viewStopafstand.findViewById(R.id.btnBereken);
        this.textViewResultaat = (TextView) viewStopafstand.findViewById(R.id.textViewResultaat);

        // Click event knop 'btnBereken' (ALT + ENTER op View.OnClickListener om methode te implementeren)
        this.buttonBereken.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
            berekenStopafstand();
            }
        });
        return viewStopafstand;
    }

    public void berekenStopafstand() {

        // Ingevulde waardes in variabelen steken
        int snelheid = Integer.parseInt(this.editTextSnelheid.getText().toString());
        float reactietijd = Float.parseFloat(this.editTextReactietijd.getText().toString());

        if (radioButtonDroog.isChecked() == true) {
            StopafstandInfo.WegType wegType = StopafstandInfo.WegType.WEGDEK_DROOG;
            StopafstandInfo info = new StopafstandInfo(snelheid, reactietijd, wegType);
            textViewResultaat.setText(info.getStopafstand() + " meter");
            Toast.makeText(getActivity(),info.getStopafstand() + " meter", Toast.LENGTH_SHORT).show();
        }

        if (radioButtonNat.isChecked() == true) {
            StopafstandInfo.WegType wegType = StopafstandInfo.WegType.WEGDEK_NAT;
            StopafstandInfo info = new StopafstandInfo(snelheid, reactietijd, wegType);
            textViewResultaat.setText(info.getStopafstand() + " meter");
            Toast.makeText(getActivity(),info.getStopafstand() + " meter", Toast.LENGTH_SHORT).show();
        }
    }
}

package be.howest.nmct.week2_oef1_stopafstand;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by robin_000 on 28/02/2015.
 */
public class StopafstandSliderFragment extends Fragment {

    // Plaats in 'onCreate' van StopafstandActivity 'new StopafstandSliderFragment'

    // Declareer Control-views als attributen
    private TextView textViewResultaat, textViewSliderSnelheid, textViewSliderReactietijd;
    private RadioButton radioButtonDroog, radioButtonNat;
    private Button buttonBereken;
    private SeekBar slideSnelheid, slideReactietijd;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // View aanmaken die verwijst naar fragment & daarna returnen
        View viewStopafstandSlider = inflater.inflate(R.layout.fragment_stopafstand_slider, container, false);

        // Attributen koppelen aan Control-views

        this.radioButtonDroog = (RadioButton) viewStopafstandSlider.findViewById(R.id.rdbDroog);
        this.radioButtonNat = (RadioButton) viewStopafstandSlider.findViewById(R.id.rdbNat);
        this.buttonBereken = (Button) viewStopafstandSlider.findViewById(R.id.btnBereken);
        this.textViewResultaat = (TextView) viewStopafstandSlider.findViewById(R.id.textViewResultaat);

        // Sliders
        slideSnelheid = (SeekBar) viewStopafstandSlider.findViewById(R.id.seekBarSnelheid);
        slideReactietijd = (SeekBar) viewStopafstandSlider.findViewById(R.id.seekBarReactietijd);

        // Slider labels
        textViewSliderSnelheid = (TextView) viewStopafstandSlider.findViewById(R.id.textViewSliderSnelheid);
        textViewSliderReactietijd = (TextView) viewStopafstandSlider.findViewById(R.id.textViewSliderReactietijd);

        slideSnelheid.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // ALT + ENTER op OnSeekBarChangeListener om methodes te implementeren
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textViewSliderSnelheid.setText(String.valueOf(progress) + " km/h");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        slideReactietijd.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textViewSliderReactietijd.setText(String.valueOf(progress / 10.0) + " seconden");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        // Click event knop 'btnBereken' (ALT + ENTER op View.OnClickListener om methode te implementeren) ZONDER THIS
        buttonBereken.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                berekenStopafstand();
            }
        });

        return viewStopafstandSlider;
    }

    public void berekenStopafstand() {

        // Waardes van slidersin variabelen steken
        int snelheid = (slideSnelheid.getProgress());
        float reactietijd = (float)(slideReactietijd.getProgress() / 10.0);

        if (radioButtonDroog.isChecked() == true) {
            StopafstandInfo.WegType wegType = StopafstandInfo.WegType.WEGDEK_DROOG;
            StopafstandInfo info = new StopafstandInfo(snelheid, reactietijd, wegType);
            textViewResultaat.setText(info.getStopafstand() + " meter");
            Toast.makeText(getActivity(), info.getStopafstand() + " meter", Toast.LENGTH_SHORT).show();
        }

        if (radioButtonNat.isChecked() == true) {
            StopafstandInfo.WegType wegType = StopafstandInfo.WegType.WEGDEK_NAT;
            StopafstandInfo info = new StopafstandInfo(snelheid, reactietijd, wegType);
            textViewResultaat.setText(info.getStopafstand() + " meter");
            Toast.makeText(getActivity(),info.getStopafstand() + " meter", Toast.LENGTH_SHORT).show();
        }
    }
}

package be.howest.nmct.week3_oef3_lifecyclecolorpicker;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by robin_000 on 28/02/2015.
 */
public class LifecycleColorpickerFragment extends Fragment {

    // Plaats in de 'onCreate' van LifecycleColorpickerActivity dit fragment

    private ColorpickerView colorpicker;
    private static final String SAVED_COLOR = "color";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View viewColorPicker = inflater.inflate(R.layout.fragment_lifecycle_colorpicker, container, false);

        colorpicker = (ColorpickerView) viewColorPicker.findViewById(R.id.myColorPicker);

        // Als er iets is opgeslaan in savedInstancestate (Color) -> Geef dat color aan de colorpicker
        if (savedInstanceState != null) {
            colorpicker.setColor(savedInstanceState.getString(SAVED_COLOR));
        }

        return viewColorPicker;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Indien App crasht -> Save de huidige kleur
        outState.putString(SAVED_COLOR, colorpicker.getColor());
    }
}

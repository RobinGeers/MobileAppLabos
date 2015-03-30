package be.howest.nmct.week3_oef3_lifecyclecolorpicker;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by robin_000 on 28/02/2015.
 */
public class ColorpickerView extends View {
    // Custom View (Colorpicker)

    private String color = "#FFFFFF";
    private Paint paint;
    private Rect rect;


    // Constructors
    public ColorpickerView(Context context) {
        super(context);
        initializePaintAndRect();
    }
    // Deze constructor moet 'Context' en 'AttributeSet' als parameters hebben -> Om instantie van Custom view te maken/bewerken
    public ColorpickerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initializePaintAndRect();
    }

    // Getter & Setter color
    public String getColor() {
        return color;
    }
    // Deze setter wordt uitgevoerd als er een kleur in het Dialogvenster is gekozen
    public void setColor(String color) {
        this.color = color;
        this.paint.setColor(Color.parseColor(color));
        invalidate();
    }

    private void initializePaintAndRect() {
        rect = new Rect(0,0,getWidth(),getHeight()); // Maak rechthoek op ColorView evengroot als ColorView zelf
        paint = new Paint(); // De kleur die de rectangle krijgt
        paint.setColor(Color.parseColor(color)); // String omzetten -> Color

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                showColorDialog();
            }
        });
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Teken de rechthoek op het canvas
        rect.set(0,0,getWidth(), getHeight());
        canvas.drawRect(rect,paint);
    }

    private void showColorDialog() {
        // Toon een dialoog venster op de huidige Acitivty (getContext)
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        builder.setTitle("Pick a color").setItems(R.array.holo_colors, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                selectColor(which);     
            }
        });
        builder.create().show();
    }

    private void selectColor(int which) {
        switch (which) {
            case 0: setColor("#33B5E5");
                break;
            case 1: setColor("#AA66CC");
                break;
            case 2: setColor("#99CC00");
                break;
            case 3: setColor("#FFBB33");
                break;
            case 4: setColor("#FF4444");
                break;
            default:
                break;
        }
    }


}

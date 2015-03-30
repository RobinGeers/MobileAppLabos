package be.howest.nmct.week2_oef1_stopafstand;

/**
 * Created by robin_000 on 13/02/2015.
 */
public class StopafstandInfo {

    private int snelheid;
    private float reactietijd;
    private WegType wegtype;

    // Getters

    public int getSnelheid() {
        return snelheid;
    }

    public float getReactietijd() {
        return reactietijd;
    }

    public WegType getWegtype() {
        return wegtype;
    }

    public enum WegType {
        // f --> Float type
        WEGDEK_DROOG(8f), WEGDEK_NAT(5f);

        private float remvertraging;

        // Getter

        public float getRemvertraging() {
            return remvertraging;
        }

        // Constructor

        WegType(float remvertraging) {
            this.remvertraging = remvertraging;
        }

    }

    // Constructor (Krijgt waarden binnen, kent waarden toe aan variabelen in DEZE (vandaar this.) klasse

    public StopafstandInfo(int snelheid, float reactietijd, WegType wegtype) {
        this.snelheid = snelheid;
        this.reactietijd = reactietijd;
        this.wegtype = wegtype;
    }

    // Methodes
    public float getStopafstand() {
        float stopafstand = ((snelheid / 3.6f) * reactietijd) + ((float)((Math.pow((snelheid / 3.6f),2)))) / (2 * wegtype.getRemvertraging());
        return stopafstand;
    }

}

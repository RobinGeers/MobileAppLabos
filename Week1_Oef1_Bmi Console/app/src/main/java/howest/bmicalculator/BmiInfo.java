package howest.bmicalculator;

/**
 * Created by robin_000 on 13/02/2015.
 */
public class BmiInfo {
    private float height;
    private float mass;
    private float bmiIndex;

    // Alt + Insert om te genereren

    // Get & Set
    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getMass() {
        return mass;
    }

    public void setMass(float mass) {
        this.mass = mass;
    }

    public float getBmiIndex() {
        return bmiIndex;
    }

    // Constructor (Krijgt waardes binnen, kent deze waardes toe aan variabelen van DEZE (vandaar this.) klasse
    public BmiInfo(float height, float mass) {
        this.height = height;
        this.mass = mass;
    }

    // Methode ( /100 -> Cm!!!)
    public void recalculate() {
        bmiIndex = mass / ((height / 100) * (height / 100));
    }

    public enum Category {
        // f -> Float, omdat je anders verward met Double
        GROOT_ONDERGEWICHT(0,15), ERNSTIG_ONDERGEWICHT(15,16), ONDERGEWICHT(16,18.5f), NORMAAL(18.5f,25), OVERGEWICHT(25,30), MATIG_OVERGEWICHT(30,35), ERNSTIG_OVERGEWICHT(35,40), ZEER_GROOT_OVERGEWICHT(40,100);

        private float lowerBoundary;
        private float upperBoundary;

        // Getters
        public float getUpperBoundary() {
            return upperBoundary;
        }

        public float getLowerBoundary() {
            return lowerBoundary;
        }

        Category(float lowerBoundary, float upperBoundary) {
            this.lowerBoundary = lowerBoundary;
            this.upperBoundary = upperBoundary;
        }

        public boolean isInBoundary(float index){
            // Ligt BMI tussen benedengrens & bovengrens
            if(index >= getLowerBoundary() && index <= getUpperBoundary()) {
                return true;
            }
            return false;
        }

        // Controleert alle categorieën
        // --> Ligt BMI in een bepaalde categorie? Geef categorie terug
        public static Category getCategory(float index) {
            for(Category category : Category.values()) {
                if(category.isInBoundary(index)) return category;
            }
            return null;
        }
    }
}

package howest.bmicalculator;

import java.util.Scanner;

/**
 * Created by robin_000 on 13/02/2015.
 */
public class RunBMI {

    // psvm --> Shortcut voor Main methode
    public static void main(String[] args) {

        // Waarden inlezen
        Scanner sc = new Scanner(System.in);
        System.out.print("Geef uw gewicht op: \t");
        float gewicht = sc.nextFloat();

        System.out.print("Geef uw lengte op: \t");
        float lengte = sc.nextFloat();

        // Steek waarden in object
        BmiInfo info = new BmiInfo(lengte, gewicht);

        // Bereken BMI
        info.recalculate();

        // Ga na in welke categorie je BMI zich bevindt
        float bmi = info.getBmiIndex();
        BmiInfo.Category mijnCategorie = BmiInfo.Category.getCategory(bmi);
        System.out.println("Uw Bmi is: " + bmi);
        System.out.println("U behoort tot de categorie: " + mijnCategorie);
    }
}

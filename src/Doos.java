import java.util.ArrayList;

public class Doos {
    private int doosId;
    private double Inhoud;
    private ArrayList<Product> Productinhoud;

    public Doos(int doosId) {
        this.doosId = doosId;
        Inhoud = 150;
        Productinhoud = new ArrayList<>();
    }

    public void pakProductIn(Product product) {
        Inhoud -= product.getGewicht();
        Productinhoud.add(product);
        System.out.println("product: " + product + " ->zit in doos " + doosId);
    }

    public void resetInhoud() {
        Inhoud = 150;
    }

    public void emptyDoos(){
        Productinhoud.clear();
    }

    public String toString() {
        return "Doos: doosid=" + doosId + ", inhoud=" + Inhoud;
    }

    public int getDoosId() {
        return doosId;
    }


    public ArrayList<Product> getProductinhoud() {
        return Productinhoud;
    }


    public double getInhoud() {
        return Inhoud;
    }
}

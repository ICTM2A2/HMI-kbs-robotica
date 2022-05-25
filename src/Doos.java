import java.util.ArrayList;

public class Doos {
    private int doosId;
    //inhoud en productinhoud, hebben voor- en na-attributen zodat het live in de GUI kan worden weergegeven wanneer de producten in de doos komen
    private double voorInhoud;
    private ArrayList<Product> voorProductinhoud;
    private double naInhoud;
    private ArrayList<Product> naProductinhoud;

    public Doos(int doosId) {
        this.doosId = doosId;
        voorInhoud = 100;
        voorProductinhoud = new ArrayList<>();
        naInhoud = 100;
        naProductinhoud = new ArrayList<>();
    }

    public void pakProductIn(Product product) {
        voorInhoud -= product.getGewicht();
        voorProductinhoud.add(product);
        System.out.println("product: " + product + " ->zit in doos " + doosId);
    }

    public void resetInhoud() {
        voorInhoud = 100;
    }

    public String toString() {
        return "Doos: doosid=" + doosId + ", inhoud=" + voorInhoud;
    }

    public int getDoosId() {
        return doosId;
    }

    public ArrayList<Product> getNaProductinhoud() {
        return naProductinhoud;
    }

    public ArrayList<Product> getVoorProductinhoud() {
        return voorProductinhoud;
    }

    public double getNaInhoud() {
        return naInhoud;
    }

    public double getVoorInhoud() {
        return voorInhoud;
    }
}

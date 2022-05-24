import java.util.ArrayList;

public class Doos {
    private int doosId;
    private double inhoud;
    private ArrayList<Product> productinhoud;

    public Doos(int doosId){
        this.doosId = doosId;
        inhoud = 100;
        productinhoud = new ArrayList<>();
    }

    public void pakProductIn(Product product){
        if(inhoud > product.getGewicht()){
            inhoud -= product.getGewicht();
            productinhoud.add(product);
            System.out.println("product: " + product + "zit in doos" + doosId);
        } else {
            System.out.println("doos " + doosId + "zit vol");
        }
    }

    public void vervangDoos(){

    }

    public String toString(){
        return "Doos: doosid=" + doosId + ", inhoud=" + inhoud;
    }

    public int getDoosId() {
        return doosId;
    }

    public ArrayList<Product> getProductinhoud() {
        return productinhoud;
    }

    public double getInhoud() {
        return inhoud;
    }
}

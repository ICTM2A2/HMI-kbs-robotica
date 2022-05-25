public class Product {
    private int productId;
    private int gewicht;

    public Product(int productId){
        this.productId = productId;
        double getal = Math.floor(Math.random()*2);
        if (getal==0.0){
            gewicht = 30;
        } else {
            gewicht = 20;
        }
    }

    public String toString(){
        return "productId: " + productId + ", gewicht: " + gewicht;
    }

    public double getGewicht() {
        return gewicht;
    }

    public int getProductId() {
        return productId;
    }
}

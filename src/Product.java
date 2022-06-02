public class Product {
    private final int productId;
    private int gewicht;

    public Product(int productId) {
        this.productId = productId;

        if (productId < 5) {
            gewicht = 10;
        } else if (productId < 10) {
            gewicht = 20;
        } else if (productId < 15) {
            gewicht = 30;
        } else if (productId < 20) {
            gewicht = 40;
        } else if (productId < 25) {
            gewicht = 50;
        }
    }

    public String toString() {
        return "productId: " + productId + ", gewicht: " + gewicht;
    }

    public double getGewicht() {
        return gewicht;
    }

    public int getProductId() {
        return productId;
    }
}

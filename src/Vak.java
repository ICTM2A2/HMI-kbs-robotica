public class Vak {
    private boolean isBezet;
    private final int xPlek;
    private final int yPlek;
    private final int vakId;
    private final Product product;

    public Vak(int x, int y, int vakId) {
        this.vakId = vakId;
        isBezet = true;
        xPlek = x;
        yPlek = y;
        product = new Product(vakId);
    }

    public String toString() {
        String a;
        a = "Vak: " + vakId + ", x: " + xPlek + " y: " + yPlek + ", bezet-status: " + isBezet + ", product: " + product;
        return a;
    }

    public void setEmpty() {
        this.isBezet = false;
    }

    public int getVakId() {
        return vakId;
    }

    public boolean isBezet() {
        return isBezet;
    }

    public int getxPlek() {
        return xPlek;
    }

    public int getyPlek() {
        return yPlek;
    }

    public Product getProduct() {
        return product;
    }
}

public class Vak {
    private boolean isBezet;
    private int xPlek;
    private int yPlek;
    private int vakId;
    private Product product;

    public Vak(int x, int y, int vakId){
        this.vakId = vakId;
        isBezet = true;
        xPlek = x;
        yPlek = y;
        product = new Product(vakId);
    }

    public String toString(){
        String a;
        if (product!=null){
            a = "Vak: " + vakId + ", x: " + xPlek + " y: " + yPlek + ", bezet-status: " + isBezet + ", product: " + product;
        } else {
            a="Vak: " + vakId + ", x: " + xPlek + " y: " + yPlek + ", bezet-status: " + isBezet;
        }
        return a;
    }

    public void setEmpty(){
        this.isBezet = false;
        this.product = null;
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

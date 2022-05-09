public class Vak {
    private boolean isBezet;
    private int xPlek;
    private int yPlek;
    private int vakId;

    public Vak(int x, int y, int vakId){
        this.vakId = vakId;
        isBezet = true;
        xPlek = x;
        yPlek = y;
    }
}

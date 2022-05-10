public class Main {
    public static void main(String[] args){
        Hoofdscherm h1 = new Hoofdscherm();
        h1.getStelling().maakOrder();
        h1.getStelling().voegProductToe(0);
        h1.getStelling().voegProductToe(3);
        h1.getStelling().voegProductToe(3);
        h1.getStelling().voegProductToe(-1);
        h1.getStelling().voegProductToe(25);
        h1.getStelling().voegProductToe(6);
        h1.getStelling().voegProductToe(9);
        h1.getStelling().printOrder();
        h1.getStelling().plaatsOrder();
        h1.getStelling().printVakken();
    }
}

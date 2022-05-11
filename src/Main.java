public class Main {
    public static void main(String[] args){
        Hoofdscherm h1 = new Hoofdscherm();

        h1.getStelling().voegProductToe(3);
        h1.getStelling().verwijderProduct(3);
        h1.getStelling().verwijderProduct(3);
        h1.getStelling().verwijderProduct(25);
    }
}

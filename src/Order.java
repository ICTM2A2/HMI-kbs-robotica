import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;


public class Order {
    private String datum;
    private ArrayList<Vak> producten;

    public Order(){
        this.datum = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE);
        producten = new ArrayList<>();
    }

    public String getDatum() {
        return datum;
    }

    public ArrayList<Vak> getProducten() {
        return producten;
    }

    public void sorteerTSP(){
    }

    public void zoekDichtsbij(int x, int y){
        Vak dichtsbij = null;
        int afstand = 0;
        for(Vak vak: producten){
            if(!(x==vak.getxPlek() && y==vak.getyPlek())) {
                int afstandX;
                if ((vak.getxPlek() + x) > 0) {
                    //product zit rechts

                } else if ((vak.getxPlek() + x) < 0) {
                    //product zit links

                } else {
                    //product zit op hetzelfde x-niveau
                }
                int afstandy;
                if ((vak.getyPlek() + y) > 0) {
                    //product zit boven

                } else if ((vak.getyPlek() + y) < 0) {
                    //product zit onder

                } else {
                    //product zit op hetzelfde y-niveau

                }
            }
        }
    }
}

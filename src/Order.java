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

    public Vak zoekDichtsbij(int x, int y){
        Vak dichtsbij = null;
        int afstand=1000;
        for(Vak vak: producten){
            if(!(x==vak.getxPlek() && y==vak.getyPlek())) {
                int afstandX;
                if (vak.getxPlek() > x) {
                    //product zit rechts
                    afstandX = vak.getxPlek() - x;
                } else if (vak.getxPlek() < x) {
                    //product zit links
                    afstandX = x- vak.getxPlek();
                } else {
                    //product zit op hetzelfde x-niveau
                    afstandX=0;
                }
                int afstandY;
                if (vak.getyPlek() >y) {
                    //product zit boven
                    afstandY= vak.getyPlek()-y;

                } else if (vak.getyPlek() < y) {
                    //product zit onder
                    afstandY = y - vak.getyPlek();

                } else {
                    //product zit op hetzelfde y-niveau
                    afstandY=0;
                }
                if(afstand > (afstandX + afstandY)){
                    afstand = afstandX+afstandY;
                    dichtsbij = vak;
                }
            }
        }
        System.out.println("dichtsbijzijnde vak " + dichtsbij);
        return dichtsbij;
    }
}

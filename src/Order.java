import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;


public class Order {
    private int orderNr;
    private String datum;
    private ArrayList<Vak> producten;

    public Order(int orderNr){
        this.datum = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE);
        producten = new ArrayList<>();
        this.orderNr = orderNr;
    }

    public String toString(){
        return "Order: ordernr: " + orderNr + ", datum: " + datum;
    }

    public String getDatum() {
        return datum;
    }

    public ArrayList<Vak> getProducten() {
        return producten;
    }

    public Vak zoekDichtsbij(int x, int y){
        Vak dichtsbij = null;
        double afstand=1000;
        int kleinsteX=1000;
        Vak dichtsbijX = null;
        for(Vak vak: producten){
            if(!(x==vak.getxPlek() && y==vak.getyPlek())) {
                int afstandX;
                if(vak.getxPlek()<kleinsteX){
                    //kleinste x coordinaat berekenen
                    kleinsteX= vak.getxPlek();
                    dichtsbijX = vak;
                }
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
                    afstand = Math.sqrt((afstandX*afstandX)+(afstandY*afstandY));
                    dichtsbij = vak;
                } else if(afstand == (afstandX + afstandY)){
                    dichtsbij = dichtsbijX;
                }
            }
        }
        return dichtsbij;
    }
}

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

    public Vak zoekDichtsbij(int x, int y, int index){
        Vak dichtsbij = null;
        double afstand=1000;
        int kleinsteX=1000;
        Vak dichtsbijX = null;
        for(int i = index; i< producten.size(); i++){
            if(!(x==producten.get(i).getxPlek() && y==producten.get(i).getyPlek())) {
                Vak vak = producten.get(i);
                int xPlek = vak.getxPlek();
                int yPlek = vak.getyPlek();

                if(producten.get(i).getxPlek()<kleinsteX){
                    //kleinste x coordinaat berekenen
                    kleinsteX= producten.get(i).getxPlek();
                    dichtsbijX = producten.get(i);
                }
                int afstandX=0;
                //afstand in een positief getal omzetten
                if (xPlek > x) {
                    //product zit rechts
                    afstandX = xPlek - x;
                } else if (xPlek < x) {
                    //product zit links
                    afstandX = x- xPlek;
                } else {
                    //product zit op hetzelfde x-niveau
                }
                int afstandY=0;
                if (yPlek >y) {
                    //product zit boven
                    afstandY= yPlek-y;

                } else if (yPlek < y) {
                    //product zit onder
                    afstandY = y - yPlek;

                } else {
                    //product zit op hetzelfde y-niveau
                }
                double afstandPyth = Math.sqrt((afstandX*afstandX)+(afstandY*afstandY));
                if(afstand > afstandPyth){
                    afstand = afstandPyth;
                    dichtsbij = vak;
                } else if(afstand == afstandPyth){
                    dichtsbij = dichtsbijX;
                }
            }
        }
        return dichtsbij;
    }
}

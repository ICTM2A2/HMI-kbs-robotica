import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Order {
    private final int orderNr;
    private final String datum;
    private ArrayList<Vak> producten;
    private ArrayList<Doos> doosVolgorde;

    public Order(int orderNr) {
        this.datum = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE);
        producten = new ArrayList<>();
        doosVolgorde = new ArrayList<>();
        this.orderNr = orderNr;
    }

    public String toString() {
        return "Order: ordernr: " + orderNr + ", datum: " + datum;
    }

    public String getDatum() {
        return datum;
    }

    public ArrayList<Vak> getProducten() {
        return producten;
    }    

    public void sorteerTSP() {
        // vars
        ArrayList<Vak> producten = new ArrayList<>(this.producten); // make clone to edit
        ArrayList<Vak> result = new ArrayList<>();
        int size = producten.size();

        // robot Vak
        Vak robot = new Vak(5, 0, -1);

        while (result.size() < size) {
            Vak nearest = null;
            double distance = Double.MAX_VALUE;

            for (Vak vak : producten) {
                Vak from = result.isEmpty() ? robot : result.get(result.size() - 1);

                double vakDistance = distanceBetweenVakken(from, vak);
                if (vakDistance < distance) {
                    distance = vakDistance;
                    nearest = vak;
                }
            }

            if (nearest != null) {
                result.add(nearest);
                producten.remove(nearest);
            }
        }

        this.producten = new ArrayList<>(result);
    }

    private double distanceBetweenVakken(Vak v1, Vak v2) {
        int diffX = v1.getxPlek() - v2.getxPlek();
        int diffY = v1.getyPlek() - v2.getyPlek();

        if (diffX < 0) {
            diffX *= -1;
        }
        if (diffY < 0) {
            diffY *= -1;
        }

        return Math.sqrt(diffX * diffX + diffY * diffY);
    }

    public ArrayList<Doos> getDoosVolgorde() {
        return doosVolgorde;
    }

    public void setDoosVolgorde(ArrayList<Doos> doosVolgorde) {
        this.doosVolgorde = doosVolgorde;
    }

    public int getOrderNr() {
        return orderNr;
    }
}

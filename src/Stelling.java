import java.util.ArrayList;

public class Stelling {
    private Vak[] opslagplekken;
    private ArrayList<Order> orderlijst;
    private Pakrobot pakrobot;
    private Inpakrobot inpakrobot;
    private Order huidigeOrder;

    public Stelling(){
        opslagplekken = new Vak[25];
        int plek = 0;
        for(int yPlek = 0; yPlek < 5; yPlek++){
            for(int xPlek = 0; xPlek < 5; xPlek++){
                opslagplekken[plek] = new Vak(xPlek, yPlek, plek);
                plek++;
            }
        }
        orderlijst = new ArrayList<>();
    }

    public void printVakken(){
        for(Vak vak: opslagplekken){
            System.out.println(vak);
        }
    }

    public void printOrder(){
        System.out.println("Order: datum " + huidigeOrder.getDatum());
        System.out.println("Producten");
        if (huidigeOrder.getProducten().size()>0) {
            for (Vak vak : huidigeOrder.getProducten()) {
                System.out.println("-- " + vak);
            }
        } else {
            System.out.println("--Uw order is nog leeg!");
        }
    }

    public void maakOrder(){
        huidigeOrder = new Order();
    }

    public void plaatsOrder(){
        if(huidigeOrder != null && huidigeOrder.getProducten().size()>0) {
            //robotacties->

            //order in de lijst plaatsen
            orderlijst.add(huidigeOrder);
            //producten uit order uit de virtuele stelling halen
            for (Vak vak : huidigeOrder.getProducten()) {
                opslagplekken[vak.getVakId()].setEmpty();
            }
            //order verwijderen uit actieve positie
            huidigeOrder = null;
        } else {
            System.out.println("uw order is nog leeg!");
        }
    }

    public void voegProductToe(int productId){
        //order aanmaken als die nog niet aan is gemaakt
        if(huidigeOrder==null) {
            maakOrder();
        }
            //product arraycheck
            if (productId >= 0 && productId <= 24) {
                //er mogen maar drie producten in de order zitten
                if (huidigeOrder.getProducten().size() < 3) {
                    //ervoor zorgen dat een product niet meerdere keren toegevoegd kan worden aan de order
                    boolean isBeschikbaar = true;
                    for (Vak vak : huidigeOrder.getProducten()) {
                        if (productId == vak.getVakId()) {
                            isBeschikbaar = false;
                        }
                    }

                    if (isBeschikbaar) {
                        //product wordt toegevoegd
                        huidigeOrder.getProducten().add(opslagplekken[productId]);
                        System.out.println("product " + productId + " is aan je order toegevoegd");
                        //TSP algoritme --> producten sorteren om het pad te bepalen

                    } else {
                        //product niet toevoegen --> zit al in order
                        System.out.println("product " + productId + " zit al in je order!");
                    }
                } else {
                    System.out.println("je order zit vol!");
                }
            } else {
                //productId valt buiten array
                System.out.println("productId moet binnen de stelling zijn([0,24])");
            }
            printOrder();
    }

    public void verwijderProduct(int productId){
        if(productId>=0 && productId <=24) {
            boolean isVerwijderd = false;
            for (int i = 0; i < huidigeOrder.getProducten().size(); i++) {
                if (productId == huidigeOrder.getProducten().get(i).getVakId()) {
                    huidigeOrder.getProducten().remove(i);
                    isVerwijderd = true;
                    System.out.println("product " + productId + " is verwijderd uit uw order");
                }
            }
            if (!isVerwijderd) {
                System.out.println("product" + productId + " zit niet in uw order en kan dus niet verwijderd worden");
            }
        } else {
            System.out.println("product " + productId + " staat niet in de stelling");
        }
        printOrder();
    }

}

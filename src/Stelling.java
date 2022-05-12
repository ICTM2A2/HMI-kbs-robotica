import java.util.ArrayList;

public class Stelling {
    private Hoofdscherm hoofdscherm;
    private Vak[] opslagplekken;
    private ArrayList<Order> orderlijst;
    private Pakrobot pakrobot;
    private Inpakrobot inpakrobot;
    private Order huidigeOrder;

    public Stelling(Hoofdscherm hoofdscherm){
        this.hoofdscherm = hoofdscherm;
        opslagplekken = new Vak[25];
        int arrayPlek = 0;
        int plek = 0;
        for(int yPlek = 4; yPlek >= 0; yPlek--){
            for(int xPlek = 0; xPlek < 5; xPlek++){
                opslagplekken[arrayPlek] = new Vak(xPlek, yPlek, plek);
                arrayPlek++;
                plek++;
            }
        }
        orderlijst = new ArrayList<>();
    }

    public void printVakken(){
        for(Vak vak: opslagplekken){
            System.out.println(vak);
            hoofdscherm.schrijfTekst(String.valueOf(vak));
        }
    }

    public void printOrder(){
        System.out.println("Order: datum " + huidigeOrder.getDatum());
        hoofdscherm.schrijfTekst("Order: datum " + huidigeOrder.getDatum());

        System.out.println("Producten");
        hoofdscherm.schrijfTekst("Producten");
        if (huidigeOrder.getProducten().size()>0) {
            for (Vak vak : huidigeOrder.getProducten()) {
                System.out.println("-- " + vak);
                hoofdscherm.schrijfTekst("-- " + vak);
            }
        } else {
            System.out.println("--Uw order is nog leeg!");
            hoofdscherm.schrijfTekst("--Uw order is nog leeg!");
        }
        hoofdscherm.schrijfTekst("\n");
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
            hoofdscherm.schrijfTekst("uw order is geplaatst!");
        } else {
            System.out.println("uw order is nog leeg!");
            hoofdscherm.schrijfTekst("uw order is nog leeg!");
        }
        hoofdscherm.schrijfTekst("\n");
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
                    //voorraad checken
                    if (opslagplekken[productId].isBezet()) {
                        if (isBeschikbaar) {
                            //product wordt toegevoegd
                            huidigeOrder.getProducten().add(opslagplekken[productId]);
                            //TSP algoritme --> producten sorteren om het pad te bepalen
                            sorteerTSP();

                        } else {
                            //product niet toevoegen --> zit al in order
                            System.out.println("product " + productId + " zit al in je order!");
                            hoofdscherm.schrijfTekst("product " + productId + " zit al in je order!");
                        }
                    } else{
                        //product is niet op voorraad
                        System.out.println("product " + productId + " is niet meer beschikbaar");
                        hoofdscherm.schrijfTekst("product " + productId + " is niet meer beschikbaar");
                    }
                } else {
                    System.out.println("je order zit vol!");
                    hoofdscherm.schrijfTekst("je order zit vol!");
                }
            } else {
                //productId valt buiten array
                System.out.println("productId moet binnen de stelling zijn([0,24])");
                hoofdscherm.schrijfTekst("productId moet binnen de stelling zijn([0,24])");
            }
    }

    public void verwijderProduct(int productId) {
        if (huidigeOrder != null && huidigeOrder.getProducten().size()>0) {
            if (productId >= 0 && productId <= 24) {
                boolean isVerwijderd = false;
                for (int i = 0; i < huidigeOrder.getProducten().size(); i++) {
                    if (productId == huidigeOrder.getProducten().get(i).getVakId()) {
                        huidigeOrder.getProducten().remove(i);
                        isVerwijderd = true;
                        System.out.println("product " + productId + " is verwijderd uit uw order");
                        hoofdscherm.schrijfTekst("product " + productId + " is verwijderd uit uw order");
                        sorteerTSP();
                    }
                }
                if (!isVerwijderd) {
                    System.out.println("product" + productId + " zit niet in uw order en kan dus niet verwijderd worden");
                    hoofdscherm.schrijfTekst("product" + productId + " zit niet in uw order en kan dus niet verwijderd worden");
                }
            } else {
                System.out.println("product " + productId + " staat niet in de stelling");
                hoofdscherm.schrijfTekst("product " + productId + " staat niet in de stelling");
            }
        } else{
            System.out.println("voeg eerst producten toe");
            hoofdscherm.schrijfTekst("voeg eerst producten toe");
        }
        hoofdscherm.schrijfTekst("\n");
    }

    public void sorteerTSP(){
        if(huidigeOrder.getProducten().size()==2){
            //dichtsbijzijnde product naar eerste plek verplaatsen
            if(!huidigeOrder.getProducten().get(0).equals(huidigeOrder.zoekDichtsbij(-1,0))) {
                Vak vak1;
                vak1 = huidigeOrder.getProducten().get(0);
                huidigeOrder.getProducten().remove(0);
                voegProductToe(vak1.getVakId());
            }
        } else if (huidigeOrder.getProducten().size()==3){
            //net zo lang de producten doorschuiven totdat het dichtsbijzijnde product op plek 1 staat
            if(!huidigeOrder.getProducten().get(0).equals(huidigeOrder.zoekDichtsbij(-1,0))){
                Vak vak1;
                vak1 = huidigeOrder.getProducten().get(0);
                huidigeOrder.getProducten().remove(0);
                voegProductToe(vak1.getVakId());
                if(!huidigeOrder.getProducten().get(0).equals(huidigeOrder.zoekDichtsbij(-1,0))){
                    vak1 = huidigeOrder.getProducten().get(0);
                    huidigeOrder.getProducten().remove(0);
                    voegProductToe(vak1.getVakId());
                }
                //de tweede en eerste plek omdraaien als de ene dichterbij die op plek 1 is
                if(!huidigeOrder.getProducten().get(1).equals(huidigeOrder.zoekDichtsbij(huidigeOrder.getProducten().get(0).getxPlek(),huidigeOrder.getProducten().get(0).getyPlek()))){
                    vak1 = huidigeOrder.getProducten().get(1);
                    huidigeOrder.getProducten().remove(1);
                    voegProductToe(vak1.getVakId());
                }
                //de tweede en eerste plek omdraaien als de ene dichterbij die op plek 1 is, terwijl het product op plek 1 al goed staat
            } else if(!huidigeOrder.getProducten().get(1).equals(huidigeOrder.zoekDichtsbij(huidigeOrder.getProducten().get(0).getxPlek(),huidigeOrder.getProducten().get(0).getyPlek()))) {
                Vak vak1;
                vak1 = huidigeOrder.getProducten().get(1);
                huidigeOrder.getProducten().remove(1);
                voegProductToe(vak1.getVakId());
            }
        }
    }

    public Vak[] getOpslagplekken() {
        return opslagplekken;
    }

    public Order getHuidigeOrder() {
        return huidigeOrder;
    }
}

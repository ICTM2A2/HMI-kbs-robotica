import java.util.ArrayList;

public class Stelling {
    private Hoofdscherm hoofdscherm;
    private Vak[] opslagplekken;
    private ArrayList<Order> orderlijst;
    private Pakrobot pakrobot;
    private Inpakrobot inpakrobot;
    private Order huidigeOrder;
    private ArrayList<Doos> dozen;

    public Stelling(Hoofdscherm hoofdscherm) {
        this.hoofdscherm = hoofdscherm;
        opslagplekken = new Vak[25];
        int arrayPlek = 0;
        int plek = 0;
        for (int yPlek = 4; yPlek >= 0; yPlek--) {
            for (int xPlek = 0; xPlek < 5; xPlek++) {
                opslagplekken[arrayPlek] = new Vak(xPlek, yPlek, plek);
                arrayPlek++;
                plek++;
            }
        }
        orderlijst = new ArrayList<>();
        dozen = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            dozen.add(new Doos(i));
        }
    }

    public void printVakken() {
        for (Vak vak : opslagplekken) {
            System.out.println(vak);
            hoofdscherm.schrijfTekst(String.valueOf(vak));
        }
    }

    public void printOrder() {
        System.out.println(huidigeOrder);
        hoofdscherm.schrijfTekst(String.valueOf(huidigeOrder));

        System.out.println("Producten");
        hoofdscherm.schrijfTekst("Producten");
        if (huidigeOrder.getProducten().size() > 0) {
            int i = 0;
            for (Vak vak : huidigeOrder.getProducten()) {
                System.out.println("-- " + vak + " gaat in doos:" + huidigeOrder.getDoosVolgorde().get(i));
                hoofdscherm.schrijfTekst("-- " + vak);
                i++;
            }
        } else {
            System.out.println("--Uw order is nog leeg!");
            hoofdscherm.schrijfTekst("--Uw order is nog leeg!");
        }
        hoofdscherm.schrijfTekst("\n");
    }

    public void maakOrder() {
        huidigeOrder = new Order(orderlijst.size());
    }

    public void plaatsOrder() {
        if (huidigeOrder != null && huidigeOrder.getProducten().size() > 0) {
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

    public void voegProductToe(int productId) {
        //order aanmaken als die nog niet aan is gemaakt
        if (huidigeOrder == null) {
            maakOrder();
        }
        //product arraycheck
        if (productId >= 0 && productId <= 24) {
            //er mogen maar drie producten in de order zitten
            if (huidigeOrder.getProducten().size() < 6) {
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
                        //BPP algoritme --> producten in de beste doos plaatsen
                        sorteerBPP();
                    } else {
                        //product niet toevoegen --> zit al in order
                        System.out.println("product " + productId + " zit al in je order!");
                        hoofdscherm.schrijfTekst("product " + productId + " zit al in je order!");
                    }
                } else {
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
        if (huidigeOrder != null && huidigeOrder.getProducten().size() > 0) {
            if (productId >= 0 && productId <= 24) {
                boolean isVerwijderd = false;
                for (int i = 0; i < huidigeOrder.getProducten().size(); i++) {
                    if (productId == huidigeOrder.getProducten().get(i).getVakId()) {
                        huidigeOrder.getProducten().remove(i);
                        isVerwijderd = true;
                        System.out.println("product " + productId + " is verwijderd uit uw order");
                        hoofdscherm.schrijfTekst("product " + productId + " is verwijderd uit uw order");
                        sorteerTSP();
                        sorteerBPP();
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
        } else {
            System.out.println("voeg eerst producten toe");
            hoofdscherm.schrijfTekst("voeg eerst producten toe");
        }
        hoofdscherm.schrijfTekst("\n");
    }

    //tsp functies
    public void sorteerTSP() {
        ArrayList<Vak> producten = huidigeOrder.getProducten();
        if (producten.size() == 2) {
            //dichtsbijzijnde product naar eerste plek verplaatsen
            if (!producten.get(0).equals(huidigeOrder.zoekDichtsbij(5, 0, 0))) {
                vervangTSP(0);
            }
        } else if (producten.size() == 3) {
            //net zo lang de producten doorschuiven totdat het dichtsbijzijnde product op plek 1 staat
            if (!producten.get(0).equals(huidigeOrder.zoekDichtsbij(5, 0, 0))) {
                vervangTSP(0);
                //de tweede en derde plek omdraaien als de ene dichterbij die op plek 1 is
                if (!producten.get(1).equals(huidigeOrder.zoekDichtsbij(producten.get(0).getxPlek(), producten.get(0).getyPlek(), 0))) {
                    vervangTSP(1);
                }
                //de tweede en eerste plek omdraaien als de ene dichterbij die op plek 1 is, terwijl het product op plek 1 al goed staat
            } else if (!producten.get(1).equals(huidigeOrder.zoekDichtsbij(producten.get(0).getxPlek(), producten.get(0).getyPlek(), 0))) {
                vervangTSP(1);
            }
        } else if (producten.size() == 4) {
            //net zo lang de producten doorschuiven totdat het dichtsbijzijnde product op plek 1 staat
            if (!producten.get(0).equals(huidigeOrder.zoekDichtsbij(5, 0, 0))) {
                vervangTSP(0);
                //het dichtsbijzijnde product tov product 1 op plek 2 zetten als dat nog niet zo is
                if (!producten.get(1).equals(huidigeOrder.zoekDichtsbij(producten.get(0).getxPlek(), producten.get(0).getyPlek(), 0))) {
                    vervangTSP(1);
                }
                //het dichtsbijzijnde product tov product 2 op plek 3 zetten als dat nog niet zo is, plek 4 staat dan automatisch ook goed
                if (!producten.get(2).equals(huidigeOrder.zoekDichtsbij(producten.get(1).getxPlek(), producten.get(1).getyPlek(), 1))) {
                    vervangTSP(2);
                }
            } else if (!producten.get(1).equals(huidigeOrder.zoekDichtsbij(producten.get(0).getxPlek(), producten.get(0).getyPlek(), 0))) {
                //het dichtsbijzijnde product tov product 1 op plek 2 zetten als dat nog niet zo is
                vervangTSP(1);
                //het dichtsbijzijnde product tov product 2 op plek 3 zetten als dat nog niet zo is, plek 4 staat dan automatisch ook goed
                if (!producten.get(2).equals(huidigeOrder.zoekDichtsbij(producten.get(1).getxPlek(), producten.get(1).getyPlek(), 1))) {
                    vervangTSP(2);
                }
            } else if (!producten.get(2).equals(huidigeOrder.zoekDichtsbij(producten.get(1).getxPlek(), producten.get(1).getyPlek(), 1))) {
                try {
                    //het dichtsbijzijnde product tov product 2 op plek 3 zetten als dat nog niet zo is, plek 4 staat dan automatisch ook goed
                    vervangTSP(2);
                } catch (StackOverflowError stackOverflowError) {
                }
            }

        } else if (producten.size() == 5) {
            //net zo lang de producten doorschuiven totdat het dichtsbijzijnde product op plek 1 staat
            if (!producten.get(0).equals(huidigeOrder.zoekDichtsbij(5, 0, 0))) {
                vervangTSP(0);
                //het dichtsbijzijnde product tov product 1 op plek 2 zetten als dat nog niet zo is
                if (!producten.get(1).equals(huidigeOrder.zoekDichtsbij(producten.get(0).getxPlek(), producten.get(0).getyPlek(), 0))) {
                    vervangTSP(1);
                }
                //het dichtsbijzijnde product tov product 2 op plek 3 zetten als dat nog niet zo is
                if (!producten.get(2).equals(huidigeOrder.zoekDichtsbij(producten.get(1).getxPlek(), producten.get(1).getyPlek(), 1))) {
                    vervangTSP(2);
                }
                //het dichtsbijzijnde product tov product 3 op plek 4 zetten als dat nog niet zo is, plek 5 staat dan ook goed
                if (!producten.get(3).equals(huidigeOrder.zoekDichtsbij(producten.get(2).getxPlek(), producten.get(2).getyPlek(), 2))) {
                    vervangTSP(3);
                }
            } else if (!producten.get(1).equals(huidigeOrder.zoekDichtsbij(producten.get(0).getxPlek(), producten.get(0).getyPlek(), 0))) {
                //het dichtsbijzijnde product tov product 1 op plek 2 zetten als dat nog niet zo is
                vervangTSP(1);
                //het dichtsbijzijnde product tov product 2 op plek 3 zetten als dat nog niet zo is, plek 4 staat dan automatisch ook goed
                if (!producten.get(2).equals(huidigeOrder.zoekDichtsbij(producten.get(1).getxPlek(), producten.get(1).getyPlek(), 1))) {
                    vervangTSP(2);
                }
                //het dichtsbijzijnde product tov product 3 op plek 4 zetten als dat nog niet zo is, plek 5 staat dan ook goed
                if (!producten.get(3).equals(huidigeOrder.zoekDichtsbij(producten.get(2).getxPlek(), producten.get(2).getyPlek(), 2))) {
                    vervangTSP(3);
                }
            } else if (!producten.get(2).equals(huidigeOrder.zoekDichtsbij(producten.get(1).getxPlek(), producten.get(1).getyPlek(), 1))) {
                //het dichtsbijzijnde product tov product 2 op plek 3 zetten als dat nog niet zo is
                vervangTSP(2);
                //het dichtsbijzijnde product tov product 3 op plek 4 zetten, plek 5 staat dan goed
                if (!producten.get(3).equals(huidigeOrder.zoekDichtsbij(producten.get(2).getxPlek(), producten.get(2).getyPlek(), 2))) {
                    vervangTSP(3);
                }
            }

        } else if (producten.size() == 6) {
            //net zo lang de producten doorschuiven totdat het dichtsbijzijnde product op plek 1 staat
            if (!producten.get(0).equals(huidigeOrder.zoekDichtsbij(5, 0, 0))) {
                vervangTSP(0);
                //het dichtsbijzijnde product tov product 1 op plek 2 zetten als dat nog niet zo is
                if (!producten.get(1).equals(huidigeOrder.zoekDichtsbij(producten.get(0).getxPlek(), producten.get(0).getyPlek(), 0))) {
                    vervangTSP(1);
                }
                //het dichtsbijzijnde product tov product 2 op plek 3 zetten als dat nog niet zo is
                if (!producten.get(2).equals(huidigeOrder.zoekDichtsbij(producten.get(1).getxPlek(), producten.get(1).getyPlek(), 1))) {
                    vervangTSP(2);
                }
                //het dichtsbijzijnde product tov product 3 op plek 4 zetten als dat nog niet zo is
                if (!producten.get(3).equals(huidigeOrder.zoekDichtsbij(producten.get(2).getxPlek(), producten.get(2).getyPlek(), 2))) {
                    vervangTSP(3);
                }
                //het dichtsbijzijnde product tov product 4 op plek 5 zetten als dat nog niet zo is, plek 6 staat dan ook goed
                if (!producten.get(4).equals(huidigeOrder.zoekDichtsbij(producten.get(3).getxPlek(), producten.get(3).getyPlek(), 3))) {
                    vervangTSP(4);
                }
            } else if (!producten.get(1).equals(huidigeOrder.zoekDichtsbij(producten.get(0).getxPlek(), producten.get(0).getyPlek(), 0))) {
                //het dichtsbijzijnde product tov product 1 op plek 2 zetten als dat nog niet zo is
                vervangTSP(1);
                //het dichtsbijzijnde product tov product 2 op plek 3 zetten als dat nog niet zo is, plek 4 staat dan automatisch ook goed
                if (!producten.get(2).equals(huidigeOrder.zoekDichtsbij(producten.get(1).getxPlek(), producten.get(1).getyPlek(), 1))) {
                    vervangTSP(2);
                }
                //het dichtsbijzijnde product tov product 3 op plek 4 zetten als dat nog niet zo is
                if (!producten.get(3).equals(huidigeOrder.zoekDichtsbij(producten.get(2).getxPlek(), producten.get(2).getyPlek(), 2))) {
                    vervangTSP(3);
                }
                //het dichtsbijzijnde product tov product 4 op plek 5 zetten als dat nog niet zo is, plek 6 staat dan ook goed
                if (!producten.get(4).equals(huidigeOrder.zoekDichtsbij(producten.get(3).getxPlek(), producten.get(3).getyPlek(), 3))) {
                    vervangTSP(4);
                }
            } else if (!producten.get(2).equals(huidigeOrder.zoekDichtsbij(producten.get(1).getxPlek(), producten.get(1).getyPlek(), 1))) {
                //het dichtsbijzijnde product tov product 2 op plek 3 zetten als dat nog niet zo is
                vervangTSP(2);
                //het dichtsbijzijnde product tov product 3 op plek 4 zetten
                if (!producten.get(3).equals(huidigeOrder.zoekDichtsbij(producten.get(2).getxPlek(), producten.get(2).getyPlek(), 2))) {
                    vervangTSP(3);
                }
                //het dichtsbijzijnde product tov product 4 op plek 5 zetten als dat nog niet zo is, plek 6 staat dan ook goed
                if (!producten.get(4).equals(huidigeOrder.zoekDichtsbij(producten.get(3).getxPlek(), producten.get(3).getyPlek(), 3))) {
                    vervangTSP(4);
                }
            }
        }


    }

    public void vervangTSP(int nummer) {
        ArrayList<Vak> producten = huidigeOrder.getProducten();
        //product uit order halen en er weer terug in stoppen
        Vak vak1;
        vak1 = producten.get(nummer);
        producten.remove(nummer);
        voegProductToe(vak1.getVakId());
    }

    //BPP functies
    public Doos zoekBestFit(Product product) {
        double gewicht = product.getGewicht();
        double kleinsteVerschil = 1000;
        Doos besteDoos = null;
        int doosVol = 0;
        for (Doos doos : dozen) {
            double verschil;
            if (doos.getVoorInhoud() >= gewicht) {
                verschil = doos.getVoorInhoud() - gewicht;
                if (verschil < kleinsteVerschil) {
                    System.out.println("verschil = " + verschil);
                    kleinsteVerschil = verschil;
                    besteDoos = doos;
                }
            } else {
                doosVol++;
                if (doosVol == 2) {
                    //1 doos vervangen en opnieuw de functie ingaan als dat is gebeurd
                    return null;
                }
            }

        }
        return besteDoos;
    }

    public void sorteerBPP() {
        for(Doos doos: dozen){
            doos.resetInhoud();
        }

        ArrayList<Doos> doosVolgorde = new ArrayList<>();
        int i = 0;
        for(Vak vak: huidigeOrder.getProducten()){
            Doos doos = zoekBestFit(vak.getProduct());
            doosVolgorde.add(doos);
            doos.pakProductIn(vak.getProduct());
            i++;
        }
        huidigeOrder.setDoosVolgorde(doosVolgorde);
    }


    //getters en setters
    public Vak[] getOpslagplekken() {
        return opslagplekken;
    }

    public Order getHuidigeOrder() {
        return huidigeOrder;
    }

    public ArrayList<Doos> getDozen() {
        return dozen;
    }
}

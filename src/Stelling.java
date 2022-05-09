import java.util.ArrayList;

public class Stelling {
    private Vak[] opslagplekken;
    private ArrayList<Order> orderlijst;
    private Pakrobot pakrobot;
    private Inpakrobot inpakrobot;

    public Stelling(){
        opslagplekken = new Vak[25];
        int plek = 0;
        for(int yPlek = 0; yPlek < 5; yPlek++){
            for(int xPlek = 0; xPlek < 5; xPlek++){
                opslagplekken[plek] = new Vak(xPlek, yPlek, plek);
            }
        }
    }


}

import javax.swing.*;
import java.awt.*;

public class BPPPanel extends JPanel {
    private Hoofdscherm hoofdscherm;

    private int xMax;
    private int yMax;

    public BPPPanel(Hoofdscherm hoofdscherm){
        xMax = 980;
        yMax = 600;
        setPreferredSize(new Dimension(xMax,yMax));
        this.hoofdscherm = hoofdscherm;
    }
}

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

    public void paintComponent(Graphics g){
        Stelling stelling = hoofdscherm.getStelling();
        super.paintComponent(g);
        g.setColor(Color.black);
        g.drawRect(0,0,xMax,yMax);

        //dozen tekenen
        g.setColor(Color.black);
        g.drawRect(xMax/20, (yMax/10)*5, xMax/5, (yMax/5)*2);
        g.drawRect((xMax/20)*15, (yMax/10)*5, xMax/5, (yMax/5)*2);
        g.drawString("Doos "+stelling.getDozen().get(0).getDoosId(), xMax/20, (yMax/10)*5);
        g.drawString("Doos "+stelling.getDozen().get(1).getDoosId(), (xMax/20)*15, (yMax/10)*5);

    }
}

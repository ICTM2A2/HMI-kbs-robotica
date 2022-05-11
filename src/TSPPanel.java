import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class TSPPanel extends JPanel {
    private Hoofdscherm hoofdscherm;

    private int xMax = 980;
    private int yMax = 600;

    private int productCoordinaten[][];

    public TSPPanel(Hoofdscherm hoofdscherm){
    this.hoofdscherm = hoofdscherm;
    setPreferredSize(new Dimension(xMax,yMax));
    productCoordinaten = new int[][]{
            //rij 1
            {(xMax / 20), (yMax / 20), (2 * (xMax / 20)), (2 * (yMax / 20))},
            {(5*xMax / 20), (yMax / 20), (2 * (xMax / 20)), (2 * (yMax / 20))},
            {(9*xMax / 20), (yMax / 20), (2 * (xMax / 20)), (2 * (yMax / 20))},
            {(13*xMax / 20), (yMax / 20), (2 * (xMax / 20)), (2 * (yMax / 20))},
            {(17*xMax / 20), (yMax / 20), (2 * (xMax / 20)), (2 * (yMax / 20))},
            //rij 2
            {(xMax / 20), (5*yMax / 20), (2 * (xMax / 20)), (2 * (yMax / 20))},
            {(5*xMax / 20), (5*yMax / 20), (2 * (xMax / 20)), (2 * (yMax / 20))},
            {(9*xMax / 20), (5*yMax / 20), (2 * (xMax / 20)), (2 * (yMax / 20))},
            {(13*xMax / 20), (5*yMax / 20), (2 * (xMax / 20)), (2 * (yMax / 20))},
            {(17*xMax / 20), (5*yMax / 20), (2 * (xMax / 20)), (2 * (yMax / 20))},
            //rij3
            {(xMax / 20), (9*yMax / 20), (2 * (xMax / 20)), (2 * (yMax / 20))},
            {(5*xMax / 20), (9*yMax / 20), (2 * (xMax / 20)), (2 * (yMax / 20))},
            {(9*xMax / 20), (9*yMax / 20), (2 * (xMax / 20)), (2 * (yMax / 20))},
            {(13*xMax / 20), (9*yMax / 20), (2 * (xMax / 20)), (2 * (yMax / 20))},
            {(17*xMax / 20), (9*yMax / 20), (2 * (xMax / 20)), (2 * (yMax / 20))},
            //rij4
            {(xMax / 20), (13*yMax / 20), (2 * (xMax / 20)), (2 * (yMax / 20))},
            {(5*xMax / 20), (13*yMax / 20), (2 * (xMax / 20)), (2 * (yMax / 20))},
            {(9*xMax / 20), (13*yMax / 20), (2 * (xMax / 20)), (2 * (yMax / 20))},
            {(13*xMax / 20), (13*yMax / 20), (2 * (xMax / 20)), (2 * (yMax / 20))},
            {(17*xMax / 20), (13*yMax / 20), (2 * (xMax / 20)), (2 * (yMax / 20))},
            //rij5
            {(xMax / 20), (17*yMax / 20), (2 * (xMax / 20)), (2 * (yMax / 20))},
            {(5*xMax / 20), (17*yMax / 20), (2 * (xMax / 20)), (2 * (yMax / 20))},
            {(9*xMax / 20), (17*yMax / 20), (2 * (xMax / 20)), (2 * (yMax / 20))},
            {(13*xMax / 20), (17*yMax / 20), (2 * (xMax / 20)), (2 * (yMax / 20))},
            {(17*xMax / 20), (17*yMax / 20), (2 * (xMax / 20)), (2 * (yMax / 20))}
    };
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponents(g);

        //frame
        g.setColor(Color.black);
        g.drawRect(0,0,xMax,yMax);

        g.drawLine((xMax/5),0,(xMax/5),yMax);
        g.drawLine((2*xMax/5),0,(2*xMax/5),yMax);
        g.drawLine((3*xMax/5),0,(3*xMax/5),yMax);
        g.drawLine((4*xMax/5),0,(4*xMax/5),yMax);

        g.drawLine(0,(yMax/5),xMax,(yMax/5));
        g.drawLine(0,(2*yMax/5),xMax,(2*yMax/5));
        g.drawLine(0,(3*yMax/5),xMax,(3*yMax/5));
        g.drawLine(0,(4*yMax/5),xMax,(4*yMax/5));

        //producten
        for(int i=0; i<25;i++){
            if(hoofdscherm.getStelling().getOpslagplekken()[i].isBezet()) {
                g.fillRect(productCoordinaten[i][0], productCoordinaten[i][1], productCoordinaten[i][2], productCoordinaten[i][3]);
            }
        }
    }
}

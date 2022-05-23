import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class TSPPanel extends JPanel {
    private Hoofdscherm hoofdscherm;

    private final int xMax;
    private final int yMax;

    private final int[][] productCoordinaten;

    private int robotX;
    private int robotY;
    private int robotRadius;

    public TSPPanel(Hoofdscherm hoofdscherm){
        this.hoofdscherm = hoofdscherm;
        xMax = 980;
        yMax = 600;
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
        robotRadius=20;
        robotX = productCoordinaten[24][0];
        robotY = productCoordinaten[24][1];
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
        //--producten tekenen uit order
        if(hoofdscherm.getStelling().getHuidigeOrder()!=null) {
            for (Vak vak : hoofdscherm.getStelling().getHuidigeOrder().getProducten()) {
                g.fillRect(productCoordinaten[vak.getVakId()][0], productCoordinaten[vak.getVakId()][1], productCoordinaten[vak.getVakId()][2], productCoordinaten[vak.getVakId()][3]);
            }
            //--lijnen tsp
            int productNr = 0;
            ArrayList<Vak> orderProducten = hoofdscherm.getStelling().getHuidigeOrder().getProducten();

            for(int i = 0; i<hoofdscherm.getStelling().getHuidigeOrder().getProducten().size();i++){
                if(productNr==0) {
                    g.drawLine(robotX, robotY, productCoordinaten[orderProducten.get(i).getVakId()][0], productCoordinaten[orderProducten.get(i).getVakId()][1]);
                } else if(productNr>0 && productNr<3){
                    g.drawLine(productCoordinaten[orderProducten.get(i-1).getVakId()][0], productCoordinaten[orderProducten.get(i-1).getVakId()][1], productCoordinaten[orderProducten.get(i).getVakId()][0], productCoordinaten[orderProducten.get(i).getVakId()][1]);
                } else if(productNr==3) {
                    g.drawLine(robotX, robotY, productCoordinaten[orderProducten.get(i).getVakId()][0], productCoordinaten[orderProducten.get(i).getVakId()][1]);
                } else if(productNr>3){
                    g.drawLine(productCoordinaten[orderProducten.get(i-1).getVakId()][0], productCoordinaten[orderProducten.get(i-1).getVakId()][1], productCoordinaten[orderProducten.get(i).getVakId()][0], productCoordinaten[orderProducten.get(i).getVakId()][1]);
                }
                productNr++;
            }
        }

        //--voorraad tekenen
//        for(int i=0; i<25;i++){
////            if(hoofdscherm.getStelling().getOpslagplekken()[i].isBezet()) {
////                g.fillRect(productCoordinaten[i][0], productCoordinaten[i][1], productCoordinaten[i][2], productCoordinaten[i][3]);
////            }
//        }

        //robot
        g.setColor(Color.red);
        g.fillOval(robotX,robotY,robotRadius,robotRadius);

    }

    public int getRobotX() {
        return robotX;
    }

    public int getRobotY() {
        return robotY;
    }
}

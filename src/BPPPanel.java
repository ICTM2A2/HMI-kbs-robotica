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
        g.drawString("Doos "+stelling.getDozen().get(0).getDoosId(), xMax/20, (yMax/20)*9);
        g.drawString("Doos "+stelling.getDozen().get(1).getDoosId(), (xMax/20)*15, (yMax/20)*9);

        //producten uit order in doos
        if(stelling.getHuidigeOrder()!=null) {
            g.setColor(Color.black);
            int j = 0;
            int k = 0;
            for (int i = 0; i < stelling.getHuidigeOrder().getProducten().size(); i++) {
                if (stelling.getHuidigeOrder().getDoosVolgorde().get(i).getDoosId() %2 == 0) {
                    g.drawString("Product " + stelling.getHuidigeOrder().getProducten().get(i).getVakId(), 2 * (xMax / 20), (yMax / 20) * (17 - k));
                    k++;
                } else if (stelling.getHuidigeOrder().getDoosVolgorde().get(i).getDoosId() %2 != 0) {
                    g.drawString("Product " + stelling.getHuidigeOrder().getProducten().get(i).getVakId(), (xMax / 20) * 16, (yMax / 20) * (17 - j));
                    j++;
                }
            }
        }

        //rolband tekenen
        g.setColor(Color.black);
        g.drawOval((xMax/20)*7, (yMax/5)*2, 30, 30);
        g.drawOval((xMax/20)*13, (yMax/5)*2, 30, 30);
        g.drawLine((xMax/40)*15, (yMax/5)*2, (xMax/40)*27, (yMax/5)*2);
        g.drawLine((xMax/40)*15, (yMax/20)*9, (xMax/40)*27, (yMax/20)*9);


        //richting rolband tekenen
        /**
         * Draw an arrow line between two points.
         * @param g the graphics component.
         * @param x1 x-position of first point.
         * @param y1 y-position of first point.
         * @param x2 x-position of second point.
         * @param y2 y-position of second point.
         * @param d  the width of the arrow.
         * @param h  the height of the arrow.
         */

        //--> pijl naar rechts
        int x1 = (xMax/20)*14, y1= (yMax/5);
        int x2 = x1/2, y2=y1;
        int d = 40;
        int h = 40;

        int dx = x2 - x1, dy = y2 - y1;
        double D = Math.sqrt(dx*dx + dy*dy);
        double xm = D - d, xn = xm, ym = h, yn = -h, x;
        double sin = dy / D, cos = dx / D;

        x = xm*cos - ym*sin + x1;
        ym = xm*sin + ym*cos + y1;
        xm = x;

        x = xn*cos - yn*sin + x1;
        yn = xn*sin + yn*cos + y1;
        xn = x;

        int[] xpoints = {x2, (int) xm, (int) xn};
        int[] ypoints = {y2, (int) ym, (int) yn};

        g.drawLine(x1, y1, x2, y2);
        g.fillPolygon(xpoints, ypoints, 3);

        //<-- pijl naar links
        x1 = (xMax/20)*7;
        y1= (yMax/5);
        x2 = x1*2;
        y2=y1;
        d = 40;
        h = 40;

        dx = x2 - x1;
        dy = y2 - y1;
        D = Math.sqrt(dx*dx + dy*dy);
        xm = D - d;
        xn = xm;
        ym = h;
        yn = -h;
        sin = dy / D;
        cos = dx / D;

        x = xm*cos - ym*sin + x1;
        ym = xm*sin + ym*cos + y1;
        xm = x;

        x = xn*cos - yn*sin + x1;
        yn = xn*sin + yn*cos + y1;
        xn = x;

        xpoints[0] = x2;
        xpoints[1] = (int) xm;
        xpoints[2] = (int) xn;

        ypoints[0] = y2;
        ypoints[1] = (int) ym;
        ypoints[2] = (int) yn;

        g.drawLine(x1, y1, x2, y2);
        g.fillPolygon(xpoints, ypoints, 3);

    }
}

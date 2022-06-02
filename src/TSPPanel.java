import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class TSPPanel extends JPanel {
    private final boolean oneLine = true;
    private final int xMax;
    private final int yMax;
    private final Hoofdscherm hoofdscherm;
    private final int size = 100;
    private final int sizeR = size/2;
    private final int margin = 1;

    private final int[][] productCoordinaten;

    private final int robotX;
    private final int robotY;

    public TSPPanel(Hoofdscherm hoofdscherm) {
        this.hoofdscherm = hoofdscherm;

        xMax = size * 5;
        yMax = size * 5;

        int width = xMax + size + margin;
        int height = yMax + margin;

        setPreferredSize(new Dimension(width, height));

        productCoordinaten = new int[25][4];

        int count = 0;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                productCoordinaten[count][0] = size * j + margin;
                productCoordinaten[count][1] = size * i + margin;
                productCoordinaten[count][2] = size + margin;
                productCoordinaten[count][3] = size + margin;
                count++;
            }
        }

        robotX = productCoordinaten[productCoordinaten.length - 1][0] + size + margin;
        robotY = productCoordinaten[productCoordinaten.length - 1][1] + margin;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponents(g);

        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(3));

        g.setColor(Color.white);
        g.fillRect(0, 0, xMax, yMax);

        //--voorraad tekenen
        for (int i = 0; i < 25; i++) {
            int x1 = productCoordinaten[i][0];
            int y1 = productCoordinaten[i][1];
            int x2 = productCoordinaten[i][2];
            int y2 = productCoordinaten[i][3];

            if (hoofdscherm.getStelling().getOpslagplekken()[i].isBezet()) {
                g.setColor(Color.lightGray);
                g.fillRect(x1, y1, x2, y2);
                g.setColor(Color.gray);
                g.drawString(String.valueOf(i), x1 + size / 10, y1 + size / 5);
            } else {
                int crossMargin = sizeR / 2;

                g.setColor(Color.lightGray);
                g.drawLine(x1 + crossMargin, y1 + crossMargin, x1 + size - crossMargin, y1 + size - crossMargin);
                g.drawLine(x1 + crossMargin, y1 + size - crossMargin, x1 + size - crossMargin, y1 + crossMargin);
            }
        }

        Order ho = hoofdscherm.getStelling().getHuidigeOrder();

        //--producten tekenen uit order
        if (hoofdscherm.getStelling().getHuidigeOrder() != null) {
            int productNr = 0;
            ArrayList<Vak> orderProducten = ho.getProducten();
            for (int i = 0; i < ho.getProducten().size(); i++) {
                Vak v = ho.getProducten().get(i);

                ArrayList<Vak> av = new ArrayList<>(ho.getProducten());
                ArrayList<Doos> ad = new ArrayList<>(ho.getDoosVolgorde());

                Doos d = ad.get(av.indexOf(v));

                if (d.getDoosId() % 2 == 0) {
                    g.setColor(DoosKleuren.Doos1);
                } else {
                    g.setColor(DoosKleuren.Doos2);
                }

                int id = orderProducten.get(i).getVakId();
                int x1 = productCoordinaten[id][0];
                int y1 = productCoordinaten[id][1];
                int x2 = productCoordinaten[id][2];
                int y2 = productCoordinaten[id][3];

                g.fillRect(x1, y1, x2, y2);
                g.setColor(Color.black);
                g.drawString(String.valueOf(id), x1 + size / 10, y1 + size / 5);

                productNr++;
            }
        }

        //robot
        {
            int x1 = robotX + sizeR / 2;
            int y1 = robotY + sizeR / 2;
            int x2 = sizeR;
            int y2 = sizeR;

            g.setColor(Color.gray);
            g.fillRect(x1, y1, x2, y2);
            g.setColor(Color.black);
            g.drawRect(x1, y1, x2, y2);
            g.drawString("Robot", x1 + size / 10, y1 + size / 5);
        }

        //frame
        {
            int x0 = margin;
            int y0 = margin;
            int x1 = xMax / 5 + margin;
            int y1 = yMax / 5 + margin;
            int x2 = 2 * xMax / 5 + margin;
            int y2 = 2 * yMax / 5 + margin;
            int x3 = 3 * xMax / 5 + margin;
            int y3 = 3 * yMax / 5 + margin;
            int x4 = 4 * xMax / 5 + margin;
            int y4 = 4 * yMax / 5 + margin;
            int x5 = xMax + 1;
            int y5 = yMax + 1;

            g.setColor(Color.black);
            g.drawRect(x0, y0, x5, y5);

            g.drawLine(x1, y0, x1, y5);
            g.drawLine(x2, y0, x2, y5);
            g.drawLine(x3, y0, x3, y5);
            g.drawLine(x4, y0, x4, y5);

            g.drawLine(x0, y1, x5, y1);
            g.drawLine(x0, y2, x5, y2);
            g.drawLine(x0, y3, x5, y3);
            g.drawLine(x0, y4, x5, y4);
        }

        //--lijnen tsp
        if (ho != null) {
            int productNr = 0;
            ArrayList<Vak> orderProducten = ho.getProducten();
            g.setColor(Color.black);

            for (int i = 0; i < ho.getProducten().size(); i++) {
                if(oneLine){
                    if (productNr == 0) {
                        g.drawLine(robotX + size / 2, robotY + size / 2, productCoordinaten[orderProducten.get(i).getVakId()][0] + size / 2, productCoordinaten[orderProducten.get(i).getVakId()][1] + size / 2);
                        g.fillOval(productCoordinaten[orderProducten.get(i).getVakId()][0] + size / 2 - 5, productCoordinaten[orderProducten.get(i).getVakId()][1] + size / 2 - 5, 10, 10);
                    } else if (productNr > 0) {
                        g.drawLine(productCoordinaten[orderProducten.get(i - 1).getVakId()][0] + size / 2, productCoordinaten[orderProducten.get(i - 1).getVakId()][1] + size / 2, productCoordinaten[orderProducten.get(i).getVakId()][0] + size / 2, productCoordinaten[orderProducten.get(i).getVakId()][1] + size / 2);
                        g.fillOval(productCoordinaten[orderProducten.get(i).getVakId()][0] + size / 2 - 5, productCoordinaten[orderProducten.get(i).getVakId()][1] + size / 2 - 5, 10, 10);
                    }
                } else {
                    if (productNr == 0) {
                        g.drawLine(robotX + size / 2, robotY + size / 2, productCoordinaten[orderProducten.get(i).getVakId()][0] + size / 2, productCoordinaten[orderProducten.get(i).getVakId()][1] + size / 2);
                        g.fillOval(productCoordinaten[orderProducten.get(i).getVakId()][0] + size / 2 - 5, productCoordinaten[orderProducten.get(i).getVakId()][1] + size / 2 - 5, 10, 10);
                    } else if (productNr > 0 && productNr < 3) {
                        g.drawLine(productCoordinaten[orderProducten.get(i - 1).getVakId()][0] + size / 2, productCoordinaten[orderProducten.get(i - 1).getVakId()][1] + size / 2, productCoordinaten[orderProducten.get(i).getVakId()][0] + size / 2, productCoordinaten[orderProducten.get(i).getVakId()][1] + size / 2);
                        g.fillOval(productCoordinaten[orderProducten.get(i).getVakId()][0] + size / 2 - 5, productCoordinaten[orderProducten.get(i).getVakId()][1] + size / 2 - 5, 10, 10);
                    } else if (productNr == 3) {
                        g.drawLine(robotX + size / 2, robotY + size / 2, productCoordinaten[orderProducten.get(i).getVakId()][0] + size / 2, productCoordinaten[orderProducten.get(i).getVakId()][1] + size / 2);
                        g.fillOval(productCoordinaten[orderProducten.get(i).getVakId()][0] + size / 2 - 5, productCoordinaten[orderProducten.get(i).getVakId()][1] + size / 2 - 5, 10, 10);
                    } else if (productNr > 3) {
                        g.drawLine(productCoordinaten[orderProducten.get(i - 1).getVakId()][0] + size / 2, productCoordinaten[orderProducten.get(i - 1).getVakId()][1] + size / 2, productCoordinaten[orderProducten.get(i).getVakId()][0] + size / 2, productCoordinaten[orderProducten.get(i).getVakId()][1] + size / 2);
                        g.fillOval(productCoordinaten[orderProducten.get(i).getVakId()][0] + size / 2 - 5, productCoordinaten[orderProducten.get(i).getVakId()][1] + size / 2 - 5, 10, 10);
                    }
                }
                productNr++;
            }
        }
    }

    public int getRobotX() {
        return robotX;
    }

    public int getRobotY() {
        return robotY;
    }
}

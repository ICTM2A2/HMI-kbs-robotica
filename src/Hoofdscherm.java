import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Hoofdscherm extends JFrame implements ActionListener {
    //tabs
    private final JPanel generalPanel = new JPanel();
    private final JPanel dataPanel = new JPanel();
    private final JTabbedPane jTabbedPane = new JTabbedPane();
    private final int width = 1002;
    private final int height = 800;

    //general tab
    private final JButton addProductButton;
    private final JButton editOrderButton;
    private final JButton placeOrderButton;

    private final JTextArea JTAtext;

    //pakrobot tab
    private final TSPPanel pakrobotTekening;

    //inpakrobot tab
    private final BPPPanel inpakrobotTekening;
    //applicatie content
    private final Stelling stelling;
    //data tab
    private JLabel orderLabel;
    private JTable voorraadTabel;
    private JTable orderTabel;
    private JScrollPane colomNamen;

    public Hoofdscherm() {
        stelling = new Stelling(this);
        setTitle("HMI");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(width, height);
        setLocationRelativeTo(null);

        generalPanel.setLayout(new FlowLayout());

        //general tab content
        addProductButton = new JButton("product toevoegen");
        editOrderButton = new JButton("order aanpassen");
        placeOrderButton = new JButton("order plaatsen");
        addProductButton.addActionListener(this);
        editOrderButton.addActionListener(this);
        placeOrderButton.addActionListener(this);
        generalPanel.add(addProductButton);
        generalPanel.add(editOrderButton);
        generalPanel.add(placeOrderButton);
        JTAtext = new JTextArea(30, 80);
        JTAtext.setEditable(false);
        generalPanel.add(JTAtext);
        JScrollPane scrollbar = new JScrollPane(JTAtext);
        generalPanel.add(scrollbar);
        scrollbar.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        //pakrobot tab content
        pakrobotTekening = new TSPPanel(this);

        //inpakrobot tab content
        inpakrobotTekening = new BPPPanel(this);

        //data-panel content
        dataPanel.setLayout(new FlowLayout());
        dataPanel.add(new JLabel("Voorraad:"));

        //voorraadtabel array maken
        String[] voorraadKolommen = {"ProductId", "Voorraad", "Gewicht", "X", "Y"};
        String[][] voorraadData = new String[25][5];

        //voorraadtabel array invullen
        for (Vak vak : getStelling().getOpslagplekken()) {
            String voorraadstatus;
            int productNr;
            double gewicht;

            if (vak.isBezet()) {
                voorraadstatus = "op voorraad";
            } else {
                voorraadstatus = "uitverkocht";
            }
            productNr = vak.getVakId();
            gewicht = vak.getProduct().getGewicht();
            voorraadData[productNr][0] = String.valueOf(productNr);
            voorraadData[productNr][1] = voorraadstatus;
            voorraadData[productNr][2] = String.valueOf(gewicht);
            voorraadData[productNr][3] = String.valueOf(vak.getxPlek());
            voorraadData[productNr][4] = String.valueOf(vak.getyPlek());
        }
        voorraadTabel = new JTable(voorraadData, voorraadKolommen);
        voorraadTabel.setDefaultEditor(Object.class, null);
        voorraadTabel.setCellSelectionEnabled(false);
        dataPanel.add(voorraadTabel);
        colomNamen = new JScrollPane(voorraadTabel);
        dataPanel.add(colomNamen);

//        //ordertabel
//        String[] orderKolommen = {"OrderId", "Datum",};
//        String[][] orderData = new String[25][2];
//
//        if (getStelling().getHuidigeOrder() != null) {
//            String[] orderKolommen = {"ProductId", "Gewicht", "X", "Y"};
//            String[][] orderData = new String[getStelling().getHuidigeOrder().getProducten().size()][4];
//            int i =0;
//            for (Vak vak : getStelling().getHuidigeOrder().getProducten()) {
//                orderData[i][0] = String.valueOf(vak.getVakId());
//                orderData[i][1] = String.valueOf(vak.getProduct().getGewicht());
//                orderData[i][2] = String.valueOf(vak.getxPlek());
//                orderData[i][3] = String.valueOf(vak.getyPlek());
//                i++;
//            }
//            orderTabel = new JTable(orderData, orderKolommen);
//            orderTabel.setDefaultEditor(Object.class, null);
//            orderTabel.setCellSelectionEnabled(false);
//            dataPanel.add(orderTabel);
//        }


        //tabladen invoegen
        jTabbedPane.add("general", generalPanel);
        jTabbedPane.add("pakrobot", pakrobotTekening);
        jTabbedPane.add("inpakrobot", inpakrobotTekening);
        jTabbedPane.add("WWI-data", dataPanel);
        add(jTabbedPane);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!getStelling().isBezigMetOrder()) {
            if (e.getSource() == addProductButton) {
                OrderDialoogMaken orderDialoogMaken = new OrderDialoogMaken(this);
                repaint();
                if (stelling.getHuidigeOrder() != null) {
                    stelling.printOrder();
                }
            }
            if (e.getSource() == editOrderButton) {
                OrderDialoogAanpassen orderDialoogAanpassen = new OrderDialoogAanpassen(this);
                repaint();
                if (stelling.getHuidigeOrder() != null) {
                    stelling.printOrder();
                }
            }
            if (e.getSource() == placeOrderButton) {
                stelling.plaatsOrder();
                refreshTabel();
            }
        }
    }

    public Stelling getStelling() {
        return stelling;
    }

    public void schrijfTekst(String text) {
        JTAtext.append("\n" + text);
        JTAtext.setCaretPosition(JTAtext.getDocument().getLength());
    }

    public void leegTekst() {
        JTAtext.selectAll();
        JTAtext.replaceSelection("");
    }

    public TSPPanel getPakrobotTekening() {
        return pakrobotTekening;
    }

    public void refreshTabel() {
        dataPanel.remove(voorraadTabel);
        dataPanel.remove(colomNamen);
        //voorraadtabel array maken
        String[] voorraadKolommen = {"ProductId", "Voorraad", "gewicht", "X", "Y"};
        String[][] voorraadData = new String[25][5];

        //voorraadtabel array invullen
        for (Vak vak : getStelling().getOpslagplekken()) {
            String voorraadstatus;
            int productNr;
            double gewicht;

            if (vak.isBezet()) {
                voorraadstatus = "op voorraad";
            } else {
                voorraadstatus = "uitverkocht";
            }
            productNr = vak.getVakId();
            gewicht = vak.getProduct().getGewicht();
            voorraadData[productNr][0] = String.valueOf(productNr);
            voorraadData[productNr][1] = voorraadstatus;
            voorraadData[productNr][2] = String.valueOf(gewicht);
            voorraadData[productNr][3] = String.valueOf(vak.getxPlek());
            voorraadData[productNr][4] = String.valueOf(vak.getyPlek());
        }
        voorraadTabel = new JTable(voorraadData, voorraadKolommen);
        colomNamen = new JScrollPane(voorraadTabel);
        voorraadTabel.revalidate();
        voorraadTabel.setDefaultEditor(Object.class, null);
        voorraadTabel.setCellSelectionEnabled(false);
        dataPanel.add(voorraadTabel);
        dataPanel.add(colomNamen);
    }

    public int getTabblad() {
        return jTabbedPane.getSelectedIndex();
    }

    public void setTabblad(int index) {
        jTabbedPane.setSelectedIndex(index);
    }
}

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Hoofdscherm extends JFrame implements ActionListener {
    //tabs
    private JPanel generalPanel = new JPanel();
    private JPanel dataPanel = new JPanel();
    private JTabbedPane jTabbedPane = new JTabbedPane();
    private int width = 1000;
    private int height = 800;

    //general tab
    private JButton addProductButton;
    private JButton editOrderButton;
    private JButton placeOrderButton;

    private JTextArea JTAtext;

    //pakrobot tab
    private TSPPanel pakrobotTekening;

    //inpakrobot tab
    private BPPPanel inpakrobotTekening;

    //data tab
    private JLabel orderLabel;
    private JTable voorraadTabel;
    private JTable orderTabel;

    //applicatie content
    private Stelling stelling;

    public Hoofdscherm() {
        stelling = new Stelling(this);
        setTitle("HMI");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(width, height);

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
        inpakrobotTekening.add(new JLabel("test3"));

        //data-panel content
        dataPanel.setLayout(new FlowLayout());
        orderLabel = new JLabel("Order: " + getStelling().getHuidigeOrder());
        dataPanel.add(orderLabel);
        dataPanel.add(new JLabel("Voorraad:"));

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
        //ordertabel
        if (getStelling().getHuidigeOrder() != null) {
            String[] orderKolommen = {"ProductId", "Gewicht", "X", "Y"};
            String[][] orderData = new String[getStelling().getHuidigeOrder().getProducten().size()][4];
            int i =0;
            for (Vak vak : getStelling().getHuidigeOrder().getProducten()) {
                orderData[i][0] = String.valueOf(vak.getVakId());
                orderData[i][1] = String.valueOf(vak.getProduct().getGewicht());
                orderData[i][2] = String.valueOf(vak.getxPlek());
                orderData[i][3] = String.valueOf(vak.getyPlek());
                i++;
            }
            orderTabel = new JTable(orderData, orderKolommen);
            orderTabel.setDefaultEditor(Object.class, null);
            orderTabel.setCellSelectionEnabled(false);
            dataPanel.add(orderTabel);
        }
        voorraadTabel = new JTable(voorraadData, voorraadKolommen);
        voorraadTabel.setDefaultEditor(Object.class, null);
        voorraadTabel.setCellSelectionEnabled(false);
        dataPanel.add(voorraadTabel);


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
        }
    }

    public Stelling getStelling() {
        return stelling;
    }

    public void schrijfTekst(String text) {
        JTAtext.append("\n" + text);
        JTAtext.setCaretPosition(JTAtext.getDocument().getLength());
    }

    public TSPPanel getPakrobotTekening() {
        return pakrobotTekening;
    }
}

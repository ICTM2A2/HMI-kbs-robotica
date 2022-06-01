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
    private JTable voorraadTabel;
    private JScrollPane colomNamen;

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
        inpakrobotTekening=new BPPPanel(this);

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

    public void refreshTabel(){
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

    public void setTabblad(int index){
        jTabbedPane.setSelectedIndex(index);
    }

    public int getTabblad(){
       return jTabbedPane.getSelectedIndex();
    }
}

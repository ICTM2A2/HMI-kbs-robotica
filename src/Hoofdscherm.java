import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Hoofdscherm extends JFrame implements ActionListener {
    //tabs
    private JPanel generalPanel = new JPanel();
    private JPanel inpakrobotPanel = new JPanel();
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

    //applicatie content
    private Stelling stelling;

    public Hoofdscherm(){
        stelling = new Stelling(this);
        setTitle("HMI");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(width,height);

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
        JTAtext = new JTextArea(30,80);
        JTAtext.setEditable(false);
        generalPanel.add(JTAtext);
        JScrollPane scrollbar = new JScrollPane(JTAtext);
        generalPanel.add(scrollbar);
        scrollbar.setVerticalScrollBarPolicy (ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        //pakrobot tab content
        pakrobotTekening = new TSPPanel(this);

        //inpakrobot tab content
        inpakrobotPanel.add(new JLabel("test3"));

        //data-panel content
        dataPanel.add(new JLabel("test4"));

        //tabladen invoegen
        jTabbedPane.add("general", generalPanel);
        jTabbedPane.add("pakrobot", pakrobotTekening);
        jTabbedPane.add("inpakrobot", inpakrobotPanel);
        jTabbedPane.add("WWI-data", dataPanel);
        add(jTabbedPane);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==addProductButton){
            OrderDialoogMaken orderDialoogMaken = new OrderDialoogMaken(this);
            pakrobotTekening.repaint();
            if(stelling.getHuidigeOrder()!=null) {
                stelling.printOrder();
            }
        }
        if(e.getSource()==editOrderButton){
            OrderDialoogAanpassen orderDialoogAanpassen = new OrderDialoogAanpassen(this);
            pakrobotTekening.repaint();
            if(stelling.getHuidigeOrder()!=null) {
                stelling.printOrder();
            }
        }
        if(e.getSource()==placeOrderButton){
            stelling.plaatsOrder();
        }
    }

    public Stelling getStelling() {
        return stelling;
    }

    public void schrijfTekst(String text){
        JTAtext.append("\n" + text);
        JTAtext.setCaretPosition(JTAtext.getDocument().getLength());
    }

    public TSPPanel getPakrobotTekening() {
        return pakrobotTekening;
    }
}

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Hoofdscherm extends JFrame implements ActionListener {

    JPanel generalPanel = new JPanel();
    JPanel pakrobotPanel = new JPanel();
    JPanel inpakrobotPanel = new JPanel();
    JPanel dataPanel = new JPanel();

    JTabbedPane jTabbedPane = new JTabbedPane();

    JButton addProductButton;
    JButton editOrderButton;
    JButton placeOrderButton;

    public Hoofdscherm(){
        setTitle("HMI");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(500,500);

        generalPanel.setLayout(new FlowLayout());

        addProductButton = new JButton("product toevoegen");
        editOrderButton = new JButton("order aanpassen");
        placeOrderButton = new JButton("order plaatsen");
        generalPanel.add(addProductButton);
        generalPanel.add(editOrderButton);
        generalPanel.add(placeOrderButton);

        pakrobotPanel.add(new JLabel("test2"));
        inpakrobotPanel.add(new JLabel("test3"));
        dataPanel.add(new JLabel("test4"));

        jTabbedPane.add("general", generalPanel);
        jTabbedPane.add("pakrobot", pakrobotPanel);
        jTabbedPane.add("inpakrobot", inpakrobotPanel);
        jTabbedPane.add("WWI-data", dataPanel);
        add(jTabbedPane);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}

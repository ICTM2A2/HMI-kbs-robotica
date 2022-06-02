import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OrderDialoogAanpassen extends JDialog implements ActionListener {
    private final Hoofdscherm hoofdscherm;
    private final JTextField jTproductId;
    private final JButton jBCancel;
    private final JButton jBOk;

    public OrderDialoogAanpassen(Hoofdscherm hoofdscherm) {
        super(hoofdscherm, true);
        this.hoofdscherm = hoofdscherm;
        setSize(200, 100);
        setTitle("product verwijderen");
        setLayout(new GridLayout(2, 2));
        setLocationRelativeTo(null);

        add(new JLabel("productId"));
        jTproductId = new JTextField();
        add(jTproductId);

        jBCancel = new JButton("cancel");
        jBCancel.addActionListener(this);
        add(jBCancel);

        jBOk = new JButton("verwijder");
        jBOk.addActionListener(this);
        add(jBOk);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == jBOk) {
            try {
                int productId = Integer.parseInt(jTproductId.getText());
                hoofdscherm.getStelling().verwijderProduct(productId);
            } catch (NumberFormatException nfe) {
                System.out.println("vul een getal in");
                hoofdscherm.schrijfTekst("vul een getal in");
            }
        }
        setVisible(false);
    }
}

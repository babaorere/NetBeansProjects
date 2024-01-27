package com.app.tupledialog;

import java.awt.*;
import java.util.List;
import javax.swing.*;

public class TupleDialog extends JDialog {

    public TupleDialog(Window parent, List<Tuple> tuples) {
        super(parent, "Tuples", ModalityType.MODELESS);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        setLayout(new GridLayout(0, 4)); // 4 columns, rows will be added dynamically

        for (Tuple tuple : tuples) {
            add(createTuplePanel(tuple));
        }

        pack();
        setLocationRelativeTo(parent);
    }

    private JPanel createTuplePanel(Tuple tuple) {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.CENTER));

        JLabel label1 = new JLabel(tuple.getLabel1());
        JLabel label2 = new JLabel(tuple.getLabel2());
        JTextField textField = new JTextField(3); // You can adjust the width as needed

        panel.add(label1);
        panel.add(label2);
        panel.add(textField);

        return panel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                List<Tuple> tuples = List.of(
                        new Tuple("Label1-1", "Label2-1"),
                        new Tuple("Label1-2", "Label2-2"),
                        new Tuple("Label1-1", "Label2-1"),
                        new Tuple("Label1-2", "Label2-2"),
                        new Tuple("Label1-1", "Label2-1"),
                        new Tuple("Label1-2", "Label2-2")
                );

                TupleDialog dialog = new TupleDialog(null, tuples);
                dialog.setVisible(true);
            }
        });
    }
}

class Tuple {

    private final String label1;
    private final String label2;

    public Tuple(String label1, String label2) {
        this.label1 = label1;
        this.label2 = label2;
    }

    public String getLabel1() {
        return label1;
    }

    public String getLabel2() {
        return label2;
    }
}

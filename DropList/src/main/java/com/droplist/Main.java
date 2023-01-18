package com.droplist;

/*from w  w  w . j a v a  2  s  .c o  m*/
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;

public class Main extends JFrame implements DropTargetListener {

    DropTarget dt;

    JTextArea ta = new JTextArea();

    public Main() {
        super("Drop Test");
        setSize(300, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        add(new JLabel("Drop a list from your file chooser here:"), BorderLayout.NORTH);
        ta.setBackground(Color.white);
        getContentPane().add(ta, BorderLayout.CENTER);

        dt = new DropTarget(ta, this);
        setVisible(true);
    }

    public void dragEnter(DropTargetDragEvent dtde) {
        System.out.println("Drag Enter");
    }

    public void dragExit(DropTargetEvent dte) {
        System.out.println("Source: " + dte.getSource());
        System.out.println("Drag Exit");
    }

    public void dragOver(DropTargetDragEvent dtde) {
        System.out.println("Drag Over");
    }

    public void dropActionChanged(DropTargetDragEvent dtde) {
        System.out.println("Drop Action Changed");
    }

    public void drop(DropTargetDropEvent dtde) {
        try {
            Transferable tr = dtde.getTransferable();
            DataFlavor[] flavors = tr.getTransferDataFlavors();
            for (int i = 0; i < flavors.length; i++) {
                System.out.println("Possible flavor: " + flavors[i].getMimeType());
                if (flavors[i].isFlavorJavaFileListType()) {
                    dtde.acceptDrop(DnDConstants.ACTION_COPY);
                    ta.setText("Successful file list drop.\n\n");

                    java.util.List list = (java.util.List) tr.getTransferData(flavors[i]);
                    for (int j = 0; j < list.size(); j++) {
                        ta.append(list.get(j) + "\n");
                    }

                    dtde.dropComplete(true);
                    return;
                }
            }
            System.out.println("Drop failed: " + dtde);
            dtde.rejectDrop();
        } catch (Exception e) {
            e.printStackTrace();
            dtde.rejectDrop();
        }
    }

    public static void main(String args[]) {
        new Main();
    }
}

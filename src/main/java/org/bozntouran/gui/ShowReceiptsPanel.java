package org.bozntouran.gui;

import org.bozntouran.entities.Receipt;
import org.bozntouran.manager.DataAccesor;
import org.bozntouran.manager.JpaDataAccess;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Optional;

public class ShowReceiptsPanel extends JPanel {

    private DataAccesor dataAccesor;
    private JPanel receiptsPanel;
    private ReceiptsInfoPanel receiptsInfoPanel;
    private Optional<List<Receipt>> receipts;

    private JList<Receipt> receiptJList ;
    private JScrollPane listScroller;

    public ShowReceiptsPanel(){

        dataAccesor = JpaDataAccess.getInstance();

        setLayout(new GridLayout(3,1,25,0));


        receipts = dataAccesor.getReceipts();

        if(receipts.isPresent()){
            receiptJList = new JList<>(receipts.get().toArray(new Receipt[0]));
            receiptJList.addListSelectionListener(e -> { showReceipt(e.getFirstIndex() ); });
            receiptJList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);

            receiptJList.setVisibleRowCount(-1);

            listScroller = new JScrollPane(receiptJList);
            listScroller.setPreferredSize(new Dimension(250, 80));
            add(listScroller);
        }

        receiptsInfoPanel = new ReceiptsInfoPanel();

        add(receiptsInfoPanel);

        JButton deleteButton = new JButton("Σβήσε την απόδιξει");
        deleteButton.addActionListener(e -> {deleteReceipt(); });
        add(deleteButton);


    }

    public void deleteReceipt(){
        receiptsInfoPanel.deleteReceipt();
        receipts = dataAccesor.getReceipts();
        receiptJList.setListData(receipts.get().toArray(new Receipt[0]));
        listScroller.repaint();
    }

    private void showReceipt(int firstIndex) {
        if(receipts.isPresent()){
            if(receipts.get().size() <= firstIndex){
                firstIndex = 0;
            }
        }
        receiptsInfoPanel.setReceipt(receipts.get().get(firstIndex));
    }

}

package org.bozntouran.gui;

import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import java.awt.*;

@Getter
@Setter
public class ContentPanel extends JPanel {

    private JTabbedPane tabbedPane;
    private JPanel cardPanel;
    private CardLayout cardLayout;

    public ContentPanel() {
        setLayout(new BorderLayout());

/*        tabbedPane = new JTabbedPane();
        tabbedPane.addTab("new",new JColorChooser());*/


        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        // Initialize the tabbedPane
/*
        tabbedPane = new JTabbedPane();
*/

        // Add the tabbedPane to the cardPanel with a unique name
        //cardPanel.add(tabbedPane, "TabbedPane");

        add(cardPanel, BorderLayout.CENTER);
    }

    public void addNewCard(JPanel jPanel,String name){
        this.cardPanel.add(jPanel,name);
    }

    public void showCard(String name) {
        if (name != null) {
            cardLayout.show(cardPanel, name);
        }
    }


}

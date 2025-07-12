package org.bozntouran.gui;

import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import java.awt.*;

@Getter
@Setter
public class ContentPanel extends JPanel {

    private JTabbedPane tabbedPane;
    private JPanel      cardPanel;
    private CardLayout  cardLayout;

    public ContentPanel() {
        setLayout(new BorderLayout());

        this.cardLayout = new CardLayout();
        this.cardPanel = new JPanel(this.cardLayout);


        add(cardPanel, BorderLayout.CENTER);
    }

    public void addNewCard(JPanel jPanel, String name) {
        this.cardPanel.add(jPanel, name);
    }

    public void showCard(String name) {
        if (name != null) {
            this.cardLayout.show(this.cardPanel, name);
        }
    }


}

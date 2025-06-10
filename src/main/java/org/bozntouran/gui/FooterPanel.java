package org.bozntouran.gui;

import javax.swing.*;
import java.awt.*;

public class FooterPanel extends JPanel {

    public FooterPanel() {
        setLayout(new BorderLayout());
        JLabel footerLabel = new JLabel("Invoice Lite all rights.....", SwingConstants.CENTER);
        footerLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        footerLabel.setOpaque(true);
        footerLabel.setBackground(Color.LIGHT_GRAY);
        add(footerLabel, BorderLayout.CENTER);

    }
}

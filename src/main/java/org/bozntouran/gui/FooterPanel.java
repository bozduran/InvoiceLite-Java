package org.bozntouran.gui;

import org.bozntouran.manager.Language;

import javax.swing.*;
import java.awt.*;

public class FooterPanel extends JPanel {

    public FooterPanel() {


        setLayout(new BorderLayout());
        JLabel footerLabel = new JLabel(Language.getInstance().getMessage("copyright"), SwingConstants.CENTER);
        footerLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        footerLabel.setOpaque(true);
        footerLabel.setBackground(Color.LIGHT_GRAY);
        add(footerLabel, BorderLayout.CENTER);

    }
}

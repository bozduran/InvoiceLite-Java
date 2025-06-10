package org.bozntouran;

import jakarta.persistence.*;
import org.bozntouran.entities.Receipt;
import org.bozntouran.gui.ContentPanel;
import org.bozntouran.gui.FooterPanel;
import org.bozntouran.gui.HeaderPanel;
import org.bozntouran.gui.MainFunctions;
import org.bozntouran.manager.DataAccesor;
import org.bozntouran.manager.JpaDataAccess;

import javax.swing.*;
import java.awt.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    public static void main(String[] args) {
        DataAccesor dataAccesor = JpaDataAccess.getInstance();
        dataAccesor.intialize();
        JFrame frame = new JFrame("Invoice Lite");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new BorderLayout());
        ContentPanel contentPanel = new ContentPanel();
        HeaderPanel headerPanel = new HeaderPanel(contentPanel);
        FooterPanel footerPanel = new FooterPanel();
        contentPanel.addNewCard(new MainFunctions(headerPanel ,contentPanel),"Main");

        contentPanel.showCard("Main");

        frame.add(headerPanel, BorderLayout.NORTH);
        frame.add(contentPanel, BorderLayout.CENTER);
        frame.add(footerPanel, BorderLayout.SOUTH);
        frame.pack();
        frame.setVisible(true);


    }

}
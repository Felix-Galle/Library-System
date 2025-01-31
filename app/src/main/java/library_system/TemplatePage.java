package library_system;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */



/**
 *
 * @author galle_f24
 */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JPanel;

public class TemplatePage {
    private JPanel templatePage;
    private JPanel spacerBar;
    private JPanel centerPanel;
    private JPanel bottomBar;

    public TemplatePage() {
        templatePage = new JPanel();
        templatePage.setLayout(new BorderLayout());

        // Create the spacer bar
        spacerBar = new JPanel();
        spacerBar.setBackground(Color.decode("#ffafff"));
        spacerBar.setLayout(new FlowLayout(FlowLayout.CENTER, 24, 40));
        templatePage.add(spacerBar, BorderLayout.NORTH);

        // Create the center panel
        centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        templatePage.add(centerPanel, BorderLayout.CENTER);

        // Create the bottom bar
        bottomBar = new JPanel();
        bottomBar.setBackground(Color.decode("#afffff"));
        bottomBar.setLayout(new FlowLayout(FlowLayout.CENTER, 24, 20));
        bottomBar.setPreferredSize(new Dimension(0, 300));
        templatePage.add(bottomBar, BorderLayout.SOUTH);
    }

    public void addComponentToSpacerBar(JComponent component) {
        spacerBar.add(component);
    }

    public void addComponentToCenterPanel(JComponent component) {
        centerPanel.add(component);
    }

    public void addComponentToBottomBar(JComponent component) {
        bottomBar.add(component);
    }

    public JPanel getTemplatePage() {
        return templatePage;
    }
}

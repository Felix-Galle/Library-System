package library_system;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Pass {

    private static String passcode_en;
    private static String inPass;

    public Pass(String passcode_en, String inPass) {
        Pass.passcode_en = passcode_en;
        Pass.inPass = inPass;
    }

    public static String hashPass(String inPass) {
        // Only to be used by Pass.java.
        // Prevents any unauthorized access.

        // Declaring the type of hashing
        String algorithm = "SHA-256";
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance(algorithm);
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }

        inPass = inPass.toLowerCase();
        byte[] bytes = md.digest(inPass.getBytes());

        StringBuilder hash = new StringBuilder();
        for (byte b : bytes) {
            hash.append(String.format("%02x", b));
        }

        return hash.toString();
    }

    public static String showPass_win() {
        JFrame passFrame = new JFrame("Pass Window");
        passFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        passFrame.setResizable(false);
        passFrame.setSize(300, 200);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(Box.createVerticalGlue());

        JLabel passLabel = new JLabel("Enter Passcode:");
        JTextField passField = new JTextField(20);
        passField.setToolTipText("Enter 8 bytes of plain text e.g. 12345678");

        JButton submitButton = new JButton("Submit");
        panel.add(passLabel);
        panel.add(passField);
        panel.add(submitButton);

        passFrame.add(panel);
        passFrame.pack();
        passFrame.setVisible(true);

        return "";
    }

    // TODO This is just me playing about with stuff that I haven't seen before.
    // I will remove it before it goes into production
    public static void showPass_win_alert() {
        JOptionPane.showMessageDialog(null, inPass, "Please enter your passkey", JOptionPane.INFORMATION_MESSAGE);
    }
}
    
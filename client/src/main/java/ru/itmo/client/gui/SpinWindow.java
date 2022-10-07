package ru.itmo.client.gui;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class SpinWindow extends JFrame {
    private final JFrame frame = new JFrame("Route Manager");
    private static SpinWindow instance = null;

    public static SpinWindow getInstance() {
        if (instance  == null) {
            instance = new SpinWindow();
        }
        return instance;
    }

    public void createWindow() {
        frame.getContentPane().add(createGUI());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void closeWindow() {
        frame.dispose();
    }

    private JPanel createGUI() {
        JPanel panel = LayoutUtils.createVerticalPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(12, 12,12, 12));

        JPanel connectingIndicator = LayoutUtils.createHorizontalPanel();
        JLabel connectingIndicatorLabel = new JLabel("Connecting to server");
        Font font = new Font("Dialog", Font.BOLD, 20);
        connectingIndicatorLabel.setFont(font);
        connectingIndicator.add(connectingIndicatorLabel);

        URL url = getClass().getResource("/image/loading.gif");
        Icon gif = new ImageIcon(url);
        JLabel gifLabel = new JLabel(gif);
        connectingIndicator.add(Box.createHorizontalStrut(12));
        connectingIndicator.add(gifLabel);

        panel.add(connectingIndicator);
        panel.add(Box.createVerticalStrut(12));
        return panel;
    }
}

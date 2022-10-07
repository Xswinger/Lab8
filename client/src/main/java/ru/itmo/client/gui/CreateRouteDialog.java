package ru.itmo.client.gui;

import ru.itmo.client.User;
import ru.itmo.common.dto.CreatingElement;
import ru.itmo.common.dto.Route;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CreateRouteDialog extends JPanel{
    private boolean routeDialogCheck;
    public Frame owner;
    protected Route route;
    private JDialog dialog;
    private final User user;
    private final Dimension FRAME_DIMENSION = new Dimension(600, 300);
    private final Dimension PANEL_DIMENSION = new Dimension(80, 28);
    private final Font FONT = new Font("Dialog", Font.PLAIN, 14);
    public CreateRouteDialog(User activeUser) {
        user = activeUser;
        setLayout(new BorderLayout());
        setSize(FRAME_DIMENSION);
        add(createDialog(), BorderLayout.CENTER);
        setVisible(true);
    }

    private JPanel createDialog() {
        JPanel panel = LayoutUtils.createVerticalPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(12,12,12,12));
        JTextField textFieldName = new JTextField(12);
        panel.add(createVerticalPanel("Enter Name", textFieldName));
        JTextField textFieldCoordinateX = new JTextField(12);
        panel.add(createVerticalPanel("Enter Coordinate x", textFieldCoordinateX));
        JTextField textFieldCoordinateY = new JTextField(12);
        panel.add(createVerticalPanel("Enter Coordinate y", textFieldCoordinateY));
        JTextField textFieldFromX = new JTextField(12);
        panel.add(createVerticalPanel("Enter Location x", textFieldFromX));
        JTextField textFieldFromY = new JTextField(12);
        panel.add(createVerticalPanel("Enter Location y", textFieldFromY));
        JTextField textFieldFromZ = new JTextField(12);
        panel.add(createVerticalPanel("Enter Location z", textFieldFromZ));
        JTextField textFieldToX = new JTextField(12);
        panel.add(createVerticalPanel("Enter Location x", textFieldToX));
        JTextField textFieldToY = new JTextField(12);
        panel.add(createVerticalPanel("Enter Location y", textFieldToY));
        JTextField textFieldToZ = new JTextField(12);
        panel.add(createVerticalPanel("Enter Location z", textFieldToZ));
        JTextField textFieldToName = new JTextField(12);
        panel.add(createVerticalPanel("Enter Location name", textFieldToName));
        JTextField textFieldDistance = new JTextField(12);
        panel.add(createVerticalPanel("Enter Distance", textFieldDistance));
        JPanel buttonsPanel = LayoutUtils.createHorizontalPanel();
        JButton executeButton = new JButton("Create route");
        executeButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object[] dataArray = {textFieldName.getText(), textFieldCoordinateX.getText(),
                        textFieldCoordinateY.getText(), textFieldFromX.getText(), textFieldFromY.getText(),
                        textFieldFromZ.getText(), textFieldToX.getText(), textFieldToY.getText(),
                        textFieldToZ.getText(), textFieldToName.getText(), textFieldDistance.getText()};
                List<Object> dataList = new ArrayList<>(Arrays.asList(dataArray));
                route = CreatingElement.manualCreatingElement(dataList);
                routeDialogCheck = true;
                dialog.setVisible(false);
            }
        });
        buttonsPanel.add(executeButton);
        panel.add(buttonsPanel);
        return panel;
    }

    private JPanel createVerticalPanel(String labelName, JTextField textField) {
        JPanel panel = LayoutUtils.createVerticalPanel();
        JLabel label = new JLabel(labelName);
        label.setFont(FONT);
        panel.add(label);
        panel.add(Box.createHorizontalStrut(12));
        textField.setPreferredSize(PANEL_DIMENSION);
        textField.setFont(FONT);
        panel.add(textField);
        panel.add(Box.createVerticalStrut(12));
        return panel;
    }
    public boolean showCommandDialog(Component parent, String title) {
        routeDialogCheck = false;
        if (parent instanceof Frame) {
            owner = (Frame) parent;
        }
        else {
            owner = (Frame) SwingUtilities.getAncestorOfClass(Frame.class, parent);
        }
        if (dialog == null || dialog.getOwner() != owner) {
            dialog = new JDialog(owner, true);
            dialog.add(this);
            dialog.pack();
            dialog.setLocationRelativeTo(null);
        }
        dialog.setTitle(title);
        dialog.setVisible(true);
        return routeDialogCheck;
    }
}

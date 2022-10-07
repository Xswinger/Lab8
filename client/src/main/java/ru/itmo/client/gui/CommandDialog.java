package ru.itmo.client.gui;

import ru.itmo.client.Client;
import ru.itmo.client.EnterCommand;
import ru.itmo.client.User;
import ru.itmo.common.dto.Command;
import ru.itmo.common.dto.Print;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Arrays;

public class CommandDialog extends JPanel {
    private boolean commandDialogCheck;
    private StringBuilder request;
    public Frame owner;
    protected Command command;
    private final Dimension FRAME_DIMENSION = new Dimension(600, 300);
    private final Dimension PANEL_DIMENSION = new Dimension(80, 28);
    private final Font FONT = new Font("Dialog", Font.PLAIN, 14);
    private final User user;
    private JDialog dialog;
    private CreateRouteDialog createRouteDialog = null;
    String inputCommand;
    JTextField tfCommand;
    public JLabel warningMessage;
    public CommandDialog(User activeUser) {
        user = activeUser;
        setLayout(new BorderLayout());
        setSize(FRAME_DIMENSION);
        add(createDialog(), BorderLayout.CENTER);
        setVisible(true);
    }

    private JPanel createDialog() {
        JPanel panel = LayoutUtils.createVerticalPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(12, 15, 12 ,15));
        JPanel commandPanel = LayoutUtils.createHorizontalPanel();
        JLabel commandLabel = new JLabel("Enter command:");
        commandLabel.setFont(FONT);
        commandPanel.add(commandLabel);
        commandPanel.add(Box.createHorizontalStrut(12));
        tfCommand = new JTextField(12);
        tfCommand.setPreferredSize(PANEL_DIMENSION);
        tfCommand.setFont(FONT);
        commandPanel.add(tfCommand);
        panel.add(commandPanel);
        panel.add(Box.createVerticalStrut(12));
        JPanel button = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        JButton execute = new JButton("Execute command");
        execute.addActionListener(e -> {
            inputCommand = tfCommand.getText();
            command = EnterCommand.formCommand(user, inputCommand);
            if (Arrays.stream(new String[]{"add", "add_if_max", "remove_greater", "update"}).anyMatch(s ->
                    s.equals(command.getNameOfCommand()))) {
                createRouteCreatingDialog();
            }
            JOptionPane.showMessageDialog(CommandDialog.this,
                    "Command completed. Execution status:\n" +
                            treatmentEnterCommand(), "Command execute", JOptionPane.INFORMATION_MESSAGE);
            commandDialogCheck = true;
            dialog.setVisible(false);
        });
        execute.setFont(FONT);
        button.add(execute);
        panel.add(button);
        return panel;
    }

    public StringBuilder treatmentEnterCommand() {
        try {
            commandDialogCheck = false;
            return Print.printContent(Client.getInstance().exchangeMessage(user, command));
        } catch (IOException | InterruptedException | ClassNotFoundException ex) {
            System.out.println(ex.getMessage());
            JOptionPane.showMessageDialog(CommandDialog.this,
                    "Error when trying to execute command", "Error", JOptionPane.ERROR_MESSAGE);
            commandDialogCheck = true;
            return null;
        }
    }

    public boolean showCommandDialog(Component parent, String title) {
        commandDialogCheck = false;
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
        } else {
            tfCommand.setText("");
        }
        dialog.setTitle(title);
        dialog.setVisible(true);
        return commandDialogCheck;
    }

    public void createRouteCreatingDialog() {
        if (createRouteDialog == null) {
            createRouteDialog = new CreateRouteDialog(user);
        }
        if (createRouteDialog.showCommandDialog(CommandDialog.this, "Execute command")) {
            command.setRouteOfCommand(createRouteDialog.route);
        }
    }
}

package ru.itmo.client.gui;

import ru.itmo.client.Client;
import ru.itmo.client.EnterCommand;
import ru.itmo.client.User;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Arrays;

public class LoginDialog extends JPanel {
    private static final long serialVersionUID = 1L;
    private final Dimension FRAME_DIMENSION = new Dimension(620, 300);
    private final Dimension PANEL_DIMENSION = new Dimension(80, 28);
    private final Font FONT = new Font("Dialog", Font.PLAIN, 14);
    private JDialog dialog;
    private boolean logInDialogCheck;
    public User user;
    public JTextField tfLogin, tfPassword;
    public JButton btCreateUser, btLogIn;
    public JLabel warningMessage;
    public Frame owner;
    public LoginDialog() {
        setLayout(new BorderLayout());
        add(createMainPanel(), BorderLayout.CENTER);
        setSize(FRAME_DIMENSION);
        setVisible(true);
    }
    private JPanel createMainPanel() {
        JPanel panel = LayoutUtils.createVerticalPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(12,15,12,15));
        JPanel name = LayoutUtils.createHorizontalPanel();
        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setFont(FONT);
        name.add(nameLabel);
        name.add(Box.createHorizontalStrut(12));
        tfLogin = new JTextField();
        tfLogin.setPreferredSize(PANEL_DIMENSION);
        tfLogin.setFont(FONT);
        name.add(tfLogin);
        JPanel password = LayoutUtils.createHorizontalPanel();
        password.setFont(FONT);
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(FONT);
        password.add(passwordLabel);
        password.add(Box.createHorizontalStrut(12));
        tfPassword = new JPasswordField();
        tfPassword.setPreferredSize(PANEL_DIMENSION);
        tfPassword.setFont(FONT);
        password.add(tfPassword);
        JPanel flow = new JPanel( new FlowLayout( FlowLayout.CENTER, 0, 0) );
        JPanel grid = new JPanel( new GridLayout( 1,2,5,0) );
        btCreateUser = new JButton("Create New User");
        btCreateUser.setFont(FONT);
        btCreateUser.addActionListener(e -> {
            String passwordText = tfPassword.getText();
            user = new User(tfLogin.getText(), passwordText.isEmpty() ? null : passwordText);
            warningMessage = new JLabel("Invalid login or password");
            warningMessage.setVisible(false);
            panel.add(Box.createVerticalStrut(17));
            panel.add(warningMessage);
            dialog.pack();
            user.setCreatingNewUser(true);
            logInDialogCheck = true;
            dialog.setVisible(false);
        });
        btLogIn = new JButton("Log In");
        btLogIn.setFont(FONT);
        grid.add(btCreateUser);
        grid.add(btLogIn);
        btLogIn.addActionListener(e -> {
            String passwordText = tfPassword.getText();
            user = new User(tfLogin.getText(), passwordText.isEmpty() ? null : passwordText);
            warningMessage = new JLabel("Invalid login or password");
            warningMessage.setVisible(false);
            panel.add(Box.createVerticalStrut(17));
            panel.add(warningMessage);
            dialog.pack();
            user.setCreatingNewUser(false);
            logInDialogCheck = true;
            dialog.setVisible(false);
        });
        flow.add(grid);
        // Выравнивание вложенных панелей по горизонтали
        LayoutUtils.setGroupAlignmentX(new JComponent[] { name, password, panel, flow },
                Component.LEFT_ALIGNMENT);
        // Выравнивание вложенных панелей по вертикали
        LayoutUtils.setGroupAlignmentY(new JComponent[] { tfLogin, tfPassword, nameLabel, passwordLabel},
                Component.CENTER_ALIGNMENT);
        // Определение размеров надписей к текстовым полям
        GUITools.makeSameSize(new JComponent[] {nameLabel, passwordLabel} );
        // Определение стандартного вида для кнопок
        GUITools.createRecommendedMargin(new JButton[] {btCreateUser, btLogIn} );
        // Устранение "бесконечной" высоты текстовых полей
        GUITools.fixTextFieldSize(tfLogin);
        GUITools.fixTextFieldSize(tfPassword);

        panel.add(name);
        panel.add(Box.createVerticalStrut(12));
        panel.add(password);
        panel.add(Box.createVerticalStrut(12));
        panel.add(flow);
        return panel;
    }
    public void checkUserData() {
        try {
            Client.getInstance().exchangeMessage(user, EnterCommand.LogInCommand(user));
            logInDialogCheck = false;
        } catch (IOException | InterruptedException | ClassNotFoundException ex) {
            System.out.println(Arrays.toString(ex.getStackTrace()));
            JOptionPane.showMessageDialog(LoginDialog.this,
                    "Error when trying to login", "Error", JOptionPane.ERROR_MESSAGE);
            user.setRegistrationStatus(false);
            user.setCreatingNewUser(false);
            logInDialogCheck = true;
        }
    }

    public boolean showDialog(Component parent, String title) {
        logInDialogCheck = false;
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
            tfLogin.setText("");
            tfPassword.setText("");
            warningMessage.setVisible(true);
        }
            dialog.setTitle(title);
            dialog.setVisible(true);
            return logInDialogCheck;
    }
}

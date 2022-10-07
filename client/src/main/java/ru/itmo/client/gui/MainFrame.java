package ru.itmo.client.gui;

import ru.itmo.client.Client;
import ru.itmo.client.User;
import ru.itmo.common.dto.Command;
import ru.itmo.common.dto.Message;
import ru.itmo.common.dto.Print;
import ru.itmo.common.dto.Route;

import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Objects;

public class MainFrame extends JFrame {
    private User activeUser = new User();
    private final Font FONT = new Font("Dialog", Font.PLAIN, 14);
    private final JFrame parent = new JFrame("Route Manager");
    private JScrollPane scrollTable;
    private JPanel panelTable;
    private LoginDialog loginDialog = null;
    private CommandDialog commandDialog = null;
    public void MainWindow() throws IOException {
        Client.getInstance().channelInitialize();
        parent.addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                try {
                    createLogInDialog();
                } catch (IOException | InterruptedException | ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        parent.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                parent.dispose();
                System.exit(0);
            }
        });
        parent.getContentPane().add(createPanel());
        parent.setJMenuBar(createMenuBar());
        parent.setFont(FONT);
        parent.setPreferredSize(new Dimension(1020, 980));
        parent.pack();
        parent.setLocationRelativeTo(null);
        parent.setVisible(true);
    }
    private JPanel createPanel() {
        panelTable = new JPanel(new BorderLayout());
        panelTable.setBorder(BorderFactory.createEmptyBorder(120,120,120,120));
        return panelTable;
    }
    private JComponent createJTable() throws IOException, InterruptedException, ClassNotFoundException {
        Message message = Client.getInstance().exchangeMessage(activeUser, new Command(Client.getInstance().
                getUserUUID(),true, activeUser.getName(),
                activeUser.getPassword(), "show"));
        if (message.getContentRoute() != null) {
            String[] headers = {"id", "name", "creation date", "coordinate x", "coordinate y", "location x",
                    "location y", "location z", "location x", "location y", "location z", "location name", "distance"};
            DefaultTableModel tableModel = new DefaultTableModel(headers, 0);
            for (Route route: message.getContentRoute()) {
                if (route.getFrom() != null) {
                    tableModel.addRow(new Object[]{route.getId(), route.getName(),
                            new SimpleDateFormat("yyyy-MM-dd").format(route.getCreationDate()),
                            route.getCoordinates().getX(), route.getCoordinates().getY(), route.getFrom().getX(),
                            route.getFrom().getY(), route.getFrom().getZ(), route.getTo().getX(), route.getTo().getY(),
                            route.getTo().getZ(), route.getTo().getName(), route.getDistance()});
                } else {
                    tableModel.addRow(new Object[]{route.getId(), route.getName(),
                            new SimpleDateFormat("yyyy-MM-dd").format(route.getCreationDate()),
                            route.getCoordinates().getX(), route.getCoordinates().getY(), "-", "-", "-",
                            route.getTo().getX(), route.getTo().getY(), route.getTo().getZ(),
                            route.getTo().getName(), route.getDistance()});
                }
            }
            JTable table = new JTable(tableModel);
            DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
            renderer.setHorizontalAlignment(JLabel.CENTER);
            tuneColumn(table, 0, 50, renderer);
            tuneColumn(table, 1, 75, renderer);
            tuneColumn(table, 2, 75, renderer);
            tuneColumn(table, 3, 25, renderer);
            tuneColumn(table, 4, 25, renderer);
            tuneColumn(table, 5, 25, renderer);
            tuneColumn(table, 6, 25, renderer);
            tuneColumn(table, 7, 25, renderer);
            tuneColumn(table, 8, 25, renderer);
            tuneColumn(table, 9, 25, renderer);
            tuneColumn(table, 10, 50, renderer);
            tuneColumn(table, 11, 50, renderer);
            tuneColumn(table, 12, 50, renderer);
            table.setFont(FONT);
            return table;
        } else {
            JLabel lable = new JLabel("No Element", SwingConstants.CENTER);
            lable.setBorder(BorderFactory.createEmptyBorder(120,120,120,120));
            return lable;
        }

    }
    private void tuneColumn(JTable table, int columnIndex, int minWidth, DefaultTableCellRenderer renderer){
        table.getColumnModel().getColumn(columnIndex).setMinWidth(minWidth);
        table.getColumnModel().getColumn(columnIndex).setCellRenderer(renderer);
    }
    private JMenuBar createMenuBar() {
        JMenu menu = new JMenu("Commands");
        JMenu user = new JMenu("User");
        JMenuBar bar = new JMenuBar();
        URL url = getClass().getResource("/image/user_icon.png");
        JMenuItem userIcon = new JMenuItem("user", new ImageIcon(Objects.requireNonNull(url)));
        userIcon.setFont(FONT);
        JMenuItem routesInfo = new JMenuItem("About collection");
        routesInfo.setFont(FONT);
        JMenuItem enterCommand = new JMenuItem("Enter command");
        enterCommand.setFont(FONT);
        enterCommand.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                createCommandDialog();
            }
        });
        routesInfo.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                try {
                    JOptionPane.showMessageDialog(MainFrame.this, Print.printContent(Client.getInstance().
                            exchangeMessage(activeUser, new Command(Client.getInstance().getUserUUID(),true, activeUser.getName(),
                                    activeUser.getPassword(), "info"))), "Info", JOptionPane.INFORMATION_MESSAGE);
                } catch (IOException | InterruptedException | ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        user.addMenuListener(new MenuListener() {
            @Override
            public void menuSelected(MenuEvent e) {
                userIcon.setText(activeUser.getName());
                user.add(userIcon);
                user.add(routesInfo);
            }

            @Override
            public void menuDeselected(MenuEvent e) {
                user.remove(userIcon);
            }

            @Override
            public void menuCanceled(MenuEvent e) {

            }
        });
        menu.add(enterCommand);
        bar.add(menu);
        bar.add(Box.createGlue());
        bar.add(user);
        bar.setFont(FONT);
        return bar;
    }
    public void createLogInDialog() throws IOException, InterruptedException, ClassNotFoundException {
        if (loginDialog == null) {
            loginDialog = new LoginDialog();
        }
        if (loginDialog.showDialog(MainFrame.this, "Authorization")) {
            activeUser = loginDialog.user;
            loginDialog.checkUserData();
            if (!activeUser.isRegistrationStatus()) {
                createLogInDialog();
            } else {
                parent.setVisible(false);
                parent.getContentPane().remove(panelTable);
                scrollTable = new JScrollPane(createJTable());
                parent.getContentPane().add(scrollTable, BorderLayout.CENTER);
                parent.pack();
                parent.setLocationRelativeTo(null);
                parent.setVisible(true);
            }
        }
    }

    public void createCommandDialog() {
        if (commandDialog == null) {
            commandDialog = new CommandDialog(activeUser);
        }
        if (commandDialog.showCommandDialog(MainFrame.this, "Execute command")) {
            parent.getContentPane().remove(scrollTable);
            try {
                scrollTable = new JScrollPane(createJTable());
                parent.getContentPane().add(scrollTable, BorderLayout.CENTER);
                parent.pack();
                parent.setLocationRelativeTo(null);
                parent.repaint();
            } catch (IOException | InterruptedException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

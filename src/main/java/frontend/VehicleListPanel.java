package frontend;

import backend.RepositoryWorker;
import backend.ServiceVehicle;
import backend.Session;
import obserwator.StatsControler;
import osoba.Klient;
import pojazd.Pojazd;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Objects;

public class VehicleListPanel extends JPanel {
    private MainFrame mainFrame;
    private ServiceVehicle serviceVehicle;
    private JPanel vehicleListPanel;
    private StatsControler statsControler;

    private Klient currentClient;
    private JLabel nameLabel;
    private JPanel statsPanel;

    public void getClientData() {
        if (Session.getCurrentUser() instanceof Klient) {
            currentClient = (Klient) Session.getCurrentUser();
            nameLabel.setText("Zalogowano jako: " + currentClient.getImie());
        }
    }

    public VehicleListPanel(MainFrame mainFrame, ServiceVehicle serviceVehicle, StatsControler statsControler) {
        this.mainFrame = mainFrame;
        this.serviceVehicle = serviceVehicle;
        this.statsControler = statsControler;

        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout(10, 10));
        topPanel.add(new JLabel("Lista dostępnych pojazdów"), BorderLayout.WEST);

        nameLabel = new JLabel();
        topPanel.add(nameLabel, BorderLayout.CENTER);

        JButton backButton = new JButton("Powrót");
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mainFrame.ChangeCard("USER");
            }
        });
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(backButton);
        topPanel.add(buttonPanel, BorderLayout.EAST);

        statsPanel = new JPanel();
        statsPanel.setLayout(new GridLayout(1,5,5,5));

        refreshStatsPanel();
        topPanel.add(statsPanel, BorderLayout.SOUTH);
        add(topPanel, BorderLayout.NORTH);

        vehicleListPanel = new JPanel();
        vehicleListPanel.setLayout(new BoxLayout(vehicleListPanel, BoxLayout.Y_AXIS));

        JScrollPane scrollPane = new JScrollPane(vehicleListPanel);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        add(scrollPane, BorderLayout.CENTER);

        refreshList();
    }

    public void refreshList() {
        vehicleListPanel.removeAll();

        ArrayList<Pojazd> pojazdy = new ArrayList<>(serviceVehicle.getVehicles());

        for (Pojazd p : pojazdy) {
            if(Objects.equals(p.getStatus(), "wolny")) {
                JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT));
                row.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY));

                JLabel label = new JLabel(p.getMarka() + " " + p.getModel() + " (" + p.getStatus() + ")");
                JButton btnDetails = new JButton("Szczegóły");
                btnDetails.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        mainFrame.setVehicle(p);
                        mainFrame.ChangeCard("VEHICLE");
                    }
                });

                row.add(label);
                row.add(btnDetails);

                vehicleListPanel.add(row);
            }
        }
        vehicleListPanel.revalidate();
        vehicleListPanel.repaint();
    }

    public void refreshStatsPanel(){
        statsPanel.removeAll();
        int[] stats = statsControler.getStats();

        JLabel carStatsLabel = new JLabel("Dostępne auta: "+stats[0]);
        JLabel motorStatsLabel = new JLabel("Dostępne motocykle: "+stats[1]);
        JLabel tirStatsLabel = new JLabel("Dostępne ciężarówki: "+stats[2]);
        JLabel scooterStatsLabel = new JLabel("Dostępne hulajnogi: "+stats[3]);
        JLabel bikeStatsLabel = new JLabel("Dostępne rowery: "+stats[4]);

        statsPanel.add(carStatsLabel);
        statsPanel.add(motorStatsLabel);
        statsPanel.add(tirStatsLabel);
        statsPanel.add(scooterStatsLabel);
        statsPanel.add(bikeStatsLabel);

        revalidate();
        repaint();
    }
}

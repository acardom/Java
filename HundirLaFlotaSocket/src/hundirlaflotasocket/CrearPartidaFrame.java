package hundirlaflotasocket;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.Random;

public class CrearPartidaFrame extends JFrame {
    private JTextField nombrePartidaField;
    private JComboBox<String> jugadoresConectadosBox;
    private JButton crearPartidaBtn;
    private String jugador1;

    public CrearPartidaFrame(String jugador1) {
        this.jugador1 = jugador1;
        setTitle("Crear Partida");
        setSize(350, 200);
        setLayout(new GridLayout(3, 2));

        add(new JLabel("Nombre de la Partida:"));
        nombrePartidaField = new JTextField();
        add(nombrePartidaField);

        add(new JLabel("Selecciona un jugador:"));
        jugadoresConectadosBox = new JComboBox<>();
        cargarJugadoresConectados();
        add(jugadoresConectadosBox);

        crearPartidaBtn = new JButton("Crear Partida");
        add(crearPartidaBtn);

        crearPartidaBtn.addActionListener(e -> iniciarPartida());

        setVisible(true);
    }

    private void iniciarPartida() {
        String nombrePartida = nombrePartidaField.getText();
        String jugador2 = (String) jugadoresConectadosBox.getSelectedItem();

        if (nombrePartida.isEmpty() || jugador2 == null || jugador2.equals("No hay jugadores disponibles")) {
            JOptionPane.showMessageDialog(null, "Ingrese un nombre y seleccione un jugador.");
            return;
        }

        int partidaId = crearPartida(nombrePartida, jugador1, jugador2);
        if (partidaId != -1) {
            JOptionPane.showMessageDialog(null, "Partida creada. Entrando al juego...");

            boolean empiezaTurno = new Random().nextBoolean();
            new TableroFrame(partidaId, jugador1, jugador2, empiezaTurno);
            new TableroFrame(partidaId, jugador2, jugador1, !empiezaTurno);

            dispose();
        } else {
            JOptionPane.showMessageDialog(null, "Error al crear la partida.");
        }
    }

    private void cargarJugadoresConectados() {
        jugadoresConectadosBox.removeAllItems();

        String query = "SELECT username FROM usuarios WHERE estado = 'conectado' AND username != ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, jugador1);
            ResultSet rs = stmt.executeQuery();

            boolean hayJugadores = false;
            while (rs.next()) {
                jugadoresConectadosBox.addItem(rs.getString("username"));
                hayJugadores = true;
            }

            if (!hayJugadores) {
                jugadoresConectadosBox.addItem("No hay jugadores disponibles");
                jugadoresConectadosBox.setEnabled(false);
            } else {
                jugadoresConectadosBox.setEnabled(true);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al cargar jugadores conectados", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private int crearPartida(String nombre, String jugador1, String jugador2) {
        String query = "INSERT INTO partidas (nombre, jugador1_id, jugador2_id) VALUES (?, (SELECT id FROM usuarios WHERE username = ?), (SELECT id FROM usuarios WHERE username = ?))";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, nombre);
            stmt.setString(2, jugador1);
            stmt.setString(3, jugador2);

            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        return generatedKeys.getInt(1);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al crear la partida", "Error", JOptionPane.ERROR_MESSAGE);
        }

        return -1;
    }
}
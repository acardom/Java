package hundirlaflotasocket;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class DetallePartidaFrame extends JFrame {
    private int partidaId;
    private String jugador1, jugador2;
    private JLabel[][] tableroJugador1 = new JLabel[5][5];
    private JLabel[][] tableroJugador2 = new JLabel[5][5];

    public DetallePartidaFrame(int partidaId) {
        this.partidaId = partidaId;
        obtenerJugadores();  // Obtener nombres de los jugadores

        setTitle("Detalle de la Partida: " + jugador1 + " vs " + jugador2);
        setSize(700, 500);
        setLayout(new GridLayout(1, 2, 20, 20));

        JPanel panelJugador1 = crearPanelTablero(jugador1);
        JPanel panelJugador2 = crearPanelTablero(jugador2);

        add(panelJugador1);
        add(panelJugador2);

        cargarMovimientos();

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    private void obtenerJugadores() {
        String query = "SELECT " +
                "(SELECT username FROM usuarios WHERE id = p.jugador1_id) AS jugador1, " +
                "(SELECT username FROM usuarios WHERE id = p.jugador2_id) AS jugador2 " +
                "FROM partidas p WHERE p.id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, partidaId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                jugador1 = rs.getString("jugador1");
                jugador2 = rs.getString("jugador2");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private JPanel crearPanelTablero(String nombreJugador) {
    JPanel panel = new JPanel(new BorderLayout());
    JLabel titulo = new JLabel("Tablero de: " + nombreJugador, SwingConstants.CENTER); // Agregamos "Tablero de:"
    panel.add(titulo, BorderLayout.NORTH);

    JPanel tablero = new JPanel(new GridLayout(5, 5));
    JLabel[][] matrizTablero = (nombreJugador.equals(jugador1)) ? tableroJugador1 : tableroJugador2;

    for (int i = 0; i < 5; i++) {
        for (int j = 0; j < 5; j++) {
            matrizTablero[i][j] = new JLabel("", SwingConstants.CENTER);
            matrizTablero[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK));
            matrizTablero[i][j].setOpaque(true);
            matrizTablero[i][j].setBackground(Color.WHITE);  // Fondo inicial
            tablero.add(matrizTablero[i][j]);
        }
    }

    panel.add(tablero, BorderLayout.CENTER);
    return panel;
}

    private void cargarMovimientos() {
        String query = "SELECT x, y, resultado, " +
                "(SELECT username FROM usuarios WHERE id = m.jugador_id) AS jugador " +
                "FROM movimientos m WHERE partida_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, partidaId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int x = rs.getInt("x");
                int y = rs.getInt("y");
                String resultado = rs.getString("resultado");
                String jugador = rs.getString("jugador");

                JLabel[][] tablero = (jugador.equals(jugador1)) ? tableroJugador2 : tableroJugador1; 
                // Si el jugador hizo un disparo, afecta al tablero del oponente

                if (resultado.equals("tocado")) {
                    tablero[x][y].setText("X");
                    tablero[x][y].setForeground(Color.WHITE);
                    tablero[x][y].setBackground(Color.RED);
                } else {
                    tablero[x][y].setText("O");
                    tablero[x][y].setForeground(Color.WHITE);
                    tablero[x][y].setBackground(Color.BLUE);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

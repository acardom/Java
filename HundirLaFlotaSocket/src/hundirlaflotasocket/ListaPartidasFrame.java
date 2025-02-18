package hundirlaflotasocket;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ListaPartidasFrame extends JFrame {
    private String username;
    private String estado; // "finalizada" o "en curso"
    private DefaultListModel<String> listModel;
    private JList<String> listaPartidas;
    private List<Integer> partidasIds;

    public ListaPartidasFrame(String username, String estado) {
        this.username = username;
        this.estado = estado;

        setTitle("Partidas " + estado);
        setSize(500, 400);
        setLayout(new BorderLayout());

        listModel = new DefaultListModel<>();
        listaPartidas = new JList<>(listModel);
        partidasIds = new ArrayList<>();

        cargarPartidas();

        listaPartidas.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int index = listaPartidas.getSelectedIndex();
                    if (index != -1) {
                        int partidaId = partidasIds.get(index);
                        new DetallePartidaFrame(partidaId);
                    }
                }
            }
        });

        add(new JScrollPane(listaPartidas), BorderLayout.CENTER);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    private void cargarPartidas() {
        String query = "SELECT id, nombre, " +
                "(SELECT username FROM usuarios WHERE id = jugador1_id) AS jugador1, " +
                "(SELECT username FROM usuarios WHERE id = jugador2_id) AS jugador2 " +
                "FROM partidas WHERE estado = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, estado);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                String jugador1 = rs.getString("jugador1");
                String jugador2 = rs.getString("jugador2");

                listModel.addElement(nombre + " (" + jugador1 + " vs " + jugador2 + ")");
                partidasIds.add(id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

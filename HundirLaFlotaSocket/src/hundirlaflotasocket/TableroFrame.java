package hundirlaflotasocket;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.Socket;
import java.sql.*;
import java.util.Random;

public class TableroFrame extends JFrame {
    private int partidaId;
    private String jugador;
    private String oponente;
    private boolean esMiTurno;
    private JButton[][] tableroRival = new JButton[5][5];
    private JLabel[][] tableroPropio = new JLabel[5][5];
    private JTextArea logArea;
    private PrintWriter out;
    private BufferedReader in;

    public TableroFrame(int partidaId, String jugador, String oponente, boolean empiezaTurno) {
        this.partidaId = partidaId;
        this.jugador = jugador;
        this.oponente = oponente;
        this.esMiTurno = empiezaTurno;

        setTitle("Hundir la Flota - " + jugador + " vs " + oponente);
        setSize(600, 700);
        setLayout(new BorderLayout());

        conectarServidor();

        JPanel panelNombres = new JPanel(new GridLayout(1, 2));
        panelNombres.add(new JLabel("Atacar a : " + jugador, SwingConstants.CENTER));
        panelNombres.add(new JLabel("Tu tablero : " + oponente, SwingConstants.CENTER));
        add(panelNombres, BorderLayout.NORTH);

        JPanel panelTableros = new JPanel(new GridLayout(1, 2, 20, 20));

        // Tablero rival
        JPanel panelRival = new JPanel(new GridLayout(5, 5));
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                JButton btn = new JButton();
                btn.setActionCommand(i + "," + j);
                btn.addActionListener(e -> {
                    if (esMiTurno) {
                        realizarDisparo(e.getActionCommand());
                    } else {
                        log("No es tu turno.");
                    }
                });
                tableroRival[i][j] = btn;
                panelRival.add(btn);
            }
        }
        panelTableros.add(panelRival);

        // Tablero propio
        JPanel panelPropio = new JPanel(new GridLayout(5, 5));
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                tableroPropio[i][j] = new JLabel("", SwingConstants.CENTER);
                tableroPropio[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK));
                panelPropio.add(tableroPropio[i][j]);
            }
        }
        panelTableros.add(panelPropio);

        add(panelTableros, BorderLayout.CENTER);

        // Log de eventos
        logArea = new JTextArea(5, 20);
        logArea.setEditable(false);
        add(new JScrollPane(logArea), BorderLayout.SOUTH);

        colocarBarcos();

        new Thread(this::recibirMensajes).start();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void conectarServidor() {
        try {
            Socket socket = new Socket("localhost", 5000);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out.println(jugador);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void colocarBarcos() {
        Random random = new Random();
        int barcosColocados = 0;

        String query = "INSERT INTO barcos (partida_id, jugador_id, x, y) VALUES (?, (SELECT id FROM usuarios WHERE username = ?), ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            while (barcosColocados < 4) {
                int x = random.nextInt(5);
                int y = random.nextInt(5);

                if (tableroPropio[x][y].getText().isEmpty()) {
                    tableroPropio[x][y].setText("B");
                    tableroPropio[x][y].setOpaque(true);
                    tableroPropio[x][y].setBackground(Color.GRAY);

                    stmt.setInt(1, partidaId);
                    stmt.setString(2, jugador);
                    stmt.setInt(3, x);
                    stmt.setInt(4, y);
                    stmt.executeUpdate();

                    barcosColocados++;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al colocar los barcos", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void realizarDisparo(String coordenadas) {
        int x = Integer.parseInt(coordenadas.split(",")[0]);
        int y = Integer.parseInt(coordenadas.split(",")[1]);

        out.println("DISPARO;" + partidaId + ";" + jugador + ";" + x + ";" + y);
        esMiTurno = false;
        log(jugador + " disparó a (" + x + "," + y + "). Esperando respuesta...");
    }

    private void recibirMensajes() {
        try {
            String mensaje;
            while ((mensaje = in.readLine()) != null) {
                String[] partes = mensaje.split(";");
                switch (partes[0]) {
                    case "DISPARO_RECIBIDO":
                        int x = Integer.parseInt(partes[1]);
                        int y = Integer.parseInt(partes[2]);
                        String resultado = partes[3];

                        tableroRival[x][y].setBackground(resultado.equals("tocado") ? Color.RED : Color.BLUE);

                        log(oponente + " disparó a (" + x + "," + y + ") y fue " + resultado);
                        break;

                    case "TURNO":
                        esMiTurno = true;
                        log("¡Es tu turno!");
                        break;

                    case "VICTORIA":
                        log("¡Ganaste la partida!");
                        JOptionPane.showMessageDialog(this, "¡Has ganado!");
                        break;

                    case "DERROTA":
                        log("Perdiste la partida.");
                        JOptionPane.showMessageDialog(this, "Has perdido...");
                        break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void log(String mensaje) {
        logArea.append(mensaje + "\n");
    }
}
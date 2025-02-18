package hundirlaflotasocket;

import java.io.*;
import java.net.*;
import java.sql.*;
import java.util.*;

public class Servidor {

    private static final int PUERTO = 5000;
    private static Map<String, Socket> jugadoresConectados = new HashMap<>();

    public static void main(String[] args) {
        resetearUsuarios();

        try (ServerSocket serverSocket = new ServerSocket(PUERTO)) {
            System.out.println("Servidor iniciado en el puerto " + PUERTO + "...");

            while (true) {
                Socket socket = serverSocket.accept();
                new HiloCliente(socket).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void resetearUsuarios() {
        String query = "UPDATE usuarios SET estado = 'desconectado'";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.executeUpdate();
            System.out.println("Todos los usuarios han sido desconectados.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static class HiloCliente extends Thread {

        private Socket socket;
        private BufferedReader in;
        private PrintWriter out;
        private String username;

        public HiloCliente(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);

                username = in.readLine();
                if (username != null) {
                    jugadoresConectados.put(username, socket);
                    System.out.println(username + " se ha conectado.");
                }

                String mensaje;
                while ((mensaje = in.readLine()) != null) {
                    procesarMensaje(mensaje);
                }
            } catch (IOException e) {
                System.out.println("Error con " + username + ": " + e.getMessage());
            } finally {
                desconectarUsuario();
            }
        }

       private void procesarMensaje(String mensaje) {
    String[] partes = mensaje.split(";");
    if (partes[0].equals("DISPARO")) {
        int partidaId = Integer.parseInt(partes[1]);
        String jugador = partes[2];
        int x = Integer.parseInt(partes[3]);
        int y = Integer.parseInt(partes[4]);

        String oponente = obtenerOponente(partidaId, jugador);
        boolean acierto = verificarImpacto(partidaId, oponente, x, y);
        String resultado = acierto ? "tocado" : "agua";

        // Guardar el movimiento en la base de datos
        registrarMovimiento(partidaId, jugador, x, y, resultado);

        enviarMensaje(jugador, "DISPARO_RECIBIDO;" + x + ";" + y + ";" + resultado);
        enviarMensaje(oponente, "ACTUALIZAR_TABLERO;" + x + ";" + y + ";" + resultado);

        if (verificarVictoria(partidaId, oponente)) {
            enviarMensaje(jugador, "VICTORIA");
            enviarMensaje(oponente, "DERROTA");
        } else {
            enviarMensaje(oponente, "TURNO");
        }
    }
}
private void registrarMovimiento(int partidaId, String jugador, int x, int y, String resultado) {
    String query = "INSERT INTO movimientos (partida_id, jugador_id, x, y, resultado) " +
                   "VALUES (?, (SELECT id FROM usuarios WHERE username = ?), ?, ?, ?)";

    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(query)) {

        stmt.setInt(1, partidaId);
        stmt.setString(2, jugador);
        stmt.setInt(3, x);
        stmt.setInt(4, y);
        stmt.setString(5, resultado);
        stmt.executeUpdate();

        System.out.println("Movimiento registrado: " + jugador + " disparó en (" + x + "," + y + ") - " + resultado);

    } catch (SQLException e) {
        e.printStackTrace();
        System.out.println("Error al registrar el movimiento.");
    }
}


        private boolean verificarImpacto(int partidaId, String jugador, int x, int y) {
            String query = "SELECT * FROM barcos WHERE partida_id = ? "
                    + "AND jugador_id = (SELECT id FROM usuarios WHERE username = ?) "
                    + "AND x = ? AND y = ?";
            try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setInt(1, partidaId);
                stmt.setString(2, jugador);
                stmt.setInt(3, x);
                stmt.setInt(4, y);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    eliminarBarco(partidaId, jugador, x, y);
                    return true;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return false;
        }

        private void eliminarBarco(int partidaId, String jugador, int x, int y) {
            String query = "DELETE FROM barcos WHERE partida_id = ? "
                    + "AND jugador_id = (SELECT id FROM usuarios WHERE username = ?) "
                    + "AND x = ? AND y = ?";
            try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setInt(1, partidaId);
                stmt.setString(2, jugador);
                stmt.setInt(3, x);
                stmt.setInt(4, y);
                stmt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        private void enviarMensaje(String jugador, String mensaje) {
            if (jugadoresConectados.containsKey(jugador)) {
                try {
                    PrintWriter outJugador = new PrintWriter(jugadoresConectados.get(jugador).getOutputStream(), true);
                    outJugador.println(mensaje);
                } catch (IOException e) {
                    System.out.println("Error al enviar mensaje a " + jugador + ": " + e.getMessage());
                }
            }
        }

        private void desconectarUsuario() {
            try {
                if (username != null && jugadoresConectados.containsKey(username)) {
                    jugadoresConectados.remove(username);
                    System.out.println(username + " se ha desconectado.");
                    socket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private String obtenerOponente(int partidaId, String jugador) {
            String query = "SELECT username FROM usuarios WHERE id = "
                    + "(SELECT jugador2_id FROM partidas WHERE id = ? AND jugador1_id = "
                    + "(SELECT id FROM usuarios WHERE username = ?)) "
                    + "UNION "
                    + "SELECT username FROM usuarios WHERE id = "
                    + "(SELECT jugador1_id FROM partidas WHERE id = ? AND jugador2_id = "
                    + "(SELECT id FROM usuarios WHERE username = ?))";
            try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setInt(1, partidaId);
                stmt.setString(2, jugador);
                stmt.setInt(3, partidaId);
                stmt.setString(4, jugador);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    return rs.getString("username");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        }

        private boolean verificarVictoria(int partidaId, String jugador) {
            String query = "SELECT COUNT(*) AS barcos_restantes FROM barcos "
                    + "WHERE partida_id = ? AND jugador_id = (SELECT id FROM usuarios WHERE username = ?)";

            try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setInt(1, partidaId);
                stmt.setString(2, jugador);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    int barcosRestantes = rs.getInt("barcos_restantes");
                    System.out.println("Jugador " + jugador + " tiene " + barcosRestantes + " barcos restantes.");

                    if (barcosRestantes == 0) {  // Si el jugador ha perdido (se quedó sin barcos)
                        actualizarPartida(partidaId, jugador);
                        return true;
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            return false;
        }

// Nueva función para actualizar la base de datos con el ganador
        private void actualizarPartida(int partidaId, String ganador) {
            String query = "UPDATE partidas SET ganador_id = (SELECT id FROM usuarios WHERE username = ?), estado = 'finalizada' WHERE id = ?";

            try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, ganador);
                stmt.setInt(2, partidaId);
                stmt.executeUpdate();
                System.out.println("Partida " + partidaId + " finalizada. Ganador: " + ganador);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }
}

package hundirlaflotasocket;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainFrame extends JFrame {
    private String username;

    public MainFrame(String username) {
        this.username = username;
        setTitle("Menú Principal");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); // Evita el cierre automático

        // Configuración del layout principal
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1, 10, 10)); // 3 filas, 1 columna, con separación

        // Crear botones con estilo mejorado
        JButton crearPartidaBtn = new JButton("Crear Partida");
        JButton verPartidasBtnAcabadas = new JButton("Ver Partidas Finalizadas");
        JButton verPartidasBtnEnCurso = new JButton("Ver Partidas En Curso");

        // Estilo de los botones
        Font font = new Font("Arial", Font.BOLD, 16);
        crearPartidaBtn.setFont(font);
        verPartidasBtnAcabadas.setFont(font);
        verPartidasBtnEnCurso.setFont(font);

        crearPartidaBtn.setFocusPainted(false);
        verPartidasBtnAcabadas.setFocusPainted(false);
        verPartidasBtnEnCurso.setFocusPainted(false);

        // Acción de los botones
        crearPartidaBtn.addActionListener(e -> new CrearPartidaFrame(username));
        verPartidasBtnAcabadas.addActionListener(e -> new ListaPartidasFrame(username, "finalizada"));
        verPartidasBtnEnCurso.addActionListener(e -> new ListaPartidasFrame(username, "en curso"));

        // Agregar botones al panel
        panel.add(crearPartidaBtn);
        panel.add(verPartidasBtnAcabadas);
        panel.add(verPartidasBtnEnCurso);

        // Agregar margen alrededor del panel
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Agregar panel a la ventana
        add(panel);

        // Agregar listener para detectar cuando la ventana se cierra
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                cerrarSesion();
                dispose(); // Cierra la ventana
                System.exit(0); // Termina la aplicación
            }
        });

        setLocationRelativeTo(null); // Centrar la ventana
        setVisible(true);
    }

    private void cerrarSesion() {
        LoginManager.logout(username); // Cambia estado a "desconectado"
        System.out.println(username + " ha cerrado sesión.");
    }
}

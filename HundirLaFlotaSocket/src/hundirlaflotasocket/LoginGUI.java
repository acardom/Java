package hundirlaflotasocket;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginGUI extends JFrame {
    private JTextField userField;
    private JPasswordField passField;
    private JButton loginButton;

    public LoginGUI() {
        setTitle("Login - Hundir La Flota");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(3, 2));

        // Componentes
        add(new JLabel("Usuario:"));
        userField = new JTextField();
        add(userField);

        add(new JLabel("Contraseña:"));
        passField = new JPasswordField();
        add(passField);

        loginButton = new JButton("Iniciar Sesión");
        add(loginButton);

        // Acción del botón
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = userField.getText();
                String password = new String(passField.getPassword());

                if (LoginManager.login(username, password)) {
                    JOptionPane.showMessageDialog(null, "Login exitoso");
                    dispose(); // Cierra la ventana de login
                    new MainFrame(username); // Abre la ventana principal
                } else {
                    JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrectos o ya conectado");
                }
            }
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        new LoginGUI();
    }
}
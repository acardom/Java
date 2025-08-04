/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package Procesos;
import java.util.Scanner;
/**
 *
 * @author DAM
 */
public class Buscar_numero_de_letras_en_texto {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // Leer el texto de la entrada estándar
        System.out.print("Introduce el texto: ");
        String texto = scanner.nextLine();
        
        // Contadores para las vocales
        int conteoA = 0;
        int conteoE = 0;
        int conteoO = 0;

        // Contar las vocales
        for (char c : texto.toCharArray()) {
            switch (Character.toLowerCase(c)) {
                case 'a':
                    conteoA++;
                    break;
                case 'e':
                    conteoE++;
                    break;
                case 'o':
                    conteoO++;
                    break;
            }
        }

        // Imprimir el resultado
        System.out.println(conteoA + " " + conteoE + " " + conteoO);
        
        scanner.close();
    }
}


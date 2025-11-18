import java.util.Scanner;
import javax.swing.*;
import java.awt.*; // pour interface graphique
public class Main {
    public static void main(String[] args) {
        int nombre;
        Scanner sc = new Scanner(System.in);
        System.out.println("Bienvenue sur le projet ! Veuillez choisir le graphe :");
        nombre = sc.nextInt();
        System.out.println("Vous avez choisit le graphe : " + nombre);
        sc.close();

    }
}
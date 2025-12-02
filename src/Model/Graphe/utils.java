package Model.Graphe;

import java.io.*;
import java.util.*;
import java.nio.file.*;


public class utils {



    public static void adjmat_to_goodformat() throws IOException {

        // Permet de convertir une matrice adjacente (avec le bon format) en une liste d'arrête avec toutes les données nécessaire.
        // fichier de base : nombre de sommet, matricea adjacente
        // fichier out : list d'arrête avec les infos avec le format : predecesseur successeur poids orienté (boolen)


        // /!\ Marche seulement pour du non orianté

        //Faut changer à chaque fois les chemins ici
        String fichierin = "data/test/adjmarc.txt";

        List<String> lines = Files.readAllLines(Paths.get(fichierin));

        int n = Integer.parseInt(lines.get(0).trim());
        double[][] adjmatrice = new double[n][n];

        for (int i = 1; i <= n; i++) {
            String[] info = lines.get(i).trim().split("\\s+");
            for (int j = 0; j < n; j++) {
                adjmatrice[i - 1][j] = Double.parseDouble(info[j]);
            }
        }

        PrintWriter pw = new PrintWriter(fichierin);

        pw.println(n);

        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (adjmatrice[i][j] != 0) {
                    pw.println(i + " " + j + " " + adjmatrice[i][j] + " 0");
                }
            }
        }

        pw.close();

    }

    public static void convertadjmat(){

        // Permet de convertir la matrice adjacente qu'on obtient de graphe online en un bon format pour la traiter ensuite
        // matrice de base : pas de nombre de sommet, virgule partout
        // matrice finale : nombre de sommet sans virgule


        //Faut changer à chaque fois les chemins ici
        String fichierin = "data/test/adjmarc.txt";

        List<List<Integer>> matrice = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(fichierin))) {
            String line;

            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;

                if (line.endsWith(",")) {
                    line = line.substring(0, line.length() - 1);
                }

                String[] info = line.split(",");
                List<Integer> colonne = new ArrayList<>();

                for (String p : info) {
                    p = p.trim();
                    if (!p.isEmpty()) {
                        colonne.add(Integer.parseInt(p));
                    }
                }

                matrice.add(colonne);
            }


            int n = matrice.size();

            try (PrintWriter pw = new PrintWriter(new FileWriter(fichierin))) {

                pw.println(n);

                for (List<Integer> row : matrice) {
                    for (int value : row) {
                        pw.print(value + " ");
                    }
                    pw.println();
                }
            }

            System.out.println("Conversion ok");

        } catch (Exception e) {
            System.out.println("Oupsidoupsi : " + e.getMessage());
        }
    }

    public static void main(String[] args){

        try {
            adjmat_to_goodformat();
            System.out.println("Conversion ok");
        } catch (Exception e) {
            System.err.println("Oupsidoupsi : " + e.getMessage());
        }

    }

}

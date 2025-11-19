package Model.Graphe;

import java.io.*;
import java.util.*;


public class utils {


    public static void ajcmat_to_goodformat(){




    }


    public static void convertadjmat(){

        // Permet de convertir la matrice adjacente qu'on obtient de graphe online en un bon format pour la traiter ensuite
        // matrice de base : pas de nombre de sommet, virgule partout
        // matrice finale : nombre de sommet sans virgule


        //Faut changer à chaque fois les chemins ici
        String fichierin = "data/test/adj1.txt";

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

        utils.convertadjmat();

    }

}

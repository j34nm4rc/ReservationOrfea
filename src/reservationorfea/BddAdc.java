/*
 * Copyright (C) 2015 shadowcjm35
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package reservationorfea;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

/**
 * <b>La classe BddAdc est en charge de la gestion de la base de données des Adc.</b>
 * <br>La base est un fichier dont le nom est récupérer dans le fichier config.txt
 * et passer en argument à la classe lors de son initialisation. Le format du
 * fichier est un simple fichier texte<br>
 * <ul>
 *  <li>dont les données de l'agent sont mis sur
 * 1 ligne</li>
 * <li>sous la forme Nom Prenom NumeroCp</li>
 * <li>séparés par un ';'</li></ul>
 *<br>Le fichier ne comporte pas d'entête. Un exemple de fichier est fournit 'exempleFichierAdc.txt'.
 *
 * @author Jean-Marc Duval
 * @version 0.1.0
 *
 */

public class BddAdc {
    
    private static ArrayList<String> nomAdc = new ArrayList<String>();
    private static ArrayList<String> prenomAdc = new ArrayList<String>();
    private static ArrayList<String> cpAdc = new ArrayList<String>();
    
    public void init(String nomFichier) {
        if (existeFichier(nomFichier)) {
            parcoursTmp(nomFichier);
        }
    }
    
    
    private boolean existeFichier(String nomFichier) {
        //To change body of generated methods, choose Tools | Templates.
        File fichier = new File(nomFichier);
        return fichier.exists(); //return true ou false
    }
    
    private void parcoursTmp(String nomFichier) {
        
        try {
            Scanner sc;

            File fichier = new File(nomFichier);

            // initialisation du reader ...
            sc = new Scanner(fichier);
            // L'expression régulière qui délimite les champs
            sc.useDelimiter(Pattern.compile(";"));
            // On boucle sur chaque champ detecté
            do {
                // Si le champ n'est pas un string, une exception de type InputMismatchException sera levée
                nomAdc.add(sc.next());
                prenomAdc.add(sc.next());
                cpAdc.add(sc.next());
                sc.nextLine();
            } while (sc.hasNext());
            sc.close();
        }       catch (FileNotFoundException ex) {
            Logger.getLogger(Orfea.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static int taille() {
        return nomAdc.size();
    }
    
    public int verifCp(String numCp)
    {
        for (int i=0; i<taille();++i)
        {
            if(numCp.equals(cpAdc.get(i))) { return i; }
        }
        return 99999999;
    }

    public String getNom(int index) {
        return nomAdc.get(index);
    }

    public String getPrenom(int index) {
        return prenomAdc.get(index);
    }


}

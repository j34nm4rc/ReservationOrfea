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

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * @author Jean-Marc Duval
 * @version 0.2
 *
 */
public class Orfea {
    // declarations
    private static ArrayList<String> nomAdc = new ArrayList<String>();
    private static ArrayList<String> prenomAdc = new ArrayList<String>();
    private static ArrayList<String> cpAdc = new ArrayList<String>();
    private static ArrayList<String> villeArrivee = new ArrayList<String>();
    private static ArrayList<String> dateArrivee = new ArrayList<String>();
    private static ArrayList<String> dateDepart = new ArrayList<String>();
    private static ArrayList<String> heureArrivee = new ArrayList<String>();
    private static ArrayList<String> heureDepart = new ArrayList<String>();
    private static ArrayList<String> jsArrivee = new ArrayList<String>();
    private static ArrayList<String> jsDepart = new ArrayList<String>();
    // declaration des entetes du fichier Orfea au 01/05/2015
    private String[] enteteFichier = {"VILRHR",
        "CTRESA",
        "CATPER",
        "ETADOR",
        "ORIPER",
        "RESPER",
        "NUMTRA",
        "PLRLTA",
        "NAETPA",
        "JFSREF",
        "HEURFS",
        "NUMTRD",
        "PLRLTD",
        "NAETPD",
        "JPSREF",
        "HEURPS",
        "ACTIVI",
        "NOMPER",
        "PRNPER",
        "IMAPER"};
    /*
     * Initialisation des variables à la création de la méthode
     */

    /*
     * Ajout d'éléments dans le tableau
     * @parametres
     * disponible pour l'utilisateur
     */
    public void ajout(String nom, String prenom, String cp, String ville, String dtArr, String hArr, String jsArr, String dtDep, String hDep, String jsDep) {
        nomAdc.add(nom);
        prenomAdc.add(prenom);
        cpAdc.add(cp);
        villeArrivee.add(ville);
        dateArrivee.add(dtArr);
        heureArrivee.add(hArr);
        jsArrivee.add(jsArr);
        dateDepart.add(dtDep);
        heureDepart.add(hDep);
        jsDepart.add(jsDep);
    }

    /*
     * retourne la taille (en entier) de la base calculé sur le ArrayList nomAdc
     * Tous les ArrayList sont remplis en même temps.
     * disponible pour l'utilisateur
     */
    public static int taille() {
        return nomAdc.size();
    }

    /*
     * Liste tous les éléments des tableaux
     * Test si la base est vide
     * Si la base n'est pas vide on affiche les éléments
     * disponible pour l'utilisateur
     */
    public void affiche() {
        int nb = nomAdc.size();
        if (Orfea.isEmpty()) {
            System.out.println("");
            for (int i = 0; i < nb; ++i) {
                System.out.println((i + 1) + " | "
                        + nomAdc.get(i) + " | "
                        + prenomAdc.get(i) + " | "
                        + cpAdc.get(i) + " | "
                        + villeArrivee.get(i) + " | "
                        + dateArrivee.get(i) + " | "
                        + heureArrivee.get(i) + " | "
                        + jsArrivee.get(i) + " | "
                        + dateDepart.get(i) + " | "
                        + heureDepart.get(i) + " | "
                        + jsDepart.get(i) + " | ");
            }
        } else {
            System.out.println("Base vide");
        }
    }

    /*
     * surcharge de affiche()
     * Recoit 1 entier en attribut qui pointe sur la ligne à afficher
     * disponible pour l'utilisateur
     */
    public void affiche(int i) {
        System.out.println((i + 1)
                + nomAdc.get(i) + " | "
                + prenomAdc.get(i) + " | "
                + cpAdc.get(i) + " | "
                + villeArrivee.get(i) + " | "
                + dateArrivee.get(i) + " | "
                + heureArrivee.get(i) + " | "
                + jsArrivee.get(i) + " | "
                + dateDepart.get(i) + " | "
                + heureDepart.get(i) + " | "
                + jsDepart.get(i) + " | ");
    }
    
    public String recupDonnees(int i) {
        String chaine;
        chaine = nomAdc.get(i) + ";"
                + prenomAdc.get(i) + ";"
                + cpAdc.get(i) + ";"
                + villeArrivee.get(i) + ";"
                + dateArrivee.get(i) + ";"
                + heureArrivee.get(i) + ";"
                + jsArrivee.get(i) + ";"
                + dateDepart.get(i) + ";"
                + heureDepart.get(i) + ";"
                + jsDepart.get(i) + ";";
        return chaine;
    }

    /*
     * Test de la base si vide. Non accessible en dehors de Orfea
     * retourne True ou False sous forme de boolean
     */
    private static boolean isEmpty() {
        // TODO Auto-generated method stub
        return !nomAdc.isEmpty();
    }

    /*
     * vide toutes les ArrayList déclarées en entête du fichier Orfea.java
     * disponible pour l'utilisateur
     */
    public void clean() {
        // TODO Auto-generated method stub
        nomAdc.clear();
        prenomAdc.clear();
        cpAdc.clear();
        villeArrivee.clear();
        dateArrivee.clear();
        heureArrivee.clear();
        jsArrivee.clear();
        dateDepart.clear();
        heureDepart.clear();
        jsDepart.clear();
    }

    /*
     * supprime de la base l'élément désigné par l'attribut int i
     */
    public void supp(int i) {
        // TODO Auto-generated method stub
        nomAdc.remove(i);
        prenomAdc.remove(i);
        cpAdc.remove(i);
        villeArrivee.remove(i);
        dateArrivee.remove(i);
        heureArrivee.remove(i);
        jsArrivee.remove(i);
        dateDepart.remove(i);
        heureDepart.remove(i);
        jsDepart.remove(i);
    }
    /*
     * fonction de création du fichier dont le nom est construit puis passé en argument
     * disponible pour l'utilisateur
    */
    public void createFichier(String nomDuFichier) {
        // TODO Auto-generated method stub
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet feuille = wb.createSheet("Orfea");
        HSSFRow ligne = feuille.createRow(0);

        //creation entete
        for (int i = 0; i < enteteFichier.length; ++i) {
            ligne.createCell(i, HSSFCell.CELL_TYPE_STRING).
                    setCellValue(new HSSFRichTextString(enteteFichier[i]));
        }
        
        //peuplement du fichier Excel en fonction de nombre d'éléments de l'ArrayList nomAdc
        for (int i = 1; i <= nomAdc.size(); ++i) {
            ligne = feuille.createRow(i);
            ligne.createCell(0, HSSFCell.CELL_TYPE_STRING).
                    setCellValue(new HSSFRichTextString(villeArrivee.get(i - 1)));
            ligne.createCell(1, HSSFCell.CELL_TYPE_STRING).
                    setCellValue(new HSSFRichTextString(""));
            ligne.createCell(2, HSSFCell.CELL_TYPE_STRING).
                    setCellValue(new HSSFRichTextString("ADC"));
            ligne.createCell(3, HSSFCell.CELL_TYPE_STRING).
                    setCellValue(new HSSFRichTextString("ET PARIS RIVE GAUCHE"));
            ligne.createCell(4, HSSFCell.CELL_TYPE_STRING).
                    setCellValue(new HSSFRichTextString("TRA"));
            ligne.createCell(5, HSSFCell.CELL_TYPE_STRING).
                    setCellValue(new HSSFRichTextString(""));
            ligne.createCell(6, HSSFCell.CELL_TYPE_STRING).
                    setCellValue(new HSSFRichTextString(jsArrivee.get(i - 1)));
            ligne.createCell(7, HSSFCell.CELL_TYPE_STRING).
                    setCellValue(new HSSFRichTextString(jsArrivee.get(i - 1)));
            ligne.createCell(8, HSSFCell.CELL_TYPE_STRING).
                    setCellValue(new HSSFRichTextString(""));
            ligne.createCell(9, HSSFCell.CELL_TYPE_STRING).
                    setCellValue(new HSSFRichTextString(dateArrivee.get(i - 1)));
            ligne.createCell(10, HSSFCell.CELL_TYPE_STRING).
                    setCellValue(new HSSFRichTextString(heureArrivee.get(i - 1)));
            ligne.createCell(11, HSSFCell.CELL_TYPE_STRING).
                    setCellValue(new HSSFRichTextString(jsDepart.get(i - 1)));
            ligne.createCell(12, HSSFCell.CELL_TYPE_STRING).
                    setCellValue(new HSSFRichTextString(jsDepart.get(i - 1)));
            ligne.createCell(13, HSSFCell.CELL_TYPE_STRING).
                    setCellValue(new HSSFRichTextString(""));
            ligne.createCell(14, HSSFCell.CELL_TYPE_STRING).
                    setCellValue(new HSSFRichTextString(dateDepart.get(i - 1)));
            ligne.createCell(15, HSSFCell.CELL_TYPE_STRING).
                    setCellValue(new HSSFRichTextString(heureDepart.get(i - 1)));
            ligne.createCell(16, HSSFCell.CELL_TYPE_STRING).
                    setCellValue(new HSSFRichTextString(""));
            ligne.createCell(17, HSSFCell.CELL_TYPE_STRING).
                    setCellValue(new HSSFRichTextString(nomAdc.get(i - 1)));
            ligne.createCell(18, HSSFCell.CELL_TYPE_STRING).
                    setCellValue(new HSSFRichTextString(prenomAdc.get(i - 1)));
            ligne.createCell(19, HSSFCell.CELL_TYPE_STRING).
                    setCellValue(new HSSFRichTextString(cpAdc.get(i - 1)));
        }
        
        // enregistrement du fichier
        FileOutputStream fileOut;
        try {
            fileOut = new FileOutputStream(nomDuFichier);
            wb.write(fileOut);
            fileOut.close();
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        }
    }

    public boolean presenceTmp(String nomFichier) {
        //To change body of generated methods, choose Tools | Templates.
        File fichier = new File(nomFichier);
        return fichier.exists(); //return true ou false
    }
    
    public void suppTmp(String nomFichier) {
        //To change body of generated methods, choose Tools | Templates.
        File fichier = new File(nomFichier);
        fichier.delete();
        System.out.println("Fichier " + fichier + " supprimé.");
    }
    
    public void parcoursTmp(String nomFichier) {
        
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
                villeArrivee.add(sc.next());
                dateArrivee.add(sc.next());
                heureArrivee.add(sc.next());
                jsArrivee.add(sc.next());
                dateDepart.add(sc.next());
                heureDepart.add(sc.next());
                jsDepart.add(sc.next());
                sc.nextLine();
            } while (sc.hasNext());
            sc.close();
        }       catch (FileNotFoundException ex) {
            Logger.getLogger(Orfea.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void saveTmp(String nomFichier) {

        try {
            PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(nomFichier)));
            int index;

            for (index = 0; index < nomAdc.size(); ++index) {
                String chaine = recupDonnees(index);
                pw.println(chaine);
            }

            pw.close();
        } catch (IOException exception) {
            System.out.println("Erreur lors de la lecture : " + exception.getMessage());
        }

    }
   
}

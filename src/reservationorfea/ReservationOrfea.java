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

import java.util.Scanner;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 *
 * @author Jean-Marc Duval
 * @version 1.0.0
 *
 */
public class ReservationOrfea {

    /**
     * @param args
     */

    static Scanner lectClavier = new Scanner(System.in);
    static Orfea donnees = new Orfea();
    static BddAdc bddAdc = new BddAdc();
    static final String NF = "~tmp.txt";
    static char choix;

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        //Scanner lectClavier = new Scanner(System.in);

        int lectNbNuit = 0;
        /*
        * A écrire: chargement du fichier de config
        */
        
        /*
        * A écrire: gestion de la base Agent
        */
        
        bddAdc.init("baseadc.txt");
        int tailleBdd = bddAdc.taille();
        if (tailleBdd != 0) { System.out.println("Nombre d'agent en base: "+tailleBdd); }
        
        testTmp(); //test présence d'un fichier tmp + choix de son utilisation

        do {
            System.out.print("[A]jouter / [S]upprimer / [L]ister / [G]énérer le fichier / [R]ecommencer (mise a 0 du fichier) / [Q]uitter : ");
            String commande = lectClavier.nextLine();
            choix = commande.charAt(0);
            choix = Character.toLowerCase(choix);
            System.out.println("");

            switch (choix) {
                case 'a':
                    String lectNomAdc;
                    String lectPrenomAdc;
                    String lectCpAdc;
                    String lectDateNuitee;
                    String lectVilleArrivee = null;
                    String lectHeureArrivee = null;
                    String lectHeureDepart = null;
                    String lectJsArrivee = null;
                    String lectJsDepart = null;

                    boolean boucleOk = false;
                    boolean confTaille = false;

                    do {
                        lectNomAdc = saisieADC("Nom");
                        lectPrenomAdc = saisieADC("Prenom");
                        do {
                            lectCpAdc = saisieADC("n° de CP");
                            if (lectCpAdc.length() == 8) {
                                confTaille = true;
                            } else {
                                System.out.println("Attention, saisie sur 8 caracteres (7 chiffres + 1 lettre):");
                            }
                        } while (!confTaille);
                        
                        boolean loopOk = false;

                        do { 
                            System.out.print("Entrez la date d'arrivee de l'agent (ex: 01/01/2015): ");
                            lectDateNuitee = lectClavier.nextLine();
                            if (lectDateNuitee.length() != 10) {
                                    System.out.println("La date doit etre sous la forme dd/mm/aaaa (ex: 12/12/2015) : ");
                            } else {
                                loopOk = true;
                            }
                        } while (!loopOk);

                        do {
                            System.out.print("Entrez le nombre de nuits consecutives concernees (mini: 1): ");
                            lectNbNuit = lectClavier.nextInt();
                            lectClavier.nextLine();
                            if (lectNbNuit < 0) {
                                System.out.println("Attention, le nombre de nuit doit être superieur a 0 !");
                                boucleOk = false;
                            } else {
                                boucleOk = true;
                            }
                        } while (lectNbNuit < 1);

                    } while (!boucleOk);

                    DateTimeFormatter formatter = DateTimeFormat.forPattern("dd/MM/yyyy");
                    LocalDate dateArrivee = LocalDate.parse(lectDateNuitee, formatter);
                    LocalDate dateArr, dateDepart;

                    boolean compoIdem = false;
                    boolean question = false;

                    for (int i = 1; i <= lectNbNuit; ++i) {
                        /* si compo identique on ne demande pas de resaisir les données
                         * le changement d'état du booléen se fait dans l'autre boucle
                         * si compoIdem  = true c'est que la question a déjà été posée
                         */

                        if (!compoIdem) {
                            System.out.print("Entrez Ville d'arrivee (format SNCF): ");
                            lectVilleArrivee = lectClavier.nextLine();
                            lectVilleArrivee = lectVilleArrivee.toUpperCase();
                            lectHeureArrivee = saisieHeure("d'arrivee");
                            lectJsArrivee = saisieJS("d'arrivee");
                            lectHeureDepart = saisieHeure("de depart");
                            lectJsDepart = saisieJS("de depart");
                        }

                        /* on pose la question si les données ont été déjà saisies uniquement si le nb de nuitée
                         * est supérieure à 1
                         */
                        if (lectNbNuit != 1) {
                            if (!question) {
                                System.out.print("Les donnees seront elles toutes identiques ?: ");
                                char test;
                                String reponse = lectClavier.nextLine();
                                test = reponse.charAt(0);
                                test = Character.toLowerCase(test);
                                if (test == 'o') {
                                    compoIdem = true;
                                }
                                question = true;
                            }
                        }

                        dateArr = dateArrivee.plusDays(i - 1);
                        dateDepart = dateArr.plusDays(1);

                        donnees.ajout(lectNomAdc,
                                lectPrenomAdc,
                                lectCpAdc,
                                lectVilleArrivee,
                                dateArr.toString("dd/MM/yyyy"),
                                lectHeureArrivee,
                                lectJsArrivee,
                                dateDepart.toString("dd/MM/yyyy"),
                                lectHeureDepart,
                                lectJsDepart);
                    }
                    afficheTaille();
                    svgTmp();
                    break;

                case 's':
                    suppLigne();
                    break;

                case 'l':
                    donnees.affiche();
                    break;

                case 'g':
                    System.out.println("Merci d'entrer le nom du fichier a generer (sans ext):");
                    String nomDuFichier = lectClavier.nextLine();
                    nomDuFichier += ".xls";
                    donnees.createFichier(nomDuFichier);
                    System.out.println("Fichier " + nomDuFichier + " sauvegarde.");
                    System.out.println("Importe ce fichier en suivant la procedure.");
                    if (donnees.presenceTmp(NF)) { donnees.saveTmp(NF); }
                    break;

                case 'r':
                    donnees.clean();
                    afficheTaille();
                    break;

                case 'q':
                    break;

            }
        } while ((choix != 'q') && (choix != 'g'));

        lectClavier.close();
    }

    public static void afficheTaille() {
        System.out.println();
        System.out.println("Le tableau contient " + donnees.taille() + " élément(s).");
        System.out.println("");
    }

    public static String saisieHeure(String texte) {
        String heure;
        boolean confTaille = false, confCompo = false;
        do {
            System.out.print("Entrez l'Heure " + texte + " (ex 19:30 pour 19 h 30): ");
            heure = lectClavier.nextLine();
            if (heure.length() != 5) {
                System.out.println("Attention, le format doit etre de type hh:mm. Exemple 19:30 pour 19h30.");
                confTaille = false;
            } else {
                char test = heure.charAt(2);
                if (test != ':') {
                    System.out.println("Attention, le format doit etre de type hh:mm. Exemple 19:30 pour 19h30.");
                    confCompo = false;
                } else {
                    confTaille = true;
                    confCompo = true;
                }
            }
        } while ((!confTaille) && (!confCompo));
        return heure;
    }

    public static String saisieADC(String texte) {
        String js;
        System.out.print("Entrez le " + texte + " de l'agent: ");
        js = lectClavier.nextLine();
        return js.toUpperCase();
    }

    public static String saisieJS(String texte) {
        String js;
        boolean confTaille = false;
        do {
            System.out.print("Entrez le n° de JS " + texte + ": ");
            js = lectClavier.nextLine();
            js = js.toUpperCase();
            if (js.length() != 4) {
                System.out.println("Attention, la saisie doit se faire sur 4 caracteres! ");
                confTaille = false;
            } else {
                confTaille = true;
            }
        } while (!confTaille);
        return js;
    }

    public static void suppLigne() {
        donnees.affiche();
        System.out.print("Entrez la ligne a supprimer:");
        int index = lectClavier.nextInt();
        lectClavier.nextLine();
        donnees.affiche(index - 1);
        System.out.print("Vous confirmez la suppression (O/n) ?");
        String confirmation = lectClavier.nextLine();
        char choix;
        choix = confirmation.charAt(0);
        choix = Character.toLowerCase(choix);
        if (choix == 'o') {
            donnees.supp(index - 1);
            afficheTaille();
        } else {
            System.out.println("Base non modifiee !");
        }

    }

    public static void testTmp() {

        if (donnees.presenceTmp(NF)) {
            System.out.print("\nVoulez-vous utiliser le fichier tmp ? (o/n) : ");
            String reponse = lectClavier.nextLine();
            choix = reponse.charAt(0);
            choix = Character.toLowerCase(choix);
            if (choix == 'o') {
                donnees.parcoursTmp(NF); //on recupere les informations dans le fichier tmp NF
            } else {
                donnees.suppTmp(NF);
            }
        }

    }

    public static void svgTmp() {

        if (donnees.presenceTmp(NF)) {
            donnees.suppTmp(NF);
        }
        
        donnees.saveTmp(NF);

    }
}

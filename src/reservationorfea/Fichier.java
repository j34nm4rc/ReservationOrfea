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

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.StringTokenizer;

/**
 *
 * @author Jean-Marc Duval
 * @version 0.1
 * 
 */

public class Fichier {
    private BufferedWriter fW;
    private BufferedReader fR;
    private String urlFichier;
    
    public void setUrlFichier(String tmp) {
        urlFichier = tmp;
    }
    
    public boolean ouvrirEnLecture(String nf) {
        try {
            setUrlFichier(nf);
            File f = new File(urlFichier);
            fR = new BufferedReader(new FileReader(nf));
            fW = null;
            return true;
        }
        catch (IOException e) {
            System.out.println(urlFichier+" : Erreur à l'ouverture en lecture !");
            return false;
        }
    }
    
    public boolean ouvrirEnEcriture(String nf) {
        try {
            setUrlFichier(nf);
            File f = new File(urlFichier);
            fW = new BufferedWriter(new FileWriter(nf));
            fR = null;
            return true;
        }
        catch (IOException e) {
            System.out.println(urlFichier+" : Erreur à l'ouverture en lecture !");
            return false;
        }
    }
    
    public String lireFichier() {
        try{
            String chaine = fR.readLine();
            return chaine;
        }
        catch (IOException err) {
            System.out.println(urlFichier+" : Erreur en lecture");
            return null;
        }
    } 
    
    public boolean ecrireFichier(String tmp) {
        try{
            if (tmp != null) {
                fW.write(tmp, 0, tmp.length());
                fW.newLine();
            }
            return true;
        }
        catch (IOException err) {
            System.out.println(urlFichier+" : Erreur en ecriture");
            return false;
        }
    } 
    
    public boolean presenceFichier(String nomFichier) {
        //To change body of generated methods, choose Tools | Templates.
        File fichier = new File(nomFichier);
        return fichier.exists(); //return true ou false
    }
    
    public void suppFichier(String nomFichier) {
        //To change body of generated methods, choose Tools | Templates.
        File fichier = new File(nomFichier);
        fichier.delete();
    }
    
    public String [] extraireDonnees(String tmp){
        if (tmp != null) {
            StringTokenizer st = new StringTokenizer(tmp,";");
            int i=0;
            String mot[] = new String[st.countTokens()];
            while (st.hasMoreTokens()) {
                mot[i] = st.nextToken();
                i++;
            }
            return mot;
        }
        else return null;
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package atos.magiemagie.main;

import atos.magiemagie.entity.Joueur;
import atos.magiemagie.entity.Partie;
import atos.magiemagie.service.JoueurService;
import atos.magiemagie.service.PartieService;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author theklaude
 */
public class Main {

    private PartieService partieService = new PartieService();
    private JoueurService joueurService = new JoueurService();

    public void menuPrincipal() {
        String choix;
        do {
            Scanner s = new Scanner(System.in);
            System.out.println("*******************************************************");
            System.out.println("******************* Menu Principal ********************");
            System.out.println("*******************************************************");
            System.out.println("********** 1.Créer une partie *************************");
            System.out.println("********** 2.Rejoindre une partie *********************");
            System.out.println("********** 3.Démarrer votre partie ********************");
            System.out.println("********** Q.Quitter **********************************");
            System.out.println("*******************************************************");
            System.out.println("Votre choix > ");

            choix = s.nextLine();

            switch (choix) {
                case "1":
                    System.out.println("Comment voulez-vous nommer votre partie ?");
                    String nomPartie = s.nextLine();
                    partieService.creerNouvellePartie(nomPartie);
                    System.out.println(nomPartie + ": partie crée !");
                    break;
                case "2":
                    List<Partie> partiesNonDemarrees = partieService.listerPartieNonDemarees();
                    System.out.println("Liste des parties non démarrées : ");
                    for (Partie p2 : partiesNonDemarrees) {
                        System.out.println(" - " + p2.getNom() + ": d'ID: " + p2.getId());
                    }
                    
                    System.out.println("Donnez l'ID de la partie que vous voulez rejoindre");
                    long idPartie = new Long(s.nextLine());
                    
                    System.out.println("Entrez votre pseudo: ");
                    String pseudo = s.nextLine();
                    
                    System.out.println("*******************************************************");
                    
                    System.out.println("Entrez votre avatar: ");
                    String avatar = s.nextLine();
                    
                    joueurService.rejoindrePartie(pseudo, avatar, idPartie);

                    break;
                case "3":
                    partiesNonDemarrees = partieService.listerPartieNonDemarees();
                    System.out.println("Liste des parties non démarrées : ");
                    for (Partie p2 : partiesNonDemarrees) {
                        System.out.println(" - " + p2.getNom() + ": Son ID pour la rejoindre est : " + p2.getId());
                    }
                    System.out.println("Donnez l'ID de la partie que vous voulez rejoindre");
                    idPartie = s.nextLong();
                    partieService.demarrerPartie(idPartie);
                    break;
                case "Q":
                    break;
                default:
                    System.out.println("Choix inconnu");
                    break;
            }
        } while (choix.equals("Q") == false);

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Main m = new Main();
        m.menuPrincipal();
    }

}

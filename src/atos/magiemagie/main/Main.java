/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package atos.magiemagie.main;

import atos.magiemagie.dao.CarteDAO;
import atos.magiemagie.dao.JoueurDAO;
import atos.magiemagie.entity.Joueur;
import atos.magiemagie.entity.Partie;
import atos.magiemagie.entity.Partie.Sort;
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
    private JoueurDAO joueurDAO = new JoueurDAO();
    private CarteDAO carteDAO = new CarteDAO();

    public static void main(String[] args) {
        Main m = new Main();
        m.menuPrincipal();
        //m.menuGame();

    }

    public void menuPrincipal() {
        String choix;
        do {
            Scanner s = new Scanner(System.in);
            System.out.println("*******************************************************");
            System.out.println("******************* Menu Principal ********************");
            System.out.println("*******************************************************");
            System.out.println("********** 1.Créer une partie *************************");
            System.out.println("********** 2.Rejoindre une partie *********************");
            System.out.println("********** 3.Quitter **********************************");
            System.out.println("*******************************************************");
            System.out.println("**************************************** Votre choix > ");

            choix = s.nextLine();

            switch (choix) {

                case "1":
                    System.out.println("Comment voulez-vous nommer votre partie ?");
                    String nomPartie = s.nextLine();
                    Partie p = partieService.creerNouvellePartie(nomPartie);
                    System.out.println("*******************************************************");
                    System.out.println(nomPartie + ": partie crée !");
                    System.out.println("*******************************************************");
                    System.out.println("Voulez-vous rejoindre votre partie " + nomPartie + "? (y/n)");
                    String answer = s.nextLine();
                    if (answer.equals("y")) {
                        System.out.println("Entrez votre pseudo: ");
                        String pseudo = s.nextLine();
                        System.out.println("Entrez votre avatar: ");
                        String avatar = s.nextLine();
                        joueurService.rejoindrePartie(pseudo, avatar, p.getId());
                        partieService.demarrerPartie(p.getId());
                    } else {
                        break;
                    }

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
                    System.out.println("Entrez votre avatar: ");
                    String avatar = s.nextLine();
                    joueurService.rejoindrePartie(pseudo, avatar, idPartie);
                    partieService.demarrerPartie(idPartie);
                    break;

                case "3":
                    break;

                default:
                    System.out.println("Choix inconnu");
                    break;
            }
        } while (choix.equals("3") == false);

    }

    public void game(long partieId, long idJoueurLanceur) throws InterruptedException {
        
        while (true) {
            Joueur joueurALaMain = partieService.rechercherJoueurQuiALaMain(partieId);

            if (joueurALaMain.getId() == idJoueurLanceur) { //a moi de jouer
                System.out.println("*******************************************************");
                System.out.println("** Choisissez la première carte que vous voulez jeter**");
                System.out.println("*******************************************************");
                System.out.println("*********** [1] CORNE LICORNE *************************");
                System.out.println("*********** [2] BAVE CRAPAUD **************************");
                System.out.println("*********** [3] MANDRAGORE ****************************");
                System.out.println("*********** [4] AILE CHAUVE SOURIS ********************");
                System.out.println("*********** [5] LAPIS LAZULI **************************");
                System.out.println("*******************************************************");
                System.out.println("************************************* Première carte > ");
                String carte1 = new Scanner(System.in).nextLine();
                
                System.out.println("*******************************************************");
                System.out.println("** Choisissez la deuxième carte que vous voulez jeter**");
                System.out.println("*******************************************************");
                System.out.println("*********** [1] CORNE LICORNE *************************");
                System.out.println("*********** [2] BAVE CRAPAUD **************************");
                System.out.println("*********** [3] MANDRAGORE ****************************");
                System.out.println("*********** [4] AILE CHAUVE SOURIS ********************");
                System.out.println("*********** [5] LAPIS LAZULI **************************");
                System.out.println("*******************************************************");
                System.out.println("************************************* Deuxième carte > ");
                String carte2 = new Scanner(System.in).nextLine();
                
                
                String choix = new Scanner(System.in).nextLine();
                switch (choix) {
                    case "1":
                        long idJoueurVictime = 0;
                        Sort sort = null;
                        System.out.println("*******************************************************");
                        System.out.println("A vous de jouer: [1] Lancer un sort [2] Passer mon tour");
                        System.out.println("*******************************************************");
                        partieService.lanceSort(partieId, idJoueurLanceur, idJoueurVictime, sort);
                        break;

                    case "2":
                        joueurService.passerMain(idJoueurLanceur);
                        break;

                    default:
                        System.out.println("*******************************************************");
                        System.out.print("A vous de jouer: [1] Lancer un sort [2] Passer mon tour");
                        System.out.println("*******************************************************");
                        break;
                }
            } else { // si c pas mon tour
                Thread.sleep(1000);
            }
        }

    }

}

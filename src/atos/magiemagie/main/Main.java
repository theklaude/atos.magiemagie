/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package atos.magiemagie.main;

import atos.magiemagie.dao.CarteDAO;
import atos.magiemagie.dao.JoueurDAO;
import atos.magiemagie.entity.Carte;
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

    public static void main(String[] args) throws InterruptedException {
        Main m = new Main();
        m.menuPrincipal();
       

    }

    public void menuPrincipal() throws InterruptedException {
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
            System.out.println(">>>>> Votre choix: ");

            choix = s.nextLine();

            switch (choix) {

                case "1":
                    System.out.println("> Comment voulez-vous nommer votre partie ?");
                    String nomPartie = s.nextLine();
                    Partie p = partieService.creerNouvellePartie(nomPartie);
                    System.out.println("*******************************************************");
                    System.out.println(nomPartie + ": partie crée !");
                    System.out.println("*******************************************************");
                    System.out.println("> Voulez-vous rejoindre votre partie " + nomPartie + "? (y/n)");
                    String answer = s.nextLine();
                    if (answer.equals("y")) {
                        System.out.println(">>>>> Entrez votre pseudo: ");
                        String pseudo = s.nextLine();
                        System.out.println(">>>>> Entrez votre avatar: ");
                        String avatar = s.nextLine();
                        Joueur nouveauJoueur = joueurService.rejoindrePartie(pseudo, avatar, p.getId());
                        game(p.getId(), nouveauJoueur.getId());
                    } 
                    break;

                case "2":
                    List<Partie> partiesNonDemarrees = partieService.listerPartieNonDemarees();
                    System.out.println("********** Liste des parties non démarrées ***********");
                    for (Partie p2 : partiesNonDemarrees) {
                        System.out.println(" - Partie: " + p2.getNom() + ": d'ID: " + p2.getId());
                    }
                    System.out.println("> Donnez l'ID de la partie que vous voulez rejoindre >");
                    long idPartie = new Long(s.nextLine());
                    System.out.println(">>>>> Entrez votre pseudo: ");
                    String pseudo = s.nextLine();
                    System.out.println(">>>>> Entrez votre avatar: ");
                    String avatar = s.nextLine();
                    Joueur nouveauJoueur = joueurService.rejoindrePartie(pseudo, avatar, idPartie);
                    p = partieService.recherchePartieParId(idPartie);
                    
                    // Demande si tu veux la démarrer
                    System.out.println("> Voulez-vous démarrer la partie " + p.getNom() + "? (y/n)");
                    String answer2 = s.nextLine();
                    if (answer2.equals("y")) {
                        partieService.demarrerPartie(idPartie);
                    } 
                    
                    // Passe ds boucle de jeu
                    game(idPartie, nouveauJoueur.getId());
                    
                    break;

                case "3":
                    break;

                default:
                    System.out.println("!!!!!!!!!!!!!! Choix inconnu !!!!!!!!!!!!!!");
                    break;
            }
        } while (choix.equals("3") == false);

    }

    public void game(long partieId, long idJoueurLanceur) throws InterruptedException {

        while (true) {
            Joueur joueurALaMain = partieService.rechercherJoueurQuiALaMain(partieId);
            Scanner s = new Scanner(System.in);
            if (joueurALaMain!=null && joueurALaMain.getId() == idJoueurLanceur) { //a moi de jouer
                String choix;
                System.out.println("*******************************************************");
                System.out.println("A vous de jouer: [1] Lancer un sort [2] Passer mon tour");
                System.out.println("*******************************************************");
                choix = s.nextLine();
                switch (choix) {
                    case "1":
                        long idJoueurVictime = 0;
                        Sort monSort = null;

                        System.out.println("******************* Vos cartes ************************");
                        for (Carte c : joueurALaMain.getCartes()) {
                            System.out.println("> Carte " + c.getTypeCarte() + " d'ID: " + c.getId());

                            monSort = null;
                            do {
                                System.out.println("*******************************************************");
                                System.out.println("**** Saisissez les ID des cartes qui correspondent ****");
                                System.out.println("*********** au sort que vous voulez lance *************");
                                System.out.println("*******************************************************");
                                System.out.println("> L'ID de la première carte >  ");
                                long idC1 = s.nextLong();
                                System.out.println("> L'ID de la deuxième carte >  ");
                                long idC2 = s.nextLong();

                                monSort = partieService.choixSort(idC1, idC2);

                                System.out.println("!!!!! Saisissez les ID des cartes qui correspondent !!!!!");
                            } while (monSort == null);
                        }

                        partieService.lanceSort(partieId, idJoueurLanceur, idJoueurVictime, monSort);
                        break;

                    case "2":
                        joueurService.passerMain(idJoueurLanceur);
                        break;

                    default:
                        System.out.println("*******************************************************");
                        System.out.println("A vous de jouer: [1] Lancer un sort [2] Passer mon tour");
                        System.out.println("*******************************************************");
                        break;
                }
            } else { // si c pas mon tour
                System.out.println("Vous n'avez pas la main");
                Thread.sleep(1000);
            }
        }

    }

}

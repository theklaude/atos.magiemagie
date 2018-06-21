/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package atos.magiemagie.service;

import atos.magiemagie.dao.JoueurDAO;
import atos.magiemagie.dao.PartieDAO;
import atos.magiemagie.entity.Carte;
import atos.magiemagie.entity.Joueur;
import atos.magiemagie.entity.Partie;
import atos.magiemagie.entity.Partie.Sort;
import java.util.List;

/**
 *
 * @author theklaude
 */
public class PartieService {

    private PartieDAO pdao = new PartieDAO();
    private JoueurDAO jdao = new JoueurDAO();
    private CarteService carteServ = new CarteService();

    public Sort choixSort(Carte.Ingredients ing1, Carte.Ingredients ing2) {
        Sort s = null;
        if (ing1 == Carte.Ingredients.CORNE_LICORNE && ing2 == Carte.Ingredients.BAVE_CRAPAUD) {
            s = Sort.INVISIBILITE;
        }
        if (ing1 == Carte.Ingredients.CORNE_LICORNE && ing2 == Carte.Ingredients.MANDRAGORE) {
            s = Sort.PHILTRE_AMOUR;
        }
        if (ing1 == Carte.Ingredients.BAVE_CRAPAUD && ing2 == Carte.Ingredients.LAPIS_LAZULI) {
            s = Sort.HYPNOSE;
        }
        if (ing1 == Carte.Ingredients.LAPIS_LAZULI && ing2 == Carte.Ingredients.AILE_CHAUVE_SOURIS) {
            s = Sort.DIVINATION;
        }
        if (ing1 == Carte.Ingredients.MANDRAGORE && ing2 == Carte.Ingredients.AILE_CHAUVE_SOURIS) {
            s = Sort.SOMMEIL_PROFOND;
        }
        return s;

    }

    public void lanceSort(long idLanceur, Long idVictime, Sort s) {
        switch (s) {
            case INVISIBILITE: //tu prend une carte(au hasard) chez tous ses adversaires
                
                break;
            case PHILTRE_AMOUR: //le joueur de votre choix vous donne la moitié de ses cartes(au hasard). S’il ne possède qu’une carte il a perdu
                

                break;
            case HYPNOSE: //tu échanges une carte de son choix contre trois cartes(au hasard) de la victime que tu choisis

                break;
            case DIVINATION: //tu peux voir les cartes de tous les autres joueurs

                break;
            case SOMMEIL_PROFOND: //tu choisis une victime qui ne pourra pas lancer de sorts pendant 2 tours

                break;
            default:

                break;

        }
    

    

    

    

    public void passeJoueurSuivant(long idPartie) {

        // Récupère joueur qui a la main => joueurQuiALaMain
        Joueur joueurQuiALaMain = jdao.rechercheJoueurQuiALaMainPourPartieId(idPartie);

        // Détermine si tous autres joueurs ont perdu
        // et passe le joueur à l'état gagné si c'est le cas
        // puis quitte la fonction
        if (pdao.determineSiPlusQueUnJoueurDansLaPartie(idPartie)) {
            joueurQuiALaMain.setEtat(Joueur.EtatJoueur.GAGNE);
            jdao.modifier(joueurQuiALaMain);
            return; //interrompre la boucle
        }
        // La partie n'est pas terminée

        // Récupérer ordre MAX des joueurs de la partie
        long ordreMax = pdao.rechercheOrdreMaxJoueurPourPartieId(idPartie);

        // joueurEvalue = joueurQuiALaMain;
        Joueur joueurEvalue = joueurQuiALaMain;

        while (true) { // Boucle qui permet de déterminer le joueur qui 'attrape' la main
            // Si joueurEvalue est le dernier joueur alors on évaluera le 1er
            if (joueurEvalue.getOrdre() >= ordreMax) {
                joueurEvalue = jdao.rechercheJoueurParPartieIdEtOrdre(idPartie, 1L);
            } else {
                long ordrePlusUn = joueurEvalue.getOrdre() + 1;
                joueurEvalue = jdao.rechercheJoueurParPartieIdEtOrdre(idPartie, ordrePlusUn);

            }
            // return si tout les joueurs nos éléminés étaient en sommeil profond (et qu'on les a a juste réveiller
            if (joueurEvalue.getId() == joueurQuiALaMain.getId()) {
                return;
            }

            // Si joueurEvalue en sommeil profond => son etat passe à PAS LA MAIN
            if (joueurEvalue.getEtatJoueur() == Joueur.EtatJoueur.SOMMEIL_PROFOND) {
                joueurEvalue.setEtat(Joueur.EtatJoueur.PAS_LA_MAIN);
                jdao.modifier(joueurEvalue);
            } else {
                // N'était pas en sommeil profond

                //Si joueurEvalue pas la main? Alors c'est lui qui prend la main
                if (joueurEvalue.getEtatJoueur() == Joueur.EtatJoueur.PAS_LA_MAIN) {

                    joueurQuiALaMain.setEtat(Joueur.EtatJoueur.PAS_LA_MAIN);
                    jdao.modifier(joueurQuiALaMain);

                    joueurEvalue.setEtat(Joueur.EtatJoueur.A_LA_MAIN);
                    jdao.modifier(joueurEvalue);

                    return;
                }

            }

        }

    }

    public void demarrerPartie(Long id) {
        // Recherche la partie
        Partie p = pdao.recherchePartieId(id);

        // Quitter si moins de 2 j
        if (p.getJoueurs().size() < 2) {
            throw new RuntimeException("Il faut plus qu'un joueur !");

        }

        // Distribue 7 cartes
        for (Joueur j : p.getJoueurs()) {
            for (int i = 0; i < 7; ++i) {
                carteServ.distribuerUneCarteAleatoirement(j.getId());
            }
        }

        // Donner la main au joueur d'ordre 1
        for (Joueur j : p.getJoueurs()) {
            if (j.getOrdre() == 1) {
                j.setEtat(Joueur.EtatJoueur.A_LA_MAIN);
                jdao.updateJoueur(j);

            }
        }
    }

    public Partie recherchePartie(String nom) {
        Partie p = new Partie();
        pdao.recherchePartieId(p.getId());
        return p;
    }

    public Partie creerNouvellePartie(String nom) {
        Partie p = new Partie();
        p.setNom(nom);
        pdao.ajouter(p);
        return p;
    }

    /**
     *
     * @return
     */
    public List<Partie> listerPartieNonDemarees() {

        return pdao.listerPartieNonDemarrees();
    }

}

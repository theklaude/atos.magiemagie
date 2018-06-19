/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package atos.magiemagie.service;

import atos.magiemagie.dao.JoueurDAO;
import atos.magiemagie.dao.PartieDAO;
import atos.magiemagie.entity.Joueur;
import atos.magiemagie.entity.Partie;
import java.util.List;

/**
 *
 * @author theklaude
 */
public class PartieService {

    private PartieDAO pdao = new PartieDAO();
    private JoueurDAO jdao = new JoueurDAO();
    private CarteService carteServ = new CarteService();

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
                carteServ.distribuerUneCarteAleatoirement(j.getIdJoueur());
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

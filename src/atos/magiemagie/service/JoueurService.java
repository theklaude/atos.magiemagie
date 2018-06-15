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
public class JoueurService {

    private JoueurDAO joueurDAO = new JoueurDAO();
    private PartieDAO partieDAO = new PartieDAO();

    public void rejoindrePartie(String pseudo, String avatar, long idPartie) {
        //Recherche si joueur existe déjà
        Joueur joueur = joueurDAO.rechercherParPseudo(pseudo);
        if (joueur == null) {
            // Le jour n'existe pas encore
            joueur = new Joueur();
            joueur.setPseudo(pseudo);
            joueur.setNbPartiesJouees(0L);
            joueur.setNbPartiesGagnees(0L);
        }
        joueur.setAvatar(avatar);
        joueur.setEtat(Joueur.EtatJoueur.PAS_LA_MAIN);
        long ordre = joueurDAO.rechercheOrdreNouveauJoueurPourPartie(idPartie);
        joueur.setOrdre(ordre);
        
        //Associe le joueur à la partie et vice-versa (JPA relations bidirectionnelles
        Partie partie = partieDAO.rechercheParId(idPartie);
        joueur.setPartie(partie);
        List<Joueur> listeJoueurs = partie.getJoueurs();
        listeJoueurs.add(joueur);
        
        if (joueur.getId()==null){
            joueurDAO.ajouter(joueur);
        }else{
            joueurDAO.modifier(joueur);
        }
    }

}
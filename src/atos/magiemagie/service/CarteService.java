/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package atos.magiemagie.service;

import atos.magiemagie.dao.CarteDAO;
import atos.magiemagie.dao.JoueurDAO;
import atos.magiemagie.entity.Carte;
import atos.magiemagie.entity.Joueur;
import java.util.Random;

/**
 *
 * @author theklaude
 */
public class CarteService {

    private JoueurDAO dao = new JoueurDAO();
    private CarteDAO carteDao = new CarteDAO();

    public void distribuerUneCarteAleatoirement(long joueurId) {
        Joueur j = dao.rechercherParId(joueurId);
        Carte c = new Carte();
        int pick = new Random().nextInt(c.typeCarte.values().length);
        c.setTypeCarte(Carte.Ingredients.values()[pick]);
        j.getCartes().add(c);
        c.setJoueur(j);
        carteDao.ajouterCarte(c);

    }

}

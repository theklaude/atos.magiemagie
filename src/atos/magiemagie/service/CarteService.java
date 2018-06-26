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
import atos.magiemagie.entity.Partie;
import java.util.List;
import java.util.Random;

/**
 *
 * @author theklaude
 */
public class CarteService {

    private static CarteService myself = new CarteService();

    public static CarteService instantiate() {
        return myself;
    }

  

    private JoueurDAO dao = new JoueurDAO();
    private CarteDAO carteDao = new CarteDAO();
    private PartieService partieServ = new PartieService();

    public void distribuerUneCarteAleatoirement(long joueurId) {
        Joueur j = dao.rechercherParId(joueurId);
        Carte c = new Carte();
        int pick = new Random().nextInt(c.typeCarte.values().length);
        c.setTypeCarte(Carte.Ingredients.values()[pick]);
        j.getCartes().add(c);
        c.setJoueur(j);
        carteDao.ajouterCarte(c);

    }

    public void prendreUneCarteAleatoirement(long idLanceur, long idVictime) {
        Joueur lanceur = dao.rechercherParId(idLanceur);
        Joueur victime = dao.rechercherParId(idVictime);

        ajouterUneCarte(RecupCarteAleatoireChezJoueur(victime), lanceur);

    }

    //le joueur de votre choix vous donne la moitié de ses cartes(au hasard). 
    //S’il ne possède qu’une carte il a perdu
    public void sortPhiltreAmour(long idLanceur, long idVictime) {
        Joueur lanceur = dao.rechercherParId(idLanceur);
        Joueur victime = dao.rechercherParId(idVictime);

        int nbreCarte = (victime.getCartes().size()) / 2;

        for (int i = 1; i <= nbreCarte; i++) {
            ajouterUneCarte(RecupCarteAleatoireChezJoueur(victime), lanceur);
        }

    }

    //tu échanges une carte de son choix contre trois cartes(au hasard) de la victime que tu choisis
    public void sortHypnose(long idLanceur, Carte c, Long idVictime) {
        Joueur lanceur = dao.rechercherParId(idLanceur);
        Joueur victime = dao.rechercherParId(idVictime);
        for (int i = 1; i <= 3; i++) {
            ajouterUneCarte(RecupCarteAleatoireChezJoueur(victime), lanceur);
        }
        ajouterUneCarte(c, victime);
    }

    public void sortDivination(long idPartie, Long idLanceur) {
        Joueur lanceur = dao.rechercherParId(idLanceur);
        String s = String.valueOf(idPartie);
        Partie p = partieServ.recherchePartie(s);

        List<Joueur> jList = p.getJoueurs();
        jList.remove(lanceur);
        for (Joueur j : jList) {
            listerCarte(j.getId());
        }

    }

    public List listerCarte(long idJoueur) {
        Joueur j = dao.rechercherParId(idJoueur);
        return j.getCartes();
    }

    private Carte RecupCarteAleatoireChezJoueur(Joueur joueur) {
        Random randomizer = new Random();
        List<Carte> cartesVictime = joueur.getCartes();
        return cartesVictime.get(randomizer.nextInt(cartesVictime.size()));
    }

    private void ajouterUneCarte(Carte c, Joueur j) {
        j.getCartes().add(c);
        c.setJoueur(j);
        carteDao.updateCarte(c);
    }
}

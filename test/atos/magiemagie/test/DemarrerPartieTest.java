/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package atos.magiemagie.test;

import atos.magiemagie.entity.Partie;
import atos.magiemagie.entity.Joueur;
import atos.magiemagie.service.CarteService;
import atos.magiemagie.service.JoueurService;
import atos.magiemagie.service.PartieService;
import org.junit.Test;

/**
 *
 * @author Administrateur
 */
public class DemarrerPartieTest {

    private PartieService service = new PartieService();
    private CarteService carteServ = new CarteService();
    private JoueurService joueurservice = new JoueurService();

    @Test
    public void demarrerPartieOK() {
//        Partie p = service.creerNouvellePartie("demarrerPartieOK");
//        Joueur j1 = joueurservice.rejoindrePartie("Joueur 1", "AAA", p.getId());
//        Joueur j2 = joueurservice.rejoindrePartie("Joueur 2", "BBB", p.getId());
//        Joueur j3 = joueurservice.rejoindrePartie("Joueur 3", "CCC", p.getId());
//        carteServ.distribuerUneCarteAleatoirement(j1.getId());
//        carteServ.distribuerUneCarteAleatoirement(j2.getId());
//        carteServ.distribuerUneCarteAleatoirement(j3.getId());
        service.demarrerPartie(1L);
    }
}

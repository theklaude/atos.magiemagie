/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package atos.magiemagie.test;

import atos.magiemagie.entity.Joueur;
import atos.magiemagie.entity.Partie;
import atos.magiemagie.service.JoueurService;
import atos.magiemagie.service.PartieService;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author theklaude
 */
public class JoueurServiceTest {

    private JoueurService joueurservice = new JoueurService();
    private PartieService partieService = new PartieService();

    @Test
    public void ordreJoueursOK() {
        Partie nouvellePartie = partieService.creerNouvellePartie("ordreJoueursOK");
        joueurservice.rejoindrePartie("A", "A", nouvellePartie.getId());
        joueurservice.rejoindrePartie("B", "B", nouvellePartie.getId());
        Joueur j = joueurservice.rejoindrePartie("c", "C", nouvellePartie.getId());
        Assert.assertEquals(3L, (long) j.getOrdre());

    }

    @Test
    public void rejoindrePartieOK() {
        Partie p = partieService.creerNouvellePartie("partie1");
        joueurservice.rejoindrePartie("Tim", "Aigle", p.getId());

    }
    
    

}

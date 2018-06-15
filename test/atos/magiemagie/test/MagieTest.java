/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package atos.magiemagie.test;

import atos.magiemagie.entity.Partie;
import atos.magiemagie.service.PartieService;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;

/**
 *
 * @author Administrateur
 */
public class MagieTest {

    private PartieService service = new PartieService();

    //@Test
    public void creerNouvellePartieOK() {
        Partie p;
        p = service.creerNouvellePartie("Blabla");
        assertNotNull(p.getId());

    }

}

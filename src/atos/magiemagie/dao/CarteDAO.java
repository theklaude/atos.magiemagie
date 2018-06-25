/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package atos.magiemagie.dao;

import atos.magiemagie.entity.Carte;
import atos.magiemagie.entity.Joueur;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;

/**
 *
 * @author theklaude
 */
public class CarteDAO {
    public void ajouterCarte(Carte c) {
        EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();
        em.getTransaction().begin();
        em.persist(c);
        em.getTransaction().commit();
    }
    
    public void updateCarte(Carte c) {
        EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();
        em.getTransaction().begin();
        em.merge(c);
        em.getTransaction().commit();
    }

    public Carte rechercherCarteparId(long idCarte) {
         EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();
         return em.find(Carte.class, idCarte);
        
    }
    
}

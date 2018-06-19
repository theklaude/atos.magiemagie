/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package atos.magiemagie.dao;

import atos.magiemagie.entity.Joueur;
import atos.magiemagie.entity.Partie;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 *
 * @author theklaude
 */
public class PartieDAO {

    public void updatePartie(Partie p) {
        EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();
        em.getTransaction().begin();
        em.merge(p);
        em.getTransaction().commit();
    }

    public void ajouter(Partie p) {
        EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();
        em.getTransaction().begin();
        em.persist(p);
        em.getTransaction().commit();
    }
    
    

    public List<Partie> listerPartieNonDemarrees() {

        EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();

        Query query = (Query) em.createQuery(""
                + "SELECT p "
                + "FROM Parie p"
                + "EXCEPT"
                + "SELECT p "
                + "FROM Parie p"
                + "     JOIN p.joueurs j "
                + "WHERE j.etat IN (:etat_gagne,:etat_alamain)");

        query.setParameter("etat_gagne", Joueur.EtatJoueur.GAGNE);
        query.setParameter("etat_alamain", Joueur.EtatJoueur.A_LA_MAIN);

        return query.getResultList();
    }

    public Partie recherchePartieId(long idPartie) {
        EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();
        return em.find(Partie.class, idPartie);
    }

}

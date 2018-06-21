/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package atos.magiemagie.dao;

import atos.magiemagie.entity.Joueur;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 *
 * @author theklaude
 */
public class JoueurDAO {

    public Joueur rechercheJoueurParPartieIdEtOrdre(long idPartie, long ordre) {
       EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();
        Query q = em.createQuery("SELECT j FROM Joueur j JOIN j.partie p WHERE p.id=:partieId AND j.ordre=:ordre");
        q.setParameter("partieId",idPartie);
        q.setParameter("ordre", ordre);
        return (Joueur) q.getSingleResult(); 
        
     

    }
    
    
    public Joueur rechercheJoueurQuiALaMainPourPartieId(long partieId) {
        EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();
        Query query = em.createQuery("SELECT j FROM Joueur j JOIN j.partie p WHERE j.etatJoueur=:etat AND p.id:=idPart");
        query.setParameter("etat", Joueur.EtatJoueur.A_LA_MAIN);
        query.setParameter("idPart", partieId);
        Joueur j = (Joueur) query.getSingleResult();
        return j;
    }

    public long nbreJoueur(long partieId) {
        EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();

        Query query = em.createQuery(""
                + "SELECT MAX(j.ordre)+1 "
                + "FROM Joueur j "
                + "JOIN j.partie p "
                + "WHERE p.id=:idPartie ");
        query.setParameter("idPartie", partieId);

        Object nbre = query.getSingleResult();

        return (Integer) nbre;
    }

    public long rechercheOrdreNouveauJoueurPourPartie(long partieId) {
        EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();

        Query query = em.createQuery(""
                + "SELECT MAX(j.ordre)+1 "
                + "FROM Joueur j "
                + "JOIN j.partie p "
                + "WHERE p.id=:idPartie ");
        query.setParameter("idPartie", partieId);

        Object res = query.getSingleResult();
        if (res == null) {
            return 1;
        }
        return (long) res;
    }

    /**
     * Renvoie le joueur dont le pseudo existe en BD, ou renvoie null si pas
     * trouvé.
     *
     * @param pseudo
     * @return
     */
    public Joueur rechercherParPseudo(String pseudo) {
        EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();

        Query query = em.createQuery(""
                + "SELECT j "
                + "FROM Joueur j "
                + "WHERE j.pseudo=:lePseudo ");
        query.setParameter("lePseudo", pseudo);

        List<Joueur> joueursTrouves = query.getResultList();

        if (joueursTrouves.isEmpty()) {
            return null;
        }

        return joueursTrouves.get(0);
    }

    public void ajouter(Joueur j) {
        EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();
        em.getTransaction().begin();
        em.persist(j);
        em.getTransaction().commit();
    }

    public void modifier(Joueur j) {
        EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();
        em.getTransaction().begin();
        em.merge(j);
        em.getTransaction().commit();
    }

    public void updateJoueur(Joueur j) {
        EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();
        em.getTransaction().begin();
        em.merge(j);
        em.getTransaction().commit();
    }

    public Joueur rechercherParId(long joueurId) {
        EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();
        return em.find(Joueur.class, joueurId);
    }

    

}

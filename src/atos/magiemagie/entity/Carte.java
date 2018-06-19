/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package atos.magiemagie.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 *
 * @author Administrateur
 */
@Entity
public class Carte implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public enum Ingredients {
        BAVE_CRAPAUD,
        CORNE_LICORNE,
        MANDRAGORE,
        LAPIS_LAZULI,
        AILE_CHAUVE_SOURIS
    };

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    public Ingredients typeCarte;

    @ManyToOne
    private Joueur joueur;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    //Getter Setter
    public Joueur getJoueur() {
        return joueur;
    }

    public void setJoueur(Joueur joueur) {
        this.joueur = joueur;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Carte)) {
            return false;
        }
        Carte other = (Carte) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    public Ingredients getTypeCarte() {
        return typeCarte;
    }

    public void setTypeCarte(Ingredients typeCarte) {
        this.typeCarte = typeCarte;
    }

    @Override
    public String toString() {
        return "atos.magiemagie.entity.Carte[ id=" + id + " ]";
    }

}

package model;

import java.io.Serializable;
import jakarta.persistence.*;


/**
 * The persistent class for the panier database table.
 * 
 */
@Entity
@NamedQuery(name="Panier.findAll", query="SELECT p FROM Panier p")
public class Panier implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int idpa;

	private float quantite;

	//bi-directional many-to-one association to Produit
	@ManyToOne
	@JoinColumn(name="idp")
	private Produit produit;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="idu")
	private User user;

	public Panier() {
	}

	public int getIdpa() {
		return this.idpa;
	}

	public void setIdpa(int idpa) {
		this.idpa = idpa;
	}

	public float getQuantite() {
		return this.quantite;
	}

	public void setQuantite(float quantite) {
		this.quantite = quantite;
	}

	public Produit getProduit() {
		return this.produit;
	}

	public void setProduit(Produit produit) {
		this.produit = produit;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
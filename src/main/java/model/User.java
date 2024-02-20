package model;

import java.io.Serializable;
import jakarta.persistence.*;
import java.util.List;

import org.eclipse.persistence.annotations.CascadeOnDelete;


/**
 * The persistent class for the user database table.
 * 
 */
@Entity
@NamedQuery(name="User.findAll", query="SELECT u FROM User u")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	private String email;

	private int phnbr;

	private String privlege;

	private String pwd;

	private String username;

	@OneToMany(mappedBy="user")
	@CascadeOnDelete
	private List<Panier> paniers;

	public User() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getPhnbr() {
		return this.phnbr;
	}

	public void setPhnbr(int phnbr) {
		this.phnbr = phnbr;
	}

	public String getPrivlege() {
		return this.privlege;
	}

	public void setPrivlege(String privlege) {
		this.privlege = privlege;
	}

	public String getPwd() {
		return this.pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<Panier> getPaniers() {
		return this.paniers;
	}

	public void setPaniers(List<Panier> paniers) {
		this.paniers = paniers;
	}

	public Panier addPanier(Panier panier) {
		getPaniers().add(panier);
		panier.setUser(this);

		return panier;
	}

	public Panier removePanier(Panier panier) {
		getPaniers().remove(panier);
		panier.setUser(null);

		return panier;
	}

}
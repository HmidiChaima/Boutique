package dao;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import model.Categorie;
import model.Panier;
import model.Produit;
import model.User;

public class GestionBoutiqueImp implements IGestionBoutique {
	private EntityManager em;
	
	
	public GestionBoutiqueImp() {
		EntityManagerFactory fac = Persistence.createEntityManagerFactory("boutique");
		em = fac.createEntityManager();
	}
	
	public List<Produit> getAllProduits() {
		return (List<Produit>)em.createQuery("SELECT e FROM Produit e",Produit.class).getResultList();
	}
	
	public List<Produit> getProduitByCategorie(int idc) {
		Categorie cat = em.find(Categorie.class, idc);
		return cat.getProduits();
	}
	
	public List<Categorie> getAllCategorie() {
		return (List<Categorie>)em.createQuery("SELECT e FROM Categorie e",Categorie.class).getResultList();
	}
	
	public List<Categorie> getAllMasterCategorie() {
		return (List<Categorie>)em.createQuery("SELECT e FROM Categorie e WHERE e.idf IS NULL",Categorie.class).getResultList();
	}

	public List<Panier> getPanierByUser(int idu) {
		return (List<Panier>)em.createQuery("SELECT e FROM Panier e WHERE e.user.id=:uid",Panier.class).setParameter("uid", idu).getResultList();
	}

	public User checkUser(String email, String pwd) {
		try {
			return (User)em.createQuery("SELECT e FROM User e WHERE e.email=:email AND e.pwd=:pwd")
			.setParameter("email", email)
			.setParameter("pwd", pwd)
			.getSingleResult();
		}catch (Exception e) {
			return null;
		}
	}

	public float calculateTotal(int idu) {
		List<Panier> p = em.createQuery("SELECT e FROM Panier e WHERE e.user.id=:id").setParameter("id", idu).getResultList();
		float sum = 0;
		for(Panier commande : p) {
			sum+= commande.getQuantite() * commande.getProduit().getPrix();
		}
		return sum;
	}

	public void deleteFromPanier(int idpa) {
		em.getTransaction().begin();
		em.remove(em.find(Panier.class, idpa));
		em.getTransaction().commit();
	}

	public void addToPanier(int idu, Produit p, int quantity) {
		Panier pa = new Panier();
		pa.setUser(em.find(User.class, idu));
		pa.setProduit(p);
		pa.setQuantite(quantity);
		em.getTransaction().begin();
		em.persist(pa);
		em.getTransaction().commit();
	}
	//why is this a thing ? !!UPDATE!! should return a boolean instead of user info (probably safer)
	public boolean getUserByEmail(String email) {
		return em.createQuery("SELECT e FROM User e WHERE email=:email")
				.setParameter("email", email)
				.getMaxResults() >= 1;
	}

	public User newUser(String email, String pwd) {
		if(this.getUserByEmail(email)) return null;
		User u = new User();
		u.setEmail(email);
		u.setPwd(pwd);
		em.getTransaction().begin();
		em.persist(u);
		em.getTransaction().commit();
		return u;
	}

	public void removeUser(User u) {
		em.getTransaction().begin();
		em.remove(u);
		em.getTransaction().commit();
	}

	public User updateUser(User u) {
		em.getTransaction().begin();
		em.merge(u);
		em.getTransaction().commit();
		return u;
	}

	public void addCategorie(Categorie c) {
		em.getTransaction().begin();
		em.persist(c);
		em.getTransaction().commit();
	}

	public void updateCategorie(Categorie c) {
		Categorie ct = em.find(Categorie.class, c.getIdc());
		ct.setNom(c.getNom());
		if(c.getIdf() != null) {
			ct.setIdf(c.getIdf());
		}else {
			ct.setIdf(null);
		}
		em.getTransaction().begin();
		em.merge(ct);
		em.getTransaction().commit();
	}

	public void deleteCategorie(int idc) {
		em.getTransaction().begin();
		em.remove(em.find(Categorie.class, idc));
		em.getTransaction().commit();
	}

	public Categorie getCategorieById(int idc) {
		return em.find(Categorie.class, idc);
	}
	public List<Categorie> getCategorieChildrenById(int idf){
		return (List<Categorie>)em.createQuery("SELECT e FROM Categorie e WHERE e.idf=:idf").setParameter("idf", idf).getResultList();
	}
	public Produit getProduitById(int id) {
		return em.find(Produit.class, id);
	}
	public void addProduit(Produit p) {
		em.getTransaction().begin();
		em.persist(p);
		em.getTransaction().commit();
	}

	public void updateProduit(Produit p) {
		em.getTransaction().begin();
		em.merge(p);
		em.getTransaction().commit();
	}

	public void deleteProduit(int idp) {
		Produit p = em.find(Produit.class, idp);
		em.getTransaction().begin();
		em.remove(p);
		em.getTransaction().commit();
	}

	
}

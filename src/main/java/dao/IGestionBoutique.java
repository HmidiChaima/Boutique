package dao;

import java.util.List;
import model.*;

public interface IGestionBoutique {
	public List<Produit> getAllProduits();
	public List<Categorie> getAllCategorie();
	public List<Categorie> getAllMasterCategorie();
	public List<Produit> getProduitByCategorie(int idc);
	public List<Panier> getPanierByUser(int idu);
	public User checkUser(String email, String pwd);
	public float calculateTotal(int idu);
	public void deleteFromPanier(int idpa);
	public void addToPanier(int idu, Produit p, int quantity);
	public boolean getUserByEmail(String email);
	public User newUser(String email, String pwd);
	public void removeUser(User u);
	public User updateUser(User u);
	public void addCategorie(Categorie c);
	public void updateCategorie(Categorie c);
	public void deleteCategorie(int idc);
	public Categorie getCategorieById(int idc);
	public List<Categorie> getCategorieChildrenById(int idf);
	public void addProduit(Produit p);
	public void updateProduit(Produit p);
	public void deleteProduit(int idp);
}

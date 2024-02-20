package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import jakarta.persistence.*;
import jakarta.persistence.Persistence;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Panier;
import model.Produit;
import model.User;

/**
 * Servlet implementation class Index
 */
@WebServlet("/Index")
public class Index extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public Index() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		EntityManagerFactory fac = Persistence.createEntityManagerFactory("boutique");
		EntityManager em = fac.createEntityManager();
		User u = new User();
		u.setUsername("tahan");
		u.setPwd("123");
		u.setId(4);
		Produit p = new Produit();
		p.setIdp(5);
		p.setNom("SABAT");
		Panier pa = new Panier();
		em.getTransaction().begin();
		em.persist(p);
		em.getTransaction().commit();
		em.getTransaction().begin();
		em.persist(u);
		em.getTransaction().commit();
		em.getTransaction().begin();
		em.persist(pa);
		em.getTransaction().commit();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

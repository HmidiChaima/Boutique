package controller;

import java.io.IOException;

import dao.GestionBoutiqueImp;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Produit;

/**
 * Servlet implementation class AjouterPanier
 */
@WebServlet("/AjouterPanier")
public class AjouterPanier extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public AjouterPanier() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		GestionBoutiqueImp db = new GestionBoutiqueImp();
		HttpSession sess = req.getSession(false);
		if(sess == null) res.sendRedirect("login.jsp");
		else {
			int uid = (int)sess.getAttribute("uid");
			int qte = Integer.parseInt(req.getParameter("qte"));
			Produit p = db.getProduitById(Integer.parseInt(req.getParameter("id")));
			db.addToPanier(uid, p, qte);
			sess.setAttribute("panier", db.getPanierByUser(uid));
			res.sendRedirect("index.jsp");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

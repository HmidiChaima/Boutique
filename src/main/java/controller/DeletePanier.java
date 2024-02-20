package controller;

import java.io.IOException;

import dao.GestionBoutiqueImp;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet implementation class DeletePanier
 */
@WebServlet("/DeletePanier")
public class DeletePanier extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public DeletePanier() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		GestionBoutiqueImp db = new GestionBoutiqueImp();
		int idpa = Integer.parseInt(req.getParameter("id"));
		db.deleteFromPanier(idpa);
		HttpSession sess = req.getSession(false);
		sess.setAttribute("total", db.calculateTotal((int)sess.getAttribute("uid")));
		res.sendRedirect("index.jsp?show_panier=true");
		
	}
}

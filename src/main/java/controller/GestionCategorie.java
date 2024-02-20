package controller;

import java.io.IOException;

import org.apache.tomcat.util.net.DispatchType;

import dao.GestionBoutiqueImp;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Categorie;

/**
 * Servlet implementation class GestionCategorie
 */
@WebServlet("/GestionCategorie")
public class GestionCategorie extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public GestionCategorie() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String dispatchType = req.getParameter("dipatchType");
		GestionBoutiqueImp db = new GestionBoutiqueImp();
		if(dispatchType.equals("modify")) {
			int id = Integer.parseInt(req.getParameter("id_cat"));
			Categorie c = db.getCategorieById(id);
			if(Integer.parseInt(req.getParameter("idf")) != 0) {
				c.setIdf(Integer.parseInt(req.getParameter("idf")));
			}else {
				c.setIdf(null);
			}
			c.setNom(req.getParameter("cat_name"));
			db.updateCategorie(c);
		}else {
			Categorie c = new Categorie();
			if(Integer.parseInt(req.getParameter("idf")) != 0) {
				c.setIdf(Integer.parseInt(req.getParameter("idf")));
			}else {
				c.setIdf(null);
			}
			c.setNom(req.getParameter("cat_name"));
			db.addCategorie(c);
		}
		res.sendRedirect("admin.jsp?menu=1");
	}

}

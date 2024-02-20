package controller;

import java.io.IOException;

import dao.GestionBoutiqueImp;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.User;

/**
 * Servlet implementation class Authenticate
 */
@WebServlet("/Authenticate")
public class Authenticate extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public Authenticate() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setAttribute("logout", true);
		req.getRequestDispatcher("Logout").forward(req,res);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		GestionBoutiqueImp db = new GestionBoutiqueImp();
		User u = db.checkUser(req.getParameter("email"), req.getParameter("pwd"));
		if(u != null) {
			HttpSession session = req.getSession();
			session.setAttribute("username", u.getUsername());
			session.setAttribute("phone", u.getPhnbr());
			session.setAttribute("privilege", u.getPrivlege());
			session.setAttribute("panier", u.getPaniers());
			session.setAttribute("uid", u.getId());
			session.setAttribute("total", db.calculateTotal(u.getId()));
			if(req.getParameter("previous") != "") {
				res.sendRedirect((String)req.getParameter("previous"));
			}else {
				res.sendRedirect("index.jsp");
			}
		}else {
			res.sendRedirect("login.jsp?error=1");
		}
	}

}

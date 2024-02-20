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
 * Servlet implementation class CalculeTotal
 */
@WebServlet("/CalculeTotal")
public class CalculeTotal extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public CalculeTotal() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession sess = request.getSession(false);
		GestionBoutiqueImp db = new GestionBoutiqueImp();
		if(sess != null) {
			response.setContentType("text/plain");
		    response.setCharacterEncoding("UTF-8"); 
		    response.getWriter().write(String.format("%.2f", db.calculateTotal((int)sess.getAttribute("uid"))));
		    System.out.println( db.calculateTotal((int)sess.getAttribute("uid")));
		}else {
			response.setContentType("text/plain");  // Set content type of the response so that jQuery knows what it can expect.
		    response.setCharacterEncoding("UTF-8"); // You want world domination, huh?
		    response.getWriter().write("0.0 DT");
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

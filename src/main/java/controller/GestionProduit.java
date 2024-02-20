package controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

import dao.GestionBoutiqueImp;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import model.Produit;

/**
 * Servlet implementation class GestionProduit
 */
@MultipartConfig
@WebServlet("/GestionProduit")
public class GestionProduit extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public GestionProduit() {
        // TODO Auto-generated constructor stub
    }
    /*private String getCurrentDirectory() {
        return this.getClass().getClassLoader().getResource("").getPath();
    } useless*/
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
		String dispatchType = req.getParameter("dipatchType");
		GestionBoutiqueImp db = new GestionBoutiqueImp();
		if(dispatchType.equals("modify")) {
			String timestamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
			Produit p = db.getProduitById(Integer.parseInt(req.getParameter("id_prod")));
			if(req.getPart("img_prod").getSize() != 0) {
				Part filePart = req.getPart("img_prod");
				InputStream fileInput = filePart.getInputStream();
				timestamp = timestamp.replace(".", "");
				File img = new File("C:\\Users\\Flowky\\jee-workspace\\boutique\\src\\main\\webapp\\pimg\\" + timestamp + ".jpg");
				Files.copy(fileInput, img.toPath());
				String imgName = timestamp + ".jpg";
				p.setImg(imgName);
			}
			String nom = req.getParameter("name_prod");
			String description = req.getParameter("disc_prod");
			float prix;
			int qte;
			try {
				qte = Integer.parseInt(req.getParameter("qte_prod"));
				
			} catch (Exception e) {
				qte = 0;
			}
			try {
				prix = Float.parseFloat(req.getParameter("price_prod"));
				
			} catch (Exception e) {
				prix = 0;
			}
			String idc = req.getParameter("idc");
			p.setNom(nom);
			if(idc == null) p.setCategorie(null);
			p.setCategorie(db.getCategorieById(Integer.parseInt(idc)));
			p.setDescription(description);
			p.setPrix(prix);
			p.setQuantite(qte);
			db.updateProduit(p);
			res.sendRedirect("admin.jsp?menu=2");
		}else {
			Part filePart = req.getPart("img_prod");
			InputStream fileInput = filePart.getInputStream();
			String timestamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
			timestamp = timestamp.replace(".", "");
			File img = new File("C:\\Users\\Flowky\\jee-workspace\\boutique\\src\\main\\webapp\\pimg\\" + timestamp + ".jpg");
			Files.copy(fileInput, img.toPath());
			Produit p = new Produit();
			String nom = req.getParameter("name_prod");
			String description = req.getParameter("disc_prod");
			String imgName = timestamp + ".jpg";
			float prix;
			int qte;
				
			
			try {
				qte = Integer.parseInt(req.getParameter("qte_prod"));
				
			} catch (Exception e) {
				qte = 0;
			}
			try {
				prix = Float.parseFloat(req.getParameter("price_prod"));
				
			} catch (Exception e) {
				prix = 0;
			}
			p.setNom(nom);
			String idc = req.getParameter("idc");
			if(idc == null) p.setCategorie(null);
			p.setCategorie(db.getCategorieById(Integer.parseInt(idc)));
			p.setDescription(description);
			p.setImg(imgName);
			p.setPrix(prix);
			p.setQuantite(qte);
			db.addProduit(p);
			res.sendRedirect("admin.jsp?menu=2");
		}
	}

}

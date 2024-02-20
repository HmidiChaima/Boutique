<%@page import="dao.GestionBoutiqueImp, java.nio.file.*, model.*, java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%
	String managmentType = "";
	String itemId = "";
	if(session == null){
		request.setAttribute("previousHop", "admin.jsp");
		request.getRequestDispatcher("login.jsp").forward(request, response);
	}else{
		try{
			if(!session.getAttribute("privilege").toString().equals("admin")){
				response.sendRedirect("index.jsp");
			}
		}catch(Exception e){
			request.setAttribute("previousHop", "admin.jsp");
			request.getRequestDispatcher("login.jsp").forward(request, response);
		}
	}
	int activeMenu = 0;
	if(request.getParameter("menu") != null){
		switch(Integer.parseInt(request.getParameter("menu"))){
			case 1:
				activeMenu = 1;
				break;
			case 2:
				activeMenu = 2;
				break;
			default:
				response.sendRedirect("admin.jsp");
				break;
		}
		if(request.getParameter("type") != null){
			managmentType = request.getParameter("type");
			itemId = request.getParameter("id");
		}
	}
	String welcomePage = new String(Files.readAllBytes(Paths.get("C:/Users/Flowky/jee-workspace/boutique/src/main/webapp/components/welcome_admin")));
	String manageCategories = new String(Files.readAllBytes(Paths.get("C:/Users/Flowky/jee-workspace/boutique/src/main/webapp/components/display_category_admin")));
	String manageProducts = new String(Files.readAllBytes(Paths.get("C:/Users/Flowky/jee-workspace/boutique/src/main/webapp/components/display_product_admin")));	
	String categoryRow = new String(Files.readAllBytes(Paths.get("C:/Users/Flowky/jee-workspace/boutique/src/main/webapp/components/admin_category_table_row")));
	String categoryForm = new String(Files.readAllBytes(Paths.get("C:/Users/Flowky/jee-workspace/boutique/src/main/webapp/components/add_category_admin")));
	String productRow = new String(Files.readAllBytes(Paths.get("C:/Users/Flowky/jee-workspace/boutique/src/main/webapp/components/admin_product_table_row")));
	String productForm = new String(Files.readAllBytes(Paths.get("C:/Users/Flowky/jee-workspace/boutique/src/main/webapp/components/add_product_admin")));
	GestionBoutiqueImp db = new GestionBoutiqueImp();
	List<Categorie> lsc = db.getAllCategorie();
	List<Produit> lsp = db.getAllProduits();
%>
<%!
	private String getMyCategories(GestionBoutiqueImp db, String categoryTemplate,String rowTemplate, List<Categorie> lsc){
			String temp = "";
			String preparedTable = categoryTemplate;
			for(Categorie c: lsc){
				if(c.getIdf() == null){
					temp+= String.format(rowTemplate, c.getIdc(), c.getNom(), "MASTER", c.getIdc(), c.getIdc());
				}else{
					temp+= String.format(rowTemplate, c.getIdc(), c.getNom(), db.getCategorieById(c.getIdf()).getNom(), c.getIdc(), c.getIdc());
				}
			}
			return String.format(preparedTable, temp);
		}
	private String prepareMyCategoryForm(GestionBoutiqueImp db, String categoryFormTemplate, String dispatchType, List<Categorie> lsc, String id){
		//this function is meant to prepare options for form and whether it should update or add
		String temp = "";
		String formTemplate = categoryFormTemplate;
		for(Categorie c: lsc){
			if(id != null && c.getIdc() == Integer.parseInt(id)) continue;
			temp += String.format("<option value='%d'>%s</option>", c.getIdc(), c.getNom());
		}
		return dispatchType.equals("modify") 
				? String.format(formTemplate, "Modifier  une catégorie", id, temp, dispatchType) 
				: String.format(formTemplate, "Ajouter une catégorie", "AUTO Généré", temp, dispatchType);
	}
	
	private String getMyProducts(GestionBoutiqueImp db, String productTemplate,String rowTemplate, List<Produit> lsp){
		String temp = "";
		String preparedTable = productTemplate;
		for(Produit c: lsp){
			if(c.getCategorie() == null){
				temp+= String.format(rowTemplate, c.getIdp(), c.getNom(), "Aucune Catégorie"
						, c.getQuantite(), c.getPrix(),c.getImg(), c.getIdp(), c.getIdp());
			}else{
				temp+= String.format(rowTemplate, c.getIdp(), c.getNom(), c.getCategorie().getNom()
						, c.getQuantite(), c.getPrix(),c.getImg(), c.getIdp(), c.getIdp());
			}
		}
		return String.format(preparedTable, temp);
	}
	private String prepareMyProductForm(GestionBoutiqueImp db, String productFormTemplate, String dispatchType, List<Categorie> lsc, String id){
		//this function is meant to prepare options for form and whether it should update or add
		String temp = "";
		String formTemplate = productFormTemplate;
		Produit p = null;
		Integer idc = 0;
		if(id != null){
			p = db.getProduitById(Integer.parseInt(id));
			if(p.getCategorie() != null) idc = p.getCategorie().getIdc();
		}
		for(Categorie c: lsc){
			if(idc == c.getIdc()){
				temp += String.format("<option selected value='%d'>%s</option>", c.getIdc(), c.getNom());
				continue;
			}
			temp += String.format("<option value='%d'>%s</option>", c.getIdc(), c.getNom());
		}
		//on modify need to fill in the form ...
		return dispatchType.equals("modify") 
				? String.format(formTemplate, "Modifier un Produit", id,p.getNom(),
						p.getPrix(), p.getQuantite(), p.getDescription(),temp, dispatchType) 
				: String.format(formTemplate, "Ajouter un Produit", "AUTO Généré", "", "", "", "", temp, dispatchType);
	}
%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<link href="./css/bootstrap.min.css"  rel="stylesheet"/>
	<link rel="stylesheet" href="assets/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="assets/fonts/font-awesome.min.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Montserrat">
    <link rel="stylesheet" href="assets/css/Simple-Vertical-Navigation-Menu-v-10.css">
    <link rel="stylesheet" href="./assets/css/styles.css">
	<title>Interface admin</title>
</head>
<body>
	<div>
        <div class="container-fluid" style="padding: 0px;">
            <div class="row no-gutters" style="width: 100%;height: 100vh;">
                <div class="col-md-2">
                    <div class="vertical-nav" style="width: 179px;">
                        <h4 class="text-center"> </h4>
                        <h5 class="text-left text-white-50 d-lg-flex justify-content-lg-center align-items-lg-center">Dashboard</h5>
                        <ul class="vertical-nav-list">
                            <li class="vnav-li"><a href="index.jsp" class="vnav-link"><i class="fa fa-home"></i>&nbsp; Vers la boutique</a></li>
                            <li class="vnav-li"><a href="admin.jsp?menu=1" class="vnav-link"><i class="fa fa-database"></i>&nbsp; Categories</a></li>
                            <li class="vnav-li"><a href="admin.jsp?menu=2" class="vnav-link"><i class="fa fa-inbox"></i>&nbsp; Produits</a></li>
                            <li class="vnav-li" style="margin: calc(100% + 180px) 0px 0px 0px;"><a href="login.jsp?logout=true" class="vnav-link"><i class="fa fa-eject"></i>Logout</a></li>
                        </ul>
                    </div>
                </div>
                <%
                	switch(activeMenu){
                		case 0:
                			out.println(welcomePage);
                			break;
                		case 1:
                			if(managmentType.equals("")){
	                			out.println(getMyCategories(db, manageCategories, categoryRow, lsc));
                			}else{
                				out.println(prepareMyCategoryForm(db, categoryForm, managmentType, lsc, itemId));
                			}
                			break;
                		case 2:
                			if(managmentType.equals("")){
	                			out.println(getMyProducts(db, manageProducts, productRow, lsp));
                			}else{
                				out.println(prepareMyProductForm(db, productForm, managmentType, lsc, itemId));
                			}
                			break;
                		default:
                			out.println(welcomePage);
                	}
                %>
                 
                
            </div>
        </div>
    </div>
	<script src="assets/js/jquery.min.js"></script>
    <script src="assets/bootstrap/js/bootstrap.min.js"></script>
    <script src="assets/js/Burger-Menu.js"></script>
</body>
</html>
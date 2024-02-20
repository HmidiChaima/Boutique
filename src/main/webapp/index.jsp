<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="dao.*, model.*, java.util.List, java.nio.file.*"%>
<%
	GestionBoutiqueImp db = new GestionBoutiqueImp();
	boolean showCart = false;
	int cartItems = 0;
	float total = 0;
	List<Panier> lspan = null;
	List<Categorie> lsc;
	List<Produit> lsp;
	String currentCategorie = "";
	int idc = 0;
	if(session != null && session.getAttribute("panier") != null){
		lspan = db.getPanierByUser((int)(session.getAttribute("uid")));
		cartItems = lspan.size();
	}
	if(request.getParameter("id") != null){
		idc = Integer.parseInt(request.getParameter("id"));
		lsp = db.getProduitByCategorie(idc);
		lsc = db.getCategorieChildrenById(idc);
		currentCategorie = db.getCategorieById(idc).getNom();
	}else{
		lsp = db.getAllProduits();
		lsc = db.getAllMasterCategorie();
	}
	if(request.getParameter("show_panier") != null){
		if(session == null || session.getAttribute("uid") == null){
			response.sendRedirect("login.jsp");
		}
		showCart = true;
	}
	String card = new String(Files.readAllBytes(Paths.get("C:/Users/Flowky/jee-workspace/boutique/src/main/webapp/components/test")));
	String panierRow = new String(Files.readAllBytes(Paths.get("C:/Users/Flowky/jee-workspace/boutique/src/main/webapp/components/user_panier_row")));
	
%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="ISO-8859-1">
		<link href="./css/bootstrap.min.css" rel="stylesheet"/>
		<title>E-Boutique</title>
	</head>
	<body>
		<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
		  <a class="navbar-brand" href="#" id="sss">E-Boutique</a>
		  <div class="navbar-collapse" id="navbarNav">
		    <ul class="navbar-nav">
		      <li class="nav-item">
		        <a class="nav-link" href="#">Home <span class="sr-only">(current)</span></a>
		      </li>
		      <li class="nav-item">
		        <a class="nav-link" href="#">Promotion</a>
		      </li>
		      <li class="nav-item">
		        <a class="nav-link" href="#">Vente Flash</a>
		      </li>
			    <li class="nav-item far-right-nav">
			    	<div style="display:flex; flex-direction:column; height : 18px;margin-right: 8px;">
				    	<%
				    		if(session.getAttribute("username") != null){
				    			String userName = (String)session.getAttribute("username");
				    			out.println("<h5>Bienvenue" + " " + userName + "</h5>");
				    			out.println("<a href=\"login.jsp?logout=true\">Logout</a>");
				    		}else{
				    			out.println("<a href=\"login.jsp\">Se Connecter</a>");
				    		}
				    	%>
			    	</div>
					<a href="index.jsp?show_panier=true">
					<button type="button" class="btn btn-secondary" id="btncalc">
					<img alt="" src="./svg/open-iconic-master/svg/cart.svg" width="18px" height="18px">
					  <span class="badge badge-light"><%= cartItems %></span>
					</button>
					</a>
			    </li>
		    </ul>
		  </div>
		</nav>
		<div class="modal fade show"  <%= !showCart ? "style='display:none;'" : "style='display:block;'" %>>
			<div class="modal-dialog modal-dialog-scrollable" >
				<div class="modal-content">
					<div class="modal-header">
						<h5 class="modal-title" id="myModalTitle"></h5>
						<a href="index.jsp"><button type="button" class="close" data-dismiss="modal">
				          <span aria-hidden="true">×</span>
				        </button></a>
					</div>
					<div class="modal-body" style="max-height : 500px; overflow-y: auto;">
						<table class="table table-dark table-striped mr-4">
					     	<thead>
							    <tr>
							      <th scope="col">Nom de produit</th>
							      <th scope="col">Quantité commandée</th>
							      <th scope="col">Prix</th>
							      <th scope="col">Action</th>
							    </tr>
							  </thead>
							 <tbody>
							<%
								if(lspan != null) {
									for(Panier p : lspan){
										Produit prod = p.getProduit();
										out.println(String.format(panierRow, prod.getNom(), p.getQuantite(), prod.getPrix(), p.getIdpa()));
									}
								}
							%>
							</tbody>
						</table>
					</div>
					<div class="modal-footer">
				        <a href="index.jsp"><button type="button" class="btn btn-secondary" data-dismiss="modal">Fermer</button></a>
				        <button type="button" class="btn btn-primary">Commander</button>
				    </div>
				</div>
			</div>
		</div>
		<div class="container-fluid">
			<div class="container-fluid">
				<div class="row mt-5">
					<div class="col-3">
						<form action="index.jsp" method="GET">
							<div class="btn-group-vertical btn-block">
								<button type="submit" class="btn btn-secondary active">Tous les Produits</button>
							    <%
							    	for(Categorie c:lsc){
							    		out.println(String.format(
							    				"<button type=\"submit\" class=\"btn btn-secondary\" name='id' value=\"%d\">%s</button>"
							    				,c.getIdc(),c.getNom()));
							    	}
							    %>
							</div>
					</form>
					</div>
						<h4 style="position : absolute; top: 65px;left :35px;"><%= currentCategorie %></h4>
						<div class="row col-9 g-9">
							<%
								for(Produit p : lsp){
									String imgLocation = "pimg/" + p.getImg();
									out.println(String.format(card,imgLocation,p.getNom(),p.getDescription(),p.getPrix(),p.getIdp()));
								}
							%>
						</div>
					</div>
			</div>
		</div>
		<script src="http://code.jquery.com/jquery-latest.min.js"></script>
		<script type="text/javascript">
			let modalTitle = document.getElementById("myModalTitle");
			modalTitle.textContent = localStorage.getItem("total") ? localStorage.getItem("total") : "Panier";
			$(document).on("click", "#btncalc", function() {
	            $.get("CalculeTotal", function(responseText) { 
	            	let total = "Panier total(" + responseText +" DT)";
	                localStorage.setItem("total" , total);
	                modalTitle.textContent = localStorage.getItem("total") ? localStorage.getItem("total") : "Panier";
	            });
	        });
			$(document).on("click", "#btncalcdelete", function() {
				$.get("CalculeTotal", function(responseText) { 
		           	let total = "Panier total(" + responseText +" DT)";
			        localStorage.setItem("total" , total);
		            	
		        });
	        });
			
		</script>
	</body>
</html>
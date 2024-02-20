<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%
	String previousHop= "";
	if(request.getAttribute("previousHop") != null){
		previousHop = (String)request.getAttribute("previousHop");
	}
	boolean invalidCredentials = false;
 	if(request.getParameter("logout") != null && request.getParameter("logout").equals("true")){
		session.invalidate();
		response.sendRedirect("index.jsp");
	}else {
		if(request.getParameter("error") != null){
			invalidCredentials = true;
		}
	}
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title>Login</title>
    <link rel="stylesheet" href="./assets/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="./assets/fonts/ionicons.min.css">
    <link rel="stylesheet" href="./assets/css/Login-Form-Clean.css">
    <link rel="stylesheet" href="./assets/css/styles.css">
</head>
	<body>
    <div class="login-clean" style="height: 100vh;width: 100%;">
        <form action="Authenticate" method="post">
            <h2 class="sr-only">Login Form</h2>
            <div class="illustration"><i class="icon ion-ios-navigate"></i></div>
            <div class="form-group"><input class="form-control" type="text" name="email" placeholder="Email"></div>
            <div class="form-group"><input class="form-control" type="password" name="pwd" placeholder="Password"></div>
            <div class="form-group"><button class="btn btn-primary btn-block" type="submit" name="previous" value="<%= previousHop%>">Log In</button></div>
            <p class="text-center style=" style="display :<%= invalidCredentials ? "inherit" : "none" %>;">Email ou mot de passe invalide</p>
        </form>
            
    </div>
	    <script src="assets/js/jquery.min.js"></script>
	    <script src="assets/bootstrap/js/bootstrap.min.js"></script>
	</body>
</html>


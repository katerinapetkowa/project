<%@page import="model.UsersManager"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="UTF-8">
<title>9GAG</title>
<link
	href='http://fonts.googleapis.com/css?family=Titillium+Web:400,300,600'
	rel='stylesheet' type='text/css'>

<link rel="stylesheet" href="css/normalize.css">


<link rel="stylesheet" href="css/style.css">




</head>

<body>

	<script type="text/javascript">
		
	</script>

	<div class="form">

		<div class="tab-content">
			<form class="signup" action="ChangeProfileServlet" method="post"
				enctype="multipart/form-data">

				<h1>Change profile</h1>
					<small style="color: white"><b>Avatar</b> </small>
					<br>
				<img class="img-responsive"
					src="PictureServlet?username=${UsersManager.getInstance().getUser(sessionScope.loggedAs).getUsername()}" alt="" width="100">
				<br>	
				<div class="field-wrap">
					<input type="file" name="profilePicture" accept="image/*" 
						autocomplete="off" />
				</div>


				<div class="field-wrap">
				<small style="color: white"><b>Name</b> </small>
					<input type="text" name="name" maxlength="50"
						value="${UsersManager.getInstance().getUser(sessionScope.loggedAs).getName()}"
						required autocomplete="off" />
				</div>

				<div class="field-wrap">
				<small style="color: white"><b> Email </b></small>
					<input type="email" name="email" maxlength="40"
						value="${UsersManager.getInstance().getUser(sessionScope.loggedAs).getEmail()}"
						required autocomplete="off" />
				</div>

				
				<div class="field-wrap">
				<small style="color: white"><b> Tell people who you are </b></small>
					<textarea style="height: 100px" name="description" id="description" maxlength="120"
					 required autocomplete="off"> ${UsersManager.getInstance().getUser(sessionScope.loggedAs).getDescription()} </textarea>
				</div>

				<button class="button button-block" type="submit">Save
					Changes</button>

			</form>
			<br>
			<a style = "text-decoration: none"href = "ChangePassword.jsp"> Change password</a>
			<br>
			<a style = "text-decoration: none"href = "deletePage.jsp"> Delete account</a> 


       <script src='http://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js'></script>

        <script src="js3/index.js"></script> 


		</div>
		<!-- tab-content -->

	</div>
	<!-- /form -->
	<script
		src='http://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js'></script>

	<script src="js/index.js"></script>




</body>
</html>
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

				<h1>Change password</h1>

				<div class="field-wrap">
					<label> Password<span class="req">*</span>
					</label> <input type="password" name="oldPassword" id="old_password" maxlength="30" required autocomplete="off" />
				</div>

				<div class="field-wrap">
					<label> New Password<span class="req">*</span>
					</label> <input type="password" name="password" id="password" maxlength="30" required autocomplete="off" />
				</div>


				<div class="field-wrap">
					<label> Confirm Password<span class="req">*</span>
					</label> <input type="password" name="password2" id="confirm_password" maxlength="30" required
						autocomplete="off" />
				</div>

				<button class="button button-block" type="submit">Save
					Changes</button>

			</form>


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
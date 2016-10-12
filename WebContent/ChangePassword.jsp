<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="UTF-8">
<title>9GAG</title>
<script
	src='http://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js'></script>

<link
	href='http://fonts.googleapis.com/css?family=Titillium+Web:400,300,600'
	rel='stylesheet' type='text/css'>

<link rel="stylesheet" href="css/normalize.css">


<link rel="stylesheet" href="css/style.css">


</head>

<body>

	<script src='http://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js'></script>


	<div class="form">

		<div class="tab-content">
			<form id="submitForm" class="signup" action="ChangePasswordServlet"
				method="post">

				<h1>Change password</h1>

				<div class="field-wrap">
					<label id="label"> Password<span class="req">*</span>
					</label> <input type="password" name="oldPassword" id="old_password"
						maxlength="30" required autocomplete="off" />
				</div>

				<div class="field-wrap">
					<label> New Password<span class="req">*</span>
					</label> <input type="password" name="password" id="password"
						maxlength="30" required autocomplete="off" />
				</div>


				<div class="field-wrap">
					<label> Confirm Password<span class="req">*</span>
					</label> <input type="password" name="password2" id="confirm_password"
						maxlength="30" required autocomplete="off" />
				</div>

				<button class="button button-block" type="button" onclick="asd()">Save
					Changes</button>

			</form>

		</div>
		<!-- tab-content -->

	</div>
	<!-- /form -->
	<script src="js/index.js"></script>
	<script>
		function asd(){
			var pass = $('#password').val();
			var newPass = $('#confirm_password').val();
			if(pass == ""){
				$('#password').get(0).setCustomValidity('Please enter a new password !');
				$('#password').get(0).reportValidity();
				return;
			}
			if(newPass == ""){
				$('#confirm_password').get(0).setCustomValidity('Please confirm new password!');
				$('#confirm_password').get(0).reportValidity();
				return;
			}
			if(pass != newPass){
				$('#confirm_password').get(0).setCustomValidity('Passwords Don\'t match!');
				$('#confirm_password').get(0).reportValidity();
			}else{
				var params = {
						oldPassword : $('#old_password').val(),
						password : $('#password').val(),
						confirmPassword : $('#confirm_password').val()
					};
					$.post("ValidatePasswordServlet", $.param(params), function(
							responseText) {
						console.log(responseText);
						if (responseText[0] == "f") {
							$('#old_password').get(0).setCustomValidity(
									'Wrong password');
							$('#old_password').get(0).reportValidity();
						}else {
							$('#submitForm').submit();
						}
					});
			}
		}
	
// 		function asdas() {
// 			var params = {
// 				oldPassword : $('#old_password').val(),
// 				password : $('#password').val(),
// 				confirmPassword : $('#confirm_password').val()
// 			};
// 			$.post("ValidatePasswordServlet", $.param(params), function(
// 					responseText) {
// 				console.log(responseText);
// 				if (responseText[0] == "f") {
// 					$('#old_password').get(0).setCustomValidity(
// 							'Wrong password');
// 					$('#old_password').get(0).reportValidity();
// 				}else {
// 					$('#submitForm').submit();
// 				}
// 			});
// 		}
	</script>

</body>
</html>

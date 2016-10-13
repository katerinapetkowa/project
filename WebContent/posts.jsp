<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="model.PostsManager" %>
<%@ page import = "model.UsersManager" %>
<%@ page import="model.Post" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>


<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>MyGag</title>

    <!-- Bootstrap Core CSS -->
    <link href="css2/bootstrap.min.css" rel="stylesheet">

    <!-- Custom CSS -->
    <link href="css2/blog-home.css" rel="stylesheet">
    <link href="button.css" rel="stylesheet">
    <link href="dropdown.css" rel="stylesheet">
    <link href="LogOutButton.css" rel="stylesheet">
    <link href="searchBox.css" rel="stylesheet">
    <link href="modal.css" rel="stylesheet">
    
     <link href='http://fonts.googleapis.com/css?family=Titillium+Web:400,300,600' rel='stylesheet' type='text/css'>
    
    <link rel="stylesheet" href="css/normalize.css">

    
        <link rel="stylesheet" href="css/style.css">

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->

</head>


<body>
	<% if(session.getAttribute("loggedAs") == null){ 
	
		request.getRequestDispatcher("index.html").forward(request, response);
	
	}
	%>
	
	
	<div id="id01" class="modal" >
	 <div class="form">
	 
<!-- 	 <form class="modal-content animate" action=""> -->
     
      <div class="tab-content">
        <form class ="login" action="LoginServlet" method="POST">  
<!--         <span onclick="document.getElementById('id01').style.display='none'" class="close" title="Close Modal">&times;</span> -->
          <h1>Please log in!</h1>
          
          
          
   			
            <div class="field-wrap">
              <label>
                Username<span class="req">*</span>
              </label>
              <input type="text" name="username" onfocus = "this.style.borderColor = '#1ab188'" onfocusout = "this.style.borderColor = '#a0b3b0' "style = "font-size: 22px;display: block;width: 100%;height: 100%;
              padding: 5px 10px; background: none; color: #ffffff; border-radius: 0;background-image: none; border: 1px solid #a0b3b0;" 
              maxlength="30" required autocomplete="off"/>
            </div>
          

          
          
          <div class="field-wrap">
            <label>
              Password<span class="req">*</span>
            </label>
            <input type="password" name="password" maxlength="30" required autocomplete="off"/>
          </div>
          
          <button class="button button-block" type="submit">Log in</button>
         <br>
<!--           <button class="button button-block" type="submit" onclick="window.location.href='/MyGag/register.html'"> Register</button> -->
          
          </form>

    
        
        
        
      </div><!-- tab-content -->
      
</div> <!-- /form -->
<!--  </form> -->
    <script src='http://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js'></script>

        <script src="js/index.js"></script>
           
</div>
    
    <div id="id02" class="modal">
	 <div class="form">
	 
<!-- 	 <form class="modal-content animate" action=""> -->
     
      <div class="tab-content">
        <form class ="signup" action="RegisterServlet" method="POST" enctype="multipart/form-data">  
        <span onclick="document.getElementById('id02').style.display='none'" class="close" title="Close Modal">&times;</span>
          <h1>Sign up</h1>
          
          
          
   			
            <div class="field-wrap">
              <label>
                Name<span class="req">*</span>
              </label>
              <input type="text" name="username" onfocus = "this.style.borderColor = '#1ab188'" onfocusout = "this.style.borderColor = '#a0b3b0' "style = "font-size: 22px;display: block;width: 100%;height: 100%;
              padding: 5px 10px; background: none; color: #ffffff; border-radius: 0;background-image: none; border: 1px solid #a0b3b0;" 
              maxlength="30" required autocomplete="off"/>
            </div>
          

          <div class="field-wrap">
              <label>
                Username<span class="req">*</span>
              </label>
              <input type="text" name="username" onfocus = "this.style.borderColor = '#1ab188'" onfocusout = "this.style.borderColor = '#a0b3b0' "style = "font-size: 22px;display: block;width: 100%;height: 100%;
              padding: 5px 10px; background: none; color: #ffffff; border-radius: 0;background-image: none; border: 1px solid #a0b3b0;" 
              maxlength="30" required autocomplete="off"/>
            </div>
          
          <div class="field-wrap">
              <label>
                Email<span class="req">*</span>
              </label>
              <input type="email" name="email" maxlength="40" required autocomplete="off"/>
            </div>   
          
          <div class="field-wrap">
            <label>
              Password<span class="req">*</span>
            </label>
            <input type="password" name="password" maxlength="30" required autocomplete="off"/>
          </div>
          
           <div class="field-wrap">
            <label>
              Confirm Password<span class="req">*</span>
            </label>
            <input type="password"  name="password2" id="confirm_password" maxlength="30" required autocomplete="off"/>
          </div>
          
 		<div class="field-wrap">
         
            <input type="file"  name="profilePicture" accept="image/*" required autocomplete="off"/>
          </div>   
          
          <button class="button button-block" type="submit">Register</button>
         <br>
<!--           <button class="button button-block" type="submit" onclick="window.location.href='/MyGag/register.html'"> Register</button> -->
          
          </form>

    
        
        <script src='http://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js'></script>

        <script src="js3/index.js"></script> 
        
      </div><!-- tab-content -->
      
</div> <!-- /form -->
<!--  </form> -->
    <script src='http://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js'></script>

        <script src="js/index.js"></script>
           
</div>
    
    <!-- Navigation -->
    <nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
        <div class="container">
        
            <!-- Brand and toggle get grouped for better mobile display -->
            <div class="navbar-header">
            
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
               <a href="posts.jsp"> <img alt="MyGag" src="9gag-logo.png" height="55" width="55"> </a>
               
                 
            </div>
            <!-- Collect the nav links, forms, and other content for toggling -->
            <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                <ul class="nav navbar-nav">
                
                	<li>
                        <a href="Hot.jsp">Hot</a>
                    </li>
                    
                    <li>
                        <a href="Fresh.jsp">Fresh</a>
                    </li>
                	
                    <li>
                        <a href="Funny.jsp">Funny</a>
                    </li>
                    <li>
                        <a href="MovieTV.jsp">MovieTV</a>
                    </li>
                    <li>
                        <a href="Sport.jsp">Sport</a>
                    </li>
                    <li>
                        <a href="Food.jsp">Food</a>
                    </li>
<!--                     <li> -->
<!--                         <a href="/MyGag/UploadPost.html"> Upload </a> -->
<!--                     </li> -->
                    <li>
                    <button class="dropbtnlog" onclick="document.getElementById('id01').style.display='block';document.getElementById('id02').style.display='none'" style="width:auto;"  >Log in</button>
                    </li>
                     <li>
                    <button class="dropbtnlog" onclick="document.getElementById('id02').style.display='block';document.getElementById('id01').style.display='none'" style="width:auto;"  >Sign up</button>
                    </li>
                    
<!--                         <li> <div class="dropdown">  -->
<!--  							 <img class="dropbtn"   -->
<%-- 					src="PictureServlet?username=${UsersManager.getInstance().getUser(sessionScope.loggedAs).getUsername()}" alt="" height="55" width="55">   --%>
<!--   									<div class="dropdown-content">  -->
<!-- 	    								<a href="/MyGag/Profile.jsp">My Profile</a>  -->
<!--  	    								<a href="/MyGag/ChangeSettings.jsp">Settings</a>  -->
<!-- 	    								<form action = "LogOutServlet" method = "post">  -->
	    								
<!--  	   									<button class="dropbtnlog" type = "submit" >Logout</button>  -->

	   									
	   								
<!--    									</form>  -->
	    						
<!--   									</div>  -->
<!-- 					</div> </li>  -->
						
						
						
					<li>
					<form action = "SearchServlet" method = "get">
					<input class = " input[type=text] " style = "color: #b4b4b4"  type="text" name="title" required  placeholder="Search..">
					</form>
					</li>	
					
                    
                </ul>
               
                
            </div>
            <!-- /.navbar-collapse -->
        </div>
        <!-- /.container -->
    </nav>

    <!-- Page Content -->
    <div class="container">

        <div class="row">

            <!-- Blog Entries Column -->
            <div class="col-md-8">

                <h1 class="page-header">
                    Welcome to our site MyGag!
                    
                </h1>
                
                <c:forEach var = "post" items='${PostsManager.getInstance().getFreshPosts().values()}'>
				
                <!-- First Blog Post -->
                <h2>
                    <a style = "text-decoration: none; color:#222222" onmouseover="this.style.color = '#23527c'" onmouseout="this.style.color = '#222222'" href="DetailsPostServlet?post_id=<c:out value="${post.postId}"></c:out>"> <c:out value="${post.title}"></c:out></a>
                </h2>
               
                <p><span class="glyphicon glyphicon-time"></span> <c:out value="${post.uploadDate}"></c:out> </p>
                <hr>
                <a href="DetailsPostServlet?post_id=<c:out value="${post.postId}"></c:out>"><img class="img-responsive" src="PostServlet?post_id=<c:out value="${post.postId}"></c:out>" alt="" width = "500"></a>
                <hr>
               
				
				</c:forEach>
               </div>
			 </div>
         
<script>
/* var modal = document.getElementById('id01');

modal.addEventListener('click', function(event){
	
	modal.style.display = 'none';
}); */

// Get the modal
 var modal = document.getElementById('id01');

// When the user clicks anywhere outside of the modal, close it
window.onclick = function(event) {
    if (event.target == modal) {
        modal.style.display = "none";
    }
} 
</script>
<script >
//Get the modal
var modal2 = document.getElementById('id02');

// When the user clicks anywhere outside of the modal, close it
window.onclick = function(event) {
    if (event.target == modal2) {
        modal2.style.display = "none";
    }
}
</script>

  

    </div>
    <!-- /.container -->

    <!-- jQuery -->
    <script src="js2/jquery.js"></script>

    <!-- Bootstrap Core JavaScript -->
    <script src="js2/bootstrap.min.js"></script>

</body>

</html>

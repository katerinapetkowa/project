<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="model.PostsManager" %>
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
                    <li>
                        <a href="/MyGag/UploadPost.html"> Upload </a>
                    </li>
                     
                       <li> <div class="dropdown">
 							 <button class="dropbtn">Settings</button>
  									<div class="dropdown-content">
	    								<a href="/MyGag/Profile.jsp">My Profile</a>
	    								<form action = "LogOutServlet" method = "post">
	    								
	   									<button class="dropbtnlog" type = "submit" >Logout</button>
	   								<% 
	    								
	    								response.setHeader("Pragma", "No-cache"); 
	    								response.setDateHeader("Expires", 0); 
	    								response.setHeader("Cache-Control", "no-cache"); 
	    								
	    							%>
	   									
	   								
	   									</form>
	    						
  									</div>
						</div> </li>
					<li>
					<form action = "SearchServlet" method = "get">
					<input class = " input[type=text] " style = "color: #b4b4b4"  type="text" name="title" placeholder="Search..">
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
               
               <c:set var="title" value="${param.title}" scope="request" />
               <h4>Search results for "<c:out value="${requestScope.title}"></c:out>"</h4>
				<c:forEach var = "post" items='${PostsManager.getInstance().searchPosts(requestScope.title).values()}'>
                <!-- First Blog Post -->
                <c:choose>
                	<c:when test="">
                <h2>
                    <a style = "text-decoration: none; color:#222222" onmouseover="this.style.color = '#23527c'" onmouseout="this.style.color = '#222222'" href="DetailsPostServlet?post_id=<c:out value="${post.postId}"></c:out>"> <c:out value="${post.title}"></c:out></a>
                </h2>
               
                <p><span class="glyphicon glyphicon-time"></span> <c:out value="${post.uploadDate}"></c:out> </p>
                <hr>
                <a href="DetailsPostServlet?post_id=<c:out value="${post.postId}"></c:out>"><img class="img-responsive" src="PostServlet?post_id=<c:out value="${post.postId}"></c:out>" alt="" width = "500"></a>
                <hr>
                </c:when>
                </c:choose>
               </c:forEach>
				
				
               </div>
			 </div>
         

            

    </div>
    <!-- /.container -->

    <!-- jQuery -->
    <script src="js2/jquery.js"></script>

    <!-- Bootstrap Core JavaScript -->
    <script src="js2/bootstrap.min.js"></script>

</body>

</html>
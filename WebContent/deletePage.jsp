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
                   Are you sure you want to delete your account?
                    
                </h1>
                <h5> This will delete your account and all of its content.</h5>
                
                <button class = " dropbtnlog "  style = "border-radius:5px" type = "submit" onclick="window.location.href='/MyGag/posts.jsp'"> No, I don't want to do that </button>
                <br>
                
                
                <form action = "DeleteServlet" method = "post">
                <input id="username" name="username" type="hidden" value="<c:out value="${sessionScope.loggedAs}"></c:out>" size="30" required>
                <button style = "background:none!important; border:none; padding:0!important;font: inherit;border-bottom:none;cursor: pointer" onmouseover="this.style.color = '#808080'" onmouseout="this.style.color = '#222222'"> I want to delete my account.</button>
                </form>
                </div>
             </div>
 </div>
</body>
</html>
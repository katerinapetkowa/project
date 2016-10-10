<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="model.PostsManager" %>
<%@ page import="model.Post" %>
<%@ page import="model.UsersManager" %>
<%@ page import="model.User" %>
<%@ page import="model.CommentsManager" %>
<%@ page import="model.Comment" %>
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
    <link href="postDetails.css" rel="stylesheet">
    <link href="searchBox.css" rel="stylesheet">

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
    
 

<script>
function byId(e){return document.getElementById(e);}

window.addEventListener('load', mInit, false);

function mInit()
{
    var tgt = byId('image');
    tgt.secondSource = 'redheart2.png';
}

function byId(e){return document.getElementById(e);}

function action() 
{
    var tgt = byId('image');
    var tmp = tgt.src;
    tgt.src = tgt.secondSource;
    tgt.secondSource = tmp;
};
</script>

<script>
function byId(e){return document.getElementById(e);}

window.addEventListener('load', mInit, false);

function mInit()
{
    var tgt = byId('image2');
    tgt.secondSource = 'reddislike2.png';
}

function byId(e){return document.getElementById(e);}

function action2() 
{
    var tgt = byId('image2');
    var tmp = tgt.src;
    tgt.src = tgt.secondSource;
    tgt.secondSource = tmp;
};

</script>



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
                     
                    <li>    <div class="dropdown">
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

           <!-- Blog Post Content Column -->
            <div class="col-lg-8">

                <!-- Blog Post -->

	 		 <c:set var = "post" value="${PostsManager.getInstance().getPost(param.post_id)}" scope = "page"/>
                <!-- Title -->
                <h1><c:out value="${post.title}"></c:out></h1>

                

                <!-- Date/Time -->
                <p><span class="glyphicon glyphicon-time"></span> <c:out value="${post.uploadDate}"></c:out></p>

                <hr>

                <!-- Preview Image -->
                <img class="img-responsive" src="PostServlet?post_id=<c:out value="${post.postId}"></c:out>" alt="" width = "500">


				<hr>
				
				<a href="LikeServlet?post_id=<c:out value="${post.postId}"></c:out>"> <input type = "image" id ="image" src="heart.png" onclick="action();" alt="Submit" width="38" height="38"></a> 
				<a href="DislikeServlet?post_id=<c:out value="${post.postId}"></c:out>"> <input type = "image"  id = "image2" src="dislikebutton.png" onclick="action2();" alt="Submit" width="38" height="38"></a>
				<br> 
				<a style = "color:gray" href = ""> <c:out value="${post.points}"></c:out> points </a>  - <a style = "color:gray" href = ""> <c:out value = "${CommentsManager.getInstance().getNumberOfCommentsOfPost(post.postId)}"></c:out> comments </a>
                <!-- Blog Comments -->
				 
				<h4> <c:out value = "${CommentsManager.getInstance().getNumberOfCommentsOfPost(post.postId)}"></c:out> comments</h4>
                <!-- Comments Form -->
                <div class="well">
                    <h4>Leave a comment</h4>
                    <form role  = "form" action = "WriteCommentServlet" method = "post">
                    
                    
                        <div class="form-group">
                            <textarea class="form-control" name = "comment" required></textarea>
                        </div> 
                        <input id="author" name="post_id" type="hidden" value="<c:out value= "${post.postId}"></c:out>" size="30" required>
                        <input id="username" name="username" type="hidden" value="<c:out value="${sessionScope.loggedAs}"></c:out>" size="30" required>
                        <button type="submit" class="btn btn-primary">Submit</button>
                       
                    </form>
                </div>

              

               <!-- Posted Comments -->
				
				 <c:forEach var = "comment" items='${CommentsManager.getInstance().getCommentsOfPost(post.postId).values()}' >
				 <c:set var = "user" value = "${UsersManager.getInstance().getUser(comment.username)}"> </c:set>
                <!-- Comment -->
                <hr>
                <div class = "media">
                    <a class = "pull-left" href="#">
                        <img class="media-object" src="PictureServlet?username=<c:out value="${user.username}"></c:out>" alt="" width="54" height = "54">
                    </a>
                    <div>
                        <h4><c:out value="${user.username}"></c:out>
                            <small><c:out value="${comment.uploadDate}"></c:out></small>
                        </h4>
                        <c:out value="${comment.text}"></c:out>
                    </div>
                </div>

 </c:forEach>

                

             <!-- Footer -->
        <footer>
            <div class="row">
                <div class="col-lg-12">
                    <p>Copyright &copy; MyGAG</p>
                </div>
            </div>
            <!-- /.row -->
        </footer>

          

     
            

    </div>
    <!-- /.container -->

    <!-- jQuery -->
    <script src="js2/jquery.js"></script>

    <!-- Bootstrap Core JavaScript -->
    <script src="js2/bootstrap.min.js"></script>

</body>

</html>

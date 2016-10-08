<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="model.PostsManager" %>
<%@ page import="model.Post" %>
<%@ page import="model.UsersManager" %>
<%@ page import="model.User" %>
<%@ page import="model.CommentsManager" %>
<%@ page import="model.Comment" %>
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
               <a href="posts.jsp"> <img alt="MyGag" src="9gag-logo.png" height="60" width="60"> </a>
               
                 
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
                     
                        <div class="dropdown">
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
						</div> 
						
                    
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
<%
    Post post = PostsManager.getInstance().getPost(Integer.parseInt(request.getAttribute("post_id").toString()));
	
	%>
                <!-- Title -->
                <h1><%= post.getTitle() %></h1>

                <hr>

                <!-- Date/Time -->
                <p><span class="glyphicon glyphicon-time"></span> <%= post.getUploadDate() %></p>

                <hr>

                <!-- Preview Image -->
                <img class="img-responsive" src="PostServlet?post_id=<%= post.getPostId()%>" alt="" width = "500">


				<hr>
				<input  type="image" id ="image" src="heart.png" onclick="action();" alt="Submit" width="38" height="38"> <input type="image" id = "image2" src="dislikebutton.png" onclick="action2();" alt="Submit" width="38" height="38">
                <!-- Blog Comments -->

				<h4> <%= CommentsManager.getInstance().getCommentsByPosts().size() %> comments</h4>
                <!-- Comments Form -->
                <div class="well">
                    <h4>Leave a comment</h4>
                    <form role  = "form" action = "WriteCommentServlet" method = "get">
                    
                    
                        <div class="form-group">
                            <textarea class="form-control" name = "comment"> </textarea>
                        </div>
                        <input id="author" name="post_id" type="hidden" value="<%=post.getPostId() %>" size="30">
                        <input id="username" name="username" type="hidden" value="<%=session.getAttribute("loggedAs").toString() %>" size="30">
                        <button type="submit" class="btn btn-primary">Submit</button>
                       
                    </form>
                </div>

              

               <!-- Posted Comments -->
				<%  
				 for(Comment comment : CommentsManager.getInstance().getCommentsOfPost(post.getPostId()).values()){ 
				      User user = UsersManager.getInstance().getUser(comment.getUsername());
				 
				 %>
                <!-- Comment -->
                <hr>
                <div class = "media">
                    <a class = "pull-left" href="#">
                        <img class="media-object" src="PictureServlet?username=<%= user.getUsername() %>" alt="" width="54" height = "54">
                    </a>
                    <div>
                        <h4  ><%= user.getUsername()%>
                            <small><%= comment.getUploadDate() %></small>
                        </h4>
                        <%= comment.getText() %>
                    </div>
                </div>
 <% }  %>

                

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

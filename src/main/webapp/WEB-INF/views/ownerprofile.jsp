<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
<title>Fund Raising</title>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link href="<c:url value="/resources/css/bootstrap.min.css" />"
	rel="stylesheet">
<link href="<c:url value="/resources/css/sb-admin.css" />"
	rel="stylesheet">
<link href="<c:url value="/resources/css/style.css" />" rel="stylesheet">
<link href="https://fonts.googleapis.com/css?family=Open+Sans:300,400,600,700" rel="stylesheet">  

<link
	href="<c:url value="/resources/css/plugins/morris.css" />"
	rel="stylesheet">

<link
	href="<c:url value="/resources/font-awesome/css/font-awesome.min.css" />"
	rel="stylesheet" type="text/css">
<link href="<c:url value="/resources/css/date.css" />" rel="stylesheet">

<script src="<c:url value="/resources/js/jquery.min.js" />"></script>
<script src="<c:url value="/resources/js/bootstrap.min.js" />"></script>
<script src="<c:url value="/resources/js/funraising.js" />"></script>

</head>
<body>

	<div id="wrapper">

		<!-- Navigation -->
		<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
		<!-- Brand and toggle get grouped for better mobile display -->
		<div class="navbar-header">
			<button type="button" class="navbar-toggle" data-toggle="collapse"
				data-target=".navbar-ex1-collapse">
				<span class="sr-only">Toggle navigation</span> <span
					class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="#">Fund Raising</a>

		</div>
		<!-- Top Menu Items -->
		<ul class="nav navbar-left top-nav">
			<div class="row">
				<div class="col-sm-6">
					<div class="search">

						<div class="input-group col-md-12">
							<form class="navbar-form" role="search">
								<div class="input-group add-on">
									<div class="input-group-btn">
										<button class="btn btn-default1" type="submit">
											<i class="glyphicon glyphicon-search"></i>
										</button>
									</div>
									<input class="form-control" placeholder="Search"
										name="srch-term" id="srch-term" type="text">

								</div>
							</form>

						</div>



					</div>
				</div>
				<div class="col-sm-6">
					<ul class="top_hed pull-right">
						<li>
							<div class="imge">
								<img src="<c:url value="/resources/images/user.png"/>" style="height: 45px;width: 45px;">
							</div>
						</li>
						<li><div class="tex">
								<h2>${currentUser.fullname}</h2>
							</div>
							<div style="float:left;padding-top: 15px;padding-right: 12px;">
								My Balance : ${myBalance} ETH
							</div>
						</li>
					</ul>
					
				</div>
			</div>
		</ul>
		<!-- Sidebar Menu Items - These collapse to the responsive navigation menu on small screens -->
		<div class="collapse navbar-collapse navbar-ex1-collapse">
			<ul class="nav navbar-nav side-nav">
				<li><a href="ownerhome"><i class="fa fa-tasks" aria-hidden="true"></i>
						My projects</a></li>

				<li class="active"><a href="#"><i class="fa fa-user" aria-hidden="true"></i>
						My profile</a></li>
				<!-- <li><a href="#"><i class="fa fa-usd" aria-hidden="true"></i>
						Funds</a></li> -->
				<li><a href="logout"><i class="fa fa-sign-out"
						aria-hidden="true"></i> Logout</a></li>

			</ul>
		</div>
		</nav>
		
		<div class="container" style="padding-top: 80px;">
			<div class="panel panel-info" >
				<div class="panel-heading">
                   	<div class="panel-title">My Profile</div>
               	</div>
             </div>
			<div style="padding: 10px">

				<div class="form-inline">
					<label for="name" style="width: 250px;">Name</label> : <label for="name" style="font-weight: normal;">
						${currentUser.fullname }</label>
				</div>

				<div class="form-inline">
					<label for="user_name" style="width: 250px;">User Name</label> : <label for="name" style="font-weight: normal;">
						${currentUser.username }</label>
				</div>

				<div class="form-inline">
					<label for="user_name" style="width: 250px;">Account Type</label> : <label for="name" style="font-weight: normal;">
						${currentUser.type.name }</label>
				</div>
				
				<div class="form-inline">
					<label for="name" style="width: 250px;">Current Balance</label> : <label for="name" style="font-weight: normal;">
						${myBalance } ETH</label>
				</div>
				
				
				<div class="form-inline">
					<label for="user_name" style="width: 250px;">Public Address</label> : <label for="name" style="font-weight: normal;">
						${currentUser.bcaddress }</label>
				</div>

				<div class="form-inline">
					<label for="number" style="width: 250px;">Contact Number</label> : <label for="name" style="font-weight: normal;">
						${currentUser.contactnumber }</label>
				</div>
				<div class="form-inline">
					<label for="email" style="width: 250px;">Email</label> : <label for="name" style="font-weight: normal;">
						${currentUser.email }</label>
				</div>
				<div class="form-inline">
					<label for="address" style="width: 250px;">Address</label> : <label for="name" style="font-weight: normal;">
						${currentUser.address }</label>
				</div>
			</div>
		</div> 

	</div>

</body>
</html>
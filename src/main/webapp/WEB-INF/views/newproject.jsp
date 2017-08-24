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
								<img src="<c:url value="/resources/images/user.png"/>"
									style="height: 45px; width: 45px;">
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
				<li class="active"><a href="ownerhome"><i
						class="fa fa-tasks" aria-hidden="true"></i> My projects</a></li>

				<li><a href="#"><i class="fa fa-user" aria-hidden="true"></i>
						My profile</a></li>
				<!-- <li><a href="#"><i class="fa fa-usd" aria-hidden="true"></i>
						Funds</a></li> -->
				<li><a href="logout"><i class="fa fa-sign-out"
						aria-hidden="true"></i> Logout</a></li>

			</ul>
		</div>
		</nav>

		<div id="page-wrapper">

			<div class="container-fluid">

				<!-- Page Heading -->
				<div class="row">
					<div class="col-lg-12">
						<!--h1 class="page-header">
                            Dashboard <small>Statistics Overview</small>
                        </h1-->
						<div class="breadcrumb">

							<form class="form-horizontal" role="form" action="addNewProject" method="post">
								<div class="panel panel-info" >
									<div class="panel-heading">
				                     	<div class="panel-title">Add New Project</div>
				                 	</div>
			                 	</div>
								<div class="errorMessageDiv"><script>showErrorMessage('<c:out value='${param.errormsg}'/>')</script></div>
								
								<div class="form-group">

									<div class="col-sm-6">
										<label>Reference Number</label> <input type="text" id="referenceNumber" name="referenceNumber"
											placeholder="e.g., 98941" class="form-control" autofocus required>

									</div>
									<div class="col-sm-6">
										<label>Title</label> <input type="text" id="jobTitle" name="jobTitle"
											placeholder="e.g., Website for church" class="form-control" required>

									</div>
								</div>
								<div class="form-group">

									<div class="col-sm-6">
										<label>Funds Required (FYN Token)</label> <input type="text" id="fundRequired" name="fundRequired"
											placeholder="e.g., 1000" class="form-control" required>
									</div>
									<div class='col-sm-6'>
										<label>Fund Raising Expire on</label>
										<div class='input-group date' id='datetimepicker1'>
											<input type="date" id="expiryDate" name="expiryDate" class="form-control" required></input>
										</div>
									</div>

								</div>
								<div class="form-group">
									<div class="col-sm-12">
										<label>Description</label>
										<textarea placeholder="Description..." rows="3" name="description"
											class="form-control" required></textarea>
									</div>
								</div>

								<button type="submit"
										class="btn btn-lg btn-info">Create</button>
							</form>
						</div>

					</div>
					

				</div>
			</div>
		</div>
	</div>

	<%-- <form action="addNewProject" method="post">
	 
		<div id="newProductScreen" style="margin-left:25%;margin-right:25%">
		    <h2>Add New Project</h2>
		    <br><label for="referenceNumber">Reference Number:</label><input type="text" id="referenceNumber" name="referenceNumber" required placeholder="e.g., 98941"></input>
		    <br><label for="jobTitle">Title:</label><input type="text" id="jobTitle" name="jobTitle" required placeholder="e.g., Website for church"></input>
		    <br><label for="fundRequired">Fund Required:</label><input type="number" id="fundRequired" name="fundRequired" required placeholder="e.g., 10000"></input>
		    <br><label for="description">Description:</label><input type="text" id="description" name="description" required placeholder="e.g., This project..."></input>
		    <br><label for="expiryDate">Fund Raised Expire On:</label><input type="date" id="expiryDate" name="expiryDate" required></input>
		    
		    <br><br><button id="add" class="btn" type="submit">Add Project</button> <button class="btn" style="background-color: gray;" id="hideAddProductScreen"  type="reset">Reset</button> <a href="ownerhome" class="buttontype">Back</a>
		  
		  	<span class="error">${param.errormsg}</span>
		  
		  </div>
	   </form> --%>
</body>
</html>
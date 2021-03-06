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
				<li class="active"><a href="#"><i class="fa fa-tasks" aria-hidden="true"></i>
						Current projects</a></li>

				<li><a href="investorprofile"><i class="fa fa-user" aria-hidden="true"></i>
						My profile</a></li>
				<!-- <li><a href="#"><i class="fa fa-usd" aria-hidden="true"></i>
						Funds</a></li> -->
				<li><a href="logout"><i class="fa fa-sign-out"
						aria-hidden="true"></i> Logout</a></li>

			</ul>
		</div>
		</nav>
		
		<div class="container" style="padding-top: 80px;">
		<div class="errorMessageDiv"><script>showErrorMessage('<c:out value='${param.errormsg}'/>')</script></div>
		<div class="row">
		    <c:set var="count" value="0" scope="page" />
			<c:forEach items="${jobs}" var="job">
				<c:set var="count" value="${count + 1}" scope="page"/>
				 <div class="col-md-3">
			        <div class="box">
			        	<span style="float:right;" class="jobStatus${count}"><script>getStatus('<c:out value='${job.status}'/>', 'jobStatus' + '<c:out value='${count}'/>');</script></span>
						<h5><b># ${job.referenceNumber}</b></h5>
						<h4><b>${job.title}</b></h4>
						<p>${job.description}</p>
						<p><b>Fund Required: ${job.fundRequired} (FYN)</b></p>
						<h6 class="funded${count}" style="float:left"><script>showFunded('<c:out value='${job.achievedAmount}'/>', '<c:out value='${job.fundRequired}'/>','funded' + '<c:out value='${count}'/>');</script></h6>
						<h6 class="targetDate${count}" style="float:right"><script>showDaysLeft('<c:out value='${job.targetDate}'/>', 'targetDate' + '<c:out value='${count}'/>');</script></h6>
						<br><br>
						<p><b>You own: ${job.myToken} (FYN)</b></p>
						<span class="showButton${count }"><script>showInvestButton('showButton' + '<c:out value='${count}'/>', '<c:out value='${job.referenceNumber}'/>', '<c:out value='${job.creator}'/>', '<c:out value='${job.status}'/>', '<c:out value='${job.myToken}'/>');</script> </span>
					</div>
			    </div>
			</c:forEach>
		</div>
	</div> 

	</div>
	
	<div class="modal fade" id="fund-modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" style="display: none;">
   	  <div class="modal-dialog">
			<div class="loginmodal-container">
			<form action="myFundInvestment" method="post">
              	<input type="hidden" id="creatorAddress" name="creatorAddress"></input>
              	<input type="hidden" id="referenceNumber" name="referenceNumber"></input>
	               <div class="form-group">
	                   
	                   <div class="col-sm-12">
					<label>Your Fund</label>
	                       <input type="text" id="myfund" name="myfund" placeholder="e.g., 200" class="form-control" autofocus>
	                   </div>
	               </div>
				<button type="submit" class="btn btn-info">Submit</button>
			  </form>
			</div>
		</div>
	  </div>
  	<%-- <div class="container">
		<div class="row">
		    <div class="col-md-12">
		        <h2>Current Projects</h2>
		    </div>
		    
		    <c:set var="count" value="0" scope="page" />
			<c:forEach items="${jobs}" var="job">
				<c:set var="count" value="${count + 1}" scope="page"/>
				 <div class="col-md-3">
			        <div class="box">
			        	<span style="float:right;" class="jobStatus${count}"><script>getStatus('<c:out value='${job.status}'/>', 'jobStatus' + '<c:out value='${count}'/>');</script></span>
						<h5><b># ${job.referenceNumber}</b></h5>
						<h4><b>${job.title}</b></h4>
						<p>${job.description}</p>
						<p><b>Fund Required: ${job.fundRequired} (ETH)</b></p>
						<h6 class="funded${count}" style="float:left"><script>showFunded('<c:out value='${job.achievedAmount}'/>', '<c:out value='${job.fundRequired}'/>','funded' + '<c:out value='${count}'/>');</script></h6>
						<h6 class="targetDate${count}" style="float:right"><script>showDaysLeft('<c:out value='${job.targetDate}'/>', 'targetDate' + '<c:out value='${count}'/>');</script></h6>
						<br><br>
						<span class="showButton${count }"><script>showInvestButton('showButton' + '<c:out value='${count}'/>', '<c:out value='${job.referenceNumber}'/>', '<c:out value='${job.creator}'/>');</script> </span>
					</div>
			    </div>
			</c:forEach>
		    <div class="col-md-12">
		        <span>Total Projects : </span> <span id="projectCount">${count}</span>
		    </div>
		</div>
	</div> --%>
</body>
</html>
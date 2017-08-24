<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
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
				<li class="active"><a href="ownerhome"><i class="fa fa-tasks" aria-hidden="true"></i>
						My projects</a></li>

				<li><a href="ownerprofile"><i class="fa fa-user" aria-hidden="true"></i>
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
                   	<div class="panel-title"> 
                   		<h3>${job.title} :  Milestones ${newMilestoneAction}</h3>  
			  		</div>
               	</div>
            </div>
            
            <div class="row">
		    
		    <div class="col-md-12">
		        
		        
		    </div>	
		    <div class="col-md-12">
		        <div class="box">
		        	 <table class="table table-bordered table-hover" id="products">
						<tr>
							<th>#</th>
				          	<th>Description</th>
				          	<th>Completion Date</th>
				          	<th>Completed Date</th>
				          	<th>Payment Percentage</th>
				          	<th>Status</th>
				          	<th>Action</th>
						</tr>
			        	<c:set var="count" value="0" scope="page" />
						<c:forEach items="${milestones}" var="milestone">
							<c:set var="count" value="${count + 1}" scope="page"/>
							<tr>
								<td>${count}</td>
								<td>${milestone.description}</td>
								<td class="completionDate${count}"><script>formatAndDisplayDate('<c:out value='${milestone.completionDate}'/>', 'completionDate' + '<c:out value='${count}'/>');</script></td>
								<td class="completedDate${count}"><script>formatAndDisplayDate('<c:out value='${milestone.completedDate}'/>', 'completedDate' + '<c:out value='${count}'/>');</script></td>
								<td>${milestone.paymentPercentage}</td>
								<td class="milestoneStatus${count}"><script>getMilestoneStatus('<c:out value='${milestone.status}'/>', 'milestoneStatus' + '<c:out value='${count}'/>');</script></td>
								<td class="milestoneButton${count}"><script>showMilestoneActionButton('<c:out value='${milestone.status}'/>', '<c:out value='${referenceNumber}'/>', '<c:out value='${vendor}'/>', '<c:out value='${milestone.currentIndex}'/>', 'milestoneButton' + '<c:out value='${count}'/>');</script></td>
							</tr>
						</c:forEach>
					</table>
		        	<span>Total : </span> <span id="logsCount">${count}</span>
		        </div>
		    </div>	
		    
		</div>
		
		</div> 

	</div>
  
  	<%-- <div class="container">
		<div class="row">
		    <div class="col-md-12">
		        <h2>Project Detail</h2>
		    </div>
		    
		    <div class="col-md-12">
		    	<div class="box" style="min-height: 190px;">
			    	<span style="float:right;" class="jobStatus${count}"><script>getStatus('<c:out value='${job.status}'/>', 'jobStatus' + '<c:out value='${count}'/>');</script></span>
					<h5><b># ${job.referenceNumber}</b></h5>
					<h6 class="createdDate${count}" style="float:right;margin-top: -5px;"><script>formatDate('<c:out value='${job.appliedDate}'/>', 'createdDate' + '<c:out value='${count}'/>');</script></h6>
					<h4><b>${job.title}</b></h4>
					<p>${job.description}</p>
					<p style="float:left;"><b>Fund Required: ${job.fundRequired} (ETH)</b></p>
					<p style="float:right;">Achieved Fund: ${job.achievedAmount } (ETH)</p>
					<br>
					<h6 class="fundedProgress${count}"><script>showFundedProgressbar('<c:out value='${job.achievedAmount}'/>', '<c:out value='${job.fundRequired}'/>','fundedProgress' + '<c:out value='${count}'/>');</script></h6>
					<h6 class="funded${count}" style="float:left;margin-top: -10px;"><script>showFunded('<c:out value='${job.achievedAmount}'/>', '<c:out value='${job.fundRequired}'/>','funded' + '<c:out value='${count}'/>');</script></h6>
					<h6 class="targetDate${count}" style="float:right;margin-top: -10px;"><script>showDaysLeft('<c:out value='${job.targetDate}'/>', 'targetDate' + '<c:out value='${count}'/>');</script></h6>
					<br><p class="refundInvestment${count}"><script>showRefundVestment('<c:out value='${job.status}'/>', '<c:out value='${job.achievedAmount}'/>', '<c:out value='${job.fundRequired}'/>', '<c:out value='${job.referenceNumber}'/>', 'refundInvestment' + '<c:out value='${count}'/>');</script></p>
		    	</div>
		    </div>
		    <div class="col-md-12">
		        <h2>List of Investor</h2>
		    </div>	
		    <div class="col-md-12">
		        <div class="box">
		        	 <table class="table table-bordered table-hover" id="products">
						<tr>
							<th>#</th>
				          	<th>Date</th>
				          	<th>Investor Name</th>
				          	<th>Investor Address</th>
				          	<th>Amount (ETH)</th>
				          	<th>Refunded</th>
						</tr>
			        	<c:set var="count" value="0" scope="page" />
						<c:forEach items="${investments}" var="investment">
							<c:set var="count" value="${count + 1}" scope="page"/>
							<tr>
								<td>${count}</td>
								
								<td><fmt:formatDate type = "both" value = "${investment.transactiondate }"/></td>
								<td>${investment.backer.fullname}</td>
								<td>${investment.backer.bcaddress}</td>
								<td>${investment.amount}</td>
								<td class="refundAmout${count}"><script>showRefundValue('<c:out value='${investment.refundstatus}'/>','<c:out value='${investment.refundamount}'/>', 'refundAmout' + '<c:out value='${count}'/>');</script></td>
							</tr>
						</c:forEach>
					</table>
		        	<span>Total : </span> <span id="logsCount">${count}</span>
		        </div>
		    </div>	
		    
		</div>
	</div> --%>
</body>
</html>
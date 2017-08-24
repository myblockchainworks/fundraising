<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
<title>Fund Raising DApp</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link href="<c:url value="/resources/css/bootstrap.min.css" />" rel="stylesheet">
<link href="<c:url value="/resources/css/style.css" />" rel="stylesheet">
<link href="https://fonts.googleapis.com/css?family=Ubuntu" rel="stylesheet">
<script src="<c:url value="/resources/js/jquery.min.js" />"></script>
<script src="<c:url value="/resources/js/bootstrap.min.js" />"></script>
<script src="<c:url value="/resources/js/funraising.js" />"></script>
</head>
<body>

	<h1>Fund Raising DApp</h1>
	
	<ul class="nav navbar-nav pull-right">
			<li style="padding-top: 15px;color: white;">Welcome ${currentUser.fullname}</li><li class=""><a href="#">|</a></li><li style="padding-top: 15px;color: white;">My Balance : ${myBalance} ETH</li><li class=""><a href="#">|</a></li><li class=""><a href="logout">Logout</a></li>
		</ul>
		<br><br>
		<div style="float:right;">
	    	<a href="investorhome" class="buttontype"> Back </a>
	  	</div>
	  	<br><br><br>
	  <form action="myFundInvestment" method="post">
	 
		<div id="newProductScreen" style="margin-left:25%;margin-right:25%">
		    <h2>Investment to Project</h2>
		    <br><label for="referenceNumber">Reference Number:</label><input type="text" id="referenceNumber" name="referenceNumber" readonly="readonly" value="${job.referenceNumber }"></input>
		    <br><label for="jobTitle">Title:</label><input type="text" id="jobTitle" name="jobTitle"  readonly="readonly" value="${job.title }"></input>
		    <br><label for="fundRequired">Fund Required:</label><input type="number" id="fundRequired" name="fundRequired"  readonly="readonly" value="${job.fundRequired }"></input>
		    <br><label for="description">Description:</label><input type="text" id="description" name="description" readonly="readonly" value="${job.description }"></input>
		    <br><label for="myfund">Fund:</label><input type="number" id="myfund" name="myfund" required placeholder="e.g., 100"></input>
		    <input type="hidden" id="creatorAddress" name="creatorAddress" value="${job.creator }"></input>
		    <br><br><button id="add" class="btn" type="submit">Invest</button> <a href="investorhome" class="buttontype">Back</a>
		    <span class="error">${param.errormsg}</span>
		  </div>
	   </form>
</body>
</html>
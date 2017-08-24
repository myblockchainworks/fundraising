<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
<title>Fund Raising - Register</title>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link href="<c:url value="/resources/css/bootstrap.min.css" />" rel="stylesheet">
<link href="<c:url value="/resources/css/style.css" />" rel="stylesheet">
<link href="<c:url value="/resources/css/font-awesome.min.css" />" rel="stylesheet">
<link href="https://fonts.googleapis.com/css?family=Open+Sans:300,400,600,700" rel="stylesheet">  
<script src="<c:url value="/resources/js/jquery.min.js" />"></script>
<script src="<c:url value="/resources/js/bootstrap.min.js" />"></script>
<script src="<c:url value="/resources/js/funraising.js" />"></script>
</head>
<body>

	<div class="center_form1">
      <div class="container">
        <div id="loginbox" style="margin-top: 10px;" class="mainbox col-md-6 col-md-offset-3 col-sm-8 col-sm-offset-2">
		<center>
			<img class="new_log" src="<c:url value="/resources/images/logo4.png" />" alt="">
		</center>
            <div class="panel panel-info" >
                 <div class="panel-heading">
                     <div class="panel-title">Registration</div>

                 </div>
                 <form action="registerUser" method="post">
                 <div class="form-group">

                    <div class="col-sm-12">
                        <div class="row">
                            <div class="col-sm-12">
                                <div class="new_form">
								    <label class="radio-inline">
								      <input type="radio" name="type" checked="checked" value="owner" onclick="hideInitialBalance();">Owner
								    </label>
								    <label class="radio-inline">
								      <input type="radio" name="type" value="investor" onclick="showInitialBalance();">Investor
								    </label>
								    <label class="radio-inline">
								      <input type="radio" name="type" value="vendor" onclick="hideInitialBalance();">Vendor
								    </label>
						     </div>
						  </div>
						</div>
			 	  	</div>
				</div>
				<br>
                    <div style="padding-top:30px" class="panel-body" >
						<div class="errorMessageDiv"><script>showErrorMessage('<c:out value='${param.errormsg}'/>')</script></div>
							<div class="form-horizontal">
                            	<div style="margin-bottom: 25px" class="input-group">
                                       <span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span>
                                       <input id="fullname" type="text" class="form-control" name="fullname" value="" placeholder="Fullname">
                                </div>
                                
                                <div style="margin-bottom: 25px" class="input-group">
                                       <span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span>
                                       <input id="username" type="text" class="form-control" name="username" value="" placeholder="Username">
                                </div>

                            	<div style="margin-bottom: 25px" class="input-group">
                                        <span class="input-group-addon"><i class="glyphicon glyphicon-lock"></i></span>
                                        <input id="password" type="password" class="form-control" name="password" placeholder="Password">
                                 </div>
                                 
                                 <div style="margin-bottom: 25px" class="input-group">
                                        <span class="input-group-addon"><i class="glyphicon glyphicon-lock"></i></span>
                                        <input id="confirmPassword" type="password" class="form-control" name="confirmPassword" placeholder="Confirm Password">
                                 </div>
                                 
                                 <div style="margin-bottom: 25px" class="input-group">
                                        <span class="input-group-addon"><i class="glyphicon glyphicon-earphone"></i></span>
                                        <input id="contactNumber" type="text" class="form-control" name="contactNumber" placeholder="Contact Number">
                                 </div>
                                 <div style="margin-bottom: 25px" class="input-group">
                                        <span class="input-group-addon"><i class="glyphicon glyphicon-globe"></i></span>
                                        <input id="email" type="text" class="form-control" name="email" placeholder="Email">
                                 </div>
                                 <div style="margin-bottom: 25px" class="input-group">
                                        <span class="input-group-addon"><i class="glyphicon glyphicon-map-marker"></i></span>
                                        <input id="address" type="text" class="form-control" name="address" placeholder="Address">
                                 </div>
                                  <div id="initialBalanceView" style="display: none;margin-bottom: 25px" class="input-group">
                                        <span class="input-group-addon"><i class="glyphicon glyphicon-bitcoin"></i></span>
                                        <input id="initialBalance" type="number" class="form-control" name="initialBalance" placeholder="Initial Balance">
                                 </div>
                                <div style="margin-top:10px" class="form-group">
                                    <!-- Button -->
                                    <div class="col-sm-12 controls">
										<button id="btn-login" type="submit" class="btn btn-success">Register</button>
										<button class="btn" style="background-color: gray;" id="hideAddProductScreen" type="reset">Reset</button> <a href="index" class="btn btn-info">Back</a>
                                    </div>
                                </div>

                                </div>
	                        </div>
	                        
                        </form>
                    </div>
        	</div>
    	</div>
	</div>
	
	  <%-- <form action="registerUser" method="post">
	 
		<div id="newProductScreen" style="margin-left:25%;margin-right:25%">
		    <h2>Registration</h2>
		    <br><label for="name">User Type:</label>  <input type="radio" name="type" value="owner" style="width:20px;" checked="checked" onclick="hideInitialBalance();"> <span style="font-size:15px;">Owner </span> <input type="radio" name="type" value="investor" style="width:20px;"  onclick="showInitialBalance();"> <span style="font-size:15px;">Investor</span>
		    <br><label for="fullname">Fullname:</label><input type="text" id="fullname" name="fullname" required placeholder="Fullname"></input>
		    <br><label for="username">Username:</label><input type="text" id="username" name="username" required placeholder="Username"></input>
		    <br><label for="password">Password:</label><input type="password" id="password" name="password" required placeholder="Password"></input>
		    <br><label for="confirmPassword">Confirm Password:</label><input type="password" id="confirmPassword" name="confirmPassword" required placeholder="Confirm Password"></input>
		    <br><label for="contactNumber">Contact Number:</label><input type="text" id="contactNumber" name="contactNumber" required placeholder="Contact Number"></input>
		    <br><label for="email">Email:</label><input type="text" id="email" name="email" placeholder="Email" required></input>
		    <br><label for="address">Address:</label><input type="text" id="address" name="address" placeholder="Address" required></input>
			<span id="initialBalanceView" style="display: none;"><br><label for="fullname">Initial Balance:</label><input type="number" id="initialBalance" name="intitalBalance" placeholder="e.g. 100" maxlength="4"  min="0" max="1000"></input></span>
			<div style="text-align: center">
				<br>
				<span class="error">${param.errormsg}</span>
				<br>
				<button id="registerUserBtn" class="btn" type="submit">Register</button> <button class="btn" style="background-color: gray;" id="hideAddProductScreen" type="reset">Reset</button> <a href="index" class="buttontype">Back</a>
		  	</div>
		  	
	    </div>
  	 </form> --%>
</body>
</html>
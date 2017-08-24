function formatDate(value, className) {
	var date = new Date(parseInt(value));
	var monthNames = [
	  "Jan", "Feb", "Mar",
	  "Apr", "May", "Jun", "Jul",
	  "Aug", "Sep", "Oct",
	  "Nov", "Dec"
	];
	
	var day = date.getDate();
	var monthIndex = date.getMonth();
	var year = date.getFullYear();
	
	var formatted = day + '-' + monthNames[monthIndex] + '-' + year;
	$('.' + className).html("Applied On: " + formatted); 
}

function formatAndDisplayDate(value, className) {
	if (value > 0) {
		var date = new Date(parseInt(value));
		var monthNames = [
		  "Jan", "Feb", "Mar",
		  "Apr", "May", "Jun", "Jul",
		  "Aug", "Sep", "Oct",
		  "Nov", "Dec"
		];
		
		var day = date.getDate();
		var monthIndex = date.getMonth();
		var year = date.getFullYear();
		
		var formatted = day + '-' + monthNames[monthIndex] + '-' + year;
		$('.' + className).html(formatted); 
	}
}


//Calculate the difference of two dates in total days
function diffDays(d1, d2)
{
  var ndays;
  var tv1 = d1.valueOf();  // msec since 1970
  var tv2 = d2.valueOf();

  ndays = (tv2 - tv1) / 1000 / 86400;
  ndays = Math.round(ndays - 0.5);
  return ndays;
}

function showDaysLeft(value, className) {
	var date = new Date(parseInt(value));
	var today = new Date();
	var daysLeft = diffDays(today, date);
	if (daysLeft > 1) {
		$('.' + className).html(daysLeft + " Days Left"); 
	} else if (daysLeft >= 0) {
		$('.' + className).html(daysLeft + " Day Left"); 
	} 
	
}

function showFunded(achievedAmount, fundRequired, className) {
	var percentage = achievedAmount / fundRequired * 100;
	
	$('.' + className).html(percentage.toFixed(2) + "% Funded"); 
}

function showFundedProgressbar(achievedAmount, fundRequired, className) {
	var percentage = achievedAmount / fundRequired * 100;
	
	var progressbar = '<div class="progress"> <div class="progress-bar" role="progressbar" aria-valuenow="' + percentage.toFixed(0) + '" aria-valuemin="0" aria-valuemax="100" style="width:' + percentage.toFixed(0) + '%"></div> </div>'
	$('.' + className).html(progressbar);
}

function showErrorMessage(message) {
	if (message != "") {
		$(".errorMessageDiv").html('<div id="login-alert" class="alert alert-danger col-sm-12">' + message + '</div>');
	}
}

function getMilestoneStatus(value, className) {
	var status = '';
    if (value == 0) {
      status = '<span class="created">CREATED</span>';
    } else if (value == 1) {
      status = '<span class="completed">COMPLETED</span>';
    } else if (value == 2) {
      status = '<span class="assigned">REVIEWED</span>';
    } else if (value == 3) {
      status = '<span class="refunded">PAYOUT</span>';
    }
    $('.' + className).html(status);
}

function getStatus(value, className) {
	var status = '';
    if (value == 0) {
      status = '<span class="created">CREATED</span>';
    } else if (value == 1) {
      status = '<span class="published">PUBLISHED</span>';
    } else if (value == 2) {
      status = '<span class="targetachieved">TARGET ACHIEVED</span>';
    } else if (value == 3) {
      status = '<span class="expired">EXPIRED</span>';
    } else if (value == 4) {
      status = '<span class="refunded">REFUNDED</span>';
    } else if (value == 5) {
      status = '<span class="assigned">VENDOR ASSIGNED</span>';
    } else if (value == 6) {
      status = '<span class="completed">COMPLETED</span>';
    }
    $('.' + className).html(status);
}

function showRefundVestment(status, achievedAmount, fundRequired, referenceNumber, className) {
	if (status == 3 && parseInt(achievedAmount) < parseInt(fundRequired)) {
		$('.' + className).html('<a href="refundInvestments?referenceNumber=' + referenceNumber + '" class="buttontype">Refund</a>');
	}
}

function showRefundValue(status, value, className) {
	if (status) {
		$('.' + className).html(value);
	}
}

function showForceRefundValueButton(id, referenceNumber, refundSatus, status, className) {
	if ((status == 1  || status == 2) && refundSatus == "false") {
		$('.' + className).html('<a href="forceRefundInvestments?id=' + id + '&referenceNumber=' + referenceNumber + '" class="forcebuttontype">Force Refund</a>');
	}
}

function setSelectedProject(referenceNumber, creator) {
	$('#referenceNumber').val(referenceNumber);
	$('#creatorAddress').val(creator);
}

function setSelectedProjectTitle(referenceNumber) {
	$('#referenceNumber').val(referenceNumber);
}

function showInvestButton(className, title, creator, status, amount) {
	if (status == 1) {
		var showButton = '<a href="#" class="buttontype" data-toggle="modal" class="btn btn-lg btn-info pull-right" data-target="#fund-modal" onclick="setSelectedProject(\'' + title + '\', \'' + creator + '\')">Add Funds</a>';
	    $('.' + className).html(showButton);
	}
}

function showButton(value, className, title, vendor) {
	var showButton = '';
    if (value == 0) {
	   showButton = '<a href="publishJob?referenceNumber=' + title + '" class="buttontype">Publish</a>';
    } else {
    	showButton = '<a href="viewproject?referenceNumber=' + title + '" class="buttontype">Fund Raised</a>';
    	if (value == 2) {
    		showButton +=  ' <a href="#" style="float:right" class="buttontype" data-toggle="modal" class="btn btn-lg btn-info pull-right" data-target="#assignvendor-modal" onclick="setSelectedProjectTitle(\'' + title + '\')">Assign Vendor</a>';
    	} else if (value == 5 || value == 6) {
    		showButton +=  ' <a href="manageproject?referenceNumber=' + title + '&vendor=' + vendor + '" style="float:right"  class="buttontype">Manage</a>'
        	
    	}
    } 
    $('.' + className).html(showButton);
}

function showMilestoneButton(status, referenceNumber, index, className) {
	var showButton = '';
	if (status == 0) {
		 showButton = '<a href="completeMilestone?indexId=' + index + '&referenceNumber=' + referenceNumber + '" class="buttontype" style="margin-top: -5px;font-size: 11px;padding: 5px;">Complete</a>';
	}
	
	$('.' + className).html(showButton);
}

function showMilestoneActionButton(status, referenceNumber, vendor, index, className) {
	var showButton = '';
	if (status == 1) {
		 showButton = '<a href="reviewMilestone?indexId=' + index + '&referenceNumber=' + referenceNumber + '&vendor=' + vendor + '" class="buttontype" style="margin-top: -5px;font-size: 11px;padding: 5px;">Review and Pay</a>';
	} else if (status == 2) {
		showButton = '<a href="payoutMilestone?indexId=' + index + '&referenceNumber=' + referenceNumber + '&vendor=' + vendor + '" class="buttontype" style="margin-top: -5px;font-size: 11px;padding: 5px;">Payout</a>';
	}
	
	$('.' + className).html(showButton);
}

function showVendorButton(className, title) {
	 $('.' + className).html('<a href="managemyproject?referenceNumber=' + title + '" class="buttontype">Manage</a>');
}

function showInitialBalance() {
	$('#initialBalanceView').show();
}

function hideInitialBalance() {
	$('#initialBalanceView').hide();
}

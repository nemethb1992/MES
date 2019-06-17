var path = location.pathname.split('/')[1];

function ApplicationCountDown()
{
	$(".countDownSpan").show();
	var IDLE_TIMEOUT = 28800; //seconds
	var _idleSecondsTimer = null;
	var _idleSecondsCounter = 0;

//	document.onclick = function() {
//	    _idleSecondsCounter = 0;
//	};
//
//	document.onmousemove = function() {
//	    _idleSecondsCounter = 0;
//	};
//
//	document.onkeypress = function() {
//	    _idleSecondsCounter = 0;
//	};

	_idleSecondsTimer = window.setInterval(CheckIdleTime, 1000);

	function CheckIdleTime() {
	     _idleSecondsCounter++;
//	     var panel = document.getElementById("counterSpan");
	    	 var timeLeft = IDLE_TIMEOUT - _idleSecondsCounter;
	     	showProgress(IDLE_TIMEOUT,timeLeft,".app-time-progress");
//	    	 panel.innerHTML = timeLeft + "";
	    if (_idleSecondsCounter >= IDLE_TIMEOUT) {
	        window.clearInterval(_idleSecondsTimer);
	        BackToTaskStart();
	    }
	}
}

function showProgress(max, actual, progressbar)
{
	var percent = (actual / max) * 1000;
 	$(progressbar).css("width", percent/10 + "%").attr("aria-valuenow", percent);
}

var getUrlParameter = function getUrlParameter(sParam) {
	var sPageURL = decodeURIComponent(window.location.search.substring(1)),
	sURLVariables = sPageURL.split('&'),
	sParameterName,
	i;

	for (i = 0; i < sURLVariables.length; i++) {
		sParameterName = sURLVariables[i].split('=');

		if (sParameterName[0] === sParam) {
			return sParameterName[1] === undefined ? true : sParameterName[1];
		}
	}
};

function loadingAnimation(element, name)
{
	$("."+name).remove();
$(element).append("<div class='"+name+" position-absolute' id='circle'><div class='loader'><div class='loader'><div class='loader'><div class='loader'></div></div></div></div></div>");	

}

function loadingAnimationStop(name)
{
	$("."+name).remove();
}


function setDateNow(element)
{
	setInterval(function(){

	    var currentTime = new Date();
	    var year = currentTime.getFullYear();
	    var rawMonth = currentTime.getMonth() + 1;
	    var month = (rawMonth < 10 ? "0"+rawMonth : rawMonth);
	    var rawDay = currentTime.getDate();
	    var day = (rawDay < 10 ? "0"+rawDay : rawDay);
	    var currentDate;
	    switch ($.cookie("language")) {
	    case "de":
	    	currentDate = day +". "+ month +". "+ year;
	        break;
	    case "en":
	    	currentDate = day +". "+ month +". "+ year;
	        break;
	    case "hu":
	    default:
	    	currentDate = year +". "+ month +". "+ day + ".";
	        break;
	    }
	   
	    $(element).val(currentDate);

	},1000);
}

function setTimeNow(element){

	setInterval(function(){

	    var currentTime = new Date();
	    var hours = currentTime.getHours();
	    var minutes = currentTime.getMinutes();
	    var seconds = currentTime.getSeconds();
	    
	    minutes = (minutes < 10 ? "0" : "") + minutes;
	    seconds = (seconds < 10 ? "0" : "") + seconds;
	    hours = (hours < 10 ? "0" : "") + hours;

	    var currentTimeString = hours + ":" + minutes + ":" + seconds;
	    $(element).val(currentTimeString);

	},1000);
}

function BackToTaskStart()
{
    document.location.href = "Logout";
}

function getUrlVars() {
    var vars = {};
    var parts = window.location.href.replace(/[?&]+([^=&]+)=([^&]*)/gi, function(m,key,value) {
        vars[key] = value;
    });
    return vars;
}

//
//var distance = IDLE_TIMEOUT - _idleSecondsCounter;
//var hours = Math.floor((distance % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
//var minutes = Math.floor((distance % (1000 * 60 * 60)) / (1000 * 60));
//var seconds = Math.floor((distance % (1000 * 60)) / 1000);
// panel.innerHTML = hours + ":" + minutes + ":" + seconds;
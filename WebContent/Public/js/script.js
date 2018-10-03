var path = location.pathname.split('/')[1];

function ApplicationCountDown()
{
	var IDLE_TIMEOUT = 1800; //seconds
	var _idleSecondsTimer = null;
	var _idleSecondsCounter = 0;

	document.onclick = function() {
	    _idleSecondsCounter = 0;
	};

	document.onmousemove = function() {
	    _idleSecondsCounter = 0;
	};

	document.onkeypress = function() {
	    _idleSecondsCounter = 0;
	};

	_idleSecondsTimer = window.setInterval(CheckIdleTime, 1000);

	function CheckIdleTime() {
	     _idleSecondsCounter++;
	     var panel = document.getElementById("counterSpan");
	     if (panel)
	    	 panel.innerHTML = (IDLE_TIMEOUT - _idleSecondsCounter) + "";
	    if (_idleSecondsCounter >= IDLE_TIMEOUT) {
	        window.clearInterval(_idleSecondsTimer);
	        document.location.href = "Home";
	    }
	}
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



//
//var distance = IDLE_TIMEOUT - _idleSecondsCounter;
//var hours = Math.floor((distance % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
//var minutes = Math.floor((distance % (1000 * 60 * 60)) / (1000 * 60));
//var seconds = Math.floor((distance % (1000 * 60)) / 1000);
// panel.innerHTML = hours + ":" + minutes + ":" + seconds;
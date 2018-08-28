//Projekt név
var path = location.pathname.split('/')[1];

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
function WS_Group(){
		var str = [];
		str = getUrlParameter('ws').split('!');
		return str[0];
}
function WS_No(){
	var str = [];
	str = getUrlParameter('ws').split('!');
	return str[1];
}
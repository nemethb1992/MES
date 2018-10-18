var path = location.pathname.split('/')[1];

$(document).ready(function(){
	var workstation = $(".workstation").val();
	if(workstation != "null")
	$.cookie('workstation', workstation, { expires: 365 });
})

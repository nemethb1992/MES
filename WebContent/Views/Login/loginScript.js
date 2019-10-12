var path = location.pathname.split('/')[1];

$(document).ready(function(){
	var workstation = $(".workstation").val();
	if(workstation != "null")
	$.cookie('workstation', workstation, { expires: 365 });
	passwordConfigure();
})

var showLength = 1;
var delay = 800;
var hideAll = setTimeout(function() {}, 0);

function passwordConfigure(){
	  $("#inp_pass").bind("paste keyup input", function() {
		    var offset = $("#inp_pass").val().length - $("#hidden").val().length;
		    if (offset > 0) $("#hidden").val($("#hidden").val() + $("#inp_pass").val().substring($("#hidden").val().length, $("#hidden").val().length + offset));
		    else if (offset < 0) $("#hidden").val($("#hidden").val().substring(0, $("#hidden").val().length + offset));

		    // Change the visible string
		    if ($(this).val().length > showLength) $(this).val($(this).val().substring(0, $(this).val().length - showLength).replace(/./g, "*") + $(this).val().substring($(this).val().length - showLength, $(this).val().length));

		    // Set the timer
		    clearTimeout(hideAll);
		    hideAll = setTimeout(function() {
		      $("#inp_pass").val($("#inp_pass").val().replace(/./g, "*"));
		    }, delay);
		  });
}

var viewState = false;


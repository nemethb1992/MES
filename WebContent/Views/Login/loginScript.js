var path = location.pathname.split('/')[1];

$(document).ready(function(){
	buttonEventHolderLogin();
	languageStartUp('1');
})

function buttonEventHolderLogin()
{	
	$('#inp_enterbutton').click(function(){
		loginEnter();
	})
}

function loginEnter()
{
	var layout;
	var str = [];
	var splitUrl;
	var ws_group = null;
	var ws_number = null;
	try {
		splitUrl = getUrlParameter('ws').split('!');
		ws_group = splitUrl[0];
		ws_number = splitUrl[1];
	}
	catch(err) {
	    document.getElementById("demo").innerHTML = err.message;
	}
	if(splitUrl.length > 0 && $("#LayoutSwitch").is(':checked'))
	{
		layout = 1;
	}	
	else
	{
		layout = 2
	}	

	
	$.ajax({
		url:  '/'+path+'/Dashboard',
		data: {
			ws_group: ws_group,
			ws_no: ws_number,
			username: $("#inp_username").val(),
			pass: $("#inp_pass").val(),
			layout: layout
		},
		success: function () {
		}
	});
}


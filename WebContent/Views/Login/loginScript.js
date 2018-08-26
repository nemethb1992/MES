var path = location.pathname.split('/')[1];

$(document).ready(function(){
	buttonEventHolderLogin();
	document.cookie = "language=hu";
	langIconFirst();
	Language_Startup($.cookie("language"),'1');
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
	if($("#LayoutSwitch").is(':checked'))
	{
		layout = 1;
	}
	else
	{
	layout = 2;
	}
	console.log(layout);
	$.ajax({
	    url:  '/MES/Dashboard',
	    data: {
	     username: $("#inp_username").val(),
	     pass: $("#inp_pass").val(),
	     layout: layout
	    },
	    success: function () {
//	    	  $( "#LR_form" ).submit();
//	    		  console.log(result);
	    	
	    }
	});
}


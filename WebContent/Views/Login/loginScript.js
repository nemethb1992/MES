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
	var ws_group = WS_Group();
	var ws_number = WS_No();
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
	    url:  '/'+path+'/Dashboard',
	    data: {
	     ws_group: ws_group,
	     ws_no: ws_number,
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


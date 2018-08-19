var path = location.pathname.split('/')[1];

$(document).ready(function(){
	buttonEventHolderLogin();
})

function buttonEventHolderLogin()
{	
	$('#inp_enterbutton').click(function(){
		loginEnter();
	})
}

function loginEnter()
{
	
	$.ajax({
	    url:  '/MES/Dashboard',
	    data: {
	     username: $("#inp_username").val(),
	     pass: $("#inp_pass").val()
	    },
	    success: function () {
//	    	  $( "#LR_form" ).submit();
//	    		  console.log(result);
	    	
	    }
	});
}


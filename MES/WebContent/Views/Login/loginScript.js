var path = location.pathname.split('/')[1];

$(document).ready(function(){
	buttonEventHolderLogin();
})

function buttonEventHolderLogin()
{
	$('#inp_enterbutton').click(function(){
//		loginEnter();
	})
}

function loginEnter()
{
	$.ajax({
	    url:  '/' + path +'/Login',
	    data: {
	    },
	    success: function () {
	    	$('#LR_form').submit();
	    }
	});
}
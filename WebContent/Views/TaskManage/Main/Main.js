var path = location.pathname.split('/')[1];

$(document).ready(function(){
	ButtonScriptElements();
	DisplayTime();
});

function ButtonScriptElements()
{
	$('#btn_select_1').click(function(){
		$('#btn_select_1').submit();
	});
	$('#btn_select_2').click(function(){
		$('#btn_select_2').submit();
	});

	$('#btn_select_3').click(function(){
		$('#btn_select_3').submit();
	});
}

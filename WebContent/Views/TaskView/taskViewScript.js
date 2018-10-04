var path = location.pathname.split('/')[1];

$(document).ready(function(){
	TabControlEventHolder();
	ApplicationCountDown();
	getView();
});

$(document).keypress(function(e) {
    if(e.which == 13) {
    	if($("#submit_input").is(":focus"))
    		$.ajax({
    		    url:  '/'+path+'/Submit',
    		    data:{
    		    	quantity: $('#submit_input').val()
    		    },
    		    success: function (respond) {
    		    	$("#submit_input").val("");
    		    	DataSheet_Clear();
    		    	DataSheet_Load();
    		    	headerNavBtnDeafult();
    		    }
    		});
    }
});

function getView(tab = 1)
{
	$( "#SwitchPanel" ).empty();
	loadingAnimation("#SwitchPanel");
	$.ajax({
	    url:  '/'+path+'/DataSheet',
	    data:{
	    	tabNo: tab
	    },
	    success: function (respond) {
	    	$( "#SwitchPanel" ).empty();
	  	  	$( "#SwitchPanel" ).append(respond);
			//TaskTimer();
	    }
	});
}



function openAsset(item)
{
	var value = $(item).attr('value');
	$.ajax({
	    url:  '/'+path+'/FileHandler',
	    data:{
	    	file: value
	    },
	    success: function (respond) {
	    	
	    }
	});
}



function TabControlEventHolder()
{
	$('.btn_logout').click(function(){
		
		$.ajax({url:  '/'+path+'/Home'});
	})
$('.btn_leftNavigation').click(function(){
	$('.btn_leftNavigation').css({'background-color':'', 'color':'','border':''});
	$(this).css({'background-color':'#f5f5f5','background-size':'24%','border-left':'3px solid #ff6666'});
});
$('#btn_lejelentes').click(function(){
	$('.btn_navHeader').css({'background-color':'', 'color':'','width':'','border':''});
	$(this).css({'width':'465px','border-right':'3px solid #ff6666'});
	$('#btn_megszakitas input').css({'display':'none'});
	$('#btn_lejelentes input').css({'display':'block'});
});
$('#btn_megszakitas').click(function(){
	$('.btn_navHeader').css({'background-color':'', 'color':'','width':'','border':''});
	$(this).css({'width':'465px','border-left':'3px solid #ff6666'});
	$('#btn_lejelentes input').css({'display':'none'});
	$('#btn_megszakitas input').css({'display':'block'});
});
$('#navigationContainer').click(headerNavBtnDeafult());

$('#btn_leftNav_1').click(function(){
	getView(1);
	headerNavBtnDeafult();
})
$('#btn_leftNav_2').click(function(){
	getView(2);
	headerNavBtnDeafult();
})
$('#btn_leftNav_3').click(function(){
	getView(3);
	headerNavBtnDeafult();
})
$('#btn_leftNav_4').click(function(){
	getView(4);
	headerNavBtnDeafult();
})

}
function headerNavBtnDeafult()
{
	$('.btn_navHeader').css({'background-color':'', 'color':'','width':'','border':''});
	$('#btn_lejelentes input').css({'display':'none'});
	$('#btn_megszakitas input').css({'display':'none'});
}

function TaskTimer(time)
{
	var preNow = new Date().getTime();
	var targetTime = preNow + time;

	var x = setInterval(function() {
		var now = new Date().getTime();
		var distance = targetTime - now;
		var hours = Math.floor((distance % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
		var minutes = Math.floor((distance % (1000 * 60 * 60)) / (1000 * 60));
		var seconds = Math.floor((distance % (1000 * 60)) / 1000);
		if(distance > 0)
		{
			$('.timerPanel').html(hours + ":" + minutes + ":" + seconds);
		}
		if (distance < 0) {
			clearInterval(x);
			$('#timerContainer').css({background:'#dc3545'});
			var downNow = new Date().getTime();
			var y = setInterval(function() {
				var now = new Date().getTime();
				var distance = now - downNow;
				var hours = Math.floor((distance % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
				var minutes = Math.floor((distance % (1000 * 60 * 60)) / (1000 * 60));
				var seconds = Math.floor((distance % (1000 * 60)) / 1000);
				$('.timerPanel').html(hours + ":" + minutes + ":" + seconds);
				console.log(hours + ":" + minutes + ":" + seconds);
//				if (distance < 0) {
//				clearInterval(y);  
//				}
			}, 1000)

		}
	}, 1000)

}


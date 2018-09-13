var path = location.pathname.split('/')[1];

$(document).ready(function(){
	TabControlEventHolder();
	languageStartUp('3');
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
function DataSheet_Load()
{
//	$.ajax({
//	    url:  '/'+path+'/DataSheet',
//	    success: function (respond) {
//	    	Data_Clear();
//	    	Data_Load(respond);
//			//TaskTimer();
//	    }
//	});
}
function Data_Load(data)
{
	  $( "#tab1_container" ).append(data[0]);
	  $( ".dokumentum-list" ).append(data[1]);
	  $( "#tab3_container" ).append(data[2]);
	  $( "#tab4_container" ).append(data[3]);
}
function Data_Clear()
{
	
	    	  $( "#tab1_container" ).empty();
	    	  $( ".dokumentum-list" ).empty();
	    	  $( ".darabjegyz-tbody" ).empty();
	    	  $( "#tab4_container" ).empty();
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
	$('.rightCont').hide();
	$('#tab1_container').show();
	headerNavBtnDeafult();
})
$('#btn_leftNav_2').click(function(){
	$('.rightCont').hide();
	$('#tab2_container').show();
	headerNavBtnDeafult();
})
$('#btn_leftNav_3').click(function(){
	$('.rightCont').hide();
	$('#tab3_container').show();
	headerNavBtnDeafult();
})
$('#btn_leftNav_4').click(function(){
	$('.rightCont').hide();
	$('#tab4_container').show();
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


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
    		    success: function (response) {
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
	    success: function (response) {
	    	$( "#SwitchPanel" ).empty();
	  	  	$( "#SwitchPanel" ).append(response);
	  	  setTimer();
	    }
	});
	
}

function setTimer()
{
	$.ajax({
	    url:  '/'+path+'/Timer',
	    success: function (response) {
	  	  	TaskTimer(response);
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
	    success: function (response) {
	    	
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

String.prototype.toHHMMSS = function () {
    var sec_num = parseInt(this, 10); // don't forget the second parm
    var hours = Math.floor(sec_num / 3600);
    var minutes = Math.floor((sec_num - (hours * 3600)) / 60);
    var seconds = sec_num - (hours * 3600) - (minutes * 60);

    if (hours < 10) {
        hours = "0" + hours;
    }
    if (minutes < 10) {
        minutes = "0" + minutes;
    }
    if (seconds < 10) {
        seconds = "0" + seconds;
    }
    var time = hours + ':' + minutes + ':' + seconds;
    return time;
}

function TaskTimer(time)
{
	var count = time.split('.')[0]; // it's 00:01:02
	var counter = setInterval(timer, 1000);
	function timer() {
	 if (parseInt(count) <= 0) {
	    clearInterval(counter);
	    return;
	 }
	 var temp = count.toHHMMSS();
	 count = (parseInt(count) - 1).toString();
	 $('.timerPanel').html(temp);
	}
}


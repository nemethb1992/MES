var path = location.pathname.split('/')[1];

$(document).ready(function(){
	TabControlEventHolder();
	ApplicationCountDown();
	getView();
	 DisplayTime();
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
			timerStart(response);
		}
	});

}

var counter = null;
function timerStart(time)
{
	if(counter == null)
	{
		var count = time.split('.')[0];
		counter = setInterval(timer, 1000);
		function timer() {
			if (parseInt(count) <= 0) {
				clearInterval(counter);
				timeUp();
				return;

			}
			var temp = count.toHHMMSS();
			count = (parseInt(count) - 1).toString();
			$('.timerPanel').html(temp);
		}
	}
};
var upcounter = null;
function timeUp()
{
	if(upcounter == null)
	{
		$("#timerContainer").css("background", "#dc3545");
		var count = "0";
		upcounter = setInterval(uptimer, 1000);
		function uptimer() {
			console.log("asd");
			var temp = count.toHHMMSS();
			count = (parseInt(count) + 1).toString();
			$('.timerPanel').html(temp);
		}
	}

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
	$('#navigationContainer').click(closeNavButtons());

	$('#btn_leftNav_1').click(function(){
		closeNavButtons();
		getView(1);
	})
	$('#btn_leftNav_2').click(function(){
		closeNavButtons();
		getView(2);
	})
	$('#btn_leftNav_3').click(function(){
		closeNavButtons();
		getView(3);
	})
	$('#btn_leftNav_4').click(function(){
		closeNavButtons();
		getView(4);
	})

	$('#btn_lejelentes').click(function(){
		if(opened != false)
		{
			console.log("l1");
			closeInterupt();
			openSubmit(this);
		}
		opened = true;
	});
	$('#btn_megszakitas').click(function(){
		if(opened != false)
		{
			console.log("r1");
			closeSubmit();
			openInterupt(this);
		}
		opened = true;
	});
	$('.btn_navHeader .lejelent-btn').click(function(){
		opened = false;
		console.log("l2");
		closeSubmit();
	});
	$('.btn_navHeader .megszak-btn').click(function(){
		opened = false;
		console.log("r2");
		closeInterupt();
	});
}

var opened = true;

function openInterupt(item)
{
	closeNavButtons();
	$(item).css({'width':'80%'});
	$('#btn_megszakitas p').css({'display':'none'});
	$('#btn_megszakitas .my-nav-container').css({'display':'block'});
	$('#btn_megszakitas').css({'background':'white'});
}
function openSubmit(item)
{
	closeNavButtons();
	$(item).css({'width':'80%'});
	$('#btn_lejelentes p').css({'display':'none'});
	$('#btn_lejelentes .my-nav-container').css({'display':'block'});
	$('#btn_lejelentes').css({'background':'white'});
}

function closeSubmit()
{
	$('#btn_lejelentes').css({'background':'', 'color':'','width':'','border':''});
	$('#btn_lejelentes .my-nav-container').css({'display':'none'});
	$('#btn_lejelentes p').css({'display':''});
}
function closeInterupt()
{
	$('#btn_megszakitas').css({'background':'', 'color':'','width':'','border':''});
	$('#btn_megszakitas .my-nav-container').css({'display':'none'});
	$('#btn_megszakitas p').css({'display':''});
}

function closeNavButtons()
{
	$('.btn_navHeader').css({'background':'', 'color':'','width':'','border':''});
	$('.btn_navHeader .my-nav-container').css({'display':'none'});
	$('.btn_navHeader p').css({'display':''});
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



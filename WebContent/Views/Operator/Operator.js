var path = location.pathname.split('/')[1];

$(document).ready(function(){
	TabControlEventHolder();
	ApplicationCountDown();
	getView();
	 DisplayTime();
});

function getView(tab = 1)
{
	$( "#SwitchPanel" ).empty();
	loadingAnimation("#SwitchPanel", "operator");
	$.post({
		url:  '/'+path+'/DataSheetLoader',
		data:{
			tabNo: tab
		},
		success: function (response) {
			loadingAnimationStop("operator");
			$( "#SwitchPanel" ).empty();
			$( "#SwitchPanel" ).append(response);
			if(response != "")
			{
				setTimer();
			}
			else
			{
				submit('interrupt-form');
			}

		}
	});
}

function setPageDefault()
{
	$(".timer-holder").append("");
}

function setTimer()
{
	$.post({
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
			var temp = count.toHHMMSS();
			count = (parseInt(count) + 1).toString();
			$('.timerPanel').html(temp);
		}
	}
}

function openAsset(item)
{
	var value = $(item).attr('value');
	$.post({
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
	
	$('.refresh-click').click(function(){
		RefreshTask();
	});
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
		closeSubmit();
	});
	$('.btn_navHeader .megszak-btn').click(function(){
		opened = false;
		closeInterupt();
	});
}

var opened = true;

function openInterupt(item)
{
	closeNavButtons();
	$(item).css({'width':'50%'});
	$('#btn_megszakitas p').css({'display':'none'});
	$('#btn_megszakitas .my-nav-container').css({'display':'block'});
	$('#btn_megszakitas').css({'background':'white'});
}

function openSubmit(item)
{
	closeNavButtons();
	$(item).css({'width':'50%'});
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


function InterruptTask()
{
	value = 1;
	
	$.post({
		url:  '/'+path+'/Suspend',
		data:{
			SuspendType: value
		},
		success: function () {
			$('.interrupt-form').submit();
//			getView();
		}
	});
}

function RefreshTask()
{	
	$.post({
	url:  '/'+path+'/RefreshDatas',
	success: function (response) {
		if( response == "null")
			{
			$('.refresh-form').submit();
			}else
				{
					getView();
				}
	}
});
}

function SubmitTask()
{
	var finished = $(".input-finished").val();
	var scrap = $(".input-scrap").val();

	var finishedQt = (finished == "" || finished == null ? 0 : finished);
	var scrapQt = (scrap == "" || scrap == null ? 0 : scrap);
	if(finishedQt == 0 && scrapQt == 0)
	{
		return;
	}
	$.post({
		url:  '/'+path+'/Submit',
		data:{
			finishedQty: finishedQt,
			scrapQty: scrapQt
		},
		success: function () {
			location.reload();
		}
	});
}


var path = location.pathname.split('/')[1];
var layoutState = 1;

$(document).ready(function(){
	pagesetup();
});

function pagesetup()
{
	TabControlEventHolder();
	ApplicationCountDown();
	getView();
	setTimer();
	setDateNow('.personal-date');
	setTimeNow('.personal-time');
}

function getView(tab = 1)
{
	closeNavButtons();
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
			$( "#SwitchPanel" ).append(response[0]);
			if(response != "")
			{
				if(response[1] == "interrupted"){
					$('#interrupt-level2').modal('show');
				}
			}
			else
			{
				submit('interrupt-form');
			}

		}
	});
}

function NavigationButtonClick(item)
{
	$(item).show();
	var tab = $(item).attr("id").split('-')[2];
	if(tab != layoutState){
		layoutState = tab;
		getView(tab);
	}
	else{
		return;
	}
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
var upcounter = null;
var way = true;
var isInRun = false;
function timerStart(time)
{
	way = true;
	$("#timerContainer").css({
		"background": "#00e68a",
		"background-image" : "url(Public/icons/timer.svg)",
		"background-size" : "20%",
		"background-repeat" : "no-repeat",
		"background-position" : "center",
		"background-position-y" : "25%"
	});
	var original = time.split('.')[0];
	var count = time.split('.')[0];
	clearInterval(counter);
	counter = setInterval(timer, 1000);
	function timer() {

		if (parseInt(count) <= 0) {
			way = false;
			clearInterval(counter);
			timeUp();
			return;
		}				
		if(way == true)
		{
				var temp = count.toHHMMSS();
				count = (parseInt(count) - 1).toString();
				$('.timerPanel').html(temp);
		}
	}
};

function timeUp()
{
	way = false;
	$("#timerContainer").css({
		"background": "#dc3545",
		"background-image" : "url(Public/icons/timer.svg)",
		"background-size" : "20%",
		"background-repeat" : "no-repeat",
		"background-position" : "center",
		"background-position-y" : "25%"
	});

	var count = "0";
	clearInterval(upcounter);
	upcounter = setInterval(uptimer, 1000);
	function uptimer() {
		if(way == false)
		{
			var temp = count.toHHMMSS();
			count = (parseInt(count) + 1).toString();
			$('.timerPanel').html(temp);
		}
		else{
			clearInterval(upcounter);
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
		$(this).css({'background-color':'#e6e6e6','background-size':'24%','border-right':'3px solid #ff6666'});
	});
	$('#navigationContainer').click(closeNavButtons());

	$('#btn_leftNav_1').click(function(){
		layoutState = 1;
		getView(layoutState);
	})
	$('#btn_leftNav_2').click(function(){
		layoutState = 2;
		getView(layoutState);
	})
	$('#btn_leftNav_3').click(function(){
		layoutState = 3;
		getView(layoutState);
	})
	$('#btn_leftNav_4').click(function(){
		layoutState = 4;
		getView(layoutState);
	})

	$('#btn_lejelentes').click(function(){
		if(opened != false)
		{
			openSubmit(this);
		}
		opened = true;
	});
	$('.btn_navHeader .lejelent-btn').click(function(){
		opened = false;
		closeSubmit();
	});
}

var opened = true;

function openInterupt(item)
{
	closeNavButtons();
}

function openSubmit(item)
{
	$(".btn_navHeader-left").hide();
	$(item).show();
	closeNavButtons();
	$(item).css({'width':'55%'});
	$('#btn_lejelentes p').css({'display':'none'});
	$('#btn_lejelentes .my-nav-container').css({'display':'block'});
	$('#btn_lejelentes').css({'background':'white'});
}

function closeSubmit()
{
	$(".btn_navHeader-left").show();
	$('#btn_lejelentes').css({'background':'', 'color':'','width':'','border':''});
	$('#btn_lejelentes .my-nav-container').css({'display':'none'});
	$('#btn_lejelentes p').css({'display':''});
}

function closeNavButtons()
{
	$(".btn_navHeader-left").show();
	$('.btn_navHeader').css({'background':'', 'color':'','width':'','border':''});
	$('.btn_navHeader .my-nav-container').css({'display':'none'});
	$('.btn_navHeader p').css({'display':''});
}

function bomListDropDown(item)
{
	var itemHeight = $(item).css("height").match(/\d+/);
	$(item).css({'height' : (itemHeight == 75 ? 'auto' : '75px'), 'background' : (itemHeight == 75 ? '#e4e4e4' : '#efefef')});

}

String.prototype.toHHMMSS = function () {
	var sec_num = parseInt(this, 10);
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
function textAreaAdjust(o) {
	  o.style.height = "1px";
	  o.style.height = (25+o.scrollHeight)+"px";
	}

function Cancel()
{
	$('#interrupt-level1').modal('hide');
}

function OpenInterruptModal()
{
	$('#interrupt-level1').modal({backdrop: 'static', keyboard: false});
	$('#interrupt-level1').modal('show');
}

function InterruptTask()
{
	$.post({
		url:  '/'+path+'/InterruptTask',
		success: function () {
//			$('.interrupt-form').submit();
		}
	});
	$('#interrupt-level1').modal('hide');
	$('#interrupt-level2').modal({backdrop: 'static', keyboard: false});
	$('#interrupt-level2').modal('show');
}
function SuspendTask()
{
	var uname = $(".username-input").val();
	var pwd = $(".password-input").val();
	console.log(uname);
	console.log(pwd);
	
	if(uname.length > 0 && pwd.length > 0)
	$.post({
		url:  '/'+path+'/SuspendTask',
		data:{
			username: uname, 
			password: pwd
			},
		success: function (response) {
			if(response == "true"){
				$('.interrupt-form').submit();
			}else
				{
				alert(response);
				}
		}
	});
}

function ResumeTask()
{
	$('#interrupt-level2').modal('hide');
	$.post({
		url:  '/'+path+'/ResumeTask',
		success: function () {
//			$('.interrupt-form').submit();
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
			getView(layoutState);
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
	console.log(finishedQt + "   " + scrapQt);
	if(finishedQt == 0 && scrapQt == 0)
	{
		return;
	}
	opened = false;
	closeSubmit();
	$.post({
		url:  '/'+path+'/Submit',
		data:{
			finishedQty: finishedQt,
			scrapQty: scrapQt
		},
		success: function (response) {
			if(response == "null")
			{
				getView();
				setTimer();
			}else{
				$(".refresh-form").submit();
			}
		}
	});
}


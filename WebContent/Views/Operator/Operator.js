var path = location.pathname.split('/')[1];
var jsessionID = location.pathname.split(';jsessionid=')[1];

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
$.urlParam = function(name){
	var results = new RegExp('[\?&]' + name + '=([^&#]*)').exec(window.location.href);
	return results[1] || 0;
}
function getView(tab = 1)
{
	closeNavButtons();
	$( "#SwitchPanel" ).empty();
	loadingAnimation("#SwitchPanel", "operator");
	$.post({
		url:  '<%=response.encodeURL(request.getContextPath()+"/DataSheetLoader")%>',
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
					OpenInterruptModal_2();
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
		url:  '<%=response.encodeURL(request.getContextPath()+"/Timer")%>',
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
		"background-image" : "url(${pageContext.request.contextPath}/Public/icons/timer.svg)",
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
		"background-image" : "url(${pageContext.request.contextPath}/Public/icons/timer.svg)",
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
	window.open(value,'_blank');
//	$.post({
//		url:  '<%=response.encodeURL(request.getContextPath()+"/FileHandler")%>',
//		data:{
//			file: value
//		},
//		success: function (response) {
//
//		}
//	});
}
function suspendListEvent(element){

	$(element).change(function() {
		$(".error-text").val($(element).val());
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
}


function openInterupt(item)
{
	closeNavButtons();
}

var closing = false;
function openSubmit(item)
{
	if(closing == true)
		{
		closing=false;
		return;
		}
	if($('#btn_lejelentes').width()<300){
		console.log("open");
		$('#btn_lejelentes').show();
		closeNavButtons();
			$(".btn_navHeader-left").hide();
		$('#btn_lejelentes').css({'display':'block','width':'55%'});
		$('#btn_lejelentes p').css({'display':'none'});
		$('#btn_lejelentes .my-nav-container').css({'display':'block'});
		$('#btn_lejelentes').css({'background':'white'});
	}

	

}

function closeSubmit()
{
	if($('#btn_lejelentes').width()>300){
		console.log("close");
		$(".btn_navHeader-left").show();
		$('#btn_lejelentes').css({'background':'', 'color':'','width':'','border':''});
		$('#btn_lejelentes .my-nav-container').css({'display':'none'});
		$('#btn_lejelentes p').css({'display':''});
		closing = true;
	}

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
	var parentitem = $(item).parents('.bom-item-row');
	var itemHeight = parentitem.css("height").match(/\d+/);
	parentitem.css({'height' : (itemHeight == 70 ? 'auto' : '70px'), 'background' : (itemHeight == 70 ? '#e4e4e4' : '#efefef')});
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

function SubmitConfirmationValidation(item){
	var open = parseInt($("#confirm-openqty").val(),10);
	var finished = parseInt($("#confirm-finished").val(),10);
	if(finished > open){
		$(item).css("color", "red");
	}
	else{
		$(item).css("color", "black");
	}
}




function CloseConfirmationModal()
{
	$('#submitcConfirmationModal').modal('hide'); 
	$(".input-finished").val(null);
	$(".input-scrap").val(null);
	closeSubmit();

	closing = false;
}

function ResumeTask()
{
	$('#interrupt-level2').modal('hide');
	$.post({
		url:  '<%=response.encodeURL(request.getContextPath()+"/ResumeTask")%>',
		success: function () {
//			$('.interrupt-form').submit();
		}
	});
}


function RefreshTask()
{	
	$.post({
	url:  '<%=response.encodeURL(request.getContextPath()+"/RefreshData")%>',
	success: function (response) {
		if( response == "null")
		{
			$('.refresh-form').submit();
		}else
		{
			location.reload();
		}
	}
	});
}

function SubmitTask()
{
	var finished = $(".confirm-finished").val();
	var scrap = $(".confirm-scrap").val();	
	
	console.log(finished+"-"+scrap);
	
	var finishedQt = (finished == "" || finished == null ? 0 : finished);
	var scrapQt = (scrap == "" || scrap == null ? 0 : scrap);
	if(finishedQt == 0 && scrapQt == 0)
	{
		return;
	}
	$.post({
		url:  '<%=response.encodeURL(request.getContextPath()+"/Submit")%>',
		data:{
			finishedQty: finishedQt,
			scrapQty: scrapQt
		},
		success: function (response) {
			if(response == "submit_done")
			{
//				location.reload();
				CloseConfirmationModal();
				getView();
				setTimer();
			}else if(response == "error"){
				alert("Lejelentesi hiba!");
			}else if(response == "exit"){
				$(".refresh-form").submit();
			}
		}
	});
}


function OpenSubmitConfirmationModal(item)
{ 
		
		var id = $(item).val();
		var finished = $(".input-finished").val();
		var scrap = $(".input-scrap").val();

		if(finished == "" && scrap == ""){
			return;
		}
		if(finished == 0 && scrap == 0){
			return;
		}
		
		$.post({
			url:  '<%=response.encodeURL(request.getContextPath()+"/SubmitConfirmationModal")%>',
			data: {
				TaskID: id,
				finishedQty: finished,
				scrapQty: scrap
			},
			success: function (response) {
				$("#submitcConfirmationModal").remove();
				document.body.innerHTML += response;
				$('#submitcConfirmationModal').modal("show");
			},
			error: function() {
			}  
		});
}


//New suspend scripts ##############################

function OpenInterruptModal()
{
	$('#interrupt-level1').modal({backdrop: 'static', keyboard: false});
	$('#interrupt-level1').modal('show');
}

function OpenInterruptModal_2()
{
	$('#interrupt-level2').modal({backdrop: 'static', keyboard: false});
	$('#interrupt-level2').modal('show');
}

function openSuspendModal(item){
	var id = $(item).val();
	$.post({
		url:  '<%=response.encodeURL(request.getContextPath()+"/TaskSuspendModal")%>',
		data: {
			TaskID: id,
			SuspendType: "operator",
		},
		success: function (response) {
			$("#SuspendModal").remove();
			document.body.innerHTML += response;
			$('#SuspendModal').modal("show");
		},
		error: function() {
		}  
	});
}

function SuspendTaskFromOperator(item)
{
	var text = $(".error-text").val();
	var id = $(item).val();
	
	if(text.length == 0)
	{
		return;
	}
	
	$.post({
		url:  '<%=response.encodeURL(request.getContextPath()+"/SuspendTask")%>',
		data:{
			secure: "false",
			errorText: text,
			taskId: id
			
			},
		success: function (response) {
			if(response == "true"){
				$('#SuspendModal').modal("hide");
				$('.interrupt-form').submit();
			}else
				{
				alert(response);
				}
		}
	});
}



function InterruptTask()
{
	var text = $(".error-text").val();
	if(text.length == 0)
		{
		return;
		}
	$.post({
		url:  '<%=response.encodeURL(request.getContextPath()+"/InterruptTask")%>',
		data:{
			errorText: text
		},
		success: function () {
			$(".error-text-back").html(text);
		}
	});
	$('#interrupt-level1').modal('hide');
	$('#interrupt-level2').modal({backdrop: 'static', keyboard: false});
	$('#interrupt-level2').modal('show');
}

function SuspendTask(authernticated = true)
{
	var text = $(".error-text").val();
	var uname = $(".username-input").val();
	var pwd = $(".password-input").val();
	
	if(text.length == 0)
	{
		return;
	}
	if(authernticated == true){
		if(uname.length == 0 || pwd.length == 0)
		{
			return;
		}
	}
	
	$.post({
		url:  '<%=response.encodeURL(request.getContextPath()+"/SuspendTask")%>',
		data:{
			secure: authernticated,
			username: uname, 
			errorText: text,
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


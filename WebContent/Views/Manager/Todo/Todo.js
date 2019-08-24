var SelectedStation = null;
var path = location.pathname.split('/')[1];

$(document).ready(function(){
	TaskManagerStartUp();
	FirstStationList();
	Sortlist(".station-list");
	
//    history.pushState(null, null, location.href);
//    window.onpopstate = function () {
//        history.go(1);
//    };
});



function TaskManagerStartUp()
{
	setToday();
	ApplicationCountDown();
	ButtonScriptElements();
	WorkStationItemCollect();
	setDateNow('.personal-date');
	setTimeNow('.personal-time');
	datepicker();
	
    $('[id^=detail-]').hide();
    $('.toggle').click(function() {
        $input = $( this );
        $target = $('#'+$input.attr('data-toggle'));
        $target.slideToggle();
    });
}

function suspendListEvent(element){

	$(element).change(function() {
		$(".error-text").val($(element).val());
	});
}
function ButtonScriptElements()
{
// $('.station-container').on("click", function(event){
// var target = event.target,
// index = $(target).index();
// console.log(target, index);
// });


	
	$(".date-refresh").click(function(){
		ListLoader();
	});

	$(".date-null").click(function(){
		$('.datepicker_own').val(null);
		ListLoader();
	});

	$('#btn_select_1').click(function(){
		$('#btn_select_1').submit();
	});

	$('.ts_searchInp').focusin(function(){
		$(this).css({'width':'55%','max-width':'200px'});
	});

	$('.ts_searchInp').focusout(function(){
		if($(this).val() == "")
		{
			$(this).css('width','');
		}
	});
	
	$('.AbasSearchImg').click(function(){
		$('.AbasSearchIn').focus();
	});

	$('.WSSearchImg').click(function(){
		$('.WSSearchIn').focus();
	});
}

function refreshButton(){
	$('#ListRefreshBtn').prop('disabled', true);
	$( ".ts_sumTime" ).val("0:00:00");
	$( ".station-container" ).empty();
	$( ".dndf1" ).empty();
	$( ".dndf2" ).empty();
	setToday(".datepicker_own");
	$(".station_label").val("<%=outputFormatter.getWord(DictionaryEntry.SELECT_A_WORKSTATION)%>");
	SessionStoreStation();
	FirstStationList();
}

function datepicker()
{
	$('.datepicker_own').datepicker({
		format: 'yyyy-mm-dd',
		autoclose: true,
		keyboardNavigation : true
	}).on('changeDate', function (ev) {
		ListLoader();
	});
}

var layoutState = 1;
function ModalDataSheetTabButtonClic(item)
{
	$(item).show();
	var id = $(item).val();
	var tab = $(item).attr("id").split('-')[2];
	if(tab != layoutState){
		layoutState = tab;
		getModalDataSheetView(tab, id);
	}
	else{
		return;
	}
}

function getModalDataSheetView(tab = 1, id)
{
	$( "#SwitchPanel" ).empty();
	loadingAnimation("#ModalSwitchPanel", "operator");
	$.post({
		url:  '<%=response.encodeURL(request.getContextPath()+"/DataSheetLoader")%>',
		data:{
			tabNo: tab,
			taskId: id
		},
		success: function (response) {
			loadingAnimationStop("operator");
			$( "#ModalSwitchPanel" ).empty();
			$( "#ModalSwitchPanel" ).append(response[0]);
		}
	});
}

function openSuspendModal(item){
	var id = $(item).val();
	$.post({
		url:  '<%=response.encodeURL(request.getContextPath()+"/TaskSuspendModal")%>',
		data: {
			TaskID: id,
			SuspendType: "manager"
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

function openDataSheetModal(item){
	var id = $(item).val();
	$.post({
		url:  '<%=response.encodeURL(request.getContextPath()+"/TaskDataSheetModal")%>',
		data: {
			TaskID: id,
			SuspendType: "manager",
		},
		success: function (response) {
			$("#DataSheetModal").remove();
			document.body.innerHTML += response;
			$('#DataSheetModal').modal("show");
			getModalDataSheetView(1,id);
		},
		error: function() {
		}  
	});
}
function UnsuspendTaskFromManager(item)
{
	var id = $(item).val();

	$(item).prop('disabled', true);
	$.post({
		url:  '<%=response.encodeURL(request.getContextPath()+"/UnsuspendTask")%>',
		data:{
			TaskID: id
			},
		success: function (response) {
			if(response == "true"){
				ListLoader();
			}else
				{
				alert(response);
				$(item).prop('disabled', false);
				}
		}
	});
}

function SuspendTaskFromManager(item)
{
	var text = $(".error-text").val();
	var id = $(item).val();
	
	if(text.length == 0)
	{
		return;
	}
	$(item).prop('disabled', true);
	
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
				ListLoader();
			}else
				{
				alert(response);
				$(item).prop('disabled', false);
				}
		}
	});
}

//function Cancel()
//{
//	$('#interrupt-level1').modal('hide');
//}

function ListLoader()
{
	workstationListLoader();
	var date = $(".datepicker_own").val();
	if(date != null && date != "" && date != "undefinied")
	{
		date = date.split('-')[0] + date.split('-')[1] + date.split('-')[2];
	}
	else
	{
		date = null;
	}
	$( ".dndf1" ).empty();
	loadingAnimation('.sortContDiv_ListHolder', 'loading');
	$.post({
		url:  '<%=response.encodeURL(request.getContextPath()+"/AbasTaskList")%>',
		data: {
			date: date
		},
		success: function (respond) {

			loadingAnimationStop('loading');
			$( ".dndf1" ).empty();
			$( ".dndf1" ).append(respond);
		},
		error: function() {
			$( ".dndf1" ).empty();
		}  
	});
}



function workstationListLoader()
{
		$( ".dndf2" ).empty();
		$( ".ts_sumTime" ).val("0:00:00");
		$.post({
			url:  '<%=response.encodeURL(request.getContextPath()+"/StationTaskList")%>',
			success: function (response) {

				$( ".dndf2" ).empty();
				$( ".dndf2" ).append(response[0]);
				$( ".ts_sumTime" ).val(response[1]);
			},
			error: function() {
				$( ".dndf2" ).empty();
			}  
		});
		return true;
}

function SessionStoreStation(station)
{
	// Tárolja a kiválasztott állomás nevét egy session változóban.
	$.post({
		url:  '<%=response.encodeURL(request.getContextPath()+"/StoreSelectedStation")%>',
		data: {
			station: station,
		},
		success: function (respond) {
		}
	});
}

function setToday()
{

	var now = new Date();
	// plusz 2 hét
	now.setDate(now.getDate() + 14);

	var day = ("0" + now.getDate()).slice(-2);

	var month = ("0" + (now.getMonth() + 1)).slice(-2);

	var today = now.getFullYear()+"-"+(month)+"-"+(day) ;

	$('.datepicker_own').val(today);
}

function WorkStationItemCollect()
{
	var list = [];
	$('.ws-list-holder .dnd-upper .dnd-in1').each(function(){
		var value = $(this).val();
		list.push(value);
	})
	if(list.length == 0)
	{
// $('.ws-list-holder .dnd-frame').append("<p class='appended-text'>Üres
// lista</p>");
	}
	else
	{
		$('.appended-text').remove();
	}
}

function FirstStationList()
{
	$.post({
		url:  '<%=response.encodeURL(request.getContextPath()+"/WorkstationControl")%>',
		success: function (view) {
// $( ".dndf1" ).empty();
// $( ".dndf2" ).empty();
			$( ".station-container" ).empty();
			$( ".station-container" ).append(view);
			level = 0;
		}
	});
}

function StationItemSelect(item, level)
{
	$( ".station-container" ).empty();
	var value = $(item).attr("value");
	$.post({
		url:  '<%=response.encodeURL(request.getContextPath()+"/WorkstationControl")%>',
		data: {
			element: value,
			level: level
		},
		success: function (view) {
			$( ".station-container" ).append(view);
			var asd = $('.station-container').children().size();
			alert(asd);
		}
	});
}

function clickOnStation(item)
{	
	var station = $(item).attr("value");
	$('#ListRefreshBtn').prop('disabled', false);
	SessionStoreStation(station);	
	SelectedStation = station;
	$(this).css({'background-image':'url(Public/icons/computerSignRed.svg)','background-color' : '#ebebeb','border-left':'5px solid #ff6666'});
	$(".station_label").val(station.split("!")[2] + "  (" + station.split("!")[0] +"-"+ station.split("!")[1]+")");
	ListLoader();
}
function isEmpty( el ){
    return !$.trim(el.html())
}
function PushToStation(item)
{
	var current = $(item).parents('.abas-list-item').children('.workSlipId').val();
	var targeted = null;
	if(!isEmpty($('.station-list'))){
		targeted = $('.station-list-item').last().children('.workSlipId').val();
	}
	AddTask(current, targeted);
}


function SaveStationList(){
	console.log("SaveStationList");
	var taskIds = [];
    $(".station-list").children().each(function() {
    	taskIds.push($(this).attr('value'));
   });
	console.log(taskIds);
	$.post({
		url:  '<%=response.encodeURL(request.getContextPath()+"/SaveStationTaskList")%>',
		data: {
			StationTaskList: taskIds
		},
		success: function () {
			workstationListLoader();

		}
	});
}

function Sortlist(element){
    $(element).sortable();
    $(element).disableSelection();
    $(element).droppable({
        drop: function(event, ui){
        	console.log("Dropped");
        	setTimeout(SaveStationList, 200);
           }

    });
}

//
//function MoveTaskDown(item)
//{
//	var task = $(item).parents('.dnd-container');
//	
//	var index = task.index();
//	if(index == StationListCount()-1)
//		{
//			return;
//		}
//	
//	var currentItemValue = task.children('.workSlipId').val();
//	var nextListItem = task.next().children('.workSlipId').val();
//	MoveTask(currentItemValue,nextListItem);
//}

//function MoveTask(current,targeted,next = null){
//	
//	$.post({
//		url:  '<%=response.encodeURL(request.getContextPath()+"/ScheduleTask")%>',
//		data: {
//			currentId: current,
//			targetId: targeted,
//			nextId: next
//		},
//		success: function () {
//			workstationListLoader();
//		}
//	});
//}

//function MoveTaskUp(item)
//{	
//	var task = $(item).parents('.dnd-container');
//	var targeted, current, next;
//	var index = task.index();
//	
//	if(index == 0)
//	{
//		return;
//	}
//	else{
//		if(index == 1)
//		{
//			targeted = null;
//		}
//		else
//		{
//			targeted = task.prev().prev().children('.workSlipId').val();
//		}
//		next = task.prev().children('.workSlipId').val();
//		current = task.children('.workSlipId').val();
//		MoveTask(current,targeted,next);
//	}
//}

function AddTask(current,targeted){
	
	$.post({
		url:  '<%=response.encodeURL(request.getContextPath()+"/ScheduleTask")%>',
		data: {
			currentId: current,
			targetId: targeted
		},
		success: function () {
			ListLoader();
		}
	});
}

function RemoveFromStation(item)
{
	var value = $(item).parents('.dnd-container').children('.workSlipId').val();
	$.post({
		url:  '<%=response.encodeURL(request.getContextPath()+"/UnScheduleTask")%>',
		data: {
			workSlipId: value,
		},
		success: function () {
			ListLoader();
		} 
	});
}

function StationListCount()
{
	return $('.station-list .dnd-container').last().index()+1;
}

function TodoListCount()
{
	return $('.abas-list .dnd-container').last().index()+1;
}
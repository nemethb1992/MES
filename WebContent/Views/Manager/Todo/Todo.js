var SelectedStation = null;
var path = location.pathname.split('/')[1];
var locale = "<%=outputFormatter.getLocale().toString()%>";
$(document).ready(function(){
	TaskManagerStartUp();
	FirstStationList();
	
    history.pushState(null, null, location.href);
    window.onpopstate = function () {
        history.go(1);
    };
	
	
	
	//for test
//	clickOnStation("<div class='workstation-container col-12 px-0' value='234PG!1!Szerel' onclick='clickOnStation(this)'></div>");
});



function TaskManagerStartUp()
{
	Sortlist();
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
	$(".station_label").val("<%=outputFormatter.getWord(DictionaryEntry.SELECT_A_WORKSTATION)%>");
	datepicker();
	SessionStoreStation();
	FirstStationList();
}

function datepicker()
{

	setToday();
	if(locale == "de"){
		formatDate = 'dd-mm-yyyy';
	}else{
		formatDate = 'yyyy-mm-dd';
	}
	$('.datepicker_own').datepicker({
		format: formatDate,
		autoclose: true,
		weekStart: 1,
		language: locale,
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
//	if(tab != layoutState){
//		layoutState = tab;
		getModalDataSheetView(tab, id);
//	}
//	else{
//		return;
//	}
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
			$('.frame-container').append(response);
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
			$('.frame-container').append(response);
//			document.body.innerHTML += response;
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
	abasListLodaer();
}

function abasListLodaer(isMoving = false)
{
	var date = $(".datepicker_own").val();
	if(locale == "de"){
		date = date.split('-')[2]+"-"+date.split('-')[1]+"-"+date.split('-')[0];
	}
	if(date != null && date != "" && date != "undefinied")
	{
		date = date.split('-')[0] + date.split('-')[1] + date.split('-')[2];
	}
	else
	{
		date = null;
	}
	if(isMoving == false){
		$( ".dndf1" ).empty();
	}
	if(isMoving == false){
		loadingAnimation('.sortContDiv_ListHolder', 'loading');
	}
	$.post({
		url:  '<%=response.encodeURL(request.getContextPath()+"/AbasTaskList")%>',
		data: {
			date: date,
			ws: SelectedStation
		},
		success: function (respond) {
			if(isMoving == false){
				loadingAnimationStop('loading');
			}
			$( ".dndf1" ).empty();
			$( ".dndf1" ).append(respond);
		},
		error: function() {
			$( ".dndf1" ).empty();
		}  
	});
}

function workstationListLoader(isMoving = false)
{
		if(isMoving == false){
			$( ".dndf2" ).empty();
		}
		$( ".ts_sumTime" ).val("0:00:00");
		$.post({		
			data: {
				ws: SelectedStation
			},
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

	if(locale == "de"){
		var today = (day)+"-"+(month)+"-"+now.getFullYear();
	}else{
		var today = now.getFullYear()+"-"+(month)+"-"+(day);
	}

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
			$( ".station-container" ).empty();
			$( ".station-container" ).append(view);
		}
	});
}

function clickOnStation(item)
{	
	var station = $(item).attr("value");
	$('#ListRefreshBtn').prop('disabled', false);
//	SessionStoreStation(station);	
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


//function SaveStationList(){
//	console.log("SaveStationList");
//	var taskIds = [];
//    $(".station-list").children().each(function() {
//    	taskIds.push($(this).attr('value'));
//   });
//	console.log(taskIds);
//	$.post({
//		url:  '<%=response.encodeURL(request.getContextPath()+"/SaveStationTaskList")%>',
//		data: {
//			StationTaskList: taskIds
//		},
//		success: function () {
//			workstationListLoader();
//
//		}
//	});
//}

function MoveTask(current,targeted,next = null){
	
	$.post({
		url:  '<%=response.encodeURL(request.getContextPath()+"/ScheduleTask")%>',
		data: {
			currentId: current,
			targetId: targeted,
			nextId: next
		},
		success: function () {
			workstationListLoader(true);
			abasListLodaer(true);
		}
	});
}

function Sortlist(){
	
	var draggedElement;
	var draggedIndex;
	var targetElement;

	new Sortable(abaslist, {
	    group: {
	    name: 'shared',
	    put: false },
	    handle: '.drag',
	    sort: false,
	    animation: 150,
		onMove: function (evt, originalEvent) {
			draggedElement = evt.dragged;
			
		},
	});

	new Sortable(stationlist, {
	    group: 'shared',
	    handle: '.drag',
	    animation: 150,		
	    onMove: function (evt, originalEvent) {
	    	draggedElement = evt.dragged;
		},
		onChange: function(/**Event*/evt) {
			draggedIndex = evt.newIndex;
			if(draggedIndex != 0){
				targetElement = $('#stationlist li').get(draggedIndex-1);
			}else{
				targetElement = null;
			}
		},
	    onSort: function (evt) {
	    	$(draggedElement).children().remove();
	    	$(draggedElement).append("<h4 class='text-center p-4'><%=outputFormatter.getWord(DictionaryEntry.SORT_PROGRESS)%></h4>");
	    	console.log($(targetElement).attr('value'));
	    	console.log($(draggedElement).attr('value'));
	    	console.log(draggedIndex);
	    	MoveTask($(draggedElement).attr('value'),$(targetElement).attr('value'));
		}

	});


}
//    $(".sort-list").sortable({
//
//    	
//    	
//  	  group: '.sort-list',
//  	 handle: 'div.drag',
////     revert: true,
//  	 cancel: ".station-list-table div div div div div table tbody tr td",
//  	  onDragStart: function ($item, container, _super) {
//  	    var offset = $item.offset(),
//  	        pointer = container.rootGroup.pointer;
//
//  	    adjustment = {
//  	      left: pointer.left - offset.left,
//  	      top: pointer.top - offset.top
//  	    };
//
//  	    _super($item, container);
//  	  },
//  	  onDrag: function ($item, position) {
//  	    $item.css({
//  	      left: position.left - adjustment.left,
//  	      top: position.top - adjustment.top
//  	    });
//  	  }
//    });
//    $(".abas-list").sortable({
//    	  onDragStart: function ($item, container, _super) {
//    		    // Duplicate items of the no drop area
//    		    if(!container.options.drop)
//    		      $item.clone().insertAfter($item);
//    		    _super($item, container);
//    		  },
//    	 cancel: ".station-list-table div div div div div table tbody tr td"
//      });    
//    $(".station-list").sortable({
//
//    	 cancel: ".station-list-table div div div div div table tbody tr td"
//      });
////    $(".sort-list").disableSelection();
//    $(".station-list").droppable({
//        drop: function(event, ui){
//        	setTimeout(SaveStationList, 500);
//           }
//
//    });

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
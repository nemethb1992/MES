var SelectedStation = null;
var path = location.pathname.split('/')[1];
var level = 0;

$(document).ready(function(){
	TaskManagerStartUp();
	FirstStationList();
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
}

function ButtonScriptElements()
{
//	$('.station-container').on("click", function(event){
//		  var target = event.target,
//		      index = $(target).index();
//		  console.log(target, index);
//	});
	
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

	$('.refresh_btn').click(function(){
		$( ".ts_sumTime" ).val("0:00:00");
		setToday(".datepicker_own");
		$(".station_label").val("<%=outputFormatter.getWord(DictionaryEntry.SELECT_A_WORKSTATION)%>");
		SessionStoreStation();
		FirstStationList();
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

function ListLoader()
{
	SessionStoreStation(SelectedStation);

	if(level > 1)
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
			loadingAnimation('.abas-list-holder', 'proba');
			$.post({
				url:  '/'+path+'/AbasTaskList',
				data: {
					date: date
				},
				success: function (respond) {
					
					loadingAnimationStop('proba');
					$( ".dndf1" ).empty();
					$( ".dndf1" ).append(respond);
				},
				error: function() {
					$( ".dndf1" ).empty();
				}  
			});
	}
}



function workstationListLoader()
{
	if(level > 1)
	{
		$( ".dndf2" ).empty();
		$( ".ts_sumTime" ).val("0:00:00");
		$.post({
			url:  '/'+path+'/StationTaskList',
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
}

function SessionStoreStation(station)
{
	// Tárolja a kiválasztott állomás nevét egy session változóban.
	$.post({
		url:  '/'+path+'/StoreSelectedStation',
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
//		$('.ws-list-holder .dnd-frame').append("<p class='appended-text'>Üres lista</p>");
	}
	else
	{
		$('.appended-text').remove();
	}
}

function FirstStationList()
{
	$.post({
		url:  '/'+path+'/WorkstationControl',
		success: function (view) {
			$( ".dndf1" ).empty();
			$( ".dndf2" ).empty();
			$( ".station-container" ).empty();
			$( ".station-container" ).append(view);
			level = 0;
		}
	});
}

function StationItemSelect(item)
{
	$( ".station-container" ).empty();
	if(level < 3)
		level++;
	var value = $(item).attr("value");
	$.post({
		url:  '/'+path+'/WorkstationControl',
		data: {
			element: value,
			level: level
		},
		success: function (view) {
			$( ".station-container" ).append(view);
		}
	});
}

function clickOnStation(item)
{	
	var station = $(item).attr("value");
	SessionStoreStation(station);	
	SelectedStation = station;
	$(this).css({'background-image':'url(Public/icons/computerSignRed.svg)','background-color' : '#ebebeb','border-left':'5px solid #ff6666'});
	$(".station_label").val(station.split("!")[2] + "  (" + station.split("!")[0] +"-"+ station.split("!")[1]+")");
	ListLoader();
}

function PushToStation(item)
{
	var currentItemValue = $(item).parents('.dnd-container').children('.workSlipId').val();
	var lastItemValue = $('.dnd-container').last().children('.workSlipId').val();
	AddTask(currentItemValue, lastItemValue);
}

function MoveTaskUp(item)
{	
	var task = $(item).parents('.dnd-container');
	var targeted, current, next;
	var index = task.index();
	
	if(index == 0)
	{
		return;
	}
	else{
		if(index == 1)
		{
			targeted = null;
		}
		else
		{
			targeted = task.prev().prev().children('.workSlipId').val();
		}
		next = task.prev().children('.workSlipId').val();
		current = task.children('.workSlipId').val();
		MoveTask(current,targeted,next);
	}
}

function MoveTaskDown(item)
{
	var task = $(item).parents('.dnd-container');
	
	var index = task.index();
	if(index == StationListCount()-1)
		{
			return;
		}
	
	var currentItemValue = task.children('.workSlipId').val();
	var nextListItem = task.next().children('.workSlipId').val();
	MoveTask(currentItemValue,nextListItem);
}

function MoveTask(current,targeted,next = null){
	
	$.post({
		url:  '/'+path+'/ScheduleTask',
		data: {
			currentId: current,
			targetId: targeted,
			nextId: next
		},
		success: function () {
			workstationListLoader();
		}
	});
}

function AddTask(current,targeted){
	
	$.post({
		url:  '/'+path+'/ScheduleTask',
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
		url:  '/'+path+'/UnScheduleTask',
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
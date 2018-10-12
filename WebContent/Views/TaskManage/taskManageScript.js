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
	$('#TM_Select_container_activity').show();
	DisplayTime();
	datepicker();
}
function datepicker()
{
	$('.datepicker_own').datepicker({
		format: 'yyyy-mm-dd',
		autoclose: true,
		keyboardNavigation : true
//		language: 'hu'
	}).on('changeDate', function (ev) {
		abasListLoader();
	});
}
function abasListLoader()
{
	if(level > 1)
	{
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
		loadingAnimation('.abas-list-holder');
		$.ajax({
			url:  '/'+path+'/AbasTaskList',
			data: {
				date: date
			},
			success: function (respond) {

				$( ".dndf1" ).empty();
				$( ".dndf1" ).append(respond);

			},
			error: function() {
				$( ".dndf1" ).empty();
			}  
		});
	}

}
function SessionStoreStation(station)
{
	// Tárolja a kiválasztott állomás nevét egy session változóban.
	$.ajax({
		url:  '/'+path+'/StoreSelectedStation',
		data: {
			station: station,
		},
		success: function (respond) {
		}
	});
}
function AddToList(item)
{
	var value = $(item).attr("value");
	$.ajax({
		url:  '/'+path+'/AddToList',
		data: {
			value: value
		},
		success: function (respond) {
			console.log(respond);

		}
	});
}
function RemoveFromList(item)
{
	var value = $(item).attr("value");
	$.ajax({
		url:  '/'+path+'/RemoveFromList',
		data: {
			value: value
		},
		success: function (respond) {
			console.log(respond);
		}
	});
}



//function TaskSizeSwitch(item)
//{
//var height = $(item).height();
//if(height < 100)
//{
//$('.dnd-container').animate({height:'60px'}, 120)
//$('.dnd-downer').hide();
//$(item).animate({height:'150px'}, 120)
//$(item).find('.dnd-downer').show();
//}
//else{
//$(item).animate({height:'50px'}, 120)
//$(item).find('.dnd-downer').hide();
//}


//}
function setToday()
{

	var now = new Date();

	var day = ("0" + now.getDate()).slice(-2);

	var month = ("0" + (now.getMonth() + 1)).slice(-2);

	var today = now.getFullYear()+"-"+(month)+"-"+(day) ;

	$('.datepicker_own').val(today);
}

function ButtonScriptElements()
{
	$(".date-refresh").click(function(){
		abasListLoader();
	});

	$(".date-null").click(function(){
		$('.datepicker_own').val(null);
		abasListLoader();
	});

	$('#btn_select_1').click(function(){
		FirstStationList();
		$('.select-panel').hide();
		$('#TM_Select_container1').css("display", "flex");
	});

	$('.refresh_btn').click(function(){
		$(".dndf1").empty();
		$(".dndf2").empty();
		setToday(".datepicker_own");
		SessionStoreStation();
		FirstStationList();
	});

	$('#btn_select_2').click(function(){
		$('.select-panel').hide();
		$('#TM_Select_container2').show();
	});

	$('#btn_select_3').click(function(){
		$('.select-panel').hide();
		$('#TM_Select_container3').show();
	});

	$('.TM_backBtn').click(function(){
		$('.select-panel').hide();
		$('#TM_Select_container_activity').show();
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


//Data control scripts #################

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
	$.ajax({
		url:  '/'+path+'/WorkstationControl',
		success: function (view) {
//			$( ".dndf1" ).empty();
			$( ".dndf2" ).empty();
			$(".station_label").val("");
			$( ".station-container" ).empty();
			$( ".station-container" ).append(view);
			level = 0;
		}
	});
}

function StationItemSelect(item)
{
	if(level < 3)
		level++;
	var value = $(item).attr("value");
	$.ajax({
		url:  '/'+path+'/WorkstationControl',
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

	$(this).css({'background-image':'url(Public/icons/computerSignRed.svg)','background-color' : '#ebebeb','border-left':'5px solid #ff6666'});
	var station = $(item).attr("value");
	$(".station_label").val(station.split("!")[2] + "  (" + station.split("!")[0] +"-"+ station.split("!")[1]+")");
	SelectedStation = station;
	SessionStoreStation(station);
	abasListLoader();

}


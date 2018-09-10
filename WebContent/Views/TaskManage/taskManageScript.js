var SelectedStation = null;
var path = location.pathname.split('/')[1];
$(document).ready(function(){
	TM_startUp();
	ButtonScriptElements();
	dnd_sortlist_scripts();
	collect_list_ws();
	langIconFirst();
	Language_Startup($.cookie("language"),'2');
});



function PC_Select(item)
{
	var pc = $(item).attr("value");
	$.ajax({
	    url:  '/'+path+'/StationPC',
	    data: {
	    pc_name: pc
	    },
	    success: function (respond) {
	    	//TODO Oszlopfrissítések
	    	  $( ".tmts_stationContainer" ).empty();
	    	  $( ".tmts_stationContainer" ).append(respond);

	    }
	});
}
function Group_Select(item)
{
	var group = $(item).attr("value");
	$.ajax({
	    url: '/'+path+'/StationGroup',
	    data: {
	    group_item: group
	    },
	    success: function (respond) {
	    	  $( ".tmts_stationContainer" ).empty();
	    	  $( ".tmts_stationContainer" ).append(respond);
	    }
	});
}
function Station_Select(item)
{
	  $( ".dndf2" ).empty();
	  $( ".dndf1" ).empty();
	var station = $(item).attr("value");
	$(".station_label").val(station);
//	$.ajax({
//	    url:  '/MES/StationTaskList',
//	    data: {
//	     station: station,
//	    },
//	    success: function (respond) {
//	    	  $( ".dndf2" ).append(respond);
//	    	
//	    }
//	});
	SelectedStation = station;
	$.ajax({
	    url:  '/'+path+'/AbasTaskList',
	    data: {
	     station: station,
	    },
	    success: function (respond) {
	    	  $( ".dndf1" ).append(respond);
	    	
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
function TM_startUp()
{
$('#TM_Select_container_activity').show();
}
function BuildUp()
{		
	$.ajax({
    url:  '/'+path+'/BuildUp',
    success: function (respond) {
    	  $( ".tmts_stationContainer" ).empty();
//    	  $( ".dndf1" ).empty();
//    	  $( ".dndf2" ).empty();
    	  $(".station_label").val("");
    	  $( ".tmts_stationContainer" ).append(respond[0]);
    }
});
	}
//function TaskSizeSwitch(item)
//{
//		var height = $(item).height();
//		if(height < 100)
//		{
//		$('.dnd-container').animate({height:'60px'}, 120)
//		$('.dnd-downer').hide();
//		$(item).animate({height:'150px'}, 120)
//		$(item).find('.dnd-downer').show();
//		}
//		else{
//			$(item).animate({height:'50px'}, 120)
//			$(item).find('.dnd-downer').hide();
//		}
//		
//	
//}
function ButtonScriptElements()
{
	$(".refresh_abaslist_btn").click(function(){
		var date =$(".datepicker_own").val();
		date = date.split('-')[0] + date.split('-')[1] + date.split('-')[2];
    	$( ".dndf1" ).empty();
		$.ajax({
		    url:  '/'+path+'/AbasTaskList',
		    data: {
		     station: SelectedStation,
		     date: date
		    },
		    success: function (respond) {
		    	  $( ".dndf1" ).append(respond);
		    	
		    }
		});
	})
	$('#btn_select_1').click(function(){
		BuildUp();
		$('.select-panel').hide();
		$('#TM_Select_container1').css("display", "flex");
	});
	
	$('.refresh_btn').click(function(){
		BuildUp();
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

	$('.tmts_stationBtnDivCont').click(function(){
		$('.tmts_stationBtnDivCont').css({'background-image':'','background-color' : '','border-left':''})
		$(this).css({'background-image':'url(Public/icons/computerSignRed.svg)','background-color' : '#ebebeb','border-left':'5px solid #ff6666'})
		var AllomasNev = $(this).children('.si1').val();
		$('.ts_wsNameInp').val(AllomasNev);
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
function dnd_sortlist_scripts()
{
//	$('.dndf1, .dndf2, #stationVisionHolder').sortable({
//		connectWith: ".dndf1, .dndf2",
//		stop: function(){
//			collect_list_ws();
//		}
//	});
//	$('.dndf1, .dndf2, #stationVisionHolder').disableSelection();
	

}

// Data control scripts #################

function collect_list_ws()
{
	var list = [];
	$('.ws-list-holder .dnd-upper .dnd-in1').each(function(){
		var value = $(this).val();
		list.push(value);
	})
	if(list.length == 0)
		{
//			$('.ws-list-holder .dnd-frame').append("<p class='appended-text'>Üres lista</p>");
		}
	else
		{
			$('.appended-text').remove();
		}
	console.log(list);
}

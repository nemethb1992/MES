
$(document).ready(function(){
	TM_startUp();
	ButtonScriptElements();
	dnd_sortlist_scripts();
	collect_list_ws();
	
});
function Station_Select(item)
{
	var station = $(item).attr("value");
	$.ajax({
	    url:  '/MES/Dashboard',
	    data: {
	     station: station,
	     pass: $("#inp_pass").val()
	    },
	    success: function () {
	    	
	    }
	});
}
function TM_startUp()
{
$('#TM_Select_container_activity').show();
//	for(i=0;i<5;i++)
//	{
//		$('.tmts_stationContainer').append("<div class='tmts_stationBtnDivCont'><input disabled class='si1'value='Station name "+i+"'><input disabled class='si3' value='Feladatok: 12'></div>");
//	}
		
		$.ajax({
		    url:  '/MES/BuildUp',
		    success: function (respond) {
		    	
		    	  $( ".tmts_stationContainer" ).append(respond[0]);
		    	  $( ".dndf1" ).append(respond[1]);
		    	  $( ".dndf2" ).append(respond[2]);
//		    		  console.log(result);
		    	
		    }
		});
}
function ButtonScriptElements()
{
	$('#btn_select_1').click(function(){
		$('.TM_content_layer').hide();
		$('#TM_Select_container1').show();
	});
	$('#btn_select_2').click(function(){
		$('.TM_content_layer').hide();
		$('#TM_Select_container2').show();
	});
	$('#btn_select_3').click(function(){
		$('.TM_content_layer').hide();
		$('#TM_Select_container3').show();
	});
	$('.TM_backBtn').click(function(){
		$('.TM_content_layer').hide();
		$('#TM_Select_container_activity').show();
	});

	$('.tmts_stationBtnDivCont').click(function(){
		$('.tmts_stationBtnDivCont').css({'background-image':'','background-color' : '','border-left':''})
		$(this).css({'background-image':'url(/MES/Public/icons/computerSignRed.svg)','background-color' : '#ebebeb','border-left':'5px solid #ff6666'})
		var AllomasNev = $(this).children('.si1').val();
		$('.ts_wsNameInp').val(AllomasNev);
	});
	$('.ts_searchInp').focusin(function(){
		$(this).css({'width':'70%','max-width':'200px'});
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
	$('.dndf1, .dndf2, #stationVisionHolder').sortable({
		connectWith: ".dndf1, .dndf2",
		stop: function(){
			collect_list_ws();
		}
	});
	$('.dndf1, .dndf2, #stationVisionHolder').disableSelection();
	
	$('.dnd-container').click(function(){
		var height = $(this).height();
		if(height < 150)
		{
		$('.dnd-container').animate({height:'50px'}, 120)
		$(this).animate({height:'150px'}, 120)
		$(this).find('.dnd-downer').show();
		}
		else{
			$(this).animate({height:'50px'}, 120)
			$(this).find('.dnd-downer').hide();
		}
			
	})
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
			$('.ws-list-holder .dnd-frame').append("<p class='appended-text'>Üres lista</p>");
		}
	else
		{
			$('.appended-text').remove();
		}
	console.log(list);
}

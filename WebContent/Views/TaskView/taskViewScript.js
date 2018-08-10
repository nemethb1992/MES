$(document).ready(function(){
	TV_startUp();
	TabControlEventHolder();
	DataSheet_Load();
});
$(document).keypress(function(e) {
    if(e.which == 13) {
    	if($("#submit_input").is(":focus"))
    		$.ajax({
    		    url:  '/MES/Submit',
    		    data:{
    		    	quantity: $('#submit_input').val()
    		    },
    		    success: function (respond) {
    		    	$("#submit_input").val("");
    		    	DataSheet_Clear();
    		    	DataSheet_Load();
    		    	headerNavBtnDeafult();
    		    }
    		});
    }
});
function DataSheet_Load()
{
	
//	$.ajax({
//	    url:  '/MES/DataSheet',
//	    success: function (respond) {
//	    	
//	    	  $( "#tab1_container" ).append(respond[0]);
//	    	  $( "#tab2_container" ).append(respond[1]);
//	    	  $( "#tab3_container" ).append(respond[2]);
//	    	  $( "#tab4_container" ).append(respond[3]);
//	    		  console.log(respond[4]);
//	    	
//	    }
//	});
}
function DataSheet_Clear()
{
	
	    	  $( "#tab1_container" ).empty();
	    	  $( "#tab2_container" ).empty();
	    	  $( "#tab3_container" ).empty();
	    	  $( "#tab4_container" ).empty();
//	    		  console.log(result);
}
function TV_startUp()
{
	$('#btn_leftNav_1').css({'background-color':'#f5f5f5','background-size':'24%','border-left':'3px solid #ff6666'});
	$('#tab1_container').css('display','block');
}
function TabControlEventHolder()
{
	$('.btn_logout').click(function(){
		$.ajax({url:  '/MES/Home'});
	})
$('.btn_leftNavigation').click(function(){
	$('.btn_leftNavigation').css({'background-color':'', 'color':'','border':''});
	$(this).css({'background-color':'#f5f5f5','background-size':'24%','border-left':'3px solid #ff6666'});
});
$('#btn_lejelentes').click(function(){
	$('.btn_navHeader').css({'background-color':'', 'color':'','width':'','border':''});
	$(this).css({'width':'465px','border-right':'3px solid #ff6666'});
	$('#btn_megszakitas input').css({'display':'none'});
	$('#btn_lejelentes input').css({'display':'block'});
});
$('#btn_megszakitas').click(function(){
	$('.btn_navHeader').css({'background-color':'', 'color':'','width':'','border':''});
	$(this).css({'width':'465px','border-left':'3px solid #ff6666'});
	$('#btn_lejelentes input').css({'display':'none'});
	$('#btn_megszakitas input').css({'display':'block'});
});
$('#navigationContainer').click(headerNavBtnDeafult());
$('#btn_leftNav_1').click(function(){
	$('.rightCont').hide();
	$('#tab1_container').show();
	headerNavBtnDeafult();
})
$('#btn_leftNav_2').click(function(){
	$('.rightCont').hide();
	$('#tab2_container').show();
	headerNavBtnDeafult();
})
$('#btn_leftNav_3').click(function(){
	$('.rightCont').hide();
	$('#tab3_container').show();
	headerNavBtnDeafult();
})
$('#btn_leftNav_4').click(function(){
	$('.rightCont').hide();
	$('#tab4_container').show();
	headerNavBtnDeafult();
})

}
function headerNavBtnDeafult()
{
	$('.btn_navHeader').css({'background-color':'', 'color':'','width':'','border':''});
	$('#btn_lejelentes input').css({'display':'none'});
	$('#btn_megszakitas input').css({'display':'none'});
}




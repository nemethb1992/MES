$(document).ready(function(){
	TV_startUp();
	TabControlEventHolder();
});
function TV_startUp()
{
	$('#btn_leftNav_1').css({'background-color':'#f5f5f5','background-size':'24%','border-left':'3px solid #ff6666'});
	$('#tab1_container').css('display','block');
}
function TabControlEventHolder()
{
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




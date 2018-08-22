$(document).ready(function(){
	langDivSlide();
});

function Language_Setter(lng)
{
	var ln;
    var cookielng = $.cookie("language");
	var val = $(lng).attr("value");
    var langSrc = new XMLHttpRequest();
    
    
    langSrc.open('GET', '/MES/Public/json/langsrc.json');
    langSrc.onload = function () {
    var langData = JSON.parse(langSrc.responseText);
    
	switch(val[0]) {
    case 'e':
    	ln = "en";
    	document.cookie = "language=en";
        break;
    case 'd':
    	ln = "de";
    	document.cookie = "language=de";
        break;
    case 'h':
    	ln = "hu";
    	document.cookie = "language=hu";
        break;
    default:
    	ln = "en";
	document.cookie = "language=en";
    break;
	}
	switch(val[1]) {
    case '1':
        Language_Login(langData,ln);
        break;
    case '2':
    	Language_Manager(langData,ln);
        break;
    case '3':
    	Language_Operator(langData,ln);
        break;
    default:
        break;
    }
    };
    langSrc.send();
}

function Language_Startup(ln,page)
{
    var langSrc = new XMLHttpRequest();
    langSrc.open('GET', '/MES/Public/json/langsrc.json');
    langSrc.onload = function () {
    var langData = JSON.parse(langSrc.responseText);
	switch(page) {
    case '1':
        Language_Login(langData,ln);
        break;
    case '2':
    	Language_Manager(langData,ln);
        break;
    case '3':
    	Language_Operator(langData,ln);
        break;
    default:
        break;
    }
};
langSrc.send();
}
function Language_Login(data,ln)
{
        $("#inp_username").attr("placeholder", data[0][ln]);
        $("#inp_pass").attr("placeholder", data[1][ln]);
        $("#inp_enterbutton").val(data[2][ln]);
        $("#login_title").html(data[3][ln]);

}
function Language_Manager(data,ln)
{
	console.log(data.length)
    $("#activity_tab1").html(data[50][ln]);
    $("#activity_tab2").html(data[51][ln]);
    $("#activity_tab3").html(data[43][ln]);
}
function Language_Operator(data,ln)
{
    
    $("#p_megszakitas").html(data[53][ln]);
    $("#p_lejelentes").html(data[13][ln]);
    
    $("#btn_leftNav_1").html(data[52][ln]);
    $("#btn_leftNav_2").html(data[23][ln]);
    $("#btn_leftNav_3").html(data[24][ln]);
    $("#btn_leftNav_4").html(data[25][ln]);
}

function langDivSlide()
{
    $(".lang_bub").hover(function () {
        $(".lang_bub").css('position', 'relative');
    });
    $(".lang_bub").mouseleave(function () {
        $(".lang_bub").css('position', 'absolute');
    });
    $(".lang_bub").click(function () {
        $(".lang_bub").css('z-index', '0');
        $(this).css('z-index', '9999');
    });
}
function langIconFirst()
{
    var langType = $.cookie("language");
    switch (langType) {
        case "hu":
            $(".lang_bub").css('z-index','0');
            $("#HU").css('z-index','9999');
            break;
        case "en":
            $(".lang_bub").css('z-index','0');
            $("#EN").css('z-index','9999');
            break;
        case "de":
            $(".lang_bub").css('z-index','0');
            $("#DE").css('z-index','9999');
            break;
    }
}
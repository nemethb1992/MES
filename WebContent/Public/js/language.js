
var path = location.pathname.split('/')[1];
var ln;
var langData;

$(document).ready(function(){
	langDivSlide();
});

function Language_Setter(lng)
{
	var val = $(lng).attr("value");
    var langSrc = new XMLHttpRequest();
    
    
    langSrc.open('GET', '/'+path+'/Public/json/langsrc.json');
    langSrc.onload = function () {
    	var langData = JSON.parse(langSrc.responseText);

    	switch(val[0]) {
    	case 'e':
    	{
    		ln = "en";
    		break;
    	}
    	case 'd':
    	{
    		ln = "de";
    		break;
    	}
    	case 'h':
    	{
    		ln = "hu";
    		break;
    	}
    	default:
    		ln = "en";
    	break;
    	}
    	$.cookie('language', ln, { expires: 365 });
    	LanguageSetOnServlet(ln);
    	location.reload();
    	switch(val[1]) {
    	case '1':
    		Language_Login();
    		break;
    	case '2':
    		Language_Manager();
    		break;
    	case '3':
    		Language_Operator();
    		break;
    	default:
    		break;
    	}
    };
    langSrc.send();
}
function LanguageSetOnServlet(lng)
{
	$.ajax({
	    url:  '/'+path+'/LanguageSetter',
	    data: { 
	     language: lng },
	    success: function () {
	    }
	});	
}
function languageStartUp(page)
{
	ln = $.cookie("language");
	if(ln == null || ln == "")
		{
		ln = "hu";
		}
    var langSrc = new XMLHttpRequest();
    langSrc.open('GET', '/'+path+'/Public/json/langsrc.json');
    langSrc.onload = function () {
    langData = JSON.parse(langSrc.responseText);
	switch(page) {
    case '1':
        Language_Login();
        break;
    case '2':
    	Language_Manager();
        break;
    case '3':
    	Language_Operator();
        break;
    default:
        break;
    }
	languageSwitchButton();
	LanguageSetOnServlet(ln)
};
langSrc.send();
}
function Language_Login()
{
        $("#inp_username").attr("placeholder", langData[0][ln]);
        $("#inp_pass").attr("placeholder", langData[1][ln]);
        $("#inp_enterbutton").val(langData[2][ln]);
        $("#login_title").html(langData[3][ln]);

}
function Language_Manager()
{
    $("#activity_tab1").html(langData[50][ln]);
    $("#activity_tab2").html(langData[51][ln]);
    $("#activity_tab3").html(langData[43][ln]);
}
function Language_Operator()
{
    
    $("#p_megszakitas").html(langData[53][ln]);
    $("#p_lejelentes").html(langData[13][ln]);
    $(".nav-btn-1").text(langData[52][ln]);
    $(".nav-btn-2").text(langData[23][ln]);
    $(".nav-btn-3").text(langData[24][ln]);
    $(".nav-btn-4").text(langData[25][ln]);
	DataSheet_Load();
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
function languageSwitchButton()
{
    switch (ln) {
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
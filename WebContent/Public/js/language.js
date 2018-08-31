
var path = location.pathname.split('/')[1];

$(document).ready(function(){
	langDivSlide();
	LanguageSetOnServlet("hu");
});

function Language_Setter(lng)
{
	var ln = "";
    var cookielng = $.cookie("language");
	var val = $(lng).attr("value");
    var langSrc = new XMLHttpRequest();
    
    
    langSrc.open('GET', '/'+path+'/Public/json/langsrc.json');
    langSrc.onload = function () {
    var langData = JSON.parse(langSrc.responseText);

	switch(val[0]) {
    case 'e':
    {
    	ln = "en";
    	document.cookie = "language="+ln;
    	LanguageSetOnServlet(ln);
        break;
    }
    case 'd':
    {
    	ln = "de";
    	document.cookie = "language="+ln;
    	LanguageSetOnServlet(ln);
        break;
    }
    case 'h':
	{
    	ln = "hu";
    	document.cookie = "language="+ln;
    	LanguageSetOnServlet(ln);
        break;
    }
    default:
    	ln = "en";
	document.cookie = "language="+ln;
	LanguageSetOnServlet(ln);
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
function Language_Startup(ln,page)
{
    var langSrc = new XMLHttpRequest();
    langSrc.open('GET', '/'+path+'/Public/json/langsrc.json');
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
    $("#activity_tab1").html(data[50][ln]);
    $("#activity_tab2").html(data[51][ln]);
    $("#activity_tab3").html(data[43][ln]);
}
function Language_Operator(data,ln)
{
    
    $("#p_megszakitas").html(data[53][ln]);
    $("#p_lejelentes").html(data[13][ln]);
    
    $(".nav-btn-1").text(data[52][ln]);
    $(".nav-btn-2").text(data[23][ln]);
    $(".nav-btn-3").text(data[24][ln]);
    $(".nav-btn-4").text(data[25][ln]);
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
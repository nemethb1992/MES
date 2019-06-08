var path = location.pathname.split('/')[1];

$(document).ready(function(){
	languageSwitchButton();
});

//function LaguageSetup()
//{
//	var language = $.cookie("language");
//	if(language == null || language == "")
//		{
//		var userLang = navigator.language;
//		language = userLang.split('-')[0];
//			if(language != "hu" && language != "en" && language != "de")
//				{
//					language = "hu";
//				}
//		}
//	LanguageSetOnServlet(language);
//}

function selectLanguage(button)
{
	console.log("+");
	var language = $(button).attr("id");
	if($.cookie("language") != language)
	{
		$.cookie('language', language, { expires: 365 });
		LanguageSetOnServlet(language);
		location.reload();
	}
}

function LanguageSetOnServlet(lng)
{
	$.post({
	    url:  '<%=response.encodeURL(request.getContextPath()+"/LanguageSetter")%>',
	    data: { 
	     language: lng },
	    success: function () {
	    }
	});	
}
var openstate = false;
function languageSwitchButton()
{
    switch ($.cookie("language")) {
    case "hu":
        $(".lang_bub").css('z-index','0');
        $("#hu").css('z-index','9999');
        break;
    case "en":
        $(".lang_bub").css('z-index','0');
        $("#en").css('z-index','9999');
        break;
    case "de":
        $(".lang_bub").css('z-index','0');
        $("#de").css('z-index','9999');
        break;
    }
    $(".lang_bub").hover(function () {
    	if(!openstate)
    	{
    		$(".lang_bub").css('position', 'relative');
    		openstate = true;
    	}
    	else{
    		$(".lang_bub").css('z-index', '0');
    		$(this).css('z-index', '9999');
    		$(".lang_bub").css('position', 'absolute');
    		openstate = false;
    	}
    });
}
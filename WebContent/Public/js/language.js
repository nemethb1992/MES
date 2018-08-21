$(document).ready(function(){
	langDivSlide();
});

function Language_Setter(lng)
{
	var ln;
	var val = $(lng).attr("value");
    var langSrc = new XMLHttpRequest();
    
    langSrc.open('GET', '/MES/Public/json/langsource.json');
    langSrc.onload = function () {
    var langData = JSON.parse(langSrc.responseText);
    
	switch(val[0]) {
    case 'e':
    	ln = "en";
        break;
    case 'd':
    	ln = "de";
        break;
    case 'h':
    	ln = "hu";
        break;
    default:
    	ln = "en";
    break;
	}
    console.log(langData[0][ln]);
	switch(val[1]) {
    case '1':
        Language_Login(langData);
        break;
    case '2':
    	Language_Manager(langData);
        break;
    case '3':
    	Language_Operator(langData);
        break;
    default:
        break;
    }
    };
    langSrc.send();
    function Language_Login(data)
    {
            $("#inp_username").attr("placeholder", data[0][ln]);
            $("#inp_pass").attr("placeholder", data[1][ln]);
            $("#inp_enterbutton").val(data[2][ln]);login_title
            $("#login_title").html(data[3][ln]);

    }
    function Language_Manager(data)
    {
            $("#test").html(data[0][ln]);
        
    }
    function Language_Operator(data)
    {
        $("#test").html(data[0][ln]);
    }
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
    var langType = $.cookie("lng");
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
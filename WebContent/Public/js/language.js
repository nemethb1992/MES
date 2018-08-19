

function language_set()
{
    var Data = new XMLHttpRequest();
    langSrc.open('GET', 'Public/json/langsource.json');
    var Detail = JSON.parse(Data.responseText);
    
    function Language_Login()
    {
        Data.onload = function () {
            $("#test").html(Detail[0][type]);
        }
        Data.send();
    }
    function Language_Manager()
    {
    	Data.onload = function () {
            $("#test").html(Detail[0][type]);
        }
    	Data.send();
    }
    function Language_Operator()
    {
    	Data.onload = function () {
            $("#test").html(Detail[0][type]);
        }
    	Data.send();
    }
    
}

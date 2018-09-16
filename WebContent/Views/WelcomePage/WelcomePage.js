/**
 * 
 */
function goToPage(page)
{
	var link = '${pageContext.request.contextPath}' + $(page).attr("value");
	
	console.log(link);
	location.href = link;
}

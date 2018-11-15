/**
 * 
 */
function goToPage(page)
{
	var link = '${pageContext.request.contextPath}' + $(page).attr("value");
	location.href = link;
}

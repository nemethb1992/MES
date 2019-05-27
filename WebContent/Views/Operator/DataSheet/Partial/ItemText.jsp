<%@page import="phoenix.mes.content.utility.OutputFormatter"%>
<%@page import="phoenix.mes.content.utility.OutputFormatter.DictionaryEntry"%>
<%@page import="phoenix.mes.abas.Task"%>
<%
	OutputFormatter of = (OutputFormatter)session.getAttribute("OutputFormatter");
	Task.Details td = (Task.Details)request.getAttribute("taskDetails");
%>

<div class='row h-100 m-0 px-0 py-3'>
	<div class='mr-2 mb-0 col col-textarea light-shadow'>
		<p class='h5 p-2'><%=of.getWord(DictionaryEntry.INFO_ARTICLE)%> 1</p>
		<textarea disabled class='p-3 BigTextInput'><%=td.getSalesOrderItemText()%></textarea>
	</div>
	<div class='ml-2 mb-0 col col-textarea light-shadow'>
		<p class='h5 p-2'><%=of.getWord(DictionaryEntry.INFO_ARTICLE)%> 2</p>
		<textarea disabled class='p-3 BigTextInput'><%=td.getSalesOrderItemText2()%></textarea>
	</div>
</div>
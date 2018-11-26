<%@page import="phoenix.mes.content.OutputFormatter"%>
<%@page import="phoenix.mes.content.OutputFormatter.DictionaryEntry"%>
<%@page import="phoenix.mes.abas.Task"%>
<%@page import="java.util.List"%>
<%
	OutputFormatter of = (OutputFormatter)session.getAttribute("OutputFormatter");
	Task.Details taskDetails = (Task.Details)request.getAttribute("taskDetails");
%>

<div class='row h-100 m-0 px-0 py-3'>
	<div class='mr-2 mb-0 col col-textarea light-shadow'>
		<p class='h5 p-2'><%=of.getWord(DictionaryEntry.INFO_ARTICLE)%>
			1
		</p>
		<textarea disabled class='p-3 BigTextInput'><%=taskDetails.getSalesOrderItemText()%></textarea>
	</div>
	<div class='ml-2 mb-0 col col-textarea light-shadow'>
		<p class='h5 p-2'><%=of.getWord(DictionaryEntry.INFO_ARTICLE)%>
			2
		</p>
		<textarea disabled class='p-3 BigTextInput'><%=taskDetails.getSalesOrderItemText2()%></textarea>
	</div>
</div>
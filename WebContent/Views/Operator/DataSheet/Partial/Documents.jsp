<%@page import="phoenix.mes.content.utility.OutputFormatter"%>
<%@page import="phoenix.mes.content.utility.OutputFormatter.DictionaryEntry"%>
<%
	OutputFormatter of = (OutputFormatter) session.getAttribute("OutputFormatter");
%>


<div class='list-group row dokumentum-list'>
	<button type='button'
		class='list-group-item list-group-item-action active disabled'><h4 class='mb-0'><%=of.getWord(DictionaryEntry.DOCUMENTS)%></h4></button>

</div>
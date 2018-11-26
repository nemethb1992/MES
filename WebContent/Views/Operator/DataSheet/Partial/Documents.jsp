<%@page import="phoenix.mes.content.OutputFormatter"%>
<%@page import="phoenix.mes.content.OutputFormatter.DictionaryEntry"%>
<%@page import="java.util.List"%>
<%
	OutputFormatter of = (OutputFormatter) session.getAttribute("OutputFormatter");
%>


<div class='list-group row dokumentum-list'>
	<button type='button'
		class='list-group-item list-group-item-action active disabled'><%=of.getWord(DictionaryEntry.DOCUMENTS)%></button>
	<%
		for (int i = 0; i < 8; i++) {
	%>
	<button type='button' onclick='openAsset(this)'
		class='document-button list-group-item list-group-item-action'>
		Dokumentum
		<%=i%></button>
	<%
		}
	%>
</div>
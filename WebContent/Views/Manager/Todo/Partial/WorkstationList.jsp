<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%@page import="phoenix.mes.content.AppBuild"%>
<%@page import="phoenix.mes.content.PostgreSql"%>
<%@page import="phoenix.mes.content.controller.User"%>
<%@page import="phoenix.mes.content.utility.OutputFormatter"%>
<%@page import="phoenix.mes.content.utility.OutputFormatter.DictionaryEntry"%>
<%
	List<Map<String,String>> list = (List<Map<String,String>>)request.getAttribute("Data");
	for (Map<String, String> row : (List<Map<String,String>>)list) {
%>
<div class='workstation-container col-12 px-0' 
	value='<%=row.get("divValue")%>' OnClick='<%=row.get("method")%>'>
	<textarea class='si1  p-3' disabled><%=row.get("inputValue") %></textarea>
</div>
<% } %>

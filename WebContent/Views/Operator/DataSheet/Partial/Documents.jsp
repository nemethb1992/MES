<%@page import="phoenix.mes.content.utility.OutputFormatter"%>
<%@page import="phoenix.mes.content.utility.OutputFormatter.DictionaryEntry"%>
<%@page import="phoenix.mes.abas.Task"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Collection"%>
<%@page import="java.net.URI"%>
<%@page import="phoenix.mes.abas.GenericTask.Document"%>
<%
	OutputFormatter of = (OutputFormatter)session.getAttribute("OutputFormatter");
	Task.Details taskDetails = (Task.Details)request.getAttribute("taskDetails");
%>

<h4 class='pt-2'><%=of.getWord(DictionaryEntry.DOCUMENTS) %></h4>
<div class='list-group row dokumentum-list'>
<!-- 		<button type='button'  onclick='openDocumentModal(this)' value='https://web-preview.pspdfkit.com/showcases/8.pdf#page=2' class='document-button list-group-item list-group-item-action'>Próba</button> -->
<!--   		<button type='button'  onclick='openDocumentModal(this)' value='file://C:/Igazolas.pdf' class='document-button list-group-item list-group-item-action'>Próba2</button> -->
  
  <%
  Collection<Document> data = taskDetails.getDocuments();
  for (Document item : data)
  {
  %>
		<button type='button' <%=(item.getURI() == null ? "disabled" : "")%>  onclick='openDocumentModal(this)' value='<%=item.getURI()%>' class='document-button list-group-item list-group-item-action'><%=item.getDescription()%> <%=(item.getURI() == null ? " - "+of.getWord(DictionaryEntry.MISSING_FILE) : "")%></button>
    <%} %>
</div>

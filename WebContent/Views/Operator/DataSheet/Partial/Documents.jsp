<%@page import="phoenix.mes.content.utility.OutputFormatter"%>
<%@page
	import="phoenix.mes.content.utility.OutputFormatter.DictionaryEntry"%>
<%@page import="phoenix.mes.abas.Task"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Collection"%>
<%@page import="java.net.URI"%>
<%@page import="phoenix.mes.abas.GenericTask.Document"%>
<%@page import="phoenix.mes.abas.AbasConnection"%>
<%@page import="phoenix.mes.abas.AbasObjectFactory"%>
<%@page import="phoenix.mes.content.controller.User"%>
<%@page import="javax.security.auth.login.LoginException"%>
<%@page import="phoenix.mes.content.Log"%>
<%@page import="phoenix.mes.content.Log.FaliureType"%>
<%@page import="java.sql.SQLException"%>
<%
	OutputFormatter of = (OutputFormatter) request.getAttribute("OutputFormatter");
	Task task = (Task) request.getAttribute("Task");
	User user = (User) request.getAttribute("User");
	boolean isTest = (boolean) request.getAttribute("isTest");
	String taskId = (String) request.getAttribute("taskId");

	AbasConnection abasConnection = null;
	Task.Details taskDetails = null;
	try {
		abasConnection = AbasObjectFactory.INSTANCE.openAbasConnection(user.getUsername(), user.getPassword(),
				of.getLocale(), isTest);
		taskDetails = task.getDetails(abasConnection);
%>

<h4 class='pt-2'><%=of.getWord(DictionaryEntry.DOCUMENTS)%></h4>
<div class='list-group row dokumentum-list'>

	<%
		Collection<Document> data = taskDetails.getDocuments();
			for (Document item : data) {
	%>
	<button type='button' <%=(item.getURI() == null ? "disabled" : "")%>
		onclick='openDocumentModal(this)' value='<%=item.getURI()%>'
		class='document-button list-group-item list-group-item-action'><%=item.getDescription()%>
		<%=(item.getURI() == null ? " - " + of.getWord(DictionaryEntry.MISSING_FILE) : "")%></button>
	<%
		}
	%>
</div>
<%
	} catch (LoginException e) {
		try {
			new Log(request).logFaliure(FaliureType.TASK_DATA_LOAD, e.getMessage(), taskId);
		} catch (SQLException exc) {
		}
	} finally {
		try {
			if (null != abasConnection) {
				abasConnection.close();
			}
		} catch (Throwable t) {
		}
	}
%>
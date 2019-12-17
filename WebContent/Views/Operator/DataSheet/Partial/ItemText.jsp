<%@page import="phoenix.mes.content.utility.OutputFormatter"%>
<%@page
	import="phoenix.mes.content.utility.OutputFormatter.DictionaryEntry"%>
<%@page import="phoenix.mes.abas.Task"%>
<%@page import="phoenix.mes.abas.GenericTask.Details"%>
<%@page import="phoenix.mes.content.controller.User"%>
<%@page import="phoenix.mes.abas.AbasConnection"%>
<%@page import="phoenix.mes.abas.AbasObjectFactory"%>
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

<div class='row m-0 px-0'>
	<div class='col'>
		<p class='h5 p-3 mb-0'><%=of.getWord(DictionaryEntry.INFO_ARTICLE)%>
			1
		</p>
	</div>
	<div class='col'>
		<p class='h5 p-3 mb-0'><%=of.getWord(DictionaryEntry.INFO_ARTICLE)%>
			2
		</p>
	</div>
</div>
<div class='row h-100 m-0 px-0 item-text-row-size'>
	<div class='mr-2 mb-0 col col-textarea light-shadow'>
		<textarea disabled class='p-3 BigTextInput'><%=taskDetails.getSalesOrderItemText()%></textarea>
	</div>
	<div class='ml-2 mb-0 col col-textarea light-shadow'>
		<textarea disabled class='p-3 BigTextInput'><%=taskDetails.getSalesOrderItemText2()%></textarea>
	</div>
</div>
<%
	} catch (LoginException e) {
		try {
			String stackTrace = Log.getStackTraceString(e);
			new Log(request).logFaliure((user == null? "null" : user.getUsername()),FaliureType.TASK_DATA_LOAD,stackTrace, e.toString(), taskId);
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
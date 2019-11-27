<%@page import="phoenix.mes.content.utility.OutputFormatter"%>
<%@page import="phoenix.mes.content.controller.Workstation"%>
<%@page
	import="phoenix.mes.content.utility.OutputFormatter.DictionaryEntry"%>
<%@page import="phoenix.mes.abas.Task"%>
<%@page import="java.util.List"%>
<%@page import="phoenix.mes.abas.GenericTask.BomElement"%>
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
<div class='container-fluid h-100 px-0 py-3'>

	<div class="row">
		<div class="col">

			<table class="table table-bordered sheet-table">
				<tbody>
					<tr>
						<th scope="row"><%=of.getWord(DictionaryEntry.OPERATION_NUMEBER)%>:</th>
						<td><%=taskDetails.getOperationIdNo()%></td>
						<th scope="row"><%=of.getWord(DictionaryEntry.EXECUTION_NO)%>:</th>
						<td><%=of.formatWithoutTrailingZeroes(taskDetails.getNumberOfExecutions())%></td>
					</tr>
					<tr>
						<th scope="row"><%=of.getWord(DictionaryEntry.SEARCH_WORD)%>:</th>
						<td><%=taskDetails.getOperationSwd()%></td>
						<th scope="row"><%=of.getWord(DictionaryEntry.SETTING_TIME)%>:</th>
						<td><%=of.formatWithoutTrailingZeroes(taskDetails.getSetupTime())%>
							<%=taskDetails.getSetupTimeUnit()%></td>
					</tr>
					<tr>
						<th scope="row"><%=of.getWord(DictionaryEntry.NAME)%>:</th>
						<td><%=taskDetails.getOperationDescription()%></td>
						<th scope="row"><%=of.getWord(DictionaryEntry.TIME_FOR_PCS)%>:</th>
						<td><%=of.formatWithoutTrailingZeroes(taskDetails.getUnitTime())%>
							<%=taskDetails.getUnitTimeUnit()%></td>
					</tr>
				</tbody>
			</table>
			<table class="table table-bordered sheet-table">
				<tbody>
					<tr>
						<th scope="row"><%=of.getWord(DictionaryEntry.PRODUCTION_INFO)%>:</th>
						<td colspan="3"><%=taskDetails.getOperationReservationText()%></td>
					</tr>
					<tr>
						<th scope="row"><%=of.getWord(DictionaryEntry.WORK_INSTRUCTIONS)%>:</th>
						<td colspan="3"><%=taskDetails.getWorkInstruction()%></td>
					</tr>
				</tbody>
			</table>

		</div>
	</div>

</div>
<%
	} catch (LoginException e) {
		try {
			new Log(request).logFaliure((user == null? "null" : user.getUsername()),FaliureType.TASK_DATA_LOAD, e.toString(), taskId);
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
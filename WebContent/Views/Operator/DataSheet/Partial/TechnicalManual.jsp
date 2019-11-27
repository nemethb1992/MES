<%@page import="phoenix.mes.content.utility.OutputFormatter"%>
<%@page import="phoenix.mes.content.controller.Workstation"%>
<%@page
	import="phoenix.mes.content.utility.OutputFormatter.DictionaryEntry"%>
<%@page import="phoenix.mes.abas.Task"%>
<%@page import="java.util.List"%>
<%@page import="phoenix.mes.abas.GenericTask.BomElement"%>
<%@page import="phoenix.mes.abas.GenericTask.Characteristic"%>
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

	<div class="row list-content-row">
		<div class="col list-item-row-size">

			<table class="table table-bordered sheet-table mpj-table">
				<tbody>

					<%
						List<Characteristic> data = taskDetails.getCharacteristicsBar();
							for (Characteristic item : data) {
					%>
					<tr>
						<th scope="row" class="w-25"><%=item.getName()%>:</th>
						<td><%=item.getValue()%> <%=(item.getValue().length() > 0 ? item.getUnit() : "")%></td>
					</tr>
					<%
						}
					%>
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
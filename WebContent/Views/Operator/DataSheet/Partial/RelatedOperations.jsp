<%@page import="phoenix.mes.content.utility.OutputFormatter"%>
<%@page
	import="phoenix.mes.content.utility.OutputFormatter.DictionaryEntry"%>
<%@page import="java.util.List"%>
<%@page import="phoenix.mes.abas.Task"%>
<%@page import="phoenix.mes.abas.GenericTask.Operation"%>
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

		List<Task.Operation> data = taskDetails.getFollowingOperations();
%>
<div class='row bom-list-full-container h-100'>

	<div class='col-12'>
		<div class='row bom-header-row p-2'>
			<div class='col'>
				<input class='w-100 header-label' readonly
					value='<%=of.getWord(DictionaryEntry.OPERATION_NUMEBER)%>'>
			</div>
			<div class='col-2'>
				<input class='w-100 header-label' readonly
					value='<%=of.getWord(DictionaryEntry.SEARCH_WORD)%>'>
			</div>
			<div class='col-4'>
				<input class='w-100 header-label' readonly
					value='<%=of.getWord(DictionaryEntry.NAME)%>'>
			</div>
			<div class='col'>
				<input class='w-100 header-label' readonly
					value='<%=of.getWord(DictionaryEntry.WORKSTATION_GROUP)%>'>
			</div>
			<div class='col-3'>
				<input class='w-100 header-label' readonly
					value='<%=of.getWord(DictionaryEntry.GROUP_NAME)%>'>
			</div>
		</div>
		<%
			for (Task.Operation item : data) {
		%>
		<div class='row bom-item-row px-2 pb-2'>

			<div class='col-12 bom-container'>
				<div class='row item-data-row py-2'>
					<div class='col'>
						<textarea class='w-100 sheet-input-textarea' readonly><%=item.getIdNo()%></textarea>
					</div>
					<div class='col-2'>
						<textarea class='w-100 sheet-input-textarea' readonly><%=item.getSwd()%></textarea>
					</div>
					<div class='col-4'>
						<textarea class='w-100 sheet-input-textarea' readonly><%=item.getDescription()%></textarea>
					</div>
					<div class='col'>
						<textarea class='w-100 sheet-input-textarea' readonly><%=item.getWorkCenterIdNo()%></textarea>
					</div>
					<div class='col-3'>
						<textarea class='w-100 sheet-input-textarea' readonly><%=item.getWorkCenterDescription()%></textarea>
					</div>
				</div>
				<div class='row bom-item-text-row' onclick='bomListDropDown(this)'>
					<div class='col-12'>
						<textarea class='w-100 h-100 item-text-textarea' readonly><%=item.getItemText()%></textarea>
					</div>
				</div>
			</div>
		</div>
		<%
			}
		%>
	</div>
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
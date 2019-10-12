<%@page import="phoenix.mes.abas.AbasConnection"%>
<%@page import="phoenix.mes.content.utility.OutputFormatter"%>
<%@page
	import="phoenix.mes.content.utility.OutputFormatter.DictionaryEntry"%>
<%@page import="phoenix.mes.abas.Task"%>
<%@page import="java.util.List"%>
<%@page import="java.math.BigDecimal"%>
<%@page import="phoenix.mes.abas.GenericTask.Status"%>
<%@page import="phoenix.mes.content.controller.User"%>
<%@page import="phoenix.mes.abas.AbasConnection"%>
<%@page import="phoenix.mes.abas.AbasObjectFactory"%>
<%@page import="phoenix.mes.content.controller.OperatingWorkstation"%>
<%@page import="javax.security.auth.login.LoginException"%>
<%@page import="phoenix.mes.content.Log"%>
<%@page import="phoenix.mes.content.Log.FaliureType"%>
<%@page import="java.sql.SQLException"%>


<ul class='pt-2'>
	<%
		OutputFormatter of = (OutputFormatter) request.getAttribute("OutputFormatter");
		User user = (User) request.getAttribute("User");
		boolean isTest = (boolean) request.getAttribute("isTest");

		OperatingWorkstation ws = new OperatingWorkstation(request);
		String taskId = (String) request.getAttribute("taskId");

		AbasConnection abasConnection = null;
		List<Task> taskList = null;
		try {
			abasConnection = AbasObjectFactory.INSTANCE.openAbasConnection(user.getUsername(), user.getPassword(),
					of.getLocale(), isTest);
			taskList = (List<Task>) AbasObjectFactory.INSTANCE.createWorkStation(ws.getGroup(), ws.getNumber(), abasConnection).getScheduledTasks(abasConnection);

			String startDate, startDateFormated;

			int i = 0;
			for (Task task : taskList) {
				final Task.Details taskDetails = task.getDetails(abasConnection);
				boolean progress = (task.getDetails(abasConnection).getStatus() == Status.IN_PROGRESS ? true
						: false);
				startDate = taskDetails.getFinishDate().toString();
				startDateFormated = startDate.substring(0, 4) + "." + startDate.substring(4, 6) + "."
						+ startDate.substring(6, 8) + ".";

				if (task.getDetails(abasConnection).getStatus() != Status.IN_PROGRESS) {
	%><li
		class="dnd-container station-list-item sort-list-holder list-group col-12 px-0 mb-2"
		value="<%=task.getWorkSlipId()%>"><input
		class="d-none workSlipId" value="<%=task.getWorkSlipId()%>">
		<div class="container-fluid ">
			<div class="row ">
				<div class="col ">
					<div class="row list-content-row">
						<table class="table station-list-table mb-0">
							<thead>
								<tr>
									<th style="border: transparent" scope="col"><%=of.getWord(DictionaryEntry.WORKSHEET_NO)%></th>
									<th style="border: transparent" scope="col"><%=of.getWord(DictionaryEntry.PLACE_OF_USE)%></th>
									<th style="border: transparent" scope="col"><%=of.getWord(DictionaryEntry.GET_FINISHED)%></th>
									<th style="border: transparent" scope="col"><%=of.getWord(DictionaryEntry.ARTICLE)%></th>
									<th style="border: transparent" scope="col"><%=of.getWord(DictionaryEntry.SEARCH_WORD)%></th>
									<th style="border: transparent" scope="col"><%=of.getWord(DictionaryEntry.NAME)%></th>
									<th style="border: transparent" scope="col"><%=of.getWord(DictionaryEntry.CALCULATED_PROD_TIME)%></th>
									<th style="border: transparent" scope="col"><%=of.getWord(DictionaryEntry.OPEN_QUANTITY)%></th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td><%=taskDetails.getWorkSlipNo()%></td>
									<td><%=taskDetails.getUsage()%></td>
									<td><%=startDateFormated%></td>
									<td><%=taskDetails.getProductIdNo()%></td>
									<td><%=taskDetails.getProductSwd()%></td>
									<td><%=taskDetails.getProductDescription()%></td>
									<td><%=of.formatTime(taskDetails.getCalculatedProductionTime())%></td>
									<td><%=of.formatWithoutTrailingZeroes(taskDetails.getOutstandingQuantity()) + " "
								+ taskDetails.getStockUnit()%></td>
								</tr>
							</tbody>
						</table>
					</div>
					<div class="row">
						<div class='offset-9 col-3'>
							<button type="button" onclick='openDataSheetModal(this)'
								value='<%=task.getWorkSlipId()%>'
								class="btn btn-info w-100 my-2"><%=of.getWord(DictionaryEntry.OPEN_TASK_DATASHEET)%></button>
						</div>

					</div>
				</div>
			</div>
		</div></li>
	<%
		i++;
				}
			}
	%>
</ul>
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
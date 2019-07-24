<%@page import="phoenix.mes.abas.AbasConnection"%>
<%@page import="phoenix.mes.content.utility.OutputFormatter"%>
<%@page
	import="phoenix.mes.content.utility.OutputFormatter.DictionaryEntry"%>
<%@page import="phoenix.mes.abas.Task"%>
<%@page import="phoenix.mes.content.PostgreSql"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.Collections"%>
<%@page import="phoenix.mes.abas.GenericTask.Status"%>
<%@page import="phoenix.mes.content.AppBuild"%>


<%
	PostgreSql pg = new PostgreSql(new AppBuild(request).isTest());
	OutputFormatter of = (OutputFormatter) request.getAttribute("OutputFormatter");
	List<Task> li = (List<Task>) request.getAttribute("AbasList");
	AbasConnection abasConnection = (AbasConnection) request.getAttribute("abasConnection");
	String startDate, startDateFormated;
	String cssClass = "";
	int i = 0;
	for (Task task : li) {
		final Task.Details taskDetails = task.getDetails(abasConnection);
		startDate = taskDetails.getStartDate().toString();
		startDateFormated = startDate.substring(0, 4) + "." + startDate.substring(4, 6) + "."
				+ startDate.substring(6, 8) + ".";

		switch (taskDetails.getStatus()) {
		case IN_PROGRESS:
			cssClass = "dnd-container-inprogress";
			break;
		case SUSPENDED:
			cssClass = "dnd-container-suspended";
			break;
		case INTERRUPTED:
			cssClass = "dnd-container-interrupted";
			break;
		default:
			cssClass = "";
			break;
		}
%>
<!-- ######### Teszt elem -->

<div class="dnd-container abas-list-item <%=cssClass%> col-12 px-0">
	<input class='d-none workSlipId' value='<%=task.getWorkSlipId()%>'>
	<div class="container-fluid ">
		<div class="row ">
			<div class="col-11  px-0">
				<div class='row'>
					<div class='col'>
						<table class="table abas-list-table mb-0">
							<thead>
								<tr>
									<th scope="col"><%=of.getWord(DictionaryEntry.WORKSHEET_NO)%></th>
									<th scope="col"><%=of.getWord(DictionaryEntry.PLACE_OF_USE)%></th>
									<th scope="col"><%=of.getWord(DictionaryEntry.ARTICLE)%></th>
									<th scope="col"><%=of.getWord(DictionaryEntry.SEARCH_WORD)%></th>
									<th scope="col"><%=of.getWord(DictionaryEntry.NAME)%></th>
									<th scope="col"><%=of.getWord(DictionaryEntry.GET_STARTED)%></th>
									<th scope="col"><%=of.getWord(DictionaryEntry.OPEN_QUANTITY)%></th>
									<th scope="col"><%=of.getWord(DictionaryEntry.CALCULATED_PROD_TIME)%></th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td><%=taskDetails.getWorkSlipNo()%></td>
									<td><%=taskDetails.getUsage()%></td>
									<td><%=taskDetails.getProductIdNo()%></td>
									<td><%=taskDetails.getProductSwd()%></td>
									<td><%=taskDetails.getProductDescription()%></td>
									<td><%=startDateFormated%></td>
									<td><%=of.formatWithoutTrailingZeroes(taskDetails.getOutstandingQuantity()) + " "
						+ taskDetails.getStockUnit()%></td>
									<td><%=of.formatTime(taskDetails.getCalculatedProductionTime())%></td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
				<div class='container-fluid collapse list-item-extended-content'
					id='list-item-extended-content-<%=i%>'>
					<%
						if (taskDetails.getStatus() == Status.INTERRUPTED || taskDetails.getStatus() == Status.SUSPENDED) {

								List<Map<String, String>> errorIssues = pg.sqlQuery(
										"select text from log where workslipno='" + taskDetails.getWorkSlipNo() + "'", "text");
					%>
					<div class='row faliure-row'>
						<div class='col-1'>
							<p class='pt-3 pl-2'>
								<b>Indoklás:</b>
							</p>
						</div>
						<div class='col-11 '>
							<textarea readonly class='w-100 pt-3 faliure-text'>
								<%
									int issuesNo = 0;
											for (Map<String, String> row : (List<Map<String, String>>) errorIssues) {
												if (issuesNo > 0) {
													out.print("  -  " + row.get("text") + ";");
												} else {
													out.print(row.get("text") + ";");
												}
												issuesNo++;

											}
								%>
							</textarea>
						</div>
					</div>
					<%
						}
					%>
					<div class='row'>
						<div class='col-3'>
							<button type="button" onclick='openDataSheetModal(this)'
								value='<%=task.getWorkSlipId()%>'
								class="btn btn-info w-100 my-2"><%=of.getWord(DictionaryEntry.OPEN_TASK_DATASHEET)%></button>
						</div>
						<%
							if (taskDetails.getStatus() == Status.WAITING || taskDetails.getStatus() == Status.IN_PROGRESS) {
						%>
						<div class='col-2 offset-7'>
							<button type="button" class="btn btn-warning w-100 my-2"
								onclick='openSuspendModal(this)'
								value='<%=task.getWorkSlipId()%>'><%=of.getWord(DictionaryEntry.INTERRUPT)%></button>
						</div>
						<%
							} else if (taskDetails.getStatus() == Status.INTERRUPTED
										|| taskDetails.getStatus() == Status.SUSPENDED) {
						%>
						<div class='col-2 offset-7'>
							<button type="button" class="btn btn-warning w-100 my-2"
								onclick='UnsuspendTaskFromManager(this)'
								value='<%=task.getWorkSlipId()%>'>Folytatás</button>
						</div>
						<%
							}
						%>
					</div>
				</div>
			</div>
			<div class="col-1 button-holder-list">
				<div class="row h-50 dnd-input-div">
					<input class="h-100 w-100 task-panel-button task-panel-button-add"
						onclick="PushToStation(this)" type="button">
				</div>
				<div class=" row h-50 dnd-input-div">
					<input class="h-100 w-100 task-panel-button task-panel-button-more"
						onclick="" type="button" data-toggle="collapse"
						data-target="#list-item-extended-content-<%=i%>"
						aria-expanded="false"
						aria-controls="list-item-extended-content-<%=i%>">
				</div>
			</div>
		</div>

	</div>
</div>

<%
	i++;
	}

	pg.dbClose();
%>
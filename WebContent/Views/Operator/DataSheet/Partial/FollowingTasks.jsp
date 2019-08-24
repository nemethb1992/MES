<%@page import="phoenix.mes.abas.AbasConnection"%>
<%@page import="phoenix.mes.content.utility.OutputFormatter"%>
<%@page
	import="phoenix.mes.content.utility.OutputFormatter.DictionaryEntry"%>
<%@page import="phoenix.mes.abas.Task"%>
<%@page import="java.util.List"%>
<%@page import="java.math.BigDecimal"%>
<%@page import="phoenix.mes.abas.GenericTask.Status"%>


<ul class='pt-2'>
	<%
		OutputFormatter of = (OutputFormatter) session.getAttribute("OutputFormatter");
		List<Task> li = (List<Task>) request.getAttribute("StationList");
		AbasConnection abasConnection = (AbasConnection) request.getAttribute("abasConnection");
		String startDate, startDateFormated;

		int i = 0;
		for (Task task : li) {
			final Task.Details taskDetails = task.getDetails(abasConnection);
			boolean progress = (task.getDetails(abasConnection).getStatus() == Status.IN_PROGRESS ? true : false);
			startDate = taskDetails.getFinishDate().toString();
			startDateFormated = startDate.substring(0, 4) + "." + startDate.substring(4, 6) + "."
					+ startDate.substring(6, 8) + ".";


			if(task.getDetails(abasConnection).getStatus() != Status.IN_PROGRESS){
	%><li
		class="dnd-container station-list-item sort-list-holder list-group col-12 px-0 mb-2"
		value="<%=task.getWorkSlipId()%>"><input
		class="d-none workSlipId" value="<%=task.getWorkSlipId()%>">
		<div class="container-fluid ">
			<div class="row ">
				<div class="col ">
					<div class="row">
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
									<td><%=of.formatWithoutTrailingZeroes(taskDetails.getOutstandingQuantity()) + " " + taskDetails.getStockUnit()%></td>
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
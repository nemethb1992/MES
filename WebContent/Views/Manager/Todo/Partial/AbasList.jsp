<%@page import="phoenix.mes.abas.AbasConnection"%>
<%@page import="phoenix.mes.content.utility.OutputFormatter"%>
<%@page import="phoenix.mes.content.utility.OutputFormatter.DictionaryEntry"%>
<%@page import="phoenix.mes.abas.Task"%>
<%@page import="java.util.List"%>
<%@page import="phoenix.mes.abas.GenericTask.Status"%>


<%
	OutputFormatter of = (OutputFormatter)session.getAttribute("OutputFormatter");
	List<Task> li = (List<Task>)request.getAttribute("AbasList");
	AbasConnection abasConnection = (AbasConnection)request.getAttribute("abasConnection");
	String startDate, startDateFormated;
	String cssClass = "";
	for (Task task: li) {
		final Task.Details taskDetails = task.getDetails(abasConnection);
				startDate = taskDetails.getStartDate().toString();
				startDateFormated = startDate.substring(0,4) + "." + startDate.substring(4,6) + "." + startDate.substring(6,8) + ".";
				
				switch(taskDetails.getStatus()){
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
<div class='dnd-container abas-list-item <%=cssClass %> col-12 px-0'>
	<input class='d-none workSlipId' value='<%=task.getWorkSlipId()%>'>
	<div class='container-fluid h-100'>
		<div class='row h-100'>
			<div class='col abas-listitem-data-col px-0'>
				<div class='container-fluid'>
					<div class='row'>
						<div class='col my-col-1 article-col px-1 pl-2 py-2 dnd-input-div'>
							<p><%=of.getWord(DictionaryEntry.WORKSHEET_NO)%></p>
							<textarea readonly class='dnd-input dnd-in1'><%=taskDetails.getWorkSlipNo()%></textarea>
						</div>
						<div class='col my-col-2 px-1 py-2 dnd-input-div'>
							<p><%=of.getWord(DictionaryEntry.ARTICLE)%></p>
							<textarea readonly class='dnd-input dnd-in1'><%=taskDetails.getProductIdNo()%></textarea>
						</div>
						<div class='col my-col-3 px-1 py-2 dnd-input-div'>
							<p><%=of.getWord(DictionaryEntry.SEARCH_WORD)%></p>
							<textarea readonly class='dnd-input dnd-in1'><%=taskDetails.getProductSwd()%></textarea>
						</div>
						<div class='col my-col-4 placeofuse-col  px-1 py-2 dnd-input-div'>
							<p><%=of.getWord(DictionaryEntry.PLACE_OF_USE)%></p>
							<textarea readonly class='dnd-input dnd-in1'><%=taskDetails.getUsage()%></textarea>
						</div>
						<div class='col my-col-5 px-1 py-2 dnd-input-div'>
							<p><%=of.getWord(DictionaryEntry.NAME)%></p>
							<textarea wrap='soft' class='dnd-input dnd-in1'><%=taskDetails.getProductDescription()%></textarea>
						</div>
						<div class='col my-col-6 px-1 py-2 dnd-input-div'>
							<p><%=of.getWord(DictionaryEntry.GET_STARTED)%></p>
							<textarea readonly class='dnd-input dnd-in1'><%=startDateFormated%></textarea>
						</div>
						<div class='col my-col-7 px-1 py-2 dnd-input-div'>
							<p><%=of.getWord(DictionaryEntry.OPEN_QUANTITY)%></p>
							<textarea readonly class='dnd-input dnd-in1'><%=of.formatWithoutTrailingZeroes(taskDetails.getOutstandingQuantity()) +" "+ taskDetails.getStockUnit()%></textarea>
						</div>
						<div class='col my-col-8 px-1 py-2 dnd-input-div'>
							<p><%=of.getWord(DictionaryEntry.CALCULATED_PROD_TIME)%></p>
							<textarea readonly class='dnd-input dnd-in1'><%=of.formatTime(taskDetails.getCalculatedProductionTime())%></textarea>
						</div>
					</div>
				</div>
			</div>
			<div class='col col-button p-1'>
				<div class='w-100 h-100 dnd-input-div px-0'>
					<input class='h-100 w-100 task-panel-button'
						onclick='PushToStation(this)' type='button'>
				</div>
			</div>
		</div>
	</div>
</div>
<% } %>
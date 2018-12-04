<%@page import="phoenix.mes.abas.AbasConnection"%>
<%@page import="phoenix.mes.content.utility.OutputFormatter"%>
<%@page import="phoenix.mes.content.utility.OutputFormatter.DictionaryEntry"%>
<%@page import="phoenix.mes.abas.Task"%>
<%@page import="java.util.List"%>
<%@page import="java.math.BigDecimal"%>
<%@page import="phoenix.mes.abas.Task.Status"%>
<%@page import="de.abas.ceks.jedp.EDPSession"%>
<%
	OutputFormatter of = (OutputFormatter)session.getAttribute("OutputFormatter");
	List<Task> li = (List<Task>)request.getAttribute("StationList");
	AbasConnection<EDPSession> abasConnection = (AbasConnection<EDPSession>)request.getAttribute("abasConnection");
	String startDate, startDateFormated;

	BigDecimal summedProductionTime = BigDecimal.ZERO;
	String cssClass= "";
	for (Task task: li) {
		final Task.Details taskDetails = task.getDetails(abasConnection);
				boolean progress = (task.getDetails(abasConnection).getStatus() == Status.IN_PROGRESS ? true : false);
				boolean suspended = (task.getDetails(abasConnection).getStatus() == Status.SUSPENDED ? true : false);
				summedProductionTime = summedProductionTime.add(taskDetails.getCalculatedProductionTime());
				
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
<div
	class='dnd-container station-list-item <%=cssClass%> col-12 <%=(progress ? "dnd-container-inprogress" : "")%> px-0'
	value='3'>
	<input class='d-none workSlipId' value='<%=task.getWorkSlipId()%>'>
	<div class='container px-0'>
		<div class='row w-100 mx-auto'>
			<div
				class='<%=(progress ? "col-6" : "col-4")%> pr-0 py-2 dnd-input-div'>
				<p><%=of.getWord(DictionaryEntry.WORKSHEET_NO)%></p>
				<textarea disabled class='dnd-input dnd-in1'><%=taskDetails.getWorkSlipNo()%></textarea>
				<p><%=of.getWord(DictionaryEntry.ARTICLE)%></p>
				<textarea disabled class='dnd-input dnd-in1'><%=taskDetails.getProductIdNo()%></textarea>
				<p><%=of.getWord(DictionaryEntry.OPEN_QUANTITY)%></p>
				<textarea disabled class='dnd-input dnd-in1'><%=of.formatWithoutTrailingZeroes(taskDetails.getOutstandingQuantity()) +" "+ taskDetails.getStockUnit()%></textarea>
			</div>
			<div class='col-6 pr-0 py-2 dnd-input-div'>
				<p><%=of.getWord(DictionaryEntry.SEARCH_WORD)%></p>
				<textarea disabled class='dnd-input dnd-in1'><%=taskDetails.getProductSwd()%></textarea>
				<p><%=of.getWord(DictionaryEntry.NAME)%></p>
				<textarea disabled class='dnd-input dnd-in1'><%=taskDetails.getProductDescription()%></textarea>
				<p><%=of.getWord(DictionaryEntry.CALCULATED_PROD_TIME)%></p>
				<textarea disabled class='dnd-input dnd-in1'><%=of.formatTime(taskDetails.getCalculatedProductionTime())%></textarea>
			</div>
			<div class='col-2 <%=(progress ? "d-none" : "")%> dnd-input-div px-0'>
				<div class='row w-100 mx-auto h-100 d-flex'>
					<div class='col-12 px-0'>
						<input
							class='h-100 w-100 task-panel-button mini-button up-task-button'
							onclick='MoveTaskUp(this)' type='button'>
					</div>
					<div class='col-12 my-1 px-0'>
						<input
							class='h-100 w-100 task-panel-button mini-button remove-task-button'
							onclick='RemoveFromStation(this)' type='button'>
					</div>
					<div class='col-12 px-0'>
						<input
							class='h-100 w-100 task-panel-button mini-button down-task-button'
							onclick='MoveTaskDown(this)' type='button'>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<%}
request.setAttribute("summedProductionTime", summedProductionTime);
%>
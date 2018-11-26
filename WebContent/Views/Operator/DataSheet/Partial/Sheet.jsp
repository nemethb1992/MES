<%@page import="phoenix.mes.content.OutputFormatter"%>
<%@page import="phoenix.mes.content.OutputFormatter.DictionaryEntry"%>
<%@page import="phoenix.mes.abas.Task"%>
<%@page import="java.util.List"%>
<%@page import="phoenix.mes.abas.Task.BomElement"%>
<%
	OutputFormatter of = (OutputFormatter)session.getAttribute("OutputFormatter");
	Task.Details taskDetails = (Task.Details)request.getAttribute("taskDetails");
	String workstation = (String)request.getAttribute("ws");
	String wsName = (String)request.getAttribute("wsName");
%>
<div class='container-fluid h-100 px-0 py-3'>
	<div class=' my-white-container h-100 px-0'>
		<div class='row data-row mx-3 mt-0'>
			<div class='col-6 pt-3'>
				<div class='inputContainer'>
					<p class='task-data-label'><%=of.getWord(DictionaryEntry.WORKSTATION)%></p>
					<input class='px-2 w-100 task-data-value' type='text' disabled
						value='<%=workstation.split("!")[0]%> - <%=workstation.split("!")[1]%>'/>
				</div>
				<div class='inputContainer'>
					<p class='task-data-label'><%=of.getWord(DictionaryEntry.WORKSHEET_NO)%></p>
					<input class='px-2 w-100 task-data-value' type='text' disabled
						value='<%=taskDetails.getWorkSlipNo()%>'/>
				</div>
				<div class='inputContainer'>
					<p class='task-data-label'> <%=of.getWord(DictionaryEntry.GET_STARTED)%> </p>
					<input class='px-2 w-100 task-data-value' type='text' disabled
						value='<%=taskDetails.getStartDate()%>'/>
				</div>
			</div>
			<div class='col-6 pt-3'>
				<div class='inputContainer'>
					<p class='task-data-label'><%=of.getWord(DictionaryEntry.WORKSTATION_NAME)%></p>
					<input class='px-2 w-100 task-data-value' type='text' disabled
						value='<%=wsName%>'/>
				</div>
				<div class='inputContainer'>
					<p class='task-data-label'><%=of.getWord(DictionaryEntry.PLACE_OF_USE)%></p>
					<input class='px-2 w-100 task-data-value' type='text' disabled
						value='<%=taskDetails.getUsage()%>'/>
				</div>
			</div>
		</div>
		<div class='row data-row mx-3 my-0'>
			<div class='col-6 pt-3'>
				<div class='inputContainer'>
					<p class='task-data-label'><%=of.getWord(DictionaryEntry.ARTICLE)%></p>
					<input class='px-2 w-100 task-data-value' type='text' disabled
						value='<%=taskDetails.getProductIdNo()%>'>
				</div>
				<div class='inputContainer'>
					<p class='task-data-label'><%=of.getWord(DictionaryEntry.SEARCH_WORD)%></p>
					<input class='px-2 w-100 task-data-value' type='text' disabled
						value='<%=taskDetails.getProductSwd()%>'>
				</div>
				<div class='inputContainer'>
					<p class='task-data-label'><%=of.getWord(DictionaryEntry.OPEN_QUANTITY)%></p>
					<input class='px-2 w-100 task-data-value' type='text' disabled
						value='<%=of.formatWithoutTrailingZeroes(taskDetails.getOutstandingQuantity())%>  <%=taskDetails.getStockUnit()%>'>
				</div>
			</div>
			<div class='col-6 pt-3'>
				<div class='inputContainer'>
					<p class='task-data-label'><%=of.getWord(DictionaryEntry.NAME)%></p>
					<input class='px-2 w-100 task-data-value' type='text' disabled
						value='<%=taskDetails.getProductDescription()%>'>
				</div>
				<div class='inputContainer'>
					<p class='task-data-label'><%=of.getWord(DictionaryEntry.NAME)%> 2</p>
					<input class='px-2 w-100 task-data-value' type='text' disabled
						value='<%=taskDetails.getProductDescription2()%>'>
				</div>
			</div>
		</div>
		<div class='row data-row mx-3 my-0'>
			<div class='col-6 pt-3 px-3'>
				<div class='inputContainer'>
					<p class='task-data-label'><%=of.getWord(DictionaryEntry.OPERATION_NUMEBER)%></p>
					<input class='px-2 w-100 task-data-value' type='text' disabled
						value='<%=taskDetails.getOperationIdNo()%>'>
				</div>
				<div class='inputContainer'>
					<p class='task-data-label'><%=of.getWord(DictionaryEntry.SEARCH_WORD)%></p>
					<input class='px-2 w-100 task-data-value' type='text' disabled
						value='<%=taskDetails.getOperationSwd()%>'>
				</div>
				<div class='inputContainer'>
					<p class='task-data-label'><%=of.getWord(DictionaryEntry.NAME)%></p>
					<input class='px-2 w-100 task-data-value' type='text' disabled
						value='<%=taskDetails.getOperationDescription()%>'>
				</div>
			</div>
			<div class='col-6 pt-3 px-3'>
				<div class='inputContainer'>
					<p class='task-data-label'><%=of.getWord(DictionaryEntry.EXECUTION_NO)%></p>
					<input class='px-2 w-100 task-data-value' type='text' disabled
						value='<%=of.formatWithoutTrailingZeroes(taskDetails.getNumberOfExecutions())%>'>
				</div>
				<div class='inputContainer'>
					<p class='task-data-label'><%=of.getWord(DictionaryEntry.SETTING_TIME)%></p>
					<input class='px-2 w-100 task-data-value' type='text' disabled
						value='<%=of.formatWithoutTrailingZeroes(taskDetails.getSetupTime())%>  <%=taskDetails.getSetupTimeUnit()%>'>
				</div>
				<div class='inputContainer'>
					<p class='task-data-label'><%=of.getWord(DictionaryEntry.TIME_FOR_PCS)%></p>
					<input class='px-2 w-100 task-data-value'
						type='text' disabled  value='<%=of.formatWithoutTrailingZeroes(taskDetails.getUnitTime())%>  <%=taskDetails.getUnitTimeUnit()%>'/>
				</div>
			</div>
		</div>
		<div class='row data-row px-3'>
			<div class='col-12'>
				<div class='inputContainer px-3'>
					<p class='mb-0 pt-1 task-data-label'><%=of.getWord(DictionaryEntry.PRODUCTION_INFO)%></p>
					<textarea class='px-2 w-100 task-data-value' disabled><%=taskDetails.getOperationReservationText()%></textarea>
				</div>
			</div>
		</div>
	</div>
</div>
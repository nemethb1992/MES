<%@page import="phoenix.mes.content.utility.OutputFormatter"%>
<%@page import="phoenix.mes.content.utility.OutputFormatter.DictionaryEntry"%>
<%@page import="java.util.List"%>
<%@page import="phoenix.mes.abas.Task"%>
<%@page import="phoenix.mes.abas.GenericTask.Operation"%>
<%
	OutputFormatter of = (OutputFormatter)session.getAttribute("OutputFormatter");
	List<Task.Operation> data = (List<Task.Operation>)request.getAttribute("operationdata");
	
%>
<div class='row bom-list-full-container h-100'>

	<div class='col-12'>
		<div class='row bom-header-row p-2'>
			<div class='col-1'>
				<input class='w-100' disabled
					value='<%=of.getWord(DictionaryEntry.OPERATION_NUMEBER)%>'>
			</div>
			<div class='col-2'>
				<input class='w-100' disabled
					value='<%=of.getWord(DictionaryEntry.SEARCH_WORD)%>'>
			</div>
			<div class='col-4'>
				<input class='w-100' disabled
					value='<%=of.getWord(DictionaryEntry.NAME)%>'>
			</div>
			<div class='col-1'>
				<input class='w-100' disabled
					value='<%=of.getWord(DictionaryEntry.WORKSTATION_GROUP)%>'>
			</div>
			<div class='col-3'>
				<input class='w-100' disabled
					value='<%=of.getWord(DictionaryEntry.WORKSTATION_NAME)%>'>
			</div>
		</div>
		<%
			for (Task.Operation item : data) {
		%>
		<div class='row bom-item-row px-2 pb-2' >
			<div class='position-absolute bg-transparent w-100' style="height: 65px; z-index: 1000;" onclick='bomListDropDown(this)'></div>
			<div class='col-12'>
				<div class='row item-data-row py-2'>
					<div class='col-1'>
						<input class='w-100' disabled value='<%=item.getIdNo()%>'>
					</div>
					<div class='col-2'>
						<input class='w-100' disabled value='<%=item.getSwd()%>'>
					</div>
					<div class='col-4'>
						<input class='w-100' disabled
							value='<%=item.getDescription()%>'>
					</div>
					<div class='col-1'>
						<input class='w-100' disabled
							value='<%=item.getWorkCenterIdNo()%>'>
					</div>
					<div class='col-3'>
						<input class='w-100' disabled
							value='<%=item.getWorkCenterDescription()%>'>
					</div>
				</div>
				<div class='row bom-item-text-row'>
					<div class='col-12'>
						<textarea class='w-100 h-100 item-text-textarea' disabled><%=item.getItemText()%></textarea>
					</div>
				</div>
			</div>
		</div>
		<%
			}
		%>
	</div>
</div>
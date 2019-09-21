<%@page import="phoenix.mes.content.utility.OutputFormatter"%>
<%@page import="phoenix.mes.content.controller.Workstation"%>
<%@page
	import="phoenix.mes.content.utility.OutputFormatter.DictionaryEntry"%>
<%@page import="phoenix.mes.abas.Task"%>
<%@page import="java.util.List"%>
<%@page import="phoenix.mes.abas.GenericTask.BomElement"%>
<%@page import="java.math.BigDecimal"%>
<%
	OutputFormatter of = (OutputFormatter) session.getAttribute("OutputFormatter");
	Task.Details taskDetails = (Task.Details) request.getAttribute("taskDetails");
	String previous_submit = "-";
	if (null != taskDetails.getYieldOfPrecedingWorkSlip()) {
		previous_submit = of.formatWithoutTrailingZeroes(taskDetails.getYieldOfPrecedingWorkSlip()).toString()
				+ " " + taskDetails.getStockUnit();
	}
	String packing_quantity = "";
	if (0 != taskDetails.getFillingQuantity().signum()) {
		packing_quantity = of.formatWithoutTrailingZeroes(taskDetails.getFillingQuantity()).toString() + " "
				+ taskDetails.getStockUnit();
	}
%>
<div class='container-fluid h-100 px-0 py-3'>

	<div class="row">
		<div class="col">
			<table class="table table-bordered sheet-table">
				<tbody>
					<tr>
						<th scope="row"><%=of.getWord(DictionaryEntry.WORKSHEET_NO)%>:</th>
						<td><%=taskDetails.getWorkSlipNo()%></td>
						<th scope="row"><%=of.getWord(DictionaryEntry.PLACE_OF_USE)%>:</th>
						<td><%=taskDetails.getUsage()%></td>
					</tr>
					<tr>
						<th scope="row"><%=of.getWord(DictionaryEntry.GET_FINISHED)%>:</th>
						<td colspan="1"><%=of.formatDate(taskDetails.getFinishDate())%></td>
					</tr>
				</tbody>
			</table>
			<table class="table table-bordered sheet-table">
				<tbody>
					<tr>
						<th scope="row"><%=of.getWord(DictionaryEntry.ARTICLE)%>:</th>
						<td><%=taskDetails.getProductIdNo()%></td>
						<th scope="row"><%=of.getWord(DictionaryEntry.NAME)%>:</th>
						<td><%=taskDetails.getProductDescription()%></td>
					</tr>
					<tr>
						<th scope="row"><%=of.getWord(DictionaryEntry.SEARCH_WORD)%>:</th>
						<td><%=taskDetails.getProductSwd()%></td>
						<th scope="row"><%=of.getWord(DictionaryEntry.NAME)%> 2:</th>
						<td><%=taskDetails.getProductDescription2()%></td>
					</tr>
					<tr>
						<th scope="row"><%=of.getWord(DictionaryEntry.OPEN_QUANTITY)%>:</th>
						<td colspan="1"><%=of.formatWithoutTrailingZeroes(taskDetails.getOutstandingQuantity())%>
							<%=taskDetails.getStockUnit()%></td>
						<th scope="row"><%=of.getWord(DictionaryEntry.PREVIOUS_WORKSHEET_NO_SUBMIT)%>:</th>
						<td colspan="1"><%=previous_submit%></td>
					</tr>
				</tbody>
			</table>

			<table class="table table-bordered sheet-table">
				<tbody>
					<tr>
						<th scope="row"><%=of.getWord(DictionaryEntry.PACKING_INSTRUCTIONS)%>:</th>
						<td><%=taskDetails.getPackingInstructionSwd()%></td>
						<th scope="row"><%=of.getWord(DictionaryEntry.PACKING_QUANTITY)%>:</th>
						<td><%=packing_quantity%></td>
					</tr>
					<tr>
						<th scope="row"><%=of.getWord(DictionaryEntry.PACKING_TOOL)%>:</th>
						<td><%=taskDetails.getPackagingMaterialIdNo()%></td>
						<th scope="row"><%=of.getWord(DictionaryEntry.NAME)%>:</th>
						<td><%=taskDetails.getPackagingMaterialDescription()%></td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>

</div>


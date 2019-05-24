<%@page import="phoenix.mes.content.utility.OutputFormatter"%>
<%@page import="phoenix.mes.content.controller.Workstation"%>
<%@page import="phoenix.mes.content.utility.OutputFormatter.DictionaryEntry"%>
<%@page import="phoenix.mes.abas.Task"%>
<%@page import="java.util.List"%>
<%@page import="phoenix.mes.abas.Task.BomElement"%>
<%
	OutputFormatter of = (OutputFormatter)session.getAttribute("OutputFormatter");
	Task.Details taskDetails = (Task.Details)request.getAttribute("taskDetails");
	Workstation ws = (Workstation)request.getAttribute("Workstation");
%>
<div class='container-fluid h-100 px-0 py-3'>

<div class="row">
		<div class="col">
		<table class="table table-bordered sheet-table">
  <tbody>
    <tr>
      <th scope="row"><%=of.getWord(DictionaryEntry.WORKSTATION)%>:</th>
      <td><%=ws.getGroup()%> - <%=ws.getNumber()%></td>
      <th scope="row"><%=of.getWord(DictionaryEntry.WORKSTATION_NAME)%>:</th>
      <td><%=ws.getName()%></td>
    </tr>
    <tr>
      <th scope="row"><%=of.getWord(DictionaryEntry.WORKSHEET_NO)%>:</th>
      <td><%=taskDetails.getWorkSlipNo()%></td>
      <th scope="row"><%=of.getWord(DictionaryEntry.PLACE_OF_USE)%>:</th>
      <td><%=taskDetails.getUsage()%></td>
    </tr>
    <tr>
      <th scope="row"><%=of.getWord(DictionaryEntry.GET_STARTED)%>:</th>
      <td  colspan="1"><%=of.formatDate(taskDetails.getStartDate())%></td>
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
      <td colspan="1"><%=of.formatWithoutTrailingZeroes(taskDetails.getOutstandingQuantity())%> <%=taskDetails.getStockUnit()%></td>
    </tr>
  </tbody>
</table>

<table class="table table-bordered sheet-table">
  <tbody>
    <tr>
      <th scope="row"><%=of.getWord(DictionaryEntry.OPERATION_NUMEBER)%>:</th>
      <td><%=taskDetails.getOperationIdNo()%></td>
      <th scope="row"><%=of.getWord(DictionaryEntry.EXECUTION_NO)%>:</th>
      <td><%=of.formatWithoutTrailingZeroes(taskDetails.getNumberOfExecutions())%></td>
    </tr>
    <tr>
      <th scope="row"><%=of.getWord(DictionaryEntry.SEARCH_WORD)%>:</th>
      <td><%=taskDetails.getOperationSwd()%></td>
      <th scope="row"><%=of.getWord(DictionaryEntry.SETTING_TIME)%>:</th>
      <td><%=of.formatWithoutTrailingZeroes(taskDetails.getSetupTime())%> <%=taskDetails.getSetupTimeUnit()%></td>
    </tr>
    <tr>
      <th scope="row"><%=of.getWord(DictionaryEntry.NAME)%>:</th>
      <td><%=taskDetails.getOperationDescription()%></td>
      <th scope="row"><%=of.getWord(DictionaryEntry.TIME_FOR_PCS)%>:</th>
      <td><%=of.formatWithoutTrailingZeroes(taskDetails.getUnitTime())%> <%=taskDetails.getUnitTimeUnit()%></td>
    </tr>
  </tbody>
</table>
<table class="table table-bordered sheet-table">
  <tbody>
    <tr>
      <th scope="row"><%=of.getWord(DictionaryEntry.PRODUCTION_INFO)%>:</th>
      <td colspan="3"><%=taskDetails.getOperationReservationText()%></td>
    </tr>
  </tbody>
</table>
		</div>
</div>


<!-- 	<div class=' my-white-container h-100 px-0'> -->
<!-- 		<div class='row data-row mx-3 mt-0'> -->
<!-- 			<div class='col-6 pt-3'> -->
<!-- 				<div class='inputContainer'> -->
<%-- 					<p class='task-data-label'><%=of.getWord(DictionaryEntry.WORKSTATION)%></p> --%>
<!-- 					<input class='px-2 w-100 task-data-value' type='text' disabled -->
<%-- 						value='<%=ws.getGroup()%> - <%=ws.getNumber()%>'/> --%>
<!-- 				</div> -->
<!-- 				<div class='inputContainer'> -->
<%-- 					<p class='task-data-label'><%=of.getWord(DictionaryEntry.WORKSHEET_NO)%></p> --%>
<!-- 					<input class='px-2 w-100 task-data-value' type='text' disabled -->
<%-- 						value='<%=taskDetails.getWorkSlipNo()%>'/> --%>
<!-- 				</div> -->
<!-- 				<div class='inputContainer'> -->
<%-- 					<p class='task-data-label'><%=of.getWord(DictionaryEntry.GET_STARTED)%> </p> --%>
<!-- 					<input class='px-2 w-100 task-data-value' type='text' disabled -->
<%-- 						value='<%=of.formatDate(taskDetails.getStartDate())%>'/> --%>
<!-- 				</div> -->
<!-- 			</div> -->
<!-- 			<div class='col-6 pt-3'> -->
<!-- 				<div class='inputContainer'> -->
<%-- 					<p class='task-data-label'><%=of.getWord(DictionaryEntry.WORKSTATION_NAME)%></p> --%>
<!-- 					<input class='px-2 w-100 task-data-value' type='text' disabled -->
<%-- 						value='<%=ws.getName()%>'/> --%>
<!-- 				</div> -->
<!-- 				<div class='inputContainer'> -->
<%-- 					<p class='task-data-label'><%=of.getWord(DictionaryEntry.PLACE_OF_USE)%></p> --%>
<!-- 					<input class='px-2 w-100 task-data-value' type='text' disabled -->
<%-- 						value='<%=taskDetails.getUsage()%>'/> --%>
<!-- 				</div> -->
<!-- 			</div> -->
<!-- 		</div> -->

<!-- 		<div class='row data-row mx-3 my-0'> -->
<!-- 			<div class='col-6 pt-3'> -->
<!-- 				<div class='inputContainer'> -->
<%-- 					<p class='task-data-label'><%=of.getWord(DictionaryEntry.ARTICLE)%></p> --%>
<!-- 					<input class='px-2 w-100 task-data-value' type='text' disabled -->
<%-- 						value='<%=taskDetails.getProductIdNo()%>'> --%>
<!-- 				</div> -->
<!-- 				<div class='inputContainer'> -->
<%-- 					<p class='task-data-label'><%=of.getWord(DictionaryEntry.SEARCH_WORD)%></p> --%>
<!-- 					<input class='px-2 w-100 task-data-value' type='text' disabled -->
<%-- 						value='<%=taskDetails.getProductSwd()%>'> --%>
<!-- 				</div> -->
<!-- 				<div class='inputContainer'> -->
<%-- 					<p class='task-data-label'><%=of.getWord(DictionaryEntry.OPEN_QUANTITY)%></p> --%>
<!-- 					<input class='px-2 w-100 task-data-value' type='text' disabled -->
<%-- 						value='<%=of.formatWithoutTrailingZeroes(taskDetails.getOutstandingQuantity())%> <%=taskDetails.getStockUnit()%>'> --%>
<!-- 				</div> -->
<!-- 			</div> -->
<!-- 			<div class='col-6 pt-3'> -->
<!-- 				<div class='inputContainer'> -->
<%-- 					<p class='task-data-label'><%=of.getWord(DictionaryEntry.NAME)%></p> --%>
<!-- 					<input class='px-2 w-100 task-data-value' type='text' disabled -->
<%-- 						value='<%=taskDetails.getProductDescription()%>'> --%>
<!-- 				</div> -->
<!-- 				<div class='inputContainer'> -->
<%-- 					<p class='task-data-label'><%=of.getWord(DictionaryEntry.NAME)%> 2</p> --%>
<!-- 					<input class='px-2 w-100 task-data-value' type='text' disabled -->
<%-- 						value='<%=taskDetails.getProductDescription2()%>'> --%>
<!-- 				</div> -->
<!-- 			</div> -->
<!-- 		</div> -->
<!-- 		<div class='row data-row mx-3 my-0'> -->
<!-- 			<div class='col-6 pt-3 px-3'> -->
<!-- 				<div class='inputContainer'> -->
<%-- 					<p class='task-data-label'><%=of.getWord(DictionaryEntry.OPERATION_NUMEBER)%></p> --%>
<!-- 					<input class='px-2 w-100 task-data-value' type='text' disabled -->
<%-- 						value='<%=taskDetails.getOperationIdNo()%>'> --%>
<!-- 				</div> -->
<!-- 				<div class='inputContainer'> -->
<%-- 					<p class='task-data-label'><%=of.getWord(DictionaryEntry.SEARCH_WORD)%></p> --%>
<!-- 					<input class='px-2 w-100 task-data-value' type='text' disabled -->
<%-- 						value='<%=taskDetails.getOperationSwd()%>'> --%>
<!-- 				</div> -->
<!-- 				<div class='inputContainer'> -->
<%-- 					<p class='task-data-label'><%=of.getWord(DictionaryEntry.NAME)%></p> --%>
<!-- 					<input class='px-2 w-100 task-data-value' type='text' disabled -->
<%-- 						value='<%=taskDetails.getOperationDescription()%>'> --%>
<!-- 				</div> -->
<!-- 			</div> -->
<!-- 			<div class='col-6 pt-3 px-3'> -->
<!-- 				<div class='inputContainer'> -->
<%-- 					<p class='task-data-label'><%=of.getWord(DictionaryEntry.EXECUTION_NO)%></p> --%>
<!-- 					<input class='px-2 w-100 task-data-value' type='text' disabled -->
<%-- 						value='<%=of.formatWithoutTrailingZeroes(taskDetails.getNumberOfExecutions())%>'> --%>
<!-- 				</div> -->
<!-- 				<div class='inputContainer'> -->
<%-- 					<p class='task-data-label'><%=of.getWord(DictionaryEntry.SETTING_TIME)%></p> --%>
<!-- 					<input class='px-2 w-100 task-data-value' type='text' disabled -->
<%-- 						value='<%=of.formatWithoutTrailingZeroes(taskDetails.getSetupTime())%> <%=taskDetails.getSetupTimeUnit()%>'> --%>
<!-- 				</div> -->
<!-- 				<div class='inputContainer'> -->
<%-- 					<p class='task-data-label'><%=of.getWord(DictionaryEntry.TIME_FOR_PCS)%></p> --%>
<!-- 					<input class='px-2 w-100 task-data-value' -->
<%-- 						type='text' disabled  value='<%=of.formatWithoutTrailingZeroes(taskDetails.getUnitTime())%> <%=taskDetails.getUnitTimeUnit()%>'/> --%>
<!-- 				</div> -->
<!-- 			</div> -->
<!-- 		</div> -->
<!-- 		<div class='row data-row px-3'> -->
<!-- 			<div class='col-12'> -->
<!-- 				<div class='inputContainer px-3'> -->
<%-- 					<p class='mb-0 pt-1 task-data-label'><%=of.getWord(DictionaryEntry.PRODUCTION_INFO)%></p> --%>
<%-- 					<textarea class='px-2 w-100 task-data-value' disabled><%=taskDetails.getOperationReservationText()%></textarea> --%>
<!-- 				</div> -->
<!-- 			</div> -->
<!-- 		</div> -->
<!-- 	</div> -->
</div>
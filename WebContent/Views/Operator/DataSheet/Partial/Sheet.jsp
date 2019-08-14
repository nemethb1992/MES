<%@page import="phoenix.mes.content.utility.OutputFormatter"%>
<%@page import="phoenix.mes.content.controller.Workstation"%>
<%@page import="phoenix.mes.content.utility.OutputFormatter.DictionaryEntry"%>
<%@page import="phoenix.mes.abas.Task"%>
<%@page import="java.util.List"%>
<%@page import="phoenix.mes.abas.GenericTask.BomElement"%>
<%
	OutputFormatter of = (OutputFormatter)session.getAttribute("OutputFormatter");
	Task.Details taskDetails = (Task.Details)request.getAttribute("taskDetails");
%>
<div class='container-fluid h-100 px-0 py-3'>

<div class="row">
		<div class="col">
		<table class="table table-bordered sheet-table">
  <tbody>
<!--     <tr> -->
<%--       <th scope="row"><%=of.getWord(DictionaryEntry.WORKSTATION)%>:</th> --%>
<%--       <td><%=ws.getGroup()%> - <%=ws.getNumber()%></td> --%>
<%--       <th scope="row"><%=of.getWord(DictionaryEntry.WORKSTATION_NAME)%>:</th> --%>
<%--       <td><%=ws.getName()%></td> --%>
<!--     </tr> -->
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
      <th scope="row"><%=of.getWord(DictionaryEntry.PREVIOUS_WORKSHEET_NO_SUBMIT)%>:</th>
      <td colspan="1"><%=of.formatWithoutTrailingZeroes(taskDetails.getYieldOfPrecedingWorkSlip())%> <%=taskDetails.getStockUnit()%></td>
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

</div>

<div class="modal fade my-fade" id="submit-confirmation-modal" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="false">
  <div class="modal-dialog modal-lg modal-dialog-centered" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLongTitle"><%=of.getWord(DictionaryEntry.COMMIT)%></h5>

      </div>
			<div class="modal-body pt-1">
				<div class="form-group row">
				<label for="example-search-input"  class="col-12 col-form-label confirm-title"><%=of.getWord(DictionaryEntry.SUBMIT_CONFIRMATION_TEXT)%></label>
				</div>					  
				<div class="form-group row">
				    <label for="confirm-openqty" class="col-sm-4 col-form-label"><%=of.getWord(DictionaryEntry.OPEN_QUANTITY)%></label>
				    <div class="col-sm-8">
				      <input type="text" disabled class="form-control" id="confirm-openqty" value="<%=of.formatWithoutTrailingZeroes(taskDetails.getOutstandingQuantity())%>" placeholder="0">
				    </div>
				  </div>  
				  <div class="form-group row">
				    <label for="confirm-finished" class="col-sm-4 col-form-label"><%=of.getWord(DictionaryEntry.FINISHED_QUANTITY)%></label>
				    <div class="col-sm-8">
				      <input type="text" disabled class="form-control" id="confirm-finished" placeholder="0">
				    </div>
				  </div>  
				  <div class="form-group row">
				    <label for="confirm-scrap" class="col-sm-4 col-form-label"><%=of.getWord(DictionaryEntry.SCRAP_QUANTITY)%></label>
				    <div class="col-sm-8">
				      <input type="text" disabled class="form-control" id="confirm-scrap" placeholder="0">
				    </div>
				  </div>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-secondary w-25" data-dismiss="modal" onclick='CloseConfirmationModal()'><%=of.getWord(DictionaryEntry.NO)%></button>
				<button type="button" class="btn btn-primary w-25"
				onclick='SubmitTask()'><%=of.getWord(DictionaryEntry.YES)%></button>
			</div>
		</div>
  </div>
</div>


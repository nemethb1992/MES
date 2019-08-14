<%@page import="phoenix.mes.content.utility.OutputFormatter"%>
<%@page import="phoenix.mes.content.utility.OutputFormatter.DictionaryEntry"%>
<%@page import="phoenix.mes.abas.Task"%>

<%@page pageEncoding="UTF-8"%>
<% request.setCharacterEncoding("UTF-8");%>
<%
	String workSlipId = (String)request.getAttribute("workSlipId");
	String finishedQty = (String)request.getAttribute("finishedQty");
	String scrapQty = (String)request.getAttribute("scrapQty");
	OutputFormatter of = (OutputFormatter)request.getAttribute("OutputFormatter");
	Task.Details taskDetails = (Task.Details)request.getAttribute("TaskDetails");
%>

<div class="modal fade my-fade" id="submitcConfirmationModal"
	tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle"
	aria-hidden="false">
	<div class="modal-dialog modal-lg modal-dialog-centered"
		role="document">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title" id="exampleModalLongTitle"><%=of.getWord(DictionaryEntry.COMMIT)%></h5>

			</div>
			<div class="modal-body pt-1">
				<div class="form-group row">
					<label for="example-search-input"
						class="col-12 col-form-label confirm-title"><%=of.getWord(DictionaryEntry.SUBMIT_CONFIRMATION_TEXT)%></label>
				</div>
				<div class="form-group row">
					<label for="confirm-openqty" class="col-sm-4 col-form-label"><%=of.getWord(DictionaryEntry.OPEN_QUANTITY)%></label>
					<div class="col-sm-8">
						<input type="text" readonly class="form-control"
							id="confirm-openqty"
							value="<%=of.formatWithoutTrailingZeroes(taskDetails.getOutstandingConfirmationQuantity())%>"
							placeholder="0">
					</div>
				</div>
				<div class="form-group row">
					<label for="confirm-finished" class="col-sm-4 col-form-label"><%=of.getWord(DictionaryEntry.FINISHED_QUANTITY)%></label>
					<div class="col-sm-8">
						<input type="text" readonly class="form-control confirm-finished" value='<%=finishedQty %>' 
							id="confirm-finished" placeholder="0">
					</div>
				</div>
				<div class="form-group row">
					<label for="confirm-scrap" class="col-sm-4 col-form-label"><%=of.getWord(DictionaryEntry.SCRAP_QUANTITY)%></label>
					<div class="col-sm-8">
						<input type="text" readonly class="form-control confirm-scrap" value='<%=scrapQty %>' 
							id="confirm-scrap" placeholder="0">
					</div>
				</div>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-secondary w-25"
					data-dismiss="modal"><%=of.getWord(DictionaryEntry.NO)%></button>
				<button type="button" class="btn btn-primary w-25"
					onclick='SubmitTask(this)'><%=of.getWord(DictionaryEntry.YES)%></button>
			</div>
		</div>
	</div>
</div>
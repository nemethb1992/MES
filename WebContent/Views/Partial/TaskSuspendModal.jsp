<%@page import="phoenix.mes.content.utility.OutputFormatter"%>
<%@page import="phoenix.mes.content.utility.OutputFormatter.DictionaryEntry"%>
<%@page import="phoenix.mes.abas.Task"%>

<%@page pageEncoding="UTF-8"%>
<% request.setCharacterEncoding("UTF-8");%>
<%
	String workSlipId = (String)request.getAttribute("workSlipId");
	String suspendType = (String)request.getAttribute("SuspendType");
	OutputFormatter of = (OutputFormatter)request.getAttribute("OutputFormatter");
	Task.Details taskDetails = (Task.Details)request.getAttribute("TaskDetails");
	boolean unsecureSuspend = (boolean)request.getAttribute("SecureSuspend");
	String suspendMethod = "openInterruptModal(this)";
	if(unsecureSuspend){
		suspendMethod = ("manager".equals(suspendType)? "SuspendTaskFromManager(this, false)" : "SuspendTaskFromOperator(this, false)");
	}

%>

<div class="modal fade my-fade" id="SuspendModal" tabindex="-1"
	role="dialog" aria-labelledby="exampleModalCenterTitle"
	aria-hidden="true">
	<div class="modal-dialog modal-lg modal-dialog-centered"
		role="document">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title" id="exampleModalLongTitle"><%=of.getWord(DictionaryEntry.DISRUPTION_TITLE)%></h5>
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
			</div>
			<div class="modal-body">

				<div class="form-group row">
					<label for="example-search-input" class="col-3 col-form-label"><%=of.getWord(DictionaryEntry.ARTICLE)%></label>
					<div class="col-9">
						<input class="form-control" disabled type="search"
							value="<%=taskDetails.getProductIdNo()%>"
							id="example-search-input">
					</div>
				</div>
				<div class="form-group row">
					<label for="example-search-input" class="col-3 col-form-label"><%=of.getWord(DictionaryEntry.SEARCH_WORD)%></label>
					<div class="col-9">
						<input class="form-control" disabled type="search"
							value="<%=taskDetails.getProductSwd()%>"
							id="example-search-input">
					</div>
				</div>
				<div class="form-group row">
					<label for="example-search-input" class="col-3 col-form-label"><%=of.getWord(DictionaryEntry.OPEN_QUANTITY)%></label>
					<div class="col-9">
						<input class="form-control" disabled type="search"
							value="<%=of.formatWithoutTrailingZeroes(taskDetails.getOutstandingQuantity())%>  <%=taskDetails.getStockUnit()%>"
							id="example-search-input">
					</div>
				</div>
				<div class="form-group row">
					<label for="example-search-input" class="col-3 col-form-label"><%=of.getWord(DictionaryEntry.NAME)%></label>
					<div class="col-9">
						<input class="form-control" disabled type="search"
							value="<%=taskDetails.getProductDescription()%>"
							id="example-search-input">
					</div>
				</div>
				<div class="form-group row">
					<label for="example-search-input" class="col-3 col-form-label"><%=of.getWord(DictionaryEntry.NAME)%>
						2</label>
					<div class="col-9">
						<input class="form-control" disabled type="search"
							value="<%=taskDetails.getProductDescription2()%>"
							id="example-search-input">
					</div>
				</div>
								<div class="form-group row">
									<label for="example-search-input" class="col-3 col-form-label"><%=of.getWord(DictionaryEntry.FAILURE_TYPE)%></label>
									<div class="col-9">
				    					<select onmousedown="suspendListEvent(this)" id="failure-list" name="failure-list" class="custom-select d-block w-100 failure-list" required>
				        					<option value=""><%=of.getWord(DictionaryEntry.SELECT)%></option>
				        					<option class='opt' value='<%=of.getWord(DictionaryEntry.SUSPEND_LIST_ITEM_0)%>'><%=of.getWord(DictionaryEntry.SUSPEND_LIST_ITEM_0)%></option>
				        					<option class='opt' value='<%=of.getWord(DictionaryEntry.SUSPEND_LIST_ITEM_1)%>'><%=of.getWord(DictionaryEntry.SUSPEND_LIST_ITEM_1)%></option>
				        					<option class='opt' value='<%=of.getWord(DictionaryEntry.SUSPEND_LIST_ITEM_2)%>'><%=of.getWord(DictionaryEntry.SUSPEND_LIST_ITEM_2)%></option>
				        					<option class='opt' value='<%=of.getWord(DictionaryEntry.SUSPEND_LIST_ITEM_3)%>'><%=of.getWord(DictionaryEntry.SUSPEND_LIST_ITEM_3)%></option>
				        					<option class='opt' value='<%=of.getWord(DictionaryEntry.SUSPEND_LIST_ITEM_4)%>'><%=of.getWord(DictionaryEntry.SUSPEND_LIST_ITEM_4)%></option>
				        					<option class='opt' value='<%=of.getWord(DictionaryEntry.SUSPEND_LIST_ITEM_5)%>'><%=of.getWord(DictionaryEntry.SUSPEND_LIST_ITEM_5)%></option>
				    					</select>
									</div>
								</div>
				<div class="form-group row">
					<label for="example-search-input" class="col-12 col-form-label"><%=of.getWord(DictionaryEntry.DISRUPTION_REASON)%></label>
					<div class="col-12">
						<textarea class="form-control error-text"
							id="example-search-input" style="height: 150px;"></textarea>
					</div>
				</div>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-secondary w-25"
					 data-dismiss="modal"><%=of.getWord(DictionaryEntry.CANCEL)%></button>
					<button type='button' class='btn btn-primary w-25' onclick='<%=suspendMethod%>' value='<%=workSlipId%>'><%=of.getWord(DictionaryEntry.NEXT)%></button>
			</div>
		</div>
	</div>
</div>
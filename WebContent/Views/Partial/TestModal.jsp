<%@page import="phoenix.mes.abas.AbasConnection"%>
<%@page import="phoenix.mes.content.utility.OutputFormatter"%>
<%@page import="phoenix.mes.content.Log"%>
<%@page import="phoenix.mes.content.controller.User"%>
<%@page import="phoenix.mes.content.utility.OutputFormatter.DictionaryEntry"%>
<%@page import="phoenix.mes.abas.Task"%>
<%@page import="java.util.List"%>
<%@page import="phoenix.mes.abas.Task.Status"%>
<%@page import="phoenix.mes.abas.AbasConnection"%>
<%@page import="phoenix.mes.abas.AbasObjectFactory"%>
<%@page import="phoenix.mes.content.AppBuild"%>
<%@page import="de.abas.ceks.jedp.EDPSession"%>
<%@page import="javax.security.auth.login.LoginException"%>

<%
OutputFormatter of = (OutputFormatter)session.getAttribute("OutputFormatter");

AbasConnection<EDPSession> abasConnection = null;
try {
	User user = new User(request);
	abasConnection = AbasObjectFactory.INSTANCE.openAbasConnection(user.getUsername(), user.getPassword(), of.getLocale(), new AppBuild(request).isTest());
	Task task = (Task)session.getAttribute("Task");
	Task.Details taskDetails = task.getDetails(abasConnection);
	session.setAttribute("Task", task);
	String errorText = "";
	if(taskDetails.getStatus() == Status.INTERRUPTED){
		Log log = new Log(request);
		errorText = log.get(taskDetails.getWorkSlipNo());
	}
	%>
	<div class="modal fade my-fade" id="interrupt-level1" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
  <div class="modal-dialog modal-lg modal-dialog-centered" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLongTitle"><%=of.getWord(DictionaryEntry.DISRUPTION_TITLE)%></h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
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
					<label for="example-search-input" class="col-12 col-form-label"><%=of.getWord(DictionaryEntry.DISRUPTION_REASON)%></label>
					<div class="col-12">
						<textarea class="form-control error-text" type="search" id="example-search-input" style="height: 150px;"></textarea>
					</div>
				</div>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-secondary w-25" onclick='Cancel()'
					data-dismiss="modal"><%=of.getWord(DictionaryEntry.CANCEL)%></button>
				<button type="button" class="btn btn-primary w-25"
					onclick='InterruptTask()'><%=of.getWord(DictionaryEntry.NEXT)%></button>
			</div>
		</div>
  </div>
  <form method='Post' action='<%=response.encodeURL(request.getContextPath()+"/OpenTask")%>' class='d-none interrupt-form'></form>
</div>

<div class="modal fade my-fade" id="interrupt-level2" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
  <div class="modal-dialog modal-lg modal-dialog-centered" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLongTitle"><%=of.getWord(DictionaryEntry.COMMIT)%></h5>

      </div>
			<div class="modal-body pt-1">
				<div class="form-group row">
					<label for="example-search-input"  class="col-12 col-form-label"><%=of.getWord(DictionaryEntry.DISRUPTION_REASON)%></label>
					<div class="col-12">
						<textarea class="form-control error-text-back" disabled type="search" id="example-search-input" style="height: 150px;"><%=errorText %></textarea>
					</div>
				</div>	
				<div class="form-group row">
					<label for="example-search-input" class="col-3 col-form-label"><%=of.getWord(DictionaryEntry.USER_NAME)%></label>
					<div class="col-9">
						<input class="form-control username-input"  type="text"
							value="" id="username-input">
					</div>
				</div>
				<div class="form-group row">
					<label for="example-search-input" class="col-3 col-form-label"><%=of.getWord(DictionaryEntry.PASSWORD)%></label>
					<div class="col-9">
						<input class="form-control password-input"  type="password"
							value="" id="password-input">
					</div>
				</div>

			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-secondary w-25" data-dismiss="modal" onclick='ResumeTask()'><%=of.getWord(DictionaryEntry.CANCEL)%></button>
				<button type="button" class="btn btn-primary w-25"
				onclick='SuspendTask()'><%=of.getWord(DictionaryEntry.NEXT)%></button>
			</div>
		</div>
  </div>
  <form method='Post' action='<%=response.encodeURL(request.getContextPath()+"/OpenTask")%>' class='d-none interrupt-form'></form>
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
<%
}catch(LoginException e )
{
}finally
{
	try {
		if (null != abasConnection) {
			abasConnection.close();
		}
	} catch (Throwable t) {
		
	}
}
%>

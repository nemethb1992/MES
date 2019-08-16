<%@page import="phoenix.mes.content.utility.OutputFormatter"%>
<%@page import="phoenix.mes.content.utility.OutputFormatter.DictionaryEntry"%>
<%@page import="phoenix.mes.abas.Task"%>
<%@page import="phoenix.mes.content.Log"%>
<%@page pageEncoding="UTF-8"%>
<% request.setCharacterEncoding("UTF-8");%>
<%
	String workSlipId = (String)request.getAttribute("workSlipId");
	String errorText = (String)request.getAttribute("errorText");
	OutputFormatter of = (OutputFormatter)request.getAttribute("OutputFormatter");
	Task.Details taskDetails = (Task.Details)request.getAttribute("TaskDetails");
	

%>

<div class="modal fade my-fade" id="SuspendValidationModal" tabindex="-1"
	role="dialog" aria-labelledby="exampleModalCenterTitle"
	aria-hidden="true">
	<div class="modal-dialog modal-lg modal-dialog-centered"
		role="document">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title" id="exampleModalLongTitle"><%=of.getWord(DictionaryEntry.COMMIT)%></h5>

			</div>
			<div class="modal-body pt-1">
				<div class="form-group row">
					<label for="example-search-input" class="col-12 col-form-label"><%=of.getWord(DictionaryEntry.DISRUPTION_REASON)%></label>
					<div class="col-12">
						<textarea class="form-control error-text" readonly
							id="example-search-input" style="height: 150px;"><%=errorText%></textarea>
					</div>
				</div>
				<div class="form-group row">
					<label for="example-search-input" class="col-3 col-form-label"><%=of.getWord(DictionaryEntry.USER_NAME)%></label>
					<div class="col-9">
						<input class="form-control username-input"  autocomplete="off" type="text" value=""
							id="username-input">
					</div>
				</div>
				<div class="form-group row">
					<label for="example-search-input" class="col-3 col-form-label"><%=of.getWord(DictionaryEntry.PASSWORD)%></label>
					<div class="col-9">
						<input class="form-control password-input"  autocomplete="off" type="password"
							value="" id="password-input">
					</div>
				</div>

			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-secondary w-25"
					data-dismiss="modal" onclick='ResumeTask(this)' value='<%=workSlipId%>'><%=of.getWord(DictionaryEntry.CANCEL)%></button>
				<button type="button" class="btn btn-primary w-25"
					onclick='SuspendTaskFromOperator(this, true)' value='<%=workSlipId%>'><%=of.getWord(DictionaryEntry.NEXT)%></button>
			</div>
		</div>
	</div>
<!-- 	<form method='Post' -->
<%-- 		action='<%=response.encodeURL(request.getContextPath() + "/OpenTask")%>' --%>
<!-- 		class='d-none interrupt-form'></form> -->
</div>
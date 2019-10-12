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
<script>
$(document).ready(function(){
	passwordConfigure();
});
var showLength = 1;
var delay = 800;
var hideAll = setTimeout(function() {}, 0);

function passwordConfigure(){
	  $("#inp_pass").bind("paste keyup input", function() {
		    var offset = $("#inp_pass").val().length - $("#hidden").val().length;
		    if (offset > 0) $("#hidden").val($("#hidden").val() + $("#inp_pass").val().substring($("#hidden").val().length, $("#hidden").val().length + offset));
		    else if (offset < 0) $("#hidden").val($("#hidden").val().substring(0, $("#hidden").val().length + offset));

		    // Change the visible string
		    if ($(this).val().length > showLength) $(this).val($(this).val().substring(0, $(this).val().length - showLength).replace(/./g, "*") + $(this).val().substring($(this).val().length - showLength, $(this).val().length));

		    // Set the timer
		    clearTimeout(hideAll);
		    hideAll = setTimeout(function() {
		      $("#inp_pass").val($("#inp_pass").val().replace(/./g, "*"));
		    }, delay);
		  });
}
</script>
<div class="modal fade my-fade" id="SuspendValidationModal" tabindex="-1"
	role="dialog" aria-labelledby="exampleModalCenterTitle"
	aria-hidden="true">
	<div class="modal-dialog modal-lg modal-dialog-centered"
		role="document">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title float-left" id="exampleModalLongTitle "><%=of.getWord(DictionaryEntry.COMMIT)%></h5>
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
<!-- 						<input class="form-control password-input true-pass-inp"  autocomplete="off" type="password" -->
<!-- 							value="" id="password-input"> -->
<!-- 						<input class="form-control password-input fake-pass-inp"  autocomplete="off" type="password" -->
<!-- 							value="" id="password-input"> -->
					<input type="password" autocomplete="off"
						class=' form-control password-input px-3 w-100 true-pass-inp' value=""
						name='password' id="hidden" /> 
					<input type='text' autocomplete="off"
						class='form-control password-input px-3 w-100  fake-pass-inp' value=''
						name="shownPassword" id='inp_pass' >
					</div>
				</div>
				<div class="form-check ml-5 mt-2 float-right">
						<input class="form-check-input half-view-check" type="checkbox"
							id="disabledFieldsetCheck" onchange='passPreCheckbox()'>
						<label class="form-check-label h5" for="disabledFieldsetCheck"><%=of.getWord(DictionaryEntry.PASSWORD_ASSISSTANT)%></label>
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
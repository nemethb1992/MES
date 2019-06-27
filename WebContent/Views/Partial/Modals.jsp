<%@page import="phoenix.mes.abas.AbasConnection"%>
<%@page import="phoenix.mes.content.utility.OutputFormatter"%>
<%@page import="phoenix.mes.content.utility.OutputFormatter.DictionaryEntry"%>
<%@page import="phoenix.mes.abas.Task"%>
<%@page import="java.util.List"%>
<%@page import="phoenix.mes.abas.GenericTask.Status"%>
<%@page import="phoenix.mes.abas.AbasConnection"%>
<%@page import="phoenix.mes.abas.AbasObjectFactory"%>
<%@page import="phoenix.mes.content.AppBuild"%>
<%@page import="de.abas.ceks.jedp.EDPSession"%>

<%
OutputFormatter of = (OutputFormatter)session.getAttribute("OutputFormatter");
Task task = (Task)session.getAttribute("Task");
AbasConnection abasConnection = AbasObjectFactory.INSTANCE.openAbasConnection((String)session.getAttribute("username"), (String)session.getAttribute("pass"), of.getLocale(), new AppBuild(request).isTest());
Task.Details taskDetails = task.getDetails(abasConnection);
%>
<div class="modal fade" id="interrupt-level1" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
  <div class="modal-dialog modal-lg modal-dialog-centered" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLongTitle">Biztosan megszakítja a feladatot?</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
			<div class="modal-body">
				<div class="form-group row">
					<label for="example-search-input" class="col-2 col-form-label"><%=of.getWord(DictionaryEntry.NAME)%></label>
					<div class="col-10">
						<input class="form-control" disabled type="search"
							value="<%=taskDetails.getProductDescription()%>" id="example-search-input">
					</div>
				</div>
				<div class="form-group row">
					<label for="example-search-input" class="col-2 col-form-label"><%=of.getWord(DictionaryEntry.NAME)%> 2</label>
					<div class="col-10">
						<input class="form-control" disabled type="search"
							value="<%=taskDetails.getProductDescription2()%>" id="example-search-input">
					</div>
				</div>
				<div class="form-group row">
					<label for="example-search-input" class="col-2 col-form-label"><%=of.getWord(DictionaryEntry.ARTICLE)%></label>
					<div class="col-10">
						<input class="form-control" disabled type="search"
							value="<%=taskDetails.getProductIdNo()%>" id="example-search-input">
					</div>
				</div>
				<div class="form-group row">
					<label for="example-search-input" class="col-2 col-form-label"><%=of.getWord(DictionaryEntry.SEARCH_WORD)%></label>
					<div class="col-10">
						<input class="form-control" disabled type="search"
							value="<%=taskDetails.getProductSwd()%>" id="example-search-input">
					</div>
				</div>
				<div class="form-group row">
					<label for="example-search-input" class="col-2 col-form-label"><%=of.getWord(DictionaryEntry.OPEN_QUANTITY)%></label>
					<div class="col-10">
						<input class="form-control" disabled type="search"
							value="<%=of.formatWithoutTrailingZeroes(taskDetails.getOutstandingQuantity())%>  <%=taskDetails.getStockUnit()%>" id="example-search-input">
					</div>
				</div>
				<div class="form-group row">
					<label for="example-search-input" class="col-2 col-form-label">ttt</label>
					<div class="col-10">
						<input class="form-control" disabled type="search"
							value="ttt" id="example-search-input">
					</div>
				</div>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-secondary" data-dismiss="modal">Bezárás</button>
				<button type="button" class="btn btn-primary"
					onclick='InterruptTask()'>Megszakítás</button>
			</div>
		</div>
  </div>
  <form method='Post' action='${pageContext.request.contextPath}/OpenTask' class='d-none interrupt-form'></form>
</div>
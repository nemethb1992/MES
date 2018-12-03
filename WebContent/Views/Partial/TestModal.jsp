<%@page import="phoenix.mes.abas.AbasConnection"%>
<%@page import="phoenix.mes.content.utility.OutputFormatter"%>
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
	String username = (String)session.getAttribute("username");
	String pass = (String)session.getAttribute("pass");
	abasConnection = AbasObjectFactory.INSTANCE.openAbasConnection((String)session.getAttribute("username"), (String)session.getAttribute("pass"), of.getLocale(), new AppBuild(request).isTest());
	Task task = (Task)session.getAttribute("Task");
	session.setAttribute("Task", task);
	

}catch(LoginException e)
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
<div class="modal fade my-fade" id="interrupt-level1" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
  <div class="modal-dialog modal-lg modal-dialog-centered" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLongTitle">Biztosan megszak�tja a feladatot?</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
			<div class="modal-body">
				<div class="form-group row">
					<label for="example-search-input" class="col-3 col-form-label"><%=of.getWord(DictionaryEntry.ARTICLE)%></label>
					<div class="col-9">
						<input class="form-control" disabled type="search"
							value="" id="example-search-input">
					</div>
				</div>
				<div class="form-group row">
					<label for="example-search-input" class="col-3 col-form-label"><%=of.getWord(DictionaryEntry.SEARCH_WORD)%></label>
					<div class="col-9">
						<input class="form-control" disabled type="search"
							value="" id="example-search-input">
					</div>
				</div>
				<div class="form-group row">
					<label for="example-search-input" class="col-3 col-form-label"><%=of.getWord(DictionaryEntry.NAME)%></label>
					<div class="col-9">
						<input class="form-control" disabled type="search"
							value="" id="example-search-input">
					</div>
				</div>
				<div class="form-group row">
					<label for="example-search-input" class="col-3 col-form-label"><%=of.getWord(DictionaryEntry.NAME)%> 2</label>
					<div class="col-9">
						<input class="form-control" disabled type="search"
							value="" id="example-search-input">
					</div>
				</div>
				<div class="form-group row">
					<label for="example-search-input" class="col-3 col-form-label"><%=of.getWord(DictionaryEntry.OPEN_QUANTITY)%></label>
					<div class="col-9">
						<input class="form-control" disabled type="search"
							value="" id="example-search-input">
					</div>
				</div>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-secondary" onclick='Cancel()' data-dismiss="modal">M�gsem</button>
				<button type="button" class="btn btn-primary"
					onclick='InterruptTask()'>Igen</button>
			</div>
		</div>
  </div>
  <form method='Post' action='${pageContext.request.contextPath}/OpenTask' class='d-none interrupt-form'></form>
</div>

<div class="modal fade my-fade" id="interrupt-level2" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
  <div class="modal-dialog modal-lg modal-dialog-centered" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLongTitle">Hiteles�tse mag�t!</h5>

      </div>
			<div class="modal-body">
				<div class="form-group row">
					<label for="example-search-input" class="col-3 col-form-label"><%=of.getWord(DictionaryEntry.USER_NAME)%></label>
					<div class="col-9">
						<input class="form-control username-input"  type="text"
							value="" id="example-search-input">
					</div>
				</div>
				<div class="form-group row">
					<label for="example-search-input" class="col-3 col-form-label"><%=of.getWord(DictionaryEntry.PASSWORD)%></label>
					<div class="col-9">
						<input class="form-control password-input"  type="password"
							value="" id="example-search-input">
					</div>
				</div>

			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-secondary" data-dismiss="modal" onclick='ResumeTask()'>Visszavon�s</button>
				<button type="button" class="btn btn-primary"
				onclick='SuspendTask()'>Megszak�t�s</button>
			</div>
		</div>
  </div>
  <form method='Post' action='${pageContext.request.contextPath}/OpenTask' class='d-none interrupt-form'></form>
</div>
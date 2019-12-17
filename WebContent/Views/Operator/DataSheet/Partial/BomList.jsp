<%@page import="phoenix.mes.content.utility.OutputFormatter"%>
<%@page
	import="phoenix.mes.content.utility.OutputFormatter.DictionaryEntry"%>
<%@page import="java.util.List"%>
<%@page import="phoenix.mes.abas.GenericTask.BomElement"%>
<%@page import="phoenix.mes.content.controller.User"%>
<%@page import="phoenix.mes.abas.AbasConnection"%>
<%@page import="phoenix.mes.abas.AbasObjectFactory"%>
<%@page import="phoenix.mes.abas.Task"%>
<%@page import="javax.security.auth.login.LoginException"%>
<%@page import="phoenix.mes.content.Log"%>
<%@page import="phoenix.mes.content.Log.FaliureType"%>
<%@page import="java.sql.SQLException"%>
<%
	OutputFormatter of = (OutputFormatter) request.getAttribute("OutputFormatter");
	Task task = (Task) request.getAttribute("Task");
	User user = (User) request.getAttribute("User");
	boolean isTest = (boolean) request.getAttribute("isTest");
	String taskId = (String) request.getAttribute("taskId");
	
	AbasConnection abasConnection= null;
	Task.Details taskDetails = null;
	try {
		abasConnection = AbasObjectFactory.INSTANCE.openAbasConnection(user.getUsername(),
				user.getPassword(), of.getLocale(), isTest);
		taskDetails = task.getDetails(abasConnection);

	List<BomElement> li = taskDetails.getBom();
%>

<ul class='pt-2 '>

		<%
			for (BomElement bomItem : li) {
		%>
	<li
		class="dnd-container station-list-item sort-list-holder  list-content-row list-group col-12 px-0 mb-2">
		<div class="container-fluid station-list-table-operator">
			<div class="row ">
				<div class="col ">
					<div class="row">
						<table
							class="table station-list-table  mb-0">
							<thead>
								<tr>
									<th style="border: transparent" scope="col"><%=of.getWord(DictionaryEntry.ARTICLE)%></th>
									<th style="border: transparent" scope="col"><%=of.getWord(DictionaryEntry.SEARCH_WORD)%></th>
									<th style="border: transparent" scope="col"><%=of.getWord(DictionaryEntry.NAME)%> 1</th>
									<th style="border: transparent" scope="col"><%=of.getWord(DictionaryEntry.NAME)%> 2</th>
									<th style="border: transparent" scope="col"><%=of.getWord(DictionaryEntry.PLUG_IN_QUANTITY)%></th>
									<th style="border: transparent" scope="col"><%=of.getWord(DictionaryEntry.WAREHOUSE)%></th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td><%=bomItem.getIdNo()%></td>
									<td><%=bomItem.getSwd()%></td>
									<td><%=bomItem.getDescription()%></td>
									<td><%=bomItem.getDescription2()%></td>
									<td><%=of.formatWithoutTrailingZeroes(bomItem.getQuantityPerProduct())%> <%=bomItem.getStockUnit()%></td>
									<td><%=bomItem.getWarehouseLocation()%></td>
								</tr>
							</tbody>
						</table>
					</div>
					<div class="row">
						<div class='col pb-1'>
							<div class='row bom-item-text-row'
								onclick='bomListDropDown(this)'>
								<div class='col-12'>
									<textarea class='w-100 h-100 item-text-textarea' readonly><%=bomItem.getItemText()%></textarea>
								</div>
							</div>
						</div>

					</div>
				</div>
			</div>
		</div>
	</li>

	<%
		}
	%>
</ul>



<%
	} catch (LoginException e) {
		try {
			String stackTrace = Log.getStackTraceString(e);
			new Log(request).logFaliure((user == null? "null" : user.getUsername()), FaliureType.TASK_DATA_LOAD,stackTrace, e.toString(), taskId);
		} catch (SQLException exc) {
		}
	} finally {
		try {
			if (null != abasConnection) {
				abasConnection.close();
			}
		} catch (Throwable t) {
		}
	}
%>
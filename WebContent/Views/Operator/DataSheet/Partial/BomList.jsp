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

<div class='row bom-list-full-container h-100'>

	<div class='col-12'>
		<div class='row bom-header-row p-2'>
			<div class='col'>
				<input class='w-100 header-label' readonly
					value='<%=of.getWord(DictionaryEntry.ARTICLE)%>'>
			</div>
			<div class='col'>
				<input class='w-100 header-label' readonly
					value='<%=of.getWord(DictionaryEntry.SEARCH_WORD)%>'>
			</div>
			<div class='col-4'>
				<input class='w-100 header-label' readonly
					value='<%=of.getWord(DictionaryEntry.NAME)%> 1'>
			</div>
			<div class='col'>
				<input class='w-100 header-label' readonly
					value='<%=of.getWord(DictionaryEntry.NAME)%> 2'>
			</div>
			<div class='col'>
				<input class='w-100 header-label' readonly
					value='<%=of.getWord(DictionaryEntry.PLUG_IN_QUANTITY)%>'>
			</div>
			<div class='col'>
				<input class='w-100 header-label' readonly
					value='<%=of.getWord(DictionaryEntry.WAREHOUSE)%>'>
			</div>
		</div>
		<%
			for (BomElement bomItem : li) {
		%>
		<div class='row bom-item-row px-2 pb-2'>
<!-- 			<div class='position-absolute bg-transparent w-100' -->
<!-- 				style="height: 65px; z-index: 1000;"></div> -->
			<div class='col-12 bom-container'>
				<div class='row item-data-row py-2'>
					<div class='col sheet-col-small'>
						<textarea class='w-100 sheet-input-textarea' readonly><%=bomItem.getIdNo()%></textarea>
					</div>
					<div class='col'>
						<textarea class='w-100 sheet-input-textarea' readonly><%=bomItem.getSwd()%></textarea>
					</div>
					<div class='col-4'>
						<textarea class='w-100 sheet-input-textarea' readonly><%=bomItem.getDescription()%></textarea>
					</div>
					<div class='col'>
						<textarea class='w-100 sheet-input-textarea' readonly><%=bomItem.getDescription2()%></textarea>
					</div>
					<div class='col sheet-col-small'>
						<textarea class='w-100 sheet-input-textarea' readonly><%=of.formatWithoutTrailingZeroes(bomItem.getQuantityPerProduct())%> <%=bomItem.getStockUnit()%></textarea>
					</div>
					<div class='col sheet-col-small'>
						<textarea class='w-100 sheet-input-textarea' readonly><%=bomItem.getWarehouseLocation()%></textarea>
					</div>
				</div>
				<div class='row bom-item-text-row'  onclick='bomListDropDown(this)'>
					<div class='col-12'>
						<textarea class='w-100 h-100 item-text-textarea' readonly><%=bomItem.getItemText()%></textarea>
					</div>
				</div>
			</div>
		</div>
		<%
			}
		%>
	</div>
</div>
<%
	} catch (LoginException e) {
		try {
			new Log(request).logFaliure(FaliureType.TASK_DATA_LOAD, e.getMessage(), taskId);
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
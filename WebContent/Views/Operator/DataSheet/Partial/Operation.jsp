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
    <tr>
      <th scope="row"><%=of.getWord(DictionaryEntry.WORK_INSTRUCTIONS)%>:</th>
      <td colspan="3"><%=taskDetails.getWorkInstruction()%></td>
    </tr>
  </tbody>
</table>
		
		</div>
</div>

</div>
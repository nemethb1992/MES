<%@page import="phoenix.mes.content.utility.OutputFormatter"%>
<%@page import="phoenix.mes.content.controller.Workstation"%>
<%@page import="phoenix.mes.content.utility.OutputFormatter.DictionaryEntry"%>
<%@page import="phoenix.mes.abas.Task"%>
<%@page import="java.util.List"%>
<%@page import="phoenix.mes.abas.GenericTask.BomElement"%>
<%@page import="phoenix.mes.abas.GenericTask.Characteristic"%>

<%
	OutputFormatter of = (OutputFormatter)session.getAttribute("OutputFormatter");
	Task.Details taskDetails = (Task.Details)request.getAttribute("taskDetails");
%>
<div class='container-fluid h-100 px-0 py-3'>

<div class="row">
		<div class="col">
		
		<table class="table table-bordered sheet-table mpj-table">
  <tbody>
  
  <%
  List<Characteristic> data = taskDetails.getCharacteristicsBar();
  for (Characteristic item : data)
  {
  %>
    <tr>
      <th scope="row" class="w-25"><%=item.getName()%>:</th>
      <td><%=item.getValue()%> <%=(item.getValue() != null & item.getValue() != "" ? item.getUnit() : "") %></td>
    </tr>
    <%} %>
  </tbody>
</table>
		
		</div>
</div>

</div>
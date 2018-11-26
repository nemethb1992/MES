<%@page import="phoenix.mes.content.OutputFormatter"%>
<%@page import="phoenix.mes.content.OutputFormatter.DictionaryEntry"%>
<%@page import="java.util.List"%>
<%@page import="phoenix.mes.abas.Task.BomElement"%>
<%
	OutputFormatter of = (OutputFormatter)session.getAttribute("OutputFormatter");
	List<BomElement> li = (List<BomElement>)request.getAttribute("bomdata");
%>

<div class='row bom-list-full-container h-100'>

	<div class='col-12'>
		<div class='row bom-header-row p-2'>
			<div class='col-2'>
				<input class='w-100' disabled
					value='<%=of.getWord(DictionaryEntry.ARTICLE)%>'>
			</div>
			<div class='col-2'>
				<input class='w-100' disabled
					value='<%=of.getWord(DictionaryEntry.SEARCH_WORD)%>'>
			</div>
			<div class='col-3'>
				<input class='w-100' disabled
					value='<%=of.getWord(DictionaryEntry.NAME)%> 1'>
			</div>
			<div class='col-3'>
				<input class='w-100' disabled
					value='<%=of.getWord(DictionaryEntry.NAME)%> 2'>
			</div>
			<div class='col-2'>
				<input class='w-100' disabled
					value='<%=of.getWord(DictionaryEntry.PLUG_IN_QUANTITY)%>'>
			</div>
		</div>
		<%
			for (BomElement bomItem : li) {
		%>
		<div class='row bom-item-row p-2' onclick='bomListDropDown(this)'>
			<div class='col-12'>
				<div class='row item-data-row py-2'>
					<div class='col-2'>
						<input class='w-100' disabled value='<%=bomItem.getIdNo()%>'>
					</div>
					<div class='col-2'>
						<input class='w-100' disabled value='<%=bomItem.getSwd()%>'>
					</div>
					<div class='col-3'>
						<input class='w-100' disabled
							value='<%=bomItem.getDescription()%>'>
					</div>
					<div class='col-3'>
						<input class='w-100' disabled
							value='<%=bomItem.getDescription2()%>'>
					</div>
					<div class='col-2'>
						<input class='w-100' disabled
							value='<%=of.formatWithoutTrailingZeroes(bomItem.getQuantityPerProduct())%>  <%=bomItem.getStockUnit()%>'>
					</div>
				</div>
				<div class='row bom-item-text-row'>
					<div class='col-12'>
						<textarea class='w-100 h-100 item-text-textarea'><%=bomItem.getItemText()%></textarea>
					</div>
				</div>
			</div>
		</div>
		<%
			}
		%>
	</div>
</div>
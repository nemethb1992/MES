<%@page import="phoenix.mes.content.utility.OutputFormatter"%>
<%@page import="phoenix.mes.content.utility.OutputFormatter.DictionaryEntry"%>
<%
	OutputFormatter of = (OutputFormatter) session.getAttribute("OutputFormatter");
%>


<div class='list-group row dokumentum-list'>
	<button type='button' class='list-group-item list-group-item-action active disabled'><h4 class='mb-0'><%=of.getWord(DictionaryEntry.DOCUMENTS)%></h4></button>
<%-- 		<button type='button' onclick='openAsset(this)' value='${pageContext.request.contextPath}/Public/pdf/Csomagol�s �ltal�nos AA55.pdf' class='document-button list-group-item list-group-item-action'>Csomagol�s �ltal�nos AA55</button> --%>
<%-- 		<button type='button' onclick='openAsset(this)' value='${pageContext.request.contextPath}/Public/pdf/El��r�s s�ly csomag AA56.pdf' class='document-button list-group-item list-group-item-action'>El��r�s s�ly csomag AA56</button> --%>
<%-- 		<button type='button' onclick='openAsset(this)' value='${pageContext.request.contextPath}/Public/pdf/Megb�z�s 413741RK.pdf' class='document-button list-group-item list-group-item-action'>Megb�z�s 413741RK</button> --%>
<%-- 		<button type='button' onclick='openAsset(this)' value='${pageContext.request.contextPath}/Public/pdf/Rajz 72130130350 KON.pdf' class='document-button list-group-item list-group-item-action'>Rajz 72130130350 KON</button> --%>
<%-- 		<button type='button' onclick='openAsset(this)' value='${pageContext.request.contextPath}/Public/pdf/Rajz 72130130350.pdf' class='document-button list-group-item list-group-item-action'>Rajz 72130130350</button> --%>
<%-- 		<button type='button' onclick='openAsset(this)' value='${pageContext.request.contextPath}/Public/pdf/Rajz A0740008_01_054.pdf' class='document-button list-group-item list-group-item-action'>Rajz A0740008_01_054</button> --%>
<%-- 		<button type='button' onclick='openAsset(this)' value='${pageContext.request.contextPath}/Public/pdf/Rajz SQB0331110342.pdf' class='document-button list-group-item list-group-item-action'>Rajz SQB0331110342</button> --%>
<%-- 		<button type='button' onclick='openAsset(this)' value='${pageContext.request.contextPath}/Public/pdf/Rajz X095502270579.pdf' class='document-button list-group-item list-group-item-action'>Rajz X095502270579</button> --%>
<%-- 		<button type='button' onclick='openAsset(this)' value='${pageContext.request.contextPath}/Public/pdf/Rajz X095502280534.pdf' class='document-button list-group-item list-group-item-action'>Rajz X095502280534</button> --%>
<%-- 		<button type='button' onclick='openAsset(this)' value='${pageContext.request.contextPath}/Public/pdf/Szerel�si utas�t�s AA18.pdf' class='document-button list-group-item list-group-item-action'>Szerel�si utas�t�s AA18</button> --%>


</div>
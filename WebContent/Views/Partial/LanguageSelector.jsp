<%String pageValue = (String)request.getAttribute("page"); %>
<div id="lang_div">
	<button onclick="selectLanguage(this)" value="<%=pageValue %>" class="lang_bub" id="de">
		<img class="lang_icon" src="${pageContext.request.contextPath}/Public/icons/DE.svg">
	</button>
	<button onclick="selectLanguage(this)" value="<%=pageValue %>" class="lang_bub" id="hu">
		<img class="lang_icon" src="${pageContext.request.contextPath}/Public/icons/HU.svg">
	</button>
</div>


<form method='POST' class='h-100 language-form d-none' action='<%=response.encodeURL(request.getContextPath() + "/"+pageValue) %>'></form>
	<%String uri = (String)request.getAttribute("URI"); %>
	<%String name = (String)request.getAttribute("name"); %>
	
<div class="col-6 document-col pl-0">
	<div class="row h-100">
		<div class="col-1 pr-0">
			<input type='button' onclick='closeSideView()' class='side-view-close-btn w-100 h-100'/>
		</div>
		<div class="col-11 px-0">
			<object
				data="<%=response.encodeURL(request.getContextPath() + "/GetPdf?uri="+uri+"")%>"
				type="application/pdf" width="100%" height="100%"></object>
		</div>
	</div>
</div>
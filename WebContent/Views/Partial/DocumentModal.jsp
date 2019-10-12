

	<%String uri = (String)request.getAttribute("URI"); %>
	<%String name = (String)request.getAttribute("name"); %>

<div class="modal fade my-fade" id="DocumentModal" tabindex="-1"
	role="dialog" aria-labelledby="exampleModalCenterTitle"
	aria-hidden="true">
	<div class="modal-dialog modal-lg modal-dialog-centered my-0 document-modal"
		role="document">
		<div class="modal-content" style="height: 100vh;">
			<div class="modal-header">
				<h5 class="modal-title" id="exampleModalLongTitle"><%=name%></h5>
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
			</div>
<object
  data="<%=response.encodeURL(request.getContextPath()+"/GetPdf?uri="+uri)%>"
  type="application/pdf"
  width="100%"
  height="100%">
</object>
<!--   <iframe -->
<%--     src="data:application/pdf;base64,<%=pdf%>" --%>
<!--     width="100%" -->
<!--     height="100%" -->
<!--     style="border: none;"> -->
<!--   </iframe> -->
		</div>
	</div>
</div>
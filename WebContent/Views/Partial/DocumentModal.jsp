

	<%String uri = (String)request.getAttribute("URI"); %>
	<%String name = (String)request.getAttribute("name"); %>
	<%String pdf = (String)request.getAttribute("pdf"); %>

<div class="modal fade my-fade" id="DocumentModal" tabindex="-1"
	role="dialog" aria-labelledby="exampleModalCenterTitle"
	aria-hidden="true">
	<div class="modal-dialog modal-lg modal-dialog-centered my-0"
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
  data="data:application/pdf;base64,<%=pdf%>"
  type="application/pdf"
  width="100%"
  height="100%">
  <iframe
    src="data:application/pdf;base64,<%=pdf%>"
    width="100%"
    height="100%"
    style="border: none;">
  </iframe>
</object>
		</div>
	</div>
</div>
<%@include file="/Views/Header.jsp"%>
<script>
	
<%-- <%@ include file="/Views/Operator/Operator.js"%> --%>
	
</script>
<div class='container-fluid h-100'>
	<div class='row opentask-row'>
		<div class='col-12  px-0 topNav'>
			<div class='h-100 w-100 mx-0 row'>
				<div class='h-100 col col-nav-logo float-left'></div>
				<div
					class='top-nav-button px-0 d-block col col-nav-logout h-100 float-right'>
					<form method='POST' class='h-100'
						action='<%=response.encodeURL(request.getContextPath()+"/Logout")%>'>
						<input class='btn_logout-gray' type='submit' value='' />
					</form>
				</div>
			</div>
		</div>
	</div>
	<p class='actual-time actual-time-tv fixed-bottom my-0 py-4 h5'></p>
	<div class='row h-25'></div>
	<div class='row'>
		<div class='col-4 offset-4 h-100'>
			<form method='POST' class='h-100 '
				action='<%=response.encodeURL(request.getContextPath() + "/DataSheet") %>'>
				<input hidden name='firstTask' value='true' />
				<input class='enter-btn w-100 light-shadow' type='submit'
					value='<%=outputFormatter.getWord(DictionaryEntry.TASK_START)%>' />
			</form>
		</div>
	</div>
</div>
<%@include file="/Views/Footer.jsp"%>
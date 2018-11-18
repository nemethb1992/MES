<%@include file="/Views/Header.jsp"%>
<script>

<%@ include file="/Views/Manager/Main/Main.js"%>
	
</script>
<div class='container-fluid h-100'>
	<p class='actual-time h5'></p>
	<div class='row'>
		<div class='col-12  px-0 topNav'>
			<div class='h-100 w-100 mx-0 row'>
				<div class='h-100 col col-nav-logo float-left'>
					<img
						src="${pageContext.request.contextPath}/Public/icons/pm_logo_normal.svg"
						class='d-block m-3 img-fluid LogoMiniPM' />
				</div>
				<div
					class='top-nav-button px-0 d-block col col-nav-logout h-100 float-right'>
					<form method='POST' class='h-100'
						action='${pageContext.request.contextPath}/Logout'>
						<input class='btn_logout-gray' type='submit' value='' />
					</form>
				</div>
			</div>
		</div>
		</div>
		<div class='row h-75'>
			<div id='TM_Select_container_activity' class='select-panel container px-4'>
			<div class='row align-items-center h-100'>
				<div class='col-12 col-md-6'>
					<form id='btn_select_1' method='POST' action='${pageContext.request.contextPath}/Todo'
						class='activity-button light-shadow btnstyle mx-auto '>
						<img
							src='${pageContext.request.contextPath}/Public/icons/btn_wscontrol.svg'
							class='mx-auto d-block'>
						<p id='activity_tab1'><%=outputFormatter.getWord(DictionaryEntry.TASKS)%></p>
					</form>
				</div>
<!-- 				<div class='col-12 col-md-4'> -->
<%-- 					<form id='btn_select_2' method='POST' action='${pageContext.request.contextPath}/StationActivity' --%>
<!-- 						class='activity-button light-shadow btnstyle mx-auto '> -->
<!-- 						<img -->
<%-- 							src='${pageContext.request.contextPath}/Public/icons/btn_vision.svg' --%>
<!-- 							class='mx-auto d-block'> -->
<%-- 						<p id='activity_tab2'><%=outputFormatter.getWord(DictionaryEntry.STATIONS)%></p> --%>
<!-- 					</form> -->
<!-- 				</div> -->
				<div class='col-12 col-md-6'>
					<form id='btn_select_3' method='POST' action='${pageContext.request.contextPath}/Settings'
						class='activity-button light-shadow btnstyle mx-auto '>
						<img
							src='${pageContext.request.contextPath}/Public/icons/btn_settings.svg'
							class='mx-auto d-block'>
						<p id='activity_tab3'><%=outputFormatter.getWord(DictionaryEntry.OPTIONS)%></p>
					</form>
				</div>
			</div>
			</div>
		</div>
	</div>

<%@include file="/Views/Footer.jsp"%>

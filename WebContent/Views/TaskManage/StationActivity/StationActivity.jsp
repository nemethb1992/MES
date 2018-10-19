<%@include file="/Views/Header.jsp"%>
<script>
	
<%@ include file="/Views/TaskManage/taskManageScript.js"%>
	
</script>
<div class='container-fluid h-100'>

	<p class='actual-time w-25 actual-time-tm fixed-top my-0 py-4 h5'></p>
	<div class='row'>
		<div class='col-12  px-0 topNav'>
			<div class='h-100 w-100 mx-0 row'>
				<div class='h-100 col col-nav-logo float-left'>
					<img
						src="${pageContext.request.contextPath}/Public/icons/pm_logo_normal.svg"
						class='d-block m-3 img-fluid LogoMiniPM' />
				</div>
				<div class='top-nav-button h-100 px-0 col-nav-back float-right'>
					<form method='POST' class='h-100'
						action='${pageContext.request.contextPath}/Main'>
						<input class='btn_back-gray' type='submit' value='' />
					</form>
				</div>
				<div
					class='top-nav-button px-0 d-block col col-nav-logout h-100 float-right'>
					<form method='POST' class='h-100'
						action='${pageContext.request.contextPath}/Home'>
						<input class='btn_logout-gray' type='submit' value='' />
					</form>
				</div>
			</div>
		</div>
	</div>
	<div id='stationVisionHolder row'>

		<div class='light-shadow TV_stationContainer col-3'>
			<div class='row h-100'>
				<div class='col-8 computer-data'>
					<div class="container-fluid">
						<div class="row pt-2 px-0">
							<div class="col-3 px-0">
								<img class='computer-icon'
									src='${pageContext.request.contextPath}/Public/icons/computerSign.svg'>
							</div>
							<div class="col-9 px-0">
								<div class="form-group mb-1">
									<p class='titleStyle'><%=outputFormatter.getWord(DictionaryEntry.STATION)%></p>
									<input disabled value='testtext'>
								</div>
							</div>
						</div>
					</div>
					<div class="form-group mb-1">
						<p><%=outputFormatter.getWord(DictionaryEntry.TASK)%></p>
						<input disabled value='testtext'>
					</div>
					<div class="form-group mb-1">
						<p><%=outputFormatter.getWord(DictionaryEntry.START_TIME)%></p>
						<input disabled value='testtext'>
					</div>
					<div class="form-group mb-1">
						<p><%=outputFormatter.getWord(DictionaryEntry.END_TIME)%></p>
						<input disabled value='testtext'>
					</div>
				</div>
				<div id='TV_timerContainer' class='col-4 my-3 px-0'>
					<img class='mx-auto pt-4'
						src='${pageContext.request.contextPath}/Public/icons/visionwatch.svg'>
					<p id='timerP' class='text-center'>13:53:10</p>
				</div>
			</div>
		</div>
	
	</div>
</div>

<%@include file="/Views/Footer.jsp"%>
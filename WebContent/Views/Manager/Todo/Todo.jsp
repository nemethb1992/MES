<%@include file="/Views/Header.jsp"%>
<script>
	
<%@ include file="/Views/Manager/Todo/Todo.js"%>
	
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
				<div class='top-nav-button h-100 px-0 col-nav-back float-right'>
					<form method='POST' class='h-100'
						action='${pageContext.request.contextPath}/Main'>
						<input class='btn_back-gray' type='submit' value='' />
					</form>
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
		<!-- 			<img -->
		<%-- 				src="${pageContext.request.contextPath}/Public/icons/pm_logo_mini_white.svg" --%>
		<!-- 				class='row d-block m-3 img-fluid LogoMiniPM float-left' /> -->
		<!-- 			<form method='POST' class='h-100 float-right' -->
		<%-- 				action='${pageContext.request.contextPath}/Home'> --%>
		<!-- 				<input class='btn_logout' type='submit' value='' /> -->
		<!-- 			</form> -->
	</div>
	<div class='row my-lower-row'>
		<div class=' col-2 px-0'>
			<div class='container-fluid'>
				<div class='row light-shadow'>
					<div class='cont_header col-12 px-0'>
						<div class='input-icon-holderDiv float-right h-100'>
							<div class='refresh_btn h-100'>
								<img class='h-100 float-left p-3' src='${pageContext.request.contextPath}/Public/icons/reloadGray.svg'>
							</div>
						</div>
					</div>
					<div class='col-12 px-0'>
						<div class='station-container row px-3'></div>
					</div>
				</div>
			</div>
		</div>
		<div class='sortContDiv col-10 pr-0'>
			<div class='sortStationListCont container-fluid'>
				<div class='sortContDiv_nav row px-0 light-shadow'>
					<div class='abas-list-head sort-list-head sort-list h-100 col-5'>
						<div class='row h-100'>
							<div class='py-2 col-12'>
								<div class='row h-100 py-1'>
									<div class='col-10'>
										<input data-date-format="yyyy/mm/dd"
											class='datepicker_own w-100 h-100' id="datepicker">
										<!-- 													<input class='datepicker_own w-100 h-100' type='date' style='font-size: 17px;'/> -->
									</div>
									<div class='col-2 px-0'>
										<button class='date-button date-null h-100 w-100'></button>
									</div>
								</div>
							</div>
							<div class='input-icon-holderDiv  col px-0'></div>
						</div>
					</div>
					<div class='ws-list-head sort-list-head sort-list col-7 px-0 h-100'>
						<div class='row h-100 mx-0'>
							<div
								class='input-icon-holderDiv stationName-holderDiv col-8 h-100'>
								<input disabled class='ts_wsNameInp station_label h-100  p-3'
									value='<%=outputFormatter.getWord(DictionaryEntry.SELECT_A_WORKSTATION)%>'>
							</div>
							<div class='input-icon-holderDiv h-100 col-4   sum-holderDiv'>
								<div class='row h-100 '>
									<input disabled class='ts_sumTime h-100 disabled col-12 text-right float-right pr-4 py-3'
										value='0:00:00'>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class='sortContDiv_ListHolder row'>
					<div class='abas-list-holder    sort-list-holder  col-9 px-0'>
						<div class='abas-list dnd-frame dndf1 m-0 row light-shadow'></div>
					</div>
					<div class='ws-list-holder sort-list-holder col-3 px-0'>
						<div class='dnd-frame h-100 m-0 dndf2 station-list'></div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<%@include file="/Views/Footer.jsp"%>
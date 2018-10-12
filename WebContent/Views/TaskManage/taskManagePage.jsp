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
					<div class='TM_backBtn h-100 w-100 btnstyle selectBackBtn'>
						<img class='h-100 w-100 p-3'
							src='${pageContext.request.contextPath}/Public/icons/backArrowgray.svg' />
					</div>
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
		<!-- 			<img -->
<%-- 				src="${pageContext.request.contextPath}/Public/icons/pm_logo_mini_white.svg" --%>
<!-- 				class='row d-block m-3 img-fluid LogoMiniPM float-left' /> -->
<!-- 			<form method='POST' class='h-100 float-right' -->
<%-- 				action='${pageContext.request.contextPath}/Home'> --%>
<!-- 				<input class='btn_logout' type='submit' value='' /> -->
<!-- 			</form> -->
		</div>
		<div class='row h-75'>

			<!-- 					Elöválasztó felület -->

			<div id='TM_Select_container_activity' class='select-panel container px-4'>
			<div class='row align-items-center h-100'>
				<div class='col-12 col-md-4'>
					<div id='btn_select_1'
						class='activity-button light-shadow btnstyle mx-auto '>
						<img
							src='${pageContext.request.contextPath}/Public/icons/btn_wscontrol.svg'
							class='mx-auto d-block'>
						<p id='activity_tab1'><%=dict.getWord(Entry.TASKS)%></p>
					</div>
				</div>
				<div class='col-12 col-md-4'>
					<div id='btn_select_2'
						class='activity-button light-shadow btnstyle mx-auto '>
						<img
							src='${pageContext.request.contextPath}/Public/icons/btn_vision.svg'
							class='mx-auto d-block'>
						<p id='activity_tab2'><%=dict.getWord(Entry.STATIONS)%></p>
					</div>
				</div>
				<div class='col-12 col-md-4'>
					<div id='btn_select_3'
						class='activity-button light-shadow btnstyle mx-auto '>
						<img
							src='${pageContext.request.contextPath}/Public/icons/btn_settings.svg'
							class='mx-auto d-block'>
						<p id='activity_tab3'><%=dict.getWord(Entry.OPTIONS)%></p>
					</div>
				</div>
			</div>
			</div>

			<!-- 					Feladatokat listázó felület -->

			<div id='TM_Select_container1'
				class='select-panel TM_content_layer container-fluid px-0'>
				<%-- 						<%@include --%>
				<%-- 							file="/Views/TaskManage/PageElements/stationTaskSort.jsp"%> --%>
				<div class=' col-2 px-0'>
					<div class='container-fluid'>
					<div class='row light-shadow'>
						<div class='cont_header col-12 px-0'>
							<div class='input-icon-holderDiv float-right h-100'>
								<div class='refresh_btn h-100'>
									<img class='h-100 float-left p-3'
										src='${pageContext.request.contextPath}/Public/icons/reloadGray.svg'>
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
<!-- 					<div class='cont_header'> -->
<!-- 						<div class='input-icon-holderDiv ts-loggedName-div'> -->
<!-- 							<img class='icon-form' -->
<%-- 								src='${pageContext.request.contextPath}/Public/icons/user-shape-white.svg'><input --%>
<!-- 								disabled value=''> -->
<!-- 						</div> -->
<!-- 					</div> -->
					<div class='sortStationListCont container-fluid'>
						<div class='sortContDiv_nav row px-0 light-shadow'>
							<div class='abas-list-head sort-list-head sort-list h-100 col-5'>
								<div class='row h-100'>
									<div class='py-2 col-12'>
											<div class='row h-100 py-1'>
												<div class='col-10'>
													<input data-date-format="yyyy/mm/dd" class='datepicker_own w-100 h-100' id="datepicker">
   
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
								<div class='input-icon-holderDiv stationName-holderDiv col-9 h-100'>
									<img class='h-100 float-left p-3'
										src='${pageContext.request.contextPath}/Public/icons/computerSignGray.svg'>
										<input
										class='ts_wsNameInp station_label h-100  p-3'
										value='<%=dict.getWord(Entry.SELECT_A_WORKSTATION)%>'>
								</div>
								<div class='input-icon-holderDiv h-100 col-3   sum-holderDiv'>
									<div class='row h-100 '>
										<input
										class='ts_sumTime h-100 col-6 float-right py-3' value='0:00:00'>
																		<img class='icon-form col-4 h-100 float-right p-4'
										src='${pageContext.request.contextPath}/Public/icons/sumSignGray.svg'>
									</div>

								</div>
								</div>
							</div>
						</div>
						<div class='sortContDiv_ListHolder row'>
							<div class='abas-list-holder dndf1   sort-list-holder  col-8 px-0'>

							</div>
							<div class='ws-list-holder sort-list-holder col-4 px-0'>
								<div class='dnd-frame h-100 m-0 dndf2'></div>
							</div>
						</div>
					</div>
				</div>
			</div>

			<!-- 					Állomás aktivitás nézet -->

			<div id='TM_Select_container2'
				class='select-panel TM_content_layer row'>
				<%-- 						<%@include --%>
				<%-- 							file="/Views/TaskManage/PageElements/stationVisionControl.jsp"%> --%>
				<div id='stationVisionHolder'>
					<div class='light-shadow TV_stationContainer'>
						<div id='' class='TV_stationHeader TV_small'>
							<img
								src='${pageContext.request.contextPath}/Public/icons/computerSign.svg'>
							<p class='titleStyle'><%=dict.getWord(Entry.STATION)%></p>
						</div>
						<div id='' class='TV_stationContent'>
							<p><%=dict.getWord(Entry.TASK)%></p>
							<input disabled>
							<p><%=dict.getWord(Entry.START_TIME)%></p>
							<input disabled>
							<p><%=dict.getWord(Entry.END_TIME)%></p>
							<input disabled>
						</div>
						<div id='TV_timerContainer'>
							<img
								src='${pageContext.request.contextPath}/Public/icons/visionwatch.svg'>
							<p id='timerP'>13:53:10</p>
						</div>
					</div>
				</div>
			</div>

			<!-- 					Beállítások -->

			<div id='TM_Select_container3' class='select-panel TM_content_layer row'>

			</div>
		</div>
	</div>

<%@include file="/Views/Footer.jsp"%>

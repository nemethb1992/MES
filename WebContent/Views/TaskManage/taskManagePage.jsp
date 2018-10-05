<%@include file="/Views/Header.jsp"%>
<script>
	
<%@ include file="/Views/TaskManage/taskManageScript.js"%>
	
</script>
<div class='container-fluid h-100'>
	<div class='row'>
		<div class='col-12 px-0 topNav'>
			<div class='container-fluid'>
				<div class='row navigation-row'>
					<div class='col-1 h-100'>
						<img
							src="${pageContext.request.contextPath}/Public/icons/pm_logo_mini_white.svg"
							class='d-block m-3 img-fluid LogoMiniPM' />
					</div>

					<div class='col-1 h-100 offset-9 px-0'>
						<div class='TM_backBtn h-100 w-100 btnstyle selectBackBtn'>
							<img class='h-100 w-100 p-3'
								src='${pageContext.request.contextPath}/Public/icons/back-arrow-white.svg' />
						</div>
					</div>
					<div class='col-1  px-0'>
						<form method='POST' class='h-100'
							action='${pageContext.request.contextPath}/Home'>
							<input class='btn_logout' type='submit' value='' />
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
		<div class='col-12'>

			<!-- 					Elöválasztó felület -->

			<div id='TM_Select_container_activity' class='select-panel row px-4'>
				<%-- 						<jsp:include page="/Views/TaskManage/PageElements/activitySelect.jsp" /> --%>
				<div class='col-4'>
					<div id='btn_select_1'
						class='TM_btn_select light-shadow btnstyle mx-auto '>
						<img
							src='${pageContext.request.contextPath}/Public/icons/btn_wscontrol.svg'
							class='mx-auto d-block'>
						<p id='activity_tab1'><%=dict.getWord(Entry.TASKS)%></p>
					</div>
				</div>
				<div class='col-4'>
					<div id='btn_select_2'
						class='TM_btn_select light-shadow btnstyle mx-auto '>
						<img
							src='${pageContext.request.contextPath}/Public/icons/btn_vision.svg'
							class='mx-auto d-block'>
						<p id='activity_tab2'><%=dict.getWord(Entry.STATIONS)%></p>
					</div>
				</div>
				<div class='col-4'>
					<div id='btn_select_3'
						class='TM_btn_select light-shadow btnstyle mx-auto '>
						<img
							src='${pageContext.request.contextPath}/Public/icons/btn_settings.svg'
							class='mx-auto d-block'>
						<p id='activity_tab3'><%=dict.getWord(Entry.OPTIONS)%></p>
					</div>
				</div>
			</div>

			<!-- 					Feladatokat listázó felület -->

			<div id='TM_Select_container1'
				class='select-panel TM_content_layer row'>
				<%-- 						<%@include --%>
				<%-- 							file="/Views/TaskManage/PageElements/stationTaskSort.jsp"%> --%>
				<div class=' col-2 pl-0'>
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
								<div class='tmts_stationContainer row px-3'></div>
							</div>
						</div>
					</div>
				</div>
				<div class='sortContDiv col-10  px-0 light-shadow'>
<!-- 					<div class='cont_header'> -->
<!-- 						<div class='input-icon-holderDiv ts-loggedName-div'> -->
<!-- 							<img class='icon-form' -->
<%-- 								src='${pageContext.request.contextPath}/Public/icons/user-shape-white.svg'><input --%>
<!-- 								disabled value=''> -->
<!-- 						</div> -->
<!-- 					</div> -->
					<div class='sortStationListCont container-fluid'>
						<div class='sortContDiv_nav row px-0'>
							<div class='abas-list-head sort-list-head sort-list h-100 col-8'>
								<div class='row h-100 w-50'>
									<div class=' px-0 py-2 col-12'>
										<div class='container h-100'>
											<div class='row h-100 py-1'>
												<div class='col-8'>
													<input class='datepicker_own w-100 h-100' type='date' style='font-size: 17px;'/>
												</div>
												<div class='col-2 px-0'>
													<button class='date-button date-refresh h-100 w-100'></button>
												</div>
												<div class='col-2 px-0'>
													<button class='date-button date-null h-100 w-100'></button>
												</div>
											</div>
										</div>
									</div>
									<div class='input-icon-holderDiv  col px-0'></div>
								</div>
							</div>
							<div class='ws-list-head sort-list-head sort-list col-4 h-100'>
								<div class='input-icon-holderDiv stationName-holderDiv  h-100'>
									<img class='h-100 float-left py-3'
										src='${pageContext.request.contextPath}/Public/icons/computerSignGray.svg'>
										<input
										class='ts_wsNameInp station_label h-100 p-3'
										value='<%=dict.getWord(Entry.SELECT_A_WORKSTATION)%>'>
								</div>
								<div class='input-icon-holderDiv h-100 w-50 sum-holderDiv'>
									<img class='icon-form h-100 float-right p-3'
										src='${pageContext.request.contextPath}/Public/icons/sumSignGray.svg'>
										<input
										class='ts_sumTime h-100 float-right py-3' value='0:00:00'>
								</div>
							</div>
						</div>
						<div class='sortContDiv_ListHolder row'>
							<div class='abas-list-holder sort-list-holder  col-8 px-0'>
								<div class='dnd-frame dndf1 abas-list m-0 row'>


<!-- 									<div class="dnd-container col-12 px-0" value="3" -->
<!-- 										style="max-height: 80px;"> -->
<!-- 										<div class="container-fluid"> -->
<!-- 											<div class="row"> -->
<!-- 												<div class='col-11 px-0'> -->
<!-- 													<div class='container-fluid'> -->
<!-- 														<div class='row'> -->
<!-- 															<div class="col-2 px-0 pl-2 py-2 dnd-input-div"> -->
<!-- 																<p>Worksheet No.</p> -->
<!-- 																<textarea disabled class="dnd-input dnd-in1">118392001</textarea> -->
<!-- 															</div> -->
<!-- 															<div class="col-2 px-0 py-2 dnd-input-div"> -->
<!-- 																<p>Article</p> -->
<!-- 																<textarea disabled class="dnd-input dnd-in1">10000044323RB</textarea> -->
<!-- 															</div> -->
<!-- 															<div class="col-2 px-0 py-2 dnd-input-div"> -->
<!-- 																<p>Search word</p> -->
<!-- 																<textarea disabled class="dnd-input dnd-in1">PWR1000004432</textarea> -->
<!-- 															</div> -->
<!-- 															<div class="col-2 px-0 py-2 dnd-input-div"> -->
<!-- 																<p>Place of use</p> -->
<!-- 																<textarea disabled class="dnd-input dnd-in1">ROBOTBA</textarea> -->
<!-- 															</div> -->
<!-- 															<div class="col-2 px-0 py-2 dnd-input-div"> -->
<!-- 																<p>Name</p> -->
<!-- 																<textarea wrap="soft" class="dnd-input dnd-in1">Sondergeh.-1 KUKA 590-70069</textarea> -->
<!-- 															</div> -->
<!-- 															<div class="col-2 px-0 py-2 dnd-input-div"> -->
<!-- 																<p>Name 2</p> -->
<!-- 																<textarea disabled class="dnd-input dnd-in1"></textarea> -->
<!-- 															</div> -->
<!-- 														</div> -->
<!-- 													</div> -->
<!-- 												</div> -->
<!-- 												<div class='col-1 px-0'> -->
<!-- 													<div class="w-100 h-100 dnd-input-div px-0"> -->
<!-- 														<input class="h-100 w-100 task-panel-button" value="" -->
<!-- 															type="button"> -->
<!-- 													</div> -->
<!-- 												</div> -->
<!-- 											</div> -->
<!-- 										</div> -->
<!-- 									</div> -->


								</div>
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

			<div id='TM_Select_container3'
				class='select-panel TM_content_layer row'>
				<%-- 						<%@include --%>
				<%-- 							file="/Views/TaskManage/PageElements/stationSettings.jsp"%> --%>

				<div class='TM_backBtn btnstyle'>
					<img
						src='${pageContext.request.contextPath}/Public/icons/backArrow.svg' />
					<p>Menü</p>

				</div>
				<iframe id="fred" style="border: 1px solid #333333"
					title="PDF in an i-Frame" src="Public/pdf/proba.pdf#page=0"
					frameborder="1" scrolling="auto" height="1100" width="850"></iframe>
			</div>
		</div>
	</div>
</div>

<%@include file="/Views/Footer.jsp"%>

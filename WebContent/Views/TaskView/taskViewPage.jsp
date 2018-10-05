<%@include file="/Views/Header.jsp"%>
<script>
	
<%@ include file="/Views/TaskView/taskViewScript.js"%>

<%-- <%task = (Task.Details)session.getAttribute("Task");%> --%>

</script>
<div class='container-fluid h-100'>
			<div class='row h-100'>
		<div class='col-12 px-0 topNav'>
			<div class='container-fluid'>
				<div class='row navigation-row'>
					<div class='col-1 h-100'>
						<img
							src="${pageContext.request.contextPath}/Public/icons/pm_logo_mini_white.svg"
							class='d-block m-3 img-fluid LogoMiniPM' />
					</div>

					<div class='col top-nav-button offset-10  px-0'>
						<form method='POST' class='h-100'
							action='${pageContext.request.contextPath}/Home'>
							<input class='btn_logout' type='submit' value='' />
						</form>
					</div>
				</div>
			</div>
		</div>
				<div class='col-12 h-100'>
					<div class='container-fluid px-0'>
					<div class='row station-upper-row'>
						<div id='timerContainer'
							class='cc_element col-3 col-md-3 col-lg-2 col-xl-2 px-0'>
							<img class='d-block mx-auto mt-3'
								src='${pageContext.request.contextPath}/Public/icons/timer.svg'>
							<p class='timerPanel h2 text-center pt-3'></p>
						</div>
						<div class='col-9 col-md-9 col-lg-10 col-xl-10 px-0 cc_element'>
					<div class='container-fluid px-0 h-100'>
							<div id='row'>
								<div id='btn_lejelentes' class='btn_navHeader col-4 float-left'>
									<p class='h5 px-0 text-center pt-3 ' id='p_lejelentes'><%=dict.getWord(Entry.SUBMIT)%></p>
									<input
										class='number_Input navBtnInside_element mx-auto  h-25 mt-4'
										id=submit_input type='number' />
								</div>
								<div id='btn_megszakitas' class='btn_navHeader offset-8 col-4'>
									<p class='h5 px-0 text-center pt-3' id='p_megszakitas'><%=dict.getWord(Entry.INTERRUPT)%></p>
									<input
										class='number_Input navBtnInside_element mx-auto  h-25 mt-4'
										type='number' />
								</div>
							</div>
						</div>
						</div>
					</div>
					</div>
					<div class='row'>
						<div id='' class='cc_element col-3 col-md-3 col-lg-2 col-xl-2'>
							<div class='btn_leftNavigation row'>
								<div class='col-12' id='btn_leftNav_1'>
									<img class='d-block mx-auto mt-4 h-50'
										src='${pageContext.request.contextPath}/Public/icons/TV_btn_nav1.svg'>
									<p class='mt-2 h5 nav-btn-1'><%=dict.getWord(Entry.IDS)%></p>
								</div>
							</div>
							<div class='btn_leftNavigation row'>
								<div class='col-12' id='btn_leftNav_2'>
									<img class='d-block mx-auto mt-4 h-50'
										src='${pageContext.request.contextPath}/Public/icons/TV_btn_nav2.svg'>
									<p class='mt-2 h5 nav-btn-2'><%=dict.getWord(Entry.DOCUMENTS)%></p>
								</div>
							</div>
							<div class='btn_leftNavigation row'>
								<div class='col-12' id='btn_leftNav_3'>
									<img class='d-block mx-auto mt-4 h-50'
										src='${pageContext.request.contextPath}/Public/icons/TV_btn_nav3.svg'>
									<p class='mt-2 h5 nav-btn-3'><%=dict.getWord(Entry.BILL_OF_MATERIAL)%></p>
								</div>
							</div>
							<div class='btn_leftNavigation row h-25'>
								<div class='col-12' id='btn_leftNav_4'>
									<img class='d-block mx-auto mt-4 h-50'
										src='${pageContext.request.contextPath}/Public/icons/TV_btn_nav4.svg'>
									<p class='mt-2 h5 nav-btn-4'><%=dict.getWord(Entry.ORDER_INFO)%></p>
								</div>
							</div>
						</div>
						<div
							class='cc_element operator-switch-grid col-9 col-md-9 col-lg-10 col-xl-10 px-0'>
							<div id='SwitchPanel' class='rightCont'></div>
						</div>
					</div>
				</div>
			</div>
		</div>
<%@include file="/Views/Footer.jsp"%>



<!-- 				<div id='leftwrap' class='col-1 px-0'> -->
<!-- 					<div class=' container px-0'> -->
<!-- 						<img id='leftwrapImg' -->
<%-- 							src="${pageContext.request.contextPath}/Public/icons/pm_logo_mini_white.svg" --%>
<!-- 							class='row mt-3 mx-auto d-block img-fluid px-3' /> -->
<!-- 						<form method='POST' class='logout-form w-100' -->
<%-- 							action='${pageContext.request.contextPath}/Home'> --%>
<!-- 							<input class='btn_logout  h-100 w-100' type='submit' value='' /> -->
<!-- 						</form> -->
<!-- 					</div> -->
<!-- 				</div> -->
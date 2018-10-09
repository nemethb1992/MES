<%@include file="/Views/Header.jsp"%>
<script>
	
<%@ include file="/Views/TaskView/taskViewScript.js"%>

<%-- <%task = (Task.Details)session.getAttribute("Task");%> --%>

</script>
<div class='container-fluid h-100'>
<!-- 	<div class='row'> -->
<!-- 		<div class='col-12 px-0 topNav'> -->
<!-- 			<div class='container-fluid'> -->
<!-- 				<div class='row navigation-row'> -->
<!-- 					<div class='col-1 h-100'> -->
<!-- 						<img -->
<%-- 							src="${pageContext.request.contextPath}/Public/icons/pm_logo_mini_white.svg" --%>
<!-- 							class='d-block m-3 img-fluid LogoMiniPM' /> -->
<!-- 					</div> -->

<!-- 					<div class='col top-nav-button offset-10  px-0'> -->
<!-- 						<form method='POST' class='h-100' -->
<%-- 							action='${pageContext.request.contextPath}/Home'> --%>
<!-- 							<input class='btn_logout' type='submit' value='' /> -->
<!-- 						</form> -->
<!-- 					</div> -->
<!-- 				</div> -->
<!-- 			</div> -->
<!-- 		</div> -->
<!-- 		</div> -->
		
	<div class='row'>
		<div class='col-12 h-100'>
			<div class='container-fluid px-0'>
				<div class='row station-upper-row'>
					<div id='timerContainer'
						class='big-nav-button col-3 col-md-3 col-lg-2 col-xl-2'
						class='col-3 col-md-3 col-lg-2 col-xl-2 px-0'>
						<img class='d-block mx-auto mt-3'
							src='${pageContext.request.contextPath}/Public/icons/timer.svg'>
						<p class='timerPanel h1 text-center pt-3'></p>
					</div>
					<div
						class='button-container light-shadow col-9 col-md-9 col-lg-10 col-xl-10 px-0'>
						<div class='container-fluid px-0 h-100'>
							<div class='nav-contianer h-100'>
								<div id='btn_lejelentes'
									class='submit-form btn_navHeader h-100 float-left '>
									<p class='h6 text-center h-100 ' id='p_lejelentes'><%=dict.getWord(Entry.SUBMIT)%></p>
									<div class='container-fluid my-nav-container h-100'>
										<div class='row h-100'>
											<div class='col-11'>
												<input
													class='number_Input navBtnInside_element mx-auto  h-25 mt-4'
													id=submit_input type='number' />
											</div>
											<div class='col-1'>
												<input type='button' />
											</div>
										</div>
									</div>
								</div>
								<div class='btn_navHeader h-100 float-right px-0'>
									<form method='POST' class='h-100'
										action='${pageContext.request.contextPath}/Home'>
										<input class='btn_logout-gray' type='submit' value='' />
									</form>
								</div>
								<div id='btn_megszakitas'
									class='btn_navHeader h-100 float-right'>
									<p class='h6 text-center h-100'><%=dict.getWord(Entry.INTERRUPT)%></p>
									<div class='container-fluid my-nav-container h-100'>
										<div class='row h-100'>
											<div class='col-11'>
												<input
													class='number_Input navBtnInside_element mx-auto  h-25 mt-4'
													type='number' />
											</div>
											<div class='col-1'>
												<input type='button' />
											</div>
										</div>
									</div>

								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class='row'>
				<div id='' class='light-shadow left-buttons col-3 col-md-3 col-lg-2 col-xl-2'>
					<div class='btn_leftNavigation big-nav-button row'>
						<div class='col-12' id='btn_leftNav_1'>
							<img class='d-block mx-auto mt-5 h-50'
								src='${pageContext.request.contextPath}/Public/icons/TV_btn_nav1.svg'>
							<p class='mt-4 h5 nav-btn-1'><%=dict.getWord(Entry.IDS)%></p>
						</div>
					</div>
					<div class='btn_leftNavigation big-nav-button row'>
						<div class='col-12' id='btn_leftNav_2'>
							<img class='d-block mx-auto mt-5 h-50'
								src='${pageContext.request.contextPath}/Public/icons/TV_btn_nav2.svg'>
							<p class='mt-4 h5 nav-btn-2'><%=dict.getWord(Entry.DOCUMENTS)%></p>
						</div>
					</div>
					<div class='btn_leftNavigation big-nav-button row'>
						<div class='col-12' id='btn_leftNav_3'>
							<img class='d-block mx-auto mt-5 h-50'
								src='${pageContext.request.contextPath}/Public/icons/TV_btn_nav3.svg'>
							<p class='mt-4 h5 nav-btn-3'><%=dict.getWord(Entry.BILL_OF_MATERIAL)%></p>
						</div>
					</div>
					<div class='btn_leftNavigation big-nav-button row'>
						<div class='col-12' id='btn_leftNav_4'>
							<img class='d-block mx-auto mt-5 h-50'
								src='${pageContext.request.contextPath}/Public/icons/TV_btn_nav4.svg'>
							<p class='mt-4 h5 nav-btn-4'><%=dict.getWord(Entry.ORDER_INFO)%></p>
						</div>
					</div>
				</div>
				<div
					class='operator-switch-grid col-9 col-md-9 col-lg-10 col-xl-10 px-0'>
					<div id='SwitchPanel' class='rightCont'>
					
						
					</div>
				</div>
			</div>
		</div>
	</div>
	</div>
</div>
<%@include file="/Views/Footer.jsp"%>


<!-- <div class='container-fluid px-0'> -->
<!-- 							<div class='row data-row mx-3 mt-3'> -->
<!-- 								<div class='col-12 col-md-12 col-lg-12 col-xl-6 p-3'> -->
<!-- 									<div class='inputContainer'> -->
<!-- 										<p></p> -->
<!-- 										<input class='px-2 w-100 h6' type='text' value=''> -->
<!-- 									</div> -->
<!-- 									<div class='inputContainer'> -->
<!-- 										<p></p> -->
<!-- 										<input class='px-2 w-100 h6' type='text' value=''> -->
<!-- 									</div> -->
<!-- 									<div class='inputContainer'> -->
<!-- 										<p></p> -->
<!-- 										<input class='px-2 w-100 h6' type='text' value=''> -->
<!-- 									</div> -->
<!-- 								</div> -->
<!-- 								<div class='col-12 col-md-12 col-lg-12 col-xl-6 p-3'> -->
<!-- 									<div class='inputContainer'> -->
<!-- 										<p></p> -->
<!-- 										<input class='px-2 w-100 h6' type='text' value=''> -->
<!-- 									</div> -->
<!-- 									<div class='inputContainer'> -->
<!-- 										<p></p> -->
<!-- 										<input class='px-2 w-100 h6' type='text' value=''> -->
<!-- 									</div> -->
<!-- 								</div> -->
<!-- 							</div> -->
<!-- 							<div class='row data-row m-3'> -->
<!-- 								<div class='col-12 col-md-12 col-lg-12 col-xl-6 p-3'> -->
<!-- 									<div class='inputContainer'> -->
<!-- 										<p></p> -->
<!-- 										<input class='px-2 w-100 h6' type='text' value=''> -->
<!-- 									</div> -->
<!-- 									<div class='inputContainer'> -->
<!-- 										<p></p> -->
<!-- 										<input class='px-2 w-100 h6' type='text' value=''> -->
<!-- 									</div> -->
<!-- 									<div class='inputContainer'> -->
<!-- 										<p></p> -->
<!-- 										<input class='px-2 w-100 h6' type='text' value=''> -->
<!-- 									</div> -->
<!-- 								</div> -->
<!-- 								<div class='col-12 col-md-12 col-lg-12 col-xl-6 p-3'> -->
<!-- 									<div class='inputContainer'> -->
<!-- 										<p></p> -->
<!-- 										<input class='px-2 w-100 h6' type='text' value=''> -->
<!-- 									</div> -->
<!-- 									<div class='inputContainer'> -->
<!-- 										<p></p> -->
<!-- 										<input class='px-2 w-100 h6' type='text' value=''> -->
<!-- 									</div> -->
<!-- 								</div> -->
<!-- 							</div> -->
<!-- 							<div class='row data-row m-3'> -->
<!-- 								<div class='col-12 col-md-12 col-lg-12 col-xl-6 pt-3 px-3'> -->
<!-- 									<div class='inputContainer'> -->
<!-- 										<p></p> -->
<!-- 										<input class='px-2 w-100 h6' type='text' value=''> -->
<!-- 									</div> -->
<!-- 									<div class='inputContainer'> -->
<!-- 										<p></p> -->
<!-- 										<input class='px-2 w-100 h6' type='text' value=''> -->
<!-- 									</div> -->
<!-- 									<div class='inputContainer'> -->
<!-- 										<p></p> -->
<!-- 										<input class='px-2 w-100 h6' type='text' value=''> -->
<!-- 									</div> -->
<!-- 								</div> -->
<!-- 								<div class='col-12 col-md-12 col-lg-12 col-xl-6 pt-3 px-3'> -->
<!-- 									<div class='inputContainer'> -->
<!-- 										<p></p> -->
<!-- 										<input class='px-2 w-100 h6' type='text' value=''> -->
<!-- 									</div> -->
<!-- 									<div class='inputContainer'> -->
<!-- 										<p></p> -->
<!-- 										<input class='px-2 w-100 h6' type='text' value=''> -->
<!-- 									</div> -->
<!-- 									<div class='inputContainer'> -->
<!-- 										<p></p> -->
<!-- 										<input class='px-2 w-100 h6' type='text' value=''> -->
<!-- 									</div> -->
<!-- 								</div> -->
<!-- 								<div class='col-12  px-3'> -->
<!-- 									<div class='inputContainer BigTextInput'> -->
<!-- 										<p class='mb-0'></p> -->
<!-- 										<textarea class='px-2 w-100 h6'></textarea> -->
<!-- 									</div> -->
<!-- 								</div> -->
<!-- 							</div> -->
<!-- 						</div> -->
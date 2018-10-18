<%@include file="/Views/Header.jsp"%>
<script>
	
<%@ include file="/Views/TaskView/taskViewScript.js"%>

<%-- <%task = (Task.Details)session.getAttribute("Task");%> --%>

</script>
<div class='container-fluid h-100'>

	<p class='actual-time actual-time-tv fixed-bottom my-0 py-4 h5'></p>
	<div class='row'>
		<div class='col-12 h-100'>
			<div class='container-fluid px-0'>
				<div class='row station-upper-row'>
					<div id='timerContainer'
						class='big-nav-button col-3 col-md-3 col-lg-2 col-xl-2'
						class='col-3 col-md-3 col-lg-2 col-xl-2 px-0'>
						<img class='d-block mx-auto mt-3'
							src='${pageContext.request.contextPath}/Public/icons/timer.svg'>
						<p class='timerPanel h2 text-center pt-3'></p>
					</div>
					<div
						class='button-container light-shadow col-9 col-md-9 col-lg-10 col-xl-10 px-0'>
						<div class='container-fluid px-0 h-100'>
							<div class='nav-contianer h-100'>
								<div id='btn_lejelentes'
									class='submit-form btn_navHeader h-100 float-left light-shadow'>
									<p class='h6 text-center h-100 ' id='p_lejelentes'><%=dict.getWord(Entry.SUBMIT)%></p>
									<div class='container-fluid my-nav-container h-100'>
										<div class='row h-100'>
											<div class='col-11 px-5'>
												<h4 class='h4 mt-5 mb-2'><%=dict.getWord(Entry.SUBMIT)%></h4>
												<div class="input-group mb-3 w-75 h-25">
													<input type="number" class="form-control h-100"
														placeholder="<%=dict.getWord(Entry.FINISHED_QUANTITY)%>"
														aria-label="<%=dict.getWord(Entry.FINISHED_QUANTITY)%>"
														aria-describedby="button-addon2">
													<div class="input-group-append w-25">
														<button
															class="btn btn-outline-secondary w-100 submit-action-btn"
															type="button" id="button-addon2"><%=dict.getWord(Entry.SEND)%></button>
													</div>
												</div>
											</div>
											<div class='col-1 px-0'>
												<input type='button'
													class='w-100 h-100 my-nav-btn lejelent-btn' />
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
									class='btn_navHeader h-100 float-right light-shadow'>
									<p class='h6 text-center h-100'><%=dict.getWord(Entry.INTERRUPT)%></p>
									<div class='container-fluid my-nav-container h-100'>
										<div class='row h-100'>
											<div class='col-1 px-0'>
												<input type='button'
													class='w-100 h-100 my-nav-btn megszak-btn' />
											</div>
											<div class='col-11'></div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class='row'>
				<div id=''
					class='left-buttons col-3 col-md-3 col-lg-2 col-xl-2'>
					<div class='row'>
						<div class='col light-shadow bord-radius-nav'>
							<div class='btn_leftNavigation big-nav-button row'>
								<div class='col-12' id='btn_leftNav_1'>
									<img class='d-block mx-auto mt-4 h-50'
										src='${pageContext.request.contextPath}/Public/icons/TV_btn_nav1.svg'>
									<p class='my-4 h5 nav-btn-1'><%=dict.getWord(Entry.IDS)%></p>
								</div>
							</div>
							<div class='btn_leftNavigation big-nav-button row'>
								<div class='col-12' id='btn_leftNav_2'>
									<img class='d-block mx-auto mt-4 h-50'
										src='${pageContext.request.contextPath}/Public/icons/TV_btn_nav2.svg'>
									<p class='my-4 h5 nav-btn-2'><%=dict.getWord(Entry.DOCUMENTS)%></p>
								</div>
							</div>
							<div class='btn_leftNavigation big-nav-button row'>
								<div class='col-12' id='btn_leftNav_3'>
									<img class='d-block mx-auto mt-4 h-50'
										src='${pageContext.request.contextPath}/Public/icons/TV_btn_nav3.svg'>
									<p class='my-4 h5 nav-btn-3'><%=dict.getWord(Entry.BILL_OF_MATERIAL)%></p>
								</div>
							</div>
							<div class='btn_leftNavigation bord-radius-nav big-nav-button row'>
								<div class='col-12 ' id='btn_leftNav_4'>
									<img class='d-block mx-auto mt-4 h-50'
										src='${pageContext.request.contextPath}/Public/icons/TV_btn_nav4.svg'>
									<p class='my-4 h5 nav-btn-4'><%=dict.getWord(Entry.ORDER_INFO)%></p>
								</div>
							</div>
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
<%@include file="/Views/Header.jsp"%>
<script>
	
<%@ include file="/Views/TaskView/taskViewScript.js"%>
	
</script>
<div class='container-fluid'>
	<div class='row  mt-5'>
		<div class='mycontainer container'>
			<div class='row h-100'>
				<div id='leftwrap' class='col-1 px-0'>
					<div class=' container px-0'>
						<img id='leftwrapImg'
							src="${pageContext.request.contextPath}/Public/icons/pm_logo_mini_white.svg"
							class='row mt-3 mx-auto d-block img-fluid px-3' />
						<form method='POST' class='logout-form w-100'
							action='${pageContext.request.contextPath}/Home'>
							<input class='btn_logout  h-100 w-100' type='submit' value='' />
						</form>
					</div>
				</div>
				<div class='col-11'>
					<div class='row p-3'>
						<div id='timerContainer'
							class='cc_element col-3 col-md-3 col-lg-2 col-xl-2 px-0'>
							<img class='d-block mx-auto mt-2'
								src='${pageContext.request.contextPath}/Public/icons/timer.svg'>
							<p class='timerPanel h3 text-center pt-2'>0:0:0</p>
						</div>
						<div class='col-9 col-md-9 col-lg-10 col-xl-10 px-0 cc_element'>
							<div id='row'>
								<div id='btn_lejelentes' class='btn_navHeader col-4'>
									<p class='h6 text-center pt-3' id='p_lejelentes'><%=dict.getWord(Entry.SUBMIT)%></p>
									<input
										class='number_Input navBtnInside_element mx-auto w-80 h-25 mt-4'
										id=submit_input type='number' />
								</div>
								<div id='btn_megszakitas' class='btn_navHeader col-4'>
									<p class='h6 text-center pt-3' id='p_megszakitas'><%=dict.getWord(Entry.INTERRUPT)%></p>
									<input
										class='number_Input navBtnInside_element mx-auto w-80 h-25 mt-4'
										type='number' />
								</div>
							</div>
						</div>
					</div>
					<div class='row px-3 pb-3'>
						<div id='' class='cc_element col-3 col-md-3 col-lg-2 col-xl-2'>
							<div class='btn_leftNavigation row'>
								<div class='col-12' id='btn_leftNav_1'>
									<img class='d-block mx-auto mt-4 h-50'
										src='${pageContext.request.contextPath}/Public/icons/TV_btn_nav1.svg'>
									<p class='mt-2 nav-btn-1'><%=dict.getWord(Entry.IDS)%></p>
								</div>
							</div>
							<div class='btn_leftNavigation row'>
								<div class='col-12' id='btn_leftNav_2'>
									<img class='d-block mx-auto mt-4 h-50'
										src='${pageContext.request.contextPath}/Public/icons/TV_btn_nav2.svg'>
									<p class='mt-2 nav-btn-2'><%=dict.getWord(Entry.DOCUMENTS)%></p>
								</div>
							</div>
							<div class='btn_leftNavigation row'>
								<div class='col-12' id='btn_leftNav_3'>
									<img class='d-block mx-auto mt-4 h-50'
										src='${pageContext.request.contextPath}/Public/icons/TV_btn_nav3.svg'>
									<p class='mt-2 nav-btn-3'><%=dict.getWord(Entry.BILL_OF_MATERIAL)%></p>
								</div>
							</div>
							<div class='btn_leftNavigation row h-25'>
								<div class='col-12' id='btn_leftNav_4'>
									<img class='d-block mx-auto mt-4 h-50'
										src='${pageContext.request.contextPath}/Public/icons/TV_btn_nav4.svg'>
									<p class='mt-2 nav-btn-4'><%=dict.getWord(Entry.ORDER_INFO)%></p>
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
	</div>
</div>
<%@include file="/Views/Footer.jsp"%>



<!-- 							<div class='container'> -->
<!-- 								<div class='row px-0'> -->
<!-- 									<div class='col-12 col-md-12 col-lg-6 col-xl-6 px-0'> -->
<!-- 										<div class='inputContainer cc_element mt-2 mx-1 mx-lg-3'> -->
<!-- 											<p>Munka�llom�s</p> -->
<!-- 											<input class='px-2 w-100' type='text' value='234PG - 1'/> -->
<!-- 										</div> -->
<!-- 										<div class='inputContainer cc_element mt-2 mx-1 mx-lg-3'> -->
<!-- 											<p>Munkalapsz�m</p> -->
<!-- 											<input class='px-2 w-100' type='text' value='141436004'/> -->
<!-- 										</div> -->
<!-- 										<div class='inputContainer cc_element mt-2 mx-1 mx-lg-3'> -->
<!-- 											<p>Cikksz�m</p> -->
<!-- 											<input class='px-2 w-100' type='text' value='00450051558O'/> -->
<!-- 										</div> -->
<!-- 										<div class='inputContainer cc_element mt-2 mx-1 mx-lg-3'> -->
<!-- 											<p>Keres�sz�</p> -->
<!-- 											<input class='px-2 w-100' type='text' value='P45.005155_8'/> -->
<!-- 										</div> -->
<!-- 										<div class='inputContainer cc_element mt-2 mx-1 mx-lg-3'> -->
<!-- 											<p>M�veleti azonos�t�</p> -->
<!-- 											<input class='px-2 w-100'  type='text' value='11200P'/> -->
<!-- 										</div> -->
<!-- 										<div class='inputContainer cc_element mt-2 mx-1 mx-lg-3'> -->
<!-- 											<p>Be�ll�t�si id�</p> -->
<!-- 											<input class='px-2 w-100' type='text' value='0.000000'/> -->
<!-- 										</div> -->
<!-- 									</div> -->
<!-- 									<div class='col-12 col-md-12 col-lg-6 col-xl-6 px-0'> -->
<!-- 										<div class='inputContainer cc_element mt-2 mx-1 mx-lg-3'> -->
<!-- 											<p>Megnevez�s</p> -->
<!-- 											<input class='px-2 w-100' type='text' value='Sondergeh-2 IFM TS02 Bedruckung'/> -->
<!-- 										</div> -->
<!-- 										<div class='inputContainer cc_element mt-2 mx-1 mx-lg-3'> -->
<!-- 											<p>Felhaszn�l�s</p> -->
<!-- 											<input class='px-2 w-100' type='text' value='KW30'/> -->
<!-- 										</div> -->
<!-- 										<div class='inputContainer cc_element mt-2 mx-1 mx-lg-3'> -->
<!-- 											<p>Keres�sz�</p> -->
<!-- 											<input class='px-2 w-100' type='text' value='P11200'/> -->
<!-- 										</div> -->
<!-- 										<div class='inputContainer cc_element mt-2 mx-1 mx-lg-3'> -->
<!-- 											<p>Megnevez�s</p> -->
<!-- 											<input class='px-2 w-100' type='text' value='Tiszt�t�s'/> -->
<!-- 										</div> -->
<!-- 										<div class='inputContainer cc_element mt-2 mx-1 mx-lg-3'> -->
<!-- 											<p>Darabid�</p> -->
<!-- 											<input class='px-2 w-100' type='text' value='0.180000'/> -->
<!-- 										</div> -->
<!-- 										<div class='inputContainer cc_element mt-2 mx-1 mx-lg-3'> -->
<!-- 											<p>Nyitott mennyis�g</p> -->
<!-- 											<input class='px-2 w-100' type='text' value='1022.000'/> -->
<!-- 										</div> -->
<!-- 									</div> -->
<!-- 									<div class='col-12 px-0'> -->
<!-- 										<div class='inputContainer BigTextInput cc_element mx-1 mx-lg-3 my-2'> -->
<!-- 											<p class='mb-0'>Gy�rt�si inform�ci�</p> -->
<!-- 											<textarea></textarea> -->
<!-- 										</div> -->
<!-- 									</div> -->
<!-- 								</div> -->
<!-- 							</div> -->
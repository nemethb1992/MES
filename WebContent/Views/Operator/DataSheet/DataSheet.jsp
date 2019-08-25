
<%@page import="java.util.Locale"%>
<%@page import="phoenix.mes.content.AppBuild"%>
<%@page import="phoenix.mes.content.controller.OperatingWorkstation"%>
<%@page import="phoenix.mes.content.controller.operator.DataSheetLoader"%>
<%@include file="/Views/Header.jsp"%>
<%@page import="phoenix.mes.abas.Task"%>
<script>
	
<%@ include file="/Views/Operator/Operator.js"%>
	
<%

request.setAttribute("page","DataSheet");
OperatingWorkstation ws = new OperatingWorkstation(request);
			String displayName = (String) session.getAttribute("displayname");
			Task task = (Task) session.getAttribute("Task");
// 			if (outputFormatter.getLocale() == Locale.GERMAN || outputFormatter.getLocale() == Locale.ENGLISH) {
// 				try {
// 					String auxiliaryArr[] = displayName.split(" ");
// 					String auxiliary = "";

// 					int i = 0;
// 					for (String part : auxiliaryArr) {
// 						if (i > 0) {
// 							auxiliary += part;
// 							auxiliary += " ";
// 						}
// 						i++;
// 					}

// 					auxiliary += auxiliaryArr[0];
// 					displayName = auxiliary;
// 				} catch (Exception e) {
// 				}
// 			}
			%>
	
</script>
<div class='container-fluid h-100'>

	<p class='actual-time h5'></p>


	<div class='row station-upper-row timer-holder'>
		<div id='timerContainer'
			class='h-100 col-2 col-md-2 col-lg-2 col-xl-2 px-0'>
			<!-- 						<img class='d-block mx-auto mt-3' -->
			<%-- 							src='${pageContext.request.contextPath}/Public/icons/timer.svg'> --%>
			<p class='timerPanel h2 text-center upcounter'></p>
		</div>
		<div
			class='button-container h-100 light-shadow col-10 col-md-10 col-lg-10 col-xl-10 px-0'>
			<div class='container-fluid px-0 h-100'>
				<div class='nav-contianer h-100'>
					<div id='btn_lejelentes' onclick='openSubmit(this)'
						class='btn_navHeader-left-submit btn_navHeader-left submit-form btn_navHeader h-100 float-left'>
						<p class='h6 text-center nav-label' id='p_lejelentes'><%=outputFormatter.getWord(DictionaryEntry.SUBMIT)%></p>
						<div class='container-fluid my-nav-container h-100'>
							<div class='row h-100'>
								<div class='col-11 px-5'>
									<h4 class='h4 mt-3 mb-2'><%=outputFormatter.getWord(DictionaryEntry.SUBMIT)%></h4>
									<div class="input-group mb-3 w-100 h-50">
										<input type="number" class="form-control input-finished h-100"
											placeholder="<%=outputFormatter.getWord(DictionaryEntry.FINISHED_QUANTITY)%>"
											aria-label="<%=outputFormatter.getWord(DictionaryEntry.FINISHED_QUANTITY)%>"
											aria-describedby="button-addon2" /> <input type="number"
											class="form-control input-scrap h-100"
											placeholder="<%=outputFormatter.getWord(DictionaryEntry.SCRAP_QUANTITY)%>"
											aria-label="<%=outputFormatter.getWord(DictionaryEntry.SCRAP_QUANTITY)%>"
											aria-describedby="button-addon2">
										<div class="input-group-append w-25">
											<button
												class="btn btn-outline-secondary w-100 submit-action-btn"
												value='<%=task.getWorkSlipId()%>' type="button"
												onclick='OpenSubmitConfirmationModal(this)'
												id="button-addon2"><%=outputFormatter.getWord(DictionaryEntry.SEND)%></button>
										</div>
									</div>
								</div>
								<div class='col-1 px-0'>
									<input type='button'
										class='w-100 h-100 lejelent-btn close-nav-btn'
										onclick='closeSubmit()' />
								</div>
							</div>
						</div>
					</div>
					<div
						class='btn_navHeader-left btn-navHeader-left-refresh-btn refresh-click btn_navHeader h-100 float-left px-0'
						onclick='RefreshTask()'>
						<p class='h6 text-center nav-label'><%=outputFormatter.getWord(DictionaryEntry.REFRESH)%></p>
						<form method='POST' class='h-100 d-none refresh-form'
							action='<%=response.encodeURL(request.getContextPath() + "/OpenTask")%>'></form>
						<form method='POST' class='h-100 d-none reload-datasheet'
							action='<%=response.encodeURL(request.getContextPath() + "/DataSheet")%>'></form>
					</div>
					<button id='btn_megszakitas' onclick='openSuspendModal(this)'
						value='<%=task.getWorkSlipId()%>'
						class='btn_navHeader h-100 float-left btn_navHeader-left'>
						<p class='h6 text-center nav-label'><%=outputFormatter.getWord(DictionaryEntry.DISRUPTION)%></p>
					</button>
					<form method='Post'
						action='<%=response.encodeURL(request.getContextPath() + "/OpenTask")%>'
						class='d-none interrupt-form'></form>
					<div class='btn_navHeader h-100 float-right px-0'>
						<form method='POST' class='h-100'
							action='<%=response.encodeURL(request.getContextPath() + "/Logout")%>'>
							<input class='btn_logout-gray' type='submit' value='' />
						</form>
					</div>

					<div class='personal-data-display h-100 float-right px-0'>
						<div class='form-group'>
							<input
								class='form-control h-100 personal-form-control personal-form-name'
								disabled value='<%=displayName%>'>
						</div>
						<div class='form-group'>
							<input class='form-control h-100 personal-form-control' disabled
								value='<%=(ws.getOperatingStation()).replace('!', '-')%>'>
						</div>
						<div class='form-group'>
							<input
								class='form-control h-100 personal-form-control w-100 personal-date'
								disabled>
						</div>
						<div class='form-group'>
							<input
								class='form-control h-100 personal-form-control w-100 personal-time'
								disabled>
						</div>

						<div class='form-group d-none py-2'>
							<%@include file="/Views/Partial/ApplicationCountDown.jsp"%>
						</div>
					</div>
					<div class='nav-language-box  float-right px-0'>
						<%@include file="/Views/Partial/LanguageSelector.jsp"%>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class='row task-details-row'>
		<div class='left-buttons col-2 col-md-2 col-lg-2 col-xl-2'>
			<div class='row h-100'>
			
								<div class='col light-shadow bord-radius-nav'>
					<div class='btn_leftNavigation big-nav-button row'>
						<button class='col-12 px-0 navigation-button' value='1'
							id='navigation-button-1' onclick='NavigationButtonClick(this)'>
							<div class="container">
								<div class="row">
									<div class="col-2 nav-img-div">
										<img class="nav-img"
											src="${pageContext.request.contextPath}/Public/icons/TV_btn_nav1.svg" />
									</div>
									<div class="col-10">
										<p class='nav-btn-1 nav-label'><%=outputFormatter.getWord(DictionaryEntry.GENERAL)%></p>
									</div>
								</div>
							</div>
						</button>
					</div>
					
					<div class='btn_leftNavigation big-nav-button row'>
						<button class='col-12 px-0 navigation-button' value='7'
							id='navigation-button-1' onclick='NavigationButtonClick(this)'>
							<div class="container">
								<div class="row">
									<div class="col-2 nav-img-div">
										<img class="nav-img"
											src="${pageContext.request.contextPath}/Public/icons/technical-support.svg" />
									</div>
									<div class="col-10">
										<p class='nav-btn-1 nav-label'><%=outputFormatter.getWord(DictionaryEntry.TECHNICAL_MANUAL)%></p>
									</div>
								</div>
							</div>
						</button>
					</div>
					
					
					<div class='btn_leftNavigation big-nav-button row'>
						<button class='col-12 px-0 navigation-button' value='8'
							id='navigation-button-1' onclick='NavigationButtonClick(this)'>
							<div class="container">
								<div class="row">
									<div class="col-2 nav-img-div">
										<img class="nav-img"
											src="${pageContext.request.contextPath}/Public/icons/list.svg" />
									</div>
									<div class="col-10">
										<p class='nav-btn-1 nav-label'><%=outputFormatter.getWord(DictionaryEntry.OPERATION_LABEL)%></p>
									</div>
								</div>
							</div>
						</button>
					</div>
					
					<div class='btn_leftNavigation big-nav-button row'>
						<button class='col-12 px-0 navigation-button' value='2'
							id='navigation-button-2' onclick='NavigationButtonClick(this)'>
							<div class="container">
								<div class="row">
									<div class="col-2 nav-img-div">
										<img class="nav-img"
											src="${pageContext.request.contextPath}/Public/icons/TV_btn_nav2.svg" />
									</div>
									<div class="col-10">
							<p class='nav-btn-2 nav-label'><%=outputFormatter.getWord(DictionaryEntry.DOCUMENTS)%></p>
									</div>
								</div>
							</div>
						</button>
					</div>
					<div class='btn_leftNavigation big-nav-button row'>
						<button class='col-12 px-0 navigation-button' value='3'
							id='navigation-button-3' onclick='NavigationButtonClick(this)'>
														<div class="container">
								<div class="row">
									<div class="col-2 nav-img-div">
										<img class="nav-img"
											src="${pageContext.request.contextPath}/Public/icons/TV_btn_nav3.svg" />
									</div>
									<div class="col-10">
							<p class='nav-btn-3 nav-label'><%=outputFormatter.getWord(DictionaryEntry.BILL_OF_MATERIAL)%></p>
									</div>
								</div>
							</div>
						</button>
					</div>
					<div class='btn_leftNavigation  big-nav-button row'>
						<button class='col-12 px-0 navigation-button' value='4'
							id='navigation-button-4' onclick='NavigationButtonClick(this)'>
							<div class="container">
								<div class="row">
									<div class="col-2 nav-img-div">
										<img class="nav-img"
											src="${pageContext.request.contextPath}/Public/icons/TV_btn_nav4.svg" />
									</div>
									<div class="col-10">
							<p class='nav-btn-4 nav-label'><%=outputFormatter.getWord(DictionaryEntry.ORDER_INFO)%></p>
									</div>
								</div>
							</div>
						</button>
					</div>
					<div class='btn_leftNavigation big-nav-button row'>
						<button class='col-12 px-0 navigation-button' value='5'
							id='navigation-button-5' onclick='NavigationButtonClick(this)'>
							<div class="container">
								<div class="row">
									<div class="col-2 nav-img-div">
										<img class="nav-img"
											src="${pageContext.request.contextPath}/Public/icons/list.svg" />
									</div>
									<div class="col-10">
							<p class='nav-btn-5 nav-label'><%=outputFormatter.getWord(DictionaryEntry.FOLLOWING_OPERATIONS)%></p>
									</div>
								</div>
							</div>
						</button>
					</div>
					<div class='btn_leftNavigation bord-radius-nav big-nav-button row'>
						<button class='col-12 px-0 navigation-button' value='6'
							id='navigation-button-6' onclick='NavigationButtonClick(this)'>
							<div class="container">
								<div class="row">
									<div class="col-2 nav-img-div">
										<img class="nav-img"
											src="${pageContext.request.contextPath}/Public/icons/calendar-2.svg" />
									</div>
									<div class="col-10">
							<p class='nav-btn-6 nav-label'><%=outputFormatter.getWord(DictionaryEntry.FOLLOWING_TASK_NUMBERS)%></p>
									</div>
								</div>
							</div>
						</button>
					</div>
				</div>
			</div>
		</div>
		<div
			class='operator-switch-grid col-10 col-md-10 col-lg-10 col-xl-10 px-0'>
			<div id='SwitchPanel' class='rightCont container-fluid'></div>
		</div>
	</div>
</div>
<%@include file="/Views/Footer.jsp"%>


<%-- <%@include file="/Views/Partial/OperatorModal.jsp"%> --%>

<%@page import="phoenix.mes.content.AppBuild"%>
<%@page import="phoenix.mes.content.controller.operator.DataSheetLoader"%>
<%@include file="/Views/Header.jsp"%>
<script>
<%@ include file="/Views/Operator/Operator.js"%>

<%-- <%task = (Task.Details)session.getAttribute("Task");%> --%>

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
		<div class='button-container h-100 light-shadow col-10 col-md-10 col-lg-10 col-xl-10 px-0'>
			<div class='container-fluid px-0 h-100'>
				<div class='nav-contianer h-100'>
					<div id='btn_lejelentes'
						class='btn_navHeader-left-submit btn_navHeader-left submit-form btn_navHeader h-100 float-left'>
						<p class='h6 text-center nav-label' id='p_lejelentes'><%=outputFormatter.getWord(DictionaryEntry.SUBMIT)%></p>
						<div class='container-fluid my-nav-container h-100'>
							<div class='row h-100'>
								<div class='col-11 px-5'>
									<h4 class='h4 mt-5 mb-2'><%=outputFormatter.getWord(DictionaryEntry.SUBMIT)%></h4>
									<div class="input-group mb-3 w-100 h-25">
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
												type="button" onclick='SubmitTask()' id="button-addon2"><%=outputFormatter.getWord(DictionaryEntry.SEND)%></button>
										</div>
									</div>
								</div>
								<div class='col-1 px-0'>
									<input type='button'
										class='w-100 h-100 lejelent-btn close-nav-btn' />
								</div>
							</div>
						</div>
					</div>
					<div
						class='btn_navHeader-left btn-navHeader-left-refresh-btn refresh-click btn_navHeader h-100 float-left px-0'>
						<p class='h6 text-center nav-label'><%=outputFormatter.getWord(DictionaryEntry.REFRESH)%></p>
						<form method='POST' class='h-100 d-none refresh-form'
							action='${pageContext.request.contextPath}/OpenTask'></form>
					</div>
					<div id='btn_megszakitas'
						class='btn_navHeader h-100 float-left btn_navHeader-left'>
						<p class='h6 text-center nav-label'><%=outputFormatter.getWord(DictionaryEntry.INTERRUPT)%></p>
						<div class='container-fluid my-nav-container h-100'>
							<div class='row h-100'>
								<form method='Post'
									action='${pageContext.request.contextPath}/OpenTask'
									class='d-none interrupt-form'></form>
								<div class='col-11'>
									<button
										class="btn btn-outline-secondary mt-5 w-100 submit-action-btn"
										type="button" onclick='InterruptTask()' id="interrupt-btn"><%=outputFormatter.getWord(DictionaryEntry.INTERRUPT)%></button>
								</div>
								<div class='col-1 px-0'>
									<input type='button'
										class='w-100 h-100 close-nav-btn megszak-btn' />
								</div>

							</div>
						</div>
					</div>
					<div class='btn_navHeader h-100 float-right px-0'>
						<form method='POST' class='h-100'
							action='${pageContext.request.contextPath}/Logout'>
							<input class='btn_logout-gray' type='submit' value='' />
						</form>
					</div>

					<div class='personal-data-display h-100 float-right px-0'>
						<div class='form-group'>
							<input
								class='form-control personal-form-control personal-form-name'
								disabled
								value='<%=(String) session.getAttribute("displayname")%>'>
						</div>
						<div class='form-group'>
							<input class='form-control personal-form-control' disabled
								value='<%=((String) session.getAttribute("operatorWorkstation")).replace('!', '-')%>'>
						</div>
						<div class='form-group personal-secondery'>
							<input class='form-control personal-form-control personal-date'
								disabled>
						</div>
						<div class='form-group personal-secondery'>
							<input class='form-control personal-form-control personal-time'
								disabled>
						</div>
						<div class='form-group py-2'>
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
						<div class='col-12 px-0 navigation-button'
							id='navigation-button-1' onclick='NavigationButtonClick(this)'>
							<p class='nav-btn-1 nav-label'><%=outputFormatter.getWord(DictionaryEntry.GENERAL)%></p>
						</div>
					</div>
					<div class='btn_leftNavigation big-nav-button row'>
						<div class='col-12 px-0 navigation-button'
							id='navigation-button-2' onclick='NavigationButtonClick(this)'>
							<p class='nav-btn-2 nav-label'><%=outputFormatter.getWord(DictionaryEntry.DOCUMENTS)%></p>
						</div>
					</div>
					<div class='btn_leftNavigation big-nav-button row'>
						<div class='col-12 px-0 navigation-button'
							id='navigation-button-3' onclick='NavigationButtonClick(this)'>
							<p class='nav-btn-3 nav-label'><%=outputFormatter.getWord(DictionaryEntry.BILL_OF_MATERIAL)%></p>
						</div>
					</div>
					<div class='btn_leftNavigation big-nav-button row'>
						<div class='col-12 px-0 navigation-button'
							id='navigation-button-3' onclick='NavigationButtonClick(this)'>
							<p class='nav-btn-3 nav-label'><%=outputFormatter.getWord(DictionaryEntry.BILL_OF_MATERIAL)%></p>
						</div>
					</div>
					<div class='btn_leftNavigation bord-radius-nav big-nav-button row'>
						<div class='col-12 px-0 navigation-button'
							id='navigation-button-4' onclick='NavigationButtonClick(this)'>
							<p class='nav-btn-4 nav-label'><%=outputFormatter.getWord(DictionaryEntry.ORDER_INFO)%></p>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div
			class='operator-switch-grid col-10 col-md-10 col-lg-10 col-xl-10 px-0'>
			<div id='SwitchPanel' class='rightCont'></div>
		</div>
	</div>
</div>
<%@include file="/Views/Footer.jsp"%>

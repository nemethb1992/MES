<%@include file="/Views/Header.jsp"%>
<script><%@ include file="/Views/WelcomePage/WelcomePage.js"%></script>


<!-- <div class=''></div> -->

<!-- <div class=''> -->

<!-- </div> -->


<div class='container-fluid'>
	<div class='row'>
		<div class='container'>
			<div class='row '>
				<div class='col-12 element-shadow myPanel'>
					<div class='row '>
						<div class='col-12 px-0'>
							<div class='container'>
								<div class='row'>
									<div class='col-12 upper-stripe light-shadow'>
										<img
											src="${pageContext.request.contextPath}/Public/icons/pm_logo_mini_white.svg"
											class='row h-100 float-left ml-1 d-block img-fluid py-2'
											style="width: 50px;" />
										<p class='name-title' id='p_loginName'></p>
									</div>
								</div>
							</div>
						</div>
						<div class='col-12'>
							<div class='row'>
								<div class='col-12 select-container'>
									<div class='container'>
										<div class='row h-100 '>
											<div class='col-6'>
												<button class='toOperator welcomepage-select-a mx-auto  light-shadow'
													onclick='goToPage(this)' value='/Operator'>
													<img
														src="${pageContext.request.contextPath}/Public/icons/wrench.svg"
														class='img-fluid select-img' />
													<p class='text-center mt-5 h3'>Operator</p>
												</button>
											</div>
											<div class='col-6'>
												<button class='toManager welcomepage-select-a mx-auto light-shadow'
													onclick='goToPage(this)' value='/Manager'>
													<img
														src="${pageContext.request.contextPath}/Public/icons/sketch.svg"
														class='img-fluid select-img' />
													<p class='text-center mt-5 h3'>Manager</p>
												</button>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<%@include file="/Views/Footer.jsp"%>
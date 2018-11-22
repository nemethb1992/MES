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
									<div class='col-12 upper-stripe col-nav-logo light-shadow'>

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
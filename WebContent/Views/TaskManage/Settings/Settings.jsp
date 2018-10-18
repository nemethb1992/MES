<%@include file="/Views/Header.jsp"%>
<script>
	
<%@ include file="/Views/TaskManage/Settings/Settings.js"%>
	
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
					<form method='POST' class='h-100'
						action='${pageContext.request.contextPath}/Main'>
						<input class='btn_back-gray' type='submit' value='' />
					</form>
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
	</div>

	<form class='py-2'>
		<div class='row'>
			<div class='container mycontainer'>
				<div class='row'>
					<div class='col'>
						<div class="form-group">
							<p class='h4'>Settings</p>
						</div>
						<div class="form-group">
							<label for="exampleFormControlSelect1">Profit Center</label> <select
								class="form-control" id="exampleFormControlSelect1">
								<option>Rose/Bopla</option>
								<option>DewertOkin</option>
								<option>Rose+Krieger</option>
								<option>Spritzerei</option>
								<option>Kundisch</option>
							</select>
						</div>
						<div class="form-group">
							<label for="exampleFormControlSelect1">Email address</label> <input
								type="email" class="form-control my-email-input"
								id="exampleFormControlInput1"
								placeholder="example@phoenix-mecano.hu">
						</div>

					</div>
					<div class='col'>
						<div class="form-group">
							<p class='h4'>-</p>
						</div>
						<div class="form-group">
							<label for="exampleFormControlSelect1">Gépcsoport</label> <select
								class="form-control" id="exampleFormControlSelect1">
								<option>Rose/Bopla</option>
								<option>DewertOkin</option>
								<option>Rose+Krieger</option>
								<option>Spritzerei</option>
								<option>Kundisch</option>
							</select>
						</div>
					</div>
				</div>
			</div>
		</div>
	</form>

</div>

<%@include file="/Views/Footer.jsp"%>
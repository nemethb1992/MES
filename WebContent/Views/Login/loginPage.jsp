
<%
String info = (String)request.getAttribute("infoTitle");
if(info == null)
{
	info = "";
}

String workstationName = request.getParameter("workstation");
if(null != workstationName && !"null".equals(workstationName))
{
	workstationName = workstationName.replace("!"," - ");
}
else{
	try{
		workstationName = (String)request.getSession().getAttribute("operatingWorkstation");
		workstationName = workstationName.replace("!"," - ");
	}catch(Exception e){
	}
}

%>
<%@include file="/Views/Header.jsp"%>
<script><%@ include file="/Views/Login/loginScript.js"%></script>
<script src="${pageContext.request.contextPath}/Public/js/script.js"></script>
<div class="container h-100 login-container">
	<div class='row distance-row'></div>
	<div class="login-row row w-100 align-self-center">
		<!--        		<div id='loginFrame' class='mycontainer'> -->
		<div id='loginLeftSide'
			class='col-md-1 col-lg-5 d-none d-md-block h-100'>

			<div id='loginPicture' class='m-3 d-md-none d-lg-block'></div>

			<div id='FooterName'>
				<p class='d-md-none d-lg-block'>Phoenix Mecano Kecskemét Kft.</p>
			</div>
		</div>
		<div id='loginRightSide' class='col-md-11 col-lg-7 px-4 h-100'>
			<div class="form-group my-4 top-row">
				<%@include file="/Views/Partial/LanguageSelector.jsp"%>
				<div id='LR_1'>
					<div class='row'>
						<div class='col'>
							<p id='p_loginName' class='mb-0'></p>
						</div>
					</div>
					<div class='row'>
						<div class='col'>
							<input id='workstation_name' disabled value='<%=(workstationName != null ? workstationName : "")%>'/>
						</div>
					</div>
				</div>
			</div>
			<div id='LR_2'>
				<img
					src='${pageContext.request.contextPath}/Public/icons/padlock.svg'>
			</div>
			<form id='LR_form' method='POST'
				action='<%=response.encodeURL(request.getContextPath()+"/Enter")%>'>
				<input type='hidden' name='workstation' class='workstation' value='<%=request.getParameter("workstation")%>' /> 
				<input type='hidden' name='LayoutType' class='layout' value='<%=request.getAttribute("LayoutType")%>' />
				<input name='infoTitle' class='w-100 mt-4' value='<%=info%>' />

				<div class="form-group">
					<p id='login_title' class='w-100 w-100 h5 mt-3'><%=outputFormatter.getWord(DictionaryEntry.LOGIN)%></p>
				</div>
				<div class="form-group mb-0">
					<input name='username' autocomplete="off" class='inp_login px-3 w-100'
						placeholder='<%=outputFormatter.getWord(DictionaryEntry.USER_NAME)%>'
						id='inp_username' type='text' value=''>
				</div>
				<div class="form-group"> 
					<input type="text" hidden value="" name='password' id="hidden"/>
					<input autocomplete="off" class='inp_login px-3 w-100 pass'
						placeholder='<%=outputFormatter.getWord(DictionaryEntry.PASSWORD)%>'
						name="shownPassword" id='inp_pass' type='text' value=''>
				</div>
				<div class="form-group">
					<input class='inp_login w-100' id='inp_enterbutton' type='submit'
						value='<%=outputFormatter.getWord(DictionaryEntry.NEXT)%>'>
				</div>
			</form>
		</div>
	</div>
</div>

<%@include file="/Views/Footer.jsp"%>

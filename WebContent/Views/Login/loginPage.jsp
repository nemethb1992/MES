<%
String info = (String)request.getAttribute("infoTitle");
if(info == null)
{
	info = "";
}
%>
<%@include file="/Views/Header.jsp"%>
<script><%@ include file="/Views/Login/loginScript.js"%></script>
<script src="${pageContext.request.contextPath}/Public/js/script.js"></script>
<div class="container">
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
			<div class="form-group my-4 ">
				<div id='LR_1'>
					<p id='p_loginName'></p>
				</div>
			</div>
			<div id='LR_2'>
				<img
					src='${pageContext.request.contextPath}/Public/icons/padlock.svg'>
			</div>
			<form id='LR_form' method='POST'
				action='${pageContext.request.contextPath}/Dashboard'>
				
				<input type='hidden' name='workstation' class='workstation' value='<%= request.getParameter("ws") %>'/>
				<input name='infoTitle' class='w-100 mt-5' value='<%=info%>'/>
				<div class="form-group">
				<p id='login_title' class='w-100 w-100 h5 mt-3'><%=dict.getWord(Entry.LOGIN)%></p>
				</div>
				<div class="form-group mb-0">
					<input name='username' class='inp_login px-3 w-100' placeholder='<%=dict.getWord(Entry.USER_NAME)%>'
						id='inp_username' type='text' value='balazs.nemeth'>
				</div>
				<div class="form-group">
					<input class='inp_login px-3 w-100' placeholder='<%=dict.getWord(Entry.PASSWORD)%>' name='password' id='inp_pass'
						type='password' value='3HgB8Wy3HgB8Wy'>
				</div>
				<div class="form-group">
					<input class='inp_login w-100' id='inp_enterbutton' type='submit'
						value='<%=dict.getWord(Entry.NEXT)%>'>
				</div>
			</form>
		</div>
	</div>
</div>

<%@include file="/Views/Footer.jsp"%>

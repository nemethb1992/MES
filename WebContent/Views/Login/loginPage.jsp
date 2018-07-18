<%@include  file="/Views/Header.jsp"%>                    
       		<div id='loginFrame' class='mycontainer'>
       			<div id='loginLeftSide' class='FrameSides'>
       				<div id='loginPicture'>
       				</div>
       				<div id='FooterName'>
       				<p>Phoenix Mecano Kecskemét kft</p>
       				</div>
       			</div>
       			<div id='loginRightSide' class='FrameSides'>
       				<div id='LR_1'><p id='p_loginName'></p></div>
       				<div id='LR_2'><img src='${pageContext.request.contextPath}/Public/icons/padlock.svg'></div>
       				<div id='LR_3'><p>Bejelentkezés:</p></div>
       				<form id='LR_form' method='post' action='${pageContext.request.contextPath}/Login'>
       				<input class='inp_login'  id='inp_username' type='text' Value=''>
       				<input class='inp_login' id='inp_pass' type='text'>
       				<input class='inp_login' id='inp_enterbutton' type='submit' value='Tovább'>
       				
       				<div id='test'></div>
       				<div id='test2'></div>
       				</form>
       			</div>
       		</div>	
<%@include  file="/Views/Footer.jsp"%>     
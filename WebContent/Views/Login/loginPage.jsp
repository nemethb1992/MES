<%@include  file="/Views/Header.jsp"%>    
       		        <input type="checkbox" id="LayoutSwitch" name="feature" value="scales" checked />                
       		<div id='loginFrame' class='mycontainer'>

       			<div id='loginLeftSide' class='FrameSides'>
       				<div id='loginPicture'>
       				</div>
       				<div id='FooterName'>
       				<p>Phoenix Mecano Kecskem�t kft</p>
       				</div>
       			</div>
       			<div id='loginRightSide' class='FrameSides'>
       				<div id='LR_1'><p id='p_loginName'></p></div>
       				<div id='LR_2'><img src='${pageContext.request.contextPath}/Public/icons/padlock.svg'></div>
       				<div id='LR_3'><p>Bejelentkez�s:</p></div>
       				<form id='LR_form' method='POST' action='${pageContext.request.contextPath}/Dashboard'>
       				<input class='inp_login' placeholder='' id='inp_username' type='text' value='balazs.nemeth'>
       				<input class='inp_login' placeholder='' id='inp_pass' type='password' value='hxx8ka3HgB8Wy'>
       				<input class='inp_login' id='inp_enterbutton' type='submit' value='Tov�bb'>
       				
       				<div id='test'></div>
       				<div id='test2'></div>
       				</form>
       			</div>
       		</div>	
<%@include  file="/Views/Footer.jsp"%>     
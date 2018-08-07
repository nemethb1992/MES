<%@include  file="/Views/Header.jsp"%>  
	<div id='container' class='mycontainer'>
		<div id='leftwrap'>
			<div id='leftwrapImg'>
			</div>
			<p>Menu</p>
			<div class='sideMenuContainer'></div>
						<form method='POST' action='${pageContext.request.contextPath}/Home'>
			<input class='btn_logout' type='submit' value=''></input>
			</form>
		</div>
		<div id='contentFrame'>
			<div id='contentHeader'>
				<div id='timerContainer' class='cc_element leftSide'>
					<img src='${pageContext.request.contextPath}/Public/icons/timer.svg'>
					<p id='timerP'>0:00:00</p>
				</div>
				<div id='navigationContainer' class='cc_element rightSide'>
					<div id='btn_lejelentes' class='btn_navHeader'>
					<p class='navBtnInside_element'>Lejentés</p>
					<input class='number_Input navBtnInside_element' id=submit_input type='number'/>
					</div>
					<div id='btn_megszakitas' class='btn_navHeader'>
					<p class='navBtnInside_element'>Megszakítás</p>
					<input class='number_Input navBtnInside_element' type='number'/>
					</div>
				</div>
			</div>
			<div id='contentContainer'>
				<div id='cc_leftNav' class='cc_element leftSide'>
					<div id='btn_leftNav_1' class='btn_leftNavigation'>Azonosítók</div>
					<div id='btn_leftNav_2' class='btn_leftNavigation'>Dokumentumok</div>
					<div id='btn_leftNav_3' class='btn_leftNavigation'>Darabjegyzék</div>
					<div id='btn_leftNav_4' class='btn_leftNavigation'>Rendelési info</div>
				</div>
				<div id='cc_rightContent' class='cc_element rightSide'>
					<div id='tab1_container' class='rightCont'>
<%-- 						<%@include  file="/Views/TaskView/PageElements/tab1.jsp"%>  --%>
					</div>
					<div id='tab2_container' class='rightCont'>
<%-- 						<%@include  file="/Views/TaskView/PageElements/tab2.jsp"%>  --%>
					</div>
					<div id='tab3_container' class='rightCont'>
<%-- 						<%@include  file="/Views/TaskView/PageElements/tab3.jsp"%>  --%>
					</div>
					<div id='tab4_container' class='rightCont'>
<%-- 						<%@include  file="/Views/TaskView/PageElements/tab4.jsp"%>  --%>
					</div>					
				</div>

			</div>
		</div>
	</div>
<%@include  file="/Views/Footer.jsp"%> 
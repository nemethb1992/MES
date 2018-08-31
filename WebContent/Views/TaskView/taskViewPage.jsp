<%@include  file="/Views/Header.jsp"%>  
<script>
	<%@ include file="/Views/TaskView/taskViewScript.js"%>
</script> 
<div id="lang_div">
	<div value="e3" onclick="Language_Setter(this)" class="lang_bub" alt="indexlang" class="lang_bub" id="EN">
		<img class="lang_icon" src="${pageContext.request.contextPath}/Public/icons/EN.svg">
	</div>
    <div value="d3" onclick="Language_Setter(this)" class="lang_bub" alt="indexlang" class="lang_bub" id="DE">
		<img class="lang_icon" src="${pageContext.request.contextPath}/Public/icons/DE.svg">
	</div>
	<div value="h3" onclick="Language_Setter(this)" class="lang_bub" alt="indexlang" class="lang_bub" id="HU">
		<img class="lang_icon" src="${pageContext.request.contextPath}/Public/icons/HU.svg">
	</div>
</div>
<div  class='container-fluid'>
	<div class='mycontainer container'>
		<div class='row mt-5'>
			<div id='leftwrap' class='col-1 px-0'>
				<div  class=' container'>
					<img id='leftwrapImg' src="${pageContext.request.contextPath}/Public/icons/pm_logo_mini_white.svg" class='row mt-3 mx-auto d-block img-fluid'/>
					<p class='row pt-2 mx-auto'>Menu</p>
<!-- 					<div class='sideMenuContainer'></div> -->
					<img class='btn_logout w-100 row pt-2 mx-auto px-3' src="${pageContext.request.contextPath}/Public/icons/logout.svg"/>
				</div>
			</div>
		<div class='col-11 p-3'>
				<div class='row'>
					<div id='timerContainer' class='col-3'>
						<img src='${pageContext.request.contextPath}/Public/icons/timer.svg'>
						<p id='timerP'>0:00:00</p>
					</div>
					<div class='col-9'>
						<div id='row '>
							<div id='btn_lejelentes' class='btn_navHeader col-4'>
								<p class='navBtnInside_element' id='p_lejelentes'>Lejentés</p>
								<input class='number_Input navBtnInside_element' id=submit_input type='number'/>
							</div>
							<div id='btn_megszakitas' class='btn_navHeader col-4'>
								<p class='navBtnInside_element' id='p_megszakitas'>Megszakítás</p>
								<input class='number_Input navBtnInside_element' type='number'/>
							</div>
						</div>
					</div>
				</div>
				<div class='row'>
					<div id='' class='cc_element col-3'>
						<div id='btn_leftNav_1' class='btn_leftNavigation row'>Azonosítók</div>
						<div id='btn_leftNav_2' class='btn_leftNavigation row'>Dokumentumok</div>
						<div id='btn_leftNav_3' class='btn_leftNavigation row'>Darabjegyzék</div>
						<div id='btn_leftNav_4' class='btn_leftNavigation row h-25'>Rendelési info</div>
					</div>
					<div id='' class='cc_element col-9'>
						<div id='tab1_container' class=''>
							</div>
							<div id='tab2_container' class='rightCont'>
							</div>
							<div id='tab3_container' class='rightCont'>
							</div>
							<div id='tab4_container' class='rightCont'>
							</div>					
						</div>
					</div>
			</div>
		</div>
	</div>
</div>
<%@include  file="/Views/Footer.jsp"%> 

<!-- 					<div class='inputContainer cc_element'> -->
<!-- 	<p>Munkaállomás</p><input type='text' value='234PG - 1'/></div> -->
<!-- <div class='inputContainer cc_element'> -->
<!-- 	<p>Munkalapszám</p><input type='text' value='141436004'/></div> -->
<!-- <div class='inputContainer cc_element'> -->
<!-- 	<p>Cikkszám</p><input type='text' value='00450051558O'/></div> -->
<!-- <div class='inputContainer cc_element'> -->
<!-- 	<p>Keresöszó</p><input type='text' value='P45.005155_8'/></div> -->
<!-- <div class='inputContainer cc_element'> -->
<!-- 	<p>Megnevezés</p><input type='text' value='Sondergeh-2 IFM TS02 Bedruckung'/></div> -->
<!-- <div class='inputContainer cc_element'> -->
<!-- 	<p>Felhasználás</p><input type='text' value='KW30'/></div> -->
<!-- <div class='inputContainer cc_element'> -->
<!-- 	<p>Müveleti azonosító</p><input type='text' value='11200P'/></div> -->
<!-- <div class='inputContainer cc_element'> -->
<!-- 	<p>Keresöszó</p><input type='text' value='P11200'/></div> -->
<!-- <div class='inputContainer cc_element'> -->
<!-- 	<p>Megnevezés</p><input type='text' value='Tisztítás'/></div> -->
<!-- <div class='inputContainer cc_element'> -->
<!-- 	<p>Beállítási idö</p><input type='text' value='0.000000'/></div> -->
<!-- <div class='inputContainer cc_element'> -->
<!-- 	<p>Darabidö</p><input type='text' value='0.180000'/></div> -->
<!-- <div class='inputContainer cc_element'> -->
<!-- 	<p>Nyitott mennyiség</p><input type='text' value='1022.000'/></div> -->
<!-- <div class='inputContainer BigTextInput cc_element'> -->
<!-- 	<p>Gyártási információ</p><textarea>null</textarea></div> -->
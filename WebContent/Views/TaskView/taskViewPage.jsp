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
								<p class='navBtnInside_element' id='p_lejelentes'>Lejent�s</p>
								<input class='number_Input navBtnInside_element' id=submit_input type='number'/>
							</div>
							<div id='btn_megszakitas' class='btn_navHeader col-4'>
								<p class='navBtnInside_element' id='p_megszakitas'>Megszak�t�s</p>
								<input class='number_Input navBtnInside_element' type='number'/>
							</div>
						</div>
					</div>
				</div>
				<div class='row'>
					<div id='' class='cc_element col-3'>
						<div id='btn_leftNav_1' class='btn_leftNavigation row'>Azonos�t�k</div>
						<div id='btn_leftNav_2' class='btn_leftNavigation row'>Dokumentumok</div>
						<div id='btn_leftNav_3' class='btn_leftNavigation row'>Darabjegyz�k</div>
						<div id='btn_leftNav_4' class='btn_leftNavigation row h-25'>Rendel�si info</div>
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
<!-- 	<p>Munka�llom�s</p><input type='text' value='234PG - 1'/></div> -->
<!-- <div class='inputContainer cc_element'> -->
<!-- 	<p>Munkalapsz�m</p><input type='text' value='141436004'/></div> -->
<!-- <div class='inputContainer cc_element'> -->
<!-- 	<p>Cikksz�m</p><input type='text' value='00450051558O'/></div> -->
<!-- <div class='inputContainer cc_element'> -->
<!-- 	<p>Keres�sz�</p><input type='text' value='P45.005155_8'/></div> -->
<!-- <div class='inputContainer cc_element'> -->
<!-- 	<p>Megnevez�s</p><input type='text' value='Sondergeh-2 IFM TS02 Bedruckung'/></div> -->
<!-- <div class='inputContainer cc_element'> -->
<!-- 	<p>Felhaszn�l�s</p><input type='text' value='KW30'/></div> -->
<!-- <div class='inputContainer cc_element'> -->
<!-- 	<p>M�veleti azonos�t�</p><input type='text' value='11200P'/></div> -->
<!-- <div class='inputContainer cc_element'> -->
<!-- 	<p>Keres�sz�</p><input type='text' value='P11200'/></div> -->
<!-- <div class='inputContainer cc_element'> -->
<!-- 	<p>Megnevez�s</p><input type='text' value='Tiszt�t�s'/></div> -->
<!-- <div class='inputContainer cc_element'> -->
<!-- 	<p>Be�ll�t�si id�</p><input type='text' value='0.000000'/></div> -->
<!-- <div class='inputContainer cc_element'> -->
<!-- 	<p>Darabid�</p><input type='text' value='0.180000'/></div> -->
<!-- <div class='inputContainer cc_element'> -->
<!-- 	<p>Nyitott mennyis�g</p><input type='text' value='1022.000'/></div> -->
<!-- <div class='inputContainer BigTextInput cc_element'> -->
<!-- 	<p>Gy�rt�si inform�ci�</p><textarea>null</textarea></div> -->
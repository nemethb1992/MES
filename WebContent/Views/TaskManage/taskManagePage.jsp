<%@include  file="/Views/Header.jsp"%>  
    <script>
        <%@ include file="/Views/TaskManage/taskManageScript.js"%>
    </script>
                        <div id="lang_div">
                    <div value="e2" onclick="Language_Setter(this)" class="lang_bub" alt="indexlang" class="lang_bub" id="EN">
                        <img class="lang_icon" src="${pageContext.request.contextPath}/Public/icons/EN.svg">
                    </div>
                    <div value="d2" onclick="Language_Setter(this)" class="lang_bub" alt="indexlang" class="lang_bub" id="DE">
                        <img class="lang_icon" src="${pageContext.request.contextPath}/Public/icons/DE.svg">
                    </div>
                    <div value="h2" onclick="Language_Setter(this)" class="lang_bub" alt="indexlang" class="lang_bub" id="HU">
                        <img class="lang_icon" src="${pageContext.request.contextPath}/Public/icons/HU.svg">
                    </div>
                </div>
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
			<div id='TM_Select_container_activity' class='TM_content_layer'>
				<%@include  file="/Views/TaskManage/PageElements/activitySelect.jsp"%> 
			</div>
			<div id='TM_Select_container1' class='TM_content_layer'>
				<%@include  file="/Views/TaskManage/PageElements/stationTaskSort.jsp"%> 
			</div>
			<div id='TM_Select_container2' class='TM_content_layer'>
				<%@include  file="/Views/TaskManage/PageElements/stationVisionControl.jsp"%> 
			</div>
			<div id='TM_Select_container3' class='TM_content_layer'>
				<%@include  file="/Views/TaskManage/PageElements/stationSettings.jsp"%> 
			</div>
	</div>
<%@include  file="/Views/Footer.jsp"%> 
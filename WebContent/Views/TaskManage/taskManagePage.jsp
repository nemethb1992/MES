<%@include file="/Views/Header.jsp"%>
<script>
        <%@ include file="/Views/TaskManage/taskManageScript.js"%>
    </script>
<div id="lang_div">
	<div value="e2" onclick="Language_Setter(this)" class="lang_bub"
		alt="indexlang" class="lang_bub" id="EN">
		<img class="lang_icon"
			src="${pageContext.request.contextPath}/Public/icons/EN.svg">
	</div>
	<div value="d2" onclick="Language_Setter(this)" class="lang_bub"
		alt="indexlang" class="lang_bub" id="DE">
		<img class="lang_icon"
			src="${pageContext.request.contextPath}/Public/icons/DE.svg">
	</div>
	<div value="h2" onclick="Language_Setter(this)" class="lang_bub"
		alt="indexlang" class="lang_bub" id="HU">
		<img class="lang_icon"
			src="${pageContext.request.contextPath}/Public/icons/HU.svg">
	</div>
</div>
<div class='container-fluid'>
	<div class='row  mt-5'>
		<div class='mycontainer container'>
			<div class='row h-100'>
				<div id='leftwrap' class='col-1 px-0'>
					<div class=' container px-0'>
						<img id='leftwrapImg'
							src="${pageContext.request.contextPath}/Public/icons/pm_logo_mini_white.svg"
							class='row mt-3 mx-auto d-block img-fluid px-3' />
						<form method='POST' class='logout-form w-100'
							action='${pageContext.request.contextPath}/Home'>
							<input class='btn_logout  h-100 w-100' type='submit' value='' />
						</form>
					</div>
				</div>
				<div class='col-11'>
						<div id='TM_Select_container_activity' class='select-panel row h-100 p-4'>
							<%@include
								file="/Views/TaskManage/PageElements/activitySelect.jsp"%>
						</div>
						<div id='TM_Select_container1' class='select-panel TM_content_layer row'>
							<%@include
								file="/Views/TaskManage/PageElements/stationTaskSort.jsp"%>
						</div>
						<div id='TM_Select_container2' class='select-panel TM_content_layer row'>
							<%@include
								file="/Views/TaskManage/PageElements/stationVisionControl.jsp"%>
						</div>
						<div id='TM_Select_container3' class='select-panel TM_content_layer row'>
							<%@include
								file="/Views/TaskManage/PageElements/stationSettings.jsp"%>
						</div>
				</div>
			</div>
		</div>
	</div>
</div>
<%@include file="/Views/Footer.jsp"%>

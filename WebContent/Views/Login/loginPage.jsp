<%@include  file="/Views/Header.jsp"%>
    <script>
        <%@ include file="/Views/Login/loginScript.js"%>
//         $(document).ready(function () {
<%--             var name = "<%=request.getContextPath()%>"; --%>
//             $("#loginHeaderDivL p").html(name.split("/")[1].toUpperCase());
//         });
    </script>     
                    <div id="lang_div">
                    <div value="e1" onclick="Language_Setter(this)" class="lang_bub" alt="indexlang" class="lang_bub" id="EN">
                        <img class="lang_icon" src="${pageContext.request.contextPath}/Public/icons/EN.svg">
                    </div>
                    <div value="d1" onclick="Language_Setter(this)" class="lang_bub" alt="indexlang" class="lang_bub" id="DE">
                        <img class="lang_icon" src="${pageContext.request.contextPath}/Public/icons/DE.svg">
                    </div>
                    <div value="h1" onclick="Language_Setter(this)" class="lang_bub" alt="indexlang" class="lang_bub" id="HU">
                        <img class="lang_icon" src="${pageContext.request.contextPath}/Public/icons/HU.svg">
                    </div>
                </div>
    
       		        <input type="checkbox" id="LayoutSwitch" name="feature" value="scales" checked />                
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
       				<div id='LR_3'><p id='login_title'>Bejelentkezés:</p></div>
       				<form id='LR_form' method='POST' action='${pageContext.request.contextPath}/Dashboard'>
       				<input class='inp_login' placeholder='' id='inp_username' type='text' value='balazs.nemeth'>
       				<input class='inp_login' placeholder='' id='inp_pass' type='password' value='3HgB8Wy3HgB8Wy'>
       				<input class='inp_login' id='inp_enterbutton' type='submit' value='Tovább'>
       				
       				<div id='test'></div>
       				<div id='test2'></div>
       				</form>
       			</div>
       		</div>	
<%@include  file="/Views/Footer.jsp"%>     
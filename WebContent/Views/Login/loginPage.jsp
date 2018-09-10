<%@include  file="/Views/Header.jsp"%>


    <script>
//         $(document).ready(function () {
	
        <%@ include file="/Views/Login/loginScript.js"%>
        <%--  var name = "<%=request.getContextPath()%>"; --%>
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
       		        
       		        
       		        
       		        
       		         
        <div class="container">      
            <div class="login-row row  w-100 align-self-center">       
<!--        		<div id='loginFrame' class='mycontainer'> -->
       			<div id='loginLeftSide' class='col-md-1 col-lg-5 d-none d-md-block h-100'>
       			
       			
       				<div id='loginPicture' class='m-3 d-md-none d-lg-block'>
       				</div>
       				
       				<div id='FooterName'>
       				<p class='d-md-none d-lg-block'>Phoenix Mecano Kecskemét kft</p>
       				</div>
       				
       				
       			</div>
       			<div id='loginRightSide' class='col-md-11 col-lg-7 px-4 h-100'>
       				<div class="form-group my-4 ">
       				<div id='LR_1'><p id='p_loginName'></p></div>
					</div>
       				<div id='LR_2'><img src='${pageContext.request.contextPath}/Public/icons/padlock.svg'></div>
       				<form id='LR_form' method='POST' action='${pageContext.request.contextPath}/Dashboard'>
       				
       				<div class="form-group">
       				<p id='login_title' class='w-100 w-100 h5 mt-5'>Bejelentkezés:</p>
					</div>
       				<div class="form-group mb-0">
       				<input class='inp_login px-3 w-100' placeholder='' id='inp_username' type='text' value=''>
					</div>
       				<div class="form-group">
       				<input class='inp_login px-3 w-100' placeholder='' id='inp_pass' type='password' value=''>
					</div>
       				<div class="form-group">
       				<input class='inp_login w-100' id='inp_enterbutton' type='submit' value='Tovább'>
					</div>
					
       				</form>
       			</div>
       		</div>	
       		</div>	
<!--        </div>	 -->
       	</div>	
       		
       		
       		
       		
       		<%@include  file="/Views/Footer.jsp"%>     
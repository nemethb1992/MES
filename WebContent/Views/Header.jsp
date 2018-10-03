<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>


<%@page import="phoenix.mes.content.Dictionary"%>
<%@page import="phoenix.mes.content.Dictionary.Entry"%>
<% Dictionary dict = (Dictionary)session.getAttribute("Dictionary"); %>

<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<script src="${pageContext.request.contextPath}/Public/js/jquery-3.3.1.js"></script>
<script src="${pageContext.request.contextPath}/Public/js/jquery_cookie.js"></script>
<script src="${pageContext.request.contextPath}/Public/js/bootstrap.js"></script>
<script src="${pageContext.request.contextPath}/Public/js/script.js"></script>
<script src="${pageContext.request.contextPath}/Public/js/language.js"></script>
<link rel="shortcut icon" type="image/png" href="${pageContext.request.contextPath}/Public/icons/pm_logo_mini.ico"/>
    <style>
    <%@ include file="/Public/css/bootstrap.min.css"%>
    <%@ include file="/Public/css/bootstrap-grid.min.css"%>
    <%@ include file="/Public/css/gijgo.min.css"%>
    <%@ include file="/Public/css/Style.css"%>
    <%@ include file="/Views/Login/loginStyle.css"%>
    <%@ include file="/Views/TaskView/taskViewStyle.css"%>
    <%@ include file="/Views/TaskManage/taskManageStyle.css"%>
    <%@ include file="/Views/WelcomePage/WelcomePage.css"%>
    </style>


<title>MES</title>
<script>
$(document).ready(function () {
    var name = "<%=request.getContextPath()%>";
    $("#p_loginName").html(name.split("/")[1].toUpperCase());
});
</script>
</head>
<body> 
<div id="lang_div">
	<div  onclick="selectLanguage(this)" class="lang_bub" class="lang_bub" id="en">
		<img class="lang_icon" src="${pageContext.request.contextPath}/Public/icons/EN.svg">
	</div>
	<div onclick="selectLanguage(this)" class="lang_bub" class="lang_bub" id="de">
		<img class="lang_icon" src="${pageContext.request.contextPath}/Public/icons/DE.svg">
	</div>
	<div onclick="selectLanguage(this)" class="lang_bub" class="lang_bub" id="hu">
		<img class="lang_icon" src="${pageContext.request.contextPath}/Public/icons/HU.svg">
	</div>
</div>
<div class='countDownSpan'>
<p class='w-50 float-left'>Time:</p>
<span class='w-50 float-right' id="counterSpan"></span>
</div>

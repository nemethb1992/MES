<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <script src="${pageContext.request.contextPath}/Public/js/jquery-3.3.1.js"></script>
        <script src="${pageContext.request.contextPath}/Public/js/datepicker.js"></script>
        <script src="${pageContext.request.contextPath}/Public/js/jquery_cookie.js"></script>
        <script src="${pageContext.request.contextPath}/Public/js/script.js"></script>
        <script src="${pageContext.request.contextPath}/Public/js/language.js"></script>
        <script src="${pageContext.request.contextPath}/Public/js/datepicker.js"></script>
<%--         <script src="${pageContext.request.contextPath}/Views/TaskView/taskViewScript.js"></script> --%>
<%--         <script src="${pageContext.request.contextPath}/Views/Login/loginScript.js"></script> --%>
<%--         <script src="${pageContext.request.contextPath}/Views/TaskManage/taskManageScript.js"></script> --%>
    <link rel="shortcut icon" type="image/png" href="${pageContext.request.contextPath}/Public/icons/pm_logo_mini.ico"/>
        <style>
    <%@ include file="/Public/css/bootstrap.min.css"%>
<%--     <%@ include file="/Public/css/bootstrap.min.css"%> --%>
    <%@ include file="/Public/css/bootstrap-grid.min.css"%>
<%--     <%@ include file="/Public/css/bootstrap-reboot.min.css"%> --%>
    <%@ include file="/Public/css/gijgo.min.css"%>
    <%@ include file="/Public/css/Style.css"%>
        <%@ include file="/Views/Login/loginStyle.css"%>
    <%@ include file="/Views/TaskView/taskViewStyle.css"%>
    <%@ include file="/Views/TaskManage/taskManageStyle.css"%>
    </style>

<!--     <script> -->
<%--         <%@ include file="/Views/Login/loginScript.js"%> --%>
<%--         <%@ include file="/Views/TaskView/taskViewScript.js"%> --%>
<%--         <%@ include file="/Views/TaskManage/taskManageScript.js"%> --%>
<!--     </script> -->
        
        
<title>MES</title>
<script>
$(document).ready(function () {
    var name = "<%=request.getContextPath()%>";
    $("#p_loginName").html(name.split("/")[1].toUpperCase());
});
</script>
</head>
<body> 

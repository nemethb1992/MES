<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <script src="${pageContext.request.contextPath}/Public/js/jquery-3.2.1.js"></script>
        <script src="${pageContext.request.contextPath}/Public/js/jquery_cookie.js"></script>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/Public/css/Style.css"/>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/Views/Login/loginStyle.css"/>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/Views/TaskView/taskViewStyle.css"/>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/Views/TaskManage/taskManageStyle.css"/>
        <script src="${pageContext.request.contextPath}/Views/Login/loginScript.js"></script>
        <script src="${pageContext.request.contextPath}/Views/TaskView/taskViewScript.js"></script>
        <script src="${pageContext.request.contextPath}/Views/TaskManage/taskManageScript.js"></script>
        
        
          <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
  <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<title>MES</title>
<script>
$(document).ready(function () {
    var name = "<%=request.getContextPath()%>";
    $("#p_loginName").html(name.split("/")[1].toUpperCase());
});
</script>
</head>
<body> 

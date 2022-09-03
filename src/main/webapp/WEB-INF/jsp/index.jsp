<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String token = request.getParameter("token");//用request得到
    request.setAttribute("token",token);
%>
    <html>
<head>
    <title>AiChat</title>
    <link rel="icon" href="${pageContext.request.contextPath}/image/icon/favicon.png" type="image/png">
</head>
<frameset rows="32px,*" border="0" bordercolor="#c4c4c4">
    <frame src="top" name="topFrame" noresize="noresize" scrolling="no">
    <frame src=
           <c:if test='${!empty user}'>
                   '${pageContext.request.contextPath}/user/jumpToChat/${user.id}?token=${token}'
            </c:if>
            <c:if test="${empty user}">
                'show'
            </c:if>
    name="bottom" noresize="noresize" scrolling="">
</frameset>

</html>

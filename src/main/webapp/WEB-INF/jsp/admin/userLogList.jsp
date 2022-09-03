<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: aoufgo
  Date: 2021/5/11
  Time: 上午12:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <script src="${pageContext.request.contextPath}/static/js/jquery.min63b9.js?v=2.1.4"></script>
    <script src="${pageContext.request.contextPath}/static/js/bootstrap.min14ed.js?v=3.3.6"></script>
    <script src="${pageContext.request.contextPath}/static/js/jquery.metisMenu.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/jquery.slimscroll.min.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/static/plugin/layui/layui.all.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/contabs.min.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/pace.min.js"></script>
    <%--<script src="tablecssjs/face/fkjava_timer.js"></script>--%>
    <link href="${pageContext.request.contextPath}/static/css/bootstrap.min14ed.css?v=3.3.6" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/static/css/animate.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/static/plugin/fontawesome/css/all.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/static/css/style.min862f.css?v=4.1.0" rel="stylesheet">
</head>
<body style="text-align: center">
<div class="row" style="width: 100%;margin: 0 auto">
    <div class="col-sm-12">
        <div class="ibox float-e-margins animate__animated  animate__faster animate__fadeIn">
            <div class="ibox-title">
                <h5>用户日志</h5>
            </div>
            <div class="ibox-content">
                <div class="table-responsive">
                    <table class="table table-striped">
                        <thead>
                        <tr>
                            <th>日志ID</th>
                            <th>用户ID</th>
                            <th>执行方法</th>
                            <th>时间</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${logs}" var="log" varStatus="count">
                            <tr>
                                <td>${log.logId}</td>
                                <td>${log.userId}</td>
                                <td>${log.operation}</td>
                                <td>${log.createTime}</td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
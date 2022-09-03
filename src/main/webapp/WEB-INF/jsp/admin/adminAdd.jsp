w<%--
  Created by IntelliJ IDEA.
  User: teng
  Date: 2021/4/28
  Time: 下午9:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
    <script src="${pageContext.request.contextPath}/static/js/jquery.min.js"></script>
    <meta charset="utf-8">
    <title>添加普通管理员</title>

    <link href="${pageContext.request.contextPath}/static/css/bootstrap.min14ed.css?v=3.3.6" rel="stylesheet">
    <script src="${pageContext.request.contextPath}/static/js/layui.all.js"></script>

    <link href="${pageContext.request.contextPath}/static/css/font-awesome.min93e3.css?v=4.4.0" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/static/css/custom.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/static/css/animate.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/static/css/style.min862f.css?v=4.1.0" rel="stylesheet">
    <style>
        .form-control1{
            display: inline;
            width: 215px;
        }
        .send{
            display: inline;
            height: 32px;
        }
        .register_div{
            border-radius: 20px;
            background-color:white;
            width:500px;
            height:400px;
            z-index:2;
            display: block;
            margin: 20px auto;
            border-top:none;
            text-align: center;
        }
        .btn-primary:hover {
            background-color: #c10606 !important;
            border-color: #c10606 !important;
        }
    </style>
</head>
<script src="${pageContext.request.contextPath}/static/js/layui.all.js"></script>
<body>
<div id="register_div" class="register_div">
    <div class="middle-box text-center loginscreen   animated fadeInDown">
        <div>
            <h2>添加管理员信息 </h2>
            <form class="m-t" role="form" action="${pageContext.request.contextPath}/admin/insertAdmin" target="_parent" method="post">
                <div class="form-group">
                    管理员名称:<input type="text" class="form-control" placeholder="请输入管理员名称" name="adminName" id="adminName" required=""><br>
                    <span id="adminmsg">&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp</span>
                </div>
                <div class="form-group">
                    密码:<input type="password" name="adminPassword" id="adminPassword" class="form-control" placeholder="请输入密码" required=""><br>
                    <span id="passwordmsg">&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp</span>
                </div>
                <div class="form-group">
                    再次输入密码:<input type="password" class="form-control" placeholder="请再次输入密码" name="adminPassword2" id="adminPassword2" required=""><br>
                    <span id="passwordmsg2">&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp</span>
                </div>

                <button type="submit" class="btn btn-primary block full-width m-b">添  加</button>


            </form>
        </div>
    </div>
</div>
<script src="${pageContext.request.contextPath}/js/checkAdmin.js"></script>
<script src="${pageContext.request.contextPath}/static/js/bootstrap.min14ed.js?v=3.3.6"></script>
<script src="${pageContext.request.contextPath}/static/js/icheck.min.js"></script>
<script src="${pageContext.request.contextPath}/js/layui.all.js"></script>
<script>
    var a=${param.type};
    if (a==0){
        layer.msg("添加失败，请重新添加。",{icon:2})
    }
</script>
</body>
</html>


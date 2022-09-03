<%--
  Created by IntelliJ IDEA.
  User: 兰擎
  Date: 2020/12/25
  Time: 17:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <title>用户注册</title>
    <link rel="icon" href="${pageContext.request.contextPath}/image/icon/favicon.png" type="image/png">
    <script src="${pageContext.request.contextPath}/static/js/jquery.min.js"></script>
    <link href="${pageContext.request.contextPath}/static/css/bootstrap.min14ed.css?v=3.3.6" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/static/css/custom.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/static/css/animate.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/static/css/style.min862f.css?v=4.1.0" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/static/css/style.css" rel="stylesheet">
    <script src="${pageContext.request.contextPath}/static/js/check.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/sendCode.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/bootstrap.min14ed.js?v=3.3.6"></script>
    <script src="${pageContext.request.contextPath}/static/js/icheck.min.js"></script>
    <script src="${pageContext.request.contextPath}/static/plugin/layui/layui.all.js"></script>
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
            border-radius: .5rem;
            background-color:white;
            width:430px;
            height:810px;
            z-index:2;
            display: block;
            margin: 64px auto;
            border-top:none;
            text-align: center;
        }
        .form-group {
            margin-bottom: 0;
        }
        .btn.btn-primary{
            background-color: #6bb9c8;
            border-color: #6bb9c8;
        }
        .btn.btn-primary:hover{
            background-color: #3b99ab!important;
        }
        .overlay-gradient-bottom:after {
            display: none;
        }
        .btn.btn-primary.block.full-width.m-b{
            margin-top: 30px;
        }
    </style>
    <script>
        $(function () {
            var d = new Date();
            var hour = d.getHours();
            console.log('现在时间:' + hour)
            if (hour < 4) {
                $(".bg").css("background-image", "url(${pageContext.request.contextPath}/image/20-4.jpg)");
            }
            if (hour >= 4 && hour < 6) {
                $(".bg").css("background-image", "url(${pageContext.request.contextPath}/image/4-6.jpg)");
            }
            if (hour >= 6 && hour < 8) {
                $(".bg").css("background-image", "url(${pageContext.request.contextPath}/image/6-8.jpg)");
            }
            if (hour >= 8 && hour < 10) {
                $(".bg").css("background-image", "url(${pageContext.request.contextPath}/image/8-10.jpg)");
            }
            if (hour >= 10 && hour < 14) {
                $(".bg").css("background-image", "url(${pageContext.request.contextPath}/image/10-14.jpg)");
            }
            if (hour >= 14 && hour < 16) {
                $(".bg").css("background-image", "url(${pageContext.request.contextPath}/image/14-16.jpg)");
            }
            if (hour >= 16 && hour < 18) {
                $(".bg").css("background-image", "url(${pageContext.request.contextPath}/image/16-18.jpg)");
            }
            if (hour >= 18 && hour < 20) {
                $(".bg").css("background-image", "url(${pageContext.request.contextPath}/image/18-20.jpg)");
            }
            if (20 <= hour) {
                $(".bg").css("background-image", "url(${pageContext.request.contextPath}/image/20-4.jpg)");
            }
        })

    </script>
</head>

<body id="body" class="bg col-12 order-lg-2 order-1 min-vh-100 background-walk-y position-relative overlay-gradient-bottom">
<div id="register_div" class="register_div">
    <div class="middle-box text-center loginscreen   animated fadeInDown">
    <div>
        <h2>欢迎注册AiChat </h2>
        <form class="m-t" role="form" action="user/register" method="post">
            <div class="form-group">
                用户名:<input type="text" class="form-control" placeholder="请输入用户名" name="name" id="userName" required=""><br>
                <span id="msg">&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp</span>
            </div>
            <div class="form-group">
                昵称:<input type="text" class="form-control" placeholder="请输入昵称" name="nickname" required=""><br>
            </div>
            <div class="form-group">
                <input type="radio" name="sex" value="m" id="userSex1"  required=""> 男
                &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
               <input type="radio" name="sex" value="f" id="userSex"  required=""> 女
                <br>
            </div>
            <div class="form-group">
                年龄:<input type="number" name="age" id="age" class="form-control" placeholder="请输入年龄" required=""><br>
            </div>
            <div class="form-group">
                密码:<input type="password" name="password" id="userPassword" class="form-control" placeholder="请输入密码" required=""><br>
                <span id="msg1">&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp</span>
            </div>
            <div class="form-group">
                再次输入密码:<input type="password" class="form-control" placeholder="请再次输入密码" name = "userPassword2" id="userPassword2" required=""><br>
                <span id="msg3">&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp</span>
            </div>
            <div class="form-group">
                手机号码:<input type="tel" name="phone" id="userPhone" class="form-control" placeholder="请输入手机号" required=""><br>
                <span id="msg2">&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp</span>
            </div>
            <div class="form-group">
                <input type="text" class="form-control form-control1" placeholder="请输入验证码" name="code" required="" id="code" style="width:200px;">
                <button type="button"  class="sendCode btn btn-primary" id="sendCode">发送验证码</button>
            </div>

            <button type="submit" class="btn btn-primary block full-width m-b" id="submit">注 册</button>

            <p class="text-muted text-center"><small>已经有账户了？</small><a href="${pageContext.request.contextPath}/login">点此登录</a>
            </p>

        </form>
    </div>
</div>
</div>
<script>
    var a = ${type}
    if(a === 0){
        layer.msg("注册失败",{icon:2})
    }
    if(a === 2){
        layer.msg("验证码错误!",{icon:2})
    }
</script>
</body>
</html>

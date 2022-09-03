<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2020/12/21 0021
  Time: 15:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>

<head>
    <link href="${pageContext.request.contextPath}/static/css/top.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/plugin/fontawesome/css/all.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/animate.min.css"/>
    <script src="${pageContext.request.contextPath}/static/js/jquery.min.js"></script>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title></title>
    <style>
        .top-nav li a {
            color: #FFFFFF;
        }

        .top-nav {
            background: rgba(28, 148, 171, 0.65);
            border-radius: 0 0 15px 15px;

        }

        .fas {
            color: #FFFFFF;
        }

        .online {
            color: #0abb87;
            font-size: 13px;
            text-shadow: -1px 0 white, 0 1px white, 1px 0 white, 0 -1px white;
        }

        .offline {
            color: gray;
            font-size: 13px
        }
    </style>
</head>
<body style="background-color: #fff">
<div class="top-nav animate__animated animate__slideInDown">
    <ul class="top-nav-left">
        <li>
            <a href="${pageContext.request.contextPath}/index" target="_top">
                <img  style="margin-top: 6px" src="${pageContext.request.contextPath}/image/icon/logo1.png" alt="logo">
            </a>
        </li>
        <c:if test="${empty user}">
            <li>
                <span class="welcome"><font
                        style="color: #FFFFFF; font-size: 13.333333px">欢迎您AiChat通讯录管理系统</font></span>
                <!--文字可以使用span标签放置-->
            </li>
            <li>
                <i class="fas fa-sign-in-alt"></i>
                <a href="login" target="_top">请登录</a>
            </li>
            <li>
                <a href="register" target="_top">免费注册</a>
            </li>
        </c:if>
        <c:if test="${!empty user}">
            <li onclick="parent.frames['bottom'].showProfile()" style="cursor: pointer">
                <img id="avatar" alt="image" class="img-circle" style="margin-top: 4px;border-radius: 50px"
                <c:if test="${!empty user.avatarUrl}"> src="${user.avatarUrl}"
                </c:if>
                <c:if test="${empty user.avatarUrl}">
                     src="${pageContext.request.contextPath}/image/portrait/img.jpg" </c:if> width=25 height=25>
            </li>
            <li>
                <font style="color: #FFFFFF; font-size: 13px;margin-top: 1px;cursor: pointer" onclick="parent.frames['bottom'].showProfile()">
                    <font style="color: #fc6b0e;text-shadow: -1px 0 white, 0 1px white, 1px 0 white, 0 -1px white;">
                            ${user.nickname}
                    </font>
                </font>
                <span class="welcome" onclick="parent.location.reload()" style="cursor: pointer">
                    <font class="offline">离线</font>
                </span><!--文字可以使用span标签放置-->
            </li>
        </c:if>
    </ul>
    <ul class="top-nav-right">
        <c:if test="${!empty user}">
            <li>
                <i class="fas fa-sign-out-alt"></i>
                <a onclick="quit()" style="cursor: pointer">安全退出</a>
            </li>
        </c:if>
        <li>
            <i class="fas fa-question-circle"></i>
            <a onclick="parent.frames['bottom'].help()" style="cursor: pointer"> 联系我们</a>
        </li>
    </ul>
</div>
<script>
    function quit() {
        window.parent.frames['bottom'].closeWebSocket();
        $.get("user/quit");
        top.location = '${pageContext.request.contextPath}/index';
    }

    function offline1() {
        $(".online").removeClass("online").addClass("offline").html("离线").attr("onclick", "top.location.reload()").css("cursor", "pointer");
    }

    function online1() {
        $(".offline").removeClass("offline").addClass("online").html("在线");
    }
</script>

</body>
</html>

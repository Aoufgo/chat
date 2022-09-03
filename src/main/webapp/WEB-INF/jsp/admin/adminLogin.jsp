<%--
  Created by IntelliJ IDEA.
  User: teng
  Date: 2021/4/26
  Time: 下午3:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%--    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />--%>
    <title>管理员登录入口</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/main.css">
    <link href="${pageContext.request.contextPath}/static/css/style.min862f.css?v=4.1.0" rel="stylesheet">
</head>
<body>
<div id="app">
    <div class="login_warp">
        <div class="loginbox fl">
            <div class="login_header">
                <span @click="cur=0" :class="{active:cur==0}">账号登录</span>
            </div>
            <div class="login_content">
                <form name="numberlogin" method="post" action="${pageContext.request.contextPath}/admin/login">
                <div v-show="cur==0" class="Cbody_item">
                    <div class="form_item"><input type="text" id="adminName" name="adminName"placeholder="用户名"></div>
                    <div class="form_item"><input type="password" id="adminPassword" name="adminPassword" placeholder="密码"></div>
                    <div class="form_item">
                        <div class="fl"><input type="checkbox">记住密码</div>
                        <div class="fr"><a href="javascript:;">找回密码</a></div>
                    </div>
                    <div class="clear"></div>
                    <div class="form_item">
                        <input type="submit" name="adminLogin" value="登录">
                    </div>
                </div>
                </form>
            </div>
        </div>
        <div class="loginrslider fl"></div>
    </div>
</div>
</body>
</html>
<script src="${pageContext.request.contextPath}/static/js/jquery.min63b9.js?v=2.1.4"></script>
<script src="${pageContext.request.contextPath}/static/js/jquery.slimscroll.min.js"></script>
<script src="${pageContext.request.contextPath}/static/js/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/static/plugin/layui/layui.all.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/html5shiv.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/vue.js"></script>
<script type="text/javascript">
    var app = new Vue({
        el: "#app",
        data: {
            cur:0 //默认选中第一个tab
        }
    });
</script>
<script>
    var a=${type};
    if (a==9){
        layer.msg("登录成功",{icon:1});
        top.location.href='${pageContext.request.contextPath}/admin_page/adminCenter';
    }
    if (a==8){
        layer.msg("登录失败",{icon:2})
    }
    if (${!empty error}){
        layer.msg("${error}")
    }
</script>
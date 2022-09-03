<%--
  Created by IntelliJ IDEA.
  User: teng
  Date: 2021/4/28
  Time: 下午3:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>管理员查询</title>
    <style>
        .bth-bor:hover {
            background-color: #ab8052 !important;
            border-color: #ab8052 !important;
        }

        .btn-primary:hover {
            background-color: #c10606 !important;
            border-color: #c10606 !important;
        }
    </style>
</head>
<%--<script src="${pageContext.request.contextPath}/static/js/jquery.min.js"></script>--%>
<%--<link href="${pageContext.request.contextPath}/static/css/bootstrap.min14ed.css?v=3.3.6" rel="stylesheet">--%>
<%--<link href="${pageContext.request.contextPath}/static/css/font-awesome.min93e3.css?v=4.4.0" rel="stylesheet">--%>
<%--<link href="${pageContext.request.contextPath}/static/css/custom.css" rel="stylesheet">--%>
<%--<link href="${pageContext.request.contextPath}/static/css/animate.min.css" rel="stylesheet">--%>
<%--<link href="${pageContext.request.contextPath}/static/css/style.min862f.css?v=4.1.0" rel="stylesheet">--%>
<%--<script src="${pageContext.request.contextPath}/static/js/jquery.min63b9.js"></script>--%>
<%--<script src="${pageContext.request.contextPath}/static/js/jquery.peity.min.js"></script>--%>
<%--<script src="${pageContext.request.contextPath}/static/js/bootstrap.min14ed.js"></script>--%>
<%--<script src="${pageContext.request.contextPath}/static/js/content.mine209.js"></script>--%>
<%--<script src="${pageContext.request.contextPath}/static/js/icheck.min.js"></script>--%>
<%--<script src="${pageContext.request.contextPath}/static/js/peity-demo.min.js"></script>--%>
<%--<link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/animate.min.css"/>--%>
<%--<script src="${pageContext.request.contextPath}/static/js/layui.all.js"></script>--%>
<script src="${pageContext.request.contextPath}/static/js/jquery.min63b9.js?v=2.1.4"></script>
<script src="${pageContext.request.contextPath}/static/js/bootstrap.min14ed.js?v=3.3.6"></script>
<script src="${pageContext.request.contextPath}/static/js/jquery.metisMenu.js"></script>
<script src="${pageContext.request.contextPath}/static/js/jquery.slimscroll.min.js"></script>
<script src="${pageContext.request.contextPath}/static/js/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/static/js/hplus.min862f.js?v=4.1.0"></script>
<script src="${pageContext.request.contextPath}/static/js/contabs.min.js"></script>
<script src="${pageContext.request.contextPath}/static/js/pace.min.js"></script>
<script src="${pageContext.request.contextPath}/static/js/contabs.min.js"></script>

<script src="${pageContext.request.contextPath}/static/plugin/layui/layui.all.js"></script>
<script src="${pageContext.request.contextPath}/static/js/fkjava_timer.js"></script>
<link href="${pageContext.request.contextPath}/static/css/bootstrap.min14ed.css?v=3.3.6" rel="stylesheet">
<link href="${pageContext.request.contextPath}/static/plugin/fontawesome/css/all.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/static/css/animate.min.css" rel="stylesheet">

<link href="${pageContext.request.contextPath}/static/css/style.min862f.css?v=4.1.0" rel="stylesheet">
<body style="text-align: center">
<div class="row" style="width: 80%;margin: 0 auto">
    <div class="col-sm-12" style="padding: 0">
        <div class="ibox float-e-margins animate__animated  animate__faster animate__fadeIn">
            <div class="ibox-title">
                <h3>管理员信息&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp</h3>

                <%--                        <div style="float: left"><button style="float: left" type="button" class="btn btn-sm bth-bor" onclick="layer.open({--%>
                <div>
                    <button style="float:left" type="button" class="btn btn-sm bth-bor" onclick="layer.open({
                            type:2,
                            title:['添加管理员','text-align:center;font-size:20px'],
                            area:['800px','500px'],
                            shade:0.6,
                            maxmin:true,
                            content:'${pageContext.request.contextPath}/admin_page/adminAdd'
                            })">添加管理员
                    </button>
                </div>
                <div class="ibox-tools">
                    <form align="center" action="${pageContext.request.contextPath}/admin/queryAdmin1" method="post">
                    <div class="input-group" style="float: left;display: inline">
                        <input type="text" placeholder="请输入用户名" class="input-sm form-control" name="adminName"
                               id="adminName">
                        <input type="text" placeholder="状态:200(正常)100(异常)" class="input-sm form-control" name="status"
                               id="status">
                    </div>
                        <div style="float: right" class="col-sm-3">

                            <div class="input-group">
                                <input type="text" placeholder="请输入管理员编号" class="input-sm form-control" name="adminId"
                                       id="adminId1"> <span class="input-group-btn">
                                <input type="submit" class="btn btn-sm btn-primary" value="搜索"> 搜索</input> </span>
                            </div>

                        </div>
                    </form>
                </div>
            </div>
            <div class="ibox-content">
                <div class="table-responsive">
                    <table class="table table-striped">
                        <thead>
                        <tr>
                            <th>管理员编号</th>
                            <th>管理员用户名</th>
                            <th>管理员密码</th>
                            <th>管理员状态</th>
                            <th>创建时间</th>
                            <th>登录时间</th>
                            <th>管理员权限</th>
                            <th>操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${admins}" var="admin" varStatus="count">
                            <tr>
                                <td>${admin.adminId}</td>
                                <td>${admin.adminName}</td>
                                <td>${admin.adminPassword}</td>
                                <td>${admin.status}</td>
                                <td>${admin.adminCreateTime}</td>
                                <td>${admin.adminLoginTime}</td>
                                <td>${admin.roleId}</td>
                                <td>
                                    <a href="${pageContext.request.contextPath}/admin/delAdmin?adminId=${admin.adminId}">退休</a>
                                        <%--                                    <a onclick="layer.open({--%>
                                        <%--                                            type:2,--%>
                                        <%--                                            title:['修改信息','text-align:center;font-size:20px'],--%>
                                        <%--                                            area:['800px','500px'],--%>
                                        <%--                                            shade:0.6,--%>
                                        <%--                                            maxmin:true,--%>
                                        <%--                                            content:'${pageContext.request.contextPath}/admin/getAdmin?adminId=${admin.adminId}'--%>
                                        <%--                                            })">修改信息</a>--%>
                                    <a type="button" style="cursor: pointer;" onclick="layer.open({
                                            type:2,
                                            title:['修改管理员信息','text-align:center;font-size:20px'],
                                            area:['800px','500px'],
                                            shade:0.6,
                                            maxmin:true,
                                            content:'${pageContext.request.contextPath}/admin/getAdmin?adminId=${admin.adminId}'
                                            })"><font style="color:#3279b7">修改</font></a>
                                </td>
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
<script>
    var a =${param.type};
    if (a == 0) {
        layer.msg("添加失败", {icon: 2})
    }
    if (a == 1) {
        layer.msg("添加成功", {icon: 1})
    }
    if (a == 2) {
        layer.msg("充值失败", {icon: 2})
    }
    if (a == 3) {
        layer.msg("充值成功", {icon: 1})
    }
    if (a == 4) {
        layer.msg("删除失败", {icon: 2})
    }
    if (a == 5) {
        layer.msg("删除成功", {icon: 1})
    }
    if (a == 6) {
        layer.msg("修改失败", {icon: 2})
    }
    if (a == 7) {
        layer.msg("修改成功", {icon: 1})
    }
    if (a == 8) {
        layer.msg("登录成功", {icon: 1})
    }
    if (${!empty error}) {
        layer.msg("${error}")
    }
</script>
</html>


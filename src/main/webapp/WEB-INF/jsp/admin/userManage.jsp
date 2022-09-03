<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
    <title>用户管理</title>
    <style>
        .input_sub{
            color:white;
            width:80px;
            height: 30px;
            background: #61b464;
            border:0px;
        }
        .input_sub1{
            color:white;
            width:60px;
            height: 30px;
            background: #d55854;
            border:0px;
        }
        .input_sub2{
            color:white;
            width:120px;
            height: 30px;
            background: #61b464;
            border:0px;
        }
        .btn-primary:hover{
            background-color: #c10606 !important;
            border-color: #c10606 !important;
        }
        .input_text{
            width:200px;
            height: 30px;
            border:2px solid green;
            /*清除掉默认的padding*/
            padding:0px;

            /*提示字首行缩进*/
            text-indent: 10px;

            /*去掉蓝色高亮框*/
            outline: none;}
    </style>
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
                <h5>会员查询</h5>
            </div>
            <div class="ibox-content">
                <div class="row">
                    <form  action="${pageContext.request.contextPath}/admin/queryUser" method="post" id="search">
                        <div style="float: left;padding-left: 20%">
                        <input type="text" placeholder="请输入会员编号" class="" name="id" id="id">&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
                        <input type="text" placeholder="请输入会员用户名" class="" name="name" id="name">&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
                        性别:
                        <select name="sex">
                            <option value="">选择性别</option>
                            <option value="f" >女</option>
                            <option value="m" >男</option>
                        </select>
                        </div>
                        <div style="float: right;padding-right: 20%">
                        <input  type="submit" class="btn btn-sm btn-primary"  value="搜索">
                        </div>
                    </form>
                </div>
                <div class="table-responsive">
                    <table class="table table-striped">
                        <thead>
                        <tr>
                            <th>会员编号</th>
                            <th>会员用户名</th>
                            <th>会员昵称</th>
                            <th>会员性别</th>
                            <th>会员年龄</th>
                            <th>会员手机号</th>
                            <th>会员状态</th>
                            <th>账号状态</th>
                            <th>操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${users}" var="user" varStatus="count">
                            <tr>
                                <td>${user.id}</td>
                                <td>${user.name}</td>
                                <td>${user.nickname}</td>
                                <td>
                                    <c:if test="${user.sex == 'f'}">女</c:if>
                                    <c:if test="${user.sex== 'm'}">男</c:if>
                                </td>
                                <td>${user.age}</td>
                                <td>${user.phone}</td>
                                <td>
                                    <c:if test="${user.status == 200}">在线</c:if>
                                    <c:if test="${user.status == 100}">离线</c:if>
                                </td>
                                <td>
                                    <c:if test="${user.accStatus == 200}">正常</c:if>
                                    <c:if test="${user.accStatus == 100}">异常</c:if>
                                </td>
                                <td>
                                    <a href="${pageContext.request.contextPath}/admin/delUser?id=${user.id}">删除</a>
                                    <a type="button" style="cursor: pointer;" onclick="layer.open({
                                            type:2,
                                            title:['修改会员信息','text-align:center;font-size:20px'],
                                            area:['600px','500px'],
                                            shade:0.6,
                                            maxmin:true,
                                            content:'${pageContext.request.contextPath}/admin/getUser?id=${user.id}'
                                            })"><font style="color:#3279b7">修改</font></a>
<%--                                    <a onclick="recharge(${user.userId})">充值</a>--%>

                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                    <script >
                        function recharge(userId) {
                            layer.prompt({title: '输入充值金额,并确认'}, function(money, index){
                                if (money != null)
                                    window.location ='user?method=recharge&money='+money+'&userId='+userId;
                                layer.close(index);
                            });
                        }
                    </script >
                </div>
                <fkjava:pager
                        pageIndex="${pageModel.pageIndex}"
                        pageSize="${pageModel.pageSize}"
                        recordCount="${pageModel.recordCount}"
                        submitUrl="${pageContext.request.contextPath}/user?method=queryAllUserByPage&pageIndex={0}"
                />
            </div>
        </div >
    </div>
</div>
</body>
<script>
    var a=${param.type};
    if (a==0){
        layer.msg("添加失败",{icon:2})
    }
    if (a==1){
        layer.msg("添加成功",{icon:1})
    }
    if (a==2){
        layer.msg("充值失败",{icon:2})
    }
    if (a==3){
        layer.msg("充值成功",{icon:1})
    }
    if (a==4){
        layer.msg("删除失败",{icon:2})
    }
    if (a==5){
        layer.msg("删除成功",{icon:1})
    }
    if (a==6){
        layer.msg("修改失败",{icon:2})
    }
    if (a==7){
        layer.msg("修改成功",{icon:1})
    }
    if (a==8){
        layer.msg("登录成功",{icon:1})
    }
    if (${!empty error}){
        layer.msg("${error}")
    }
</script>
</html>

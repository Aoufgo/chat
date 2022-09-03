<%--
  Created by IntelliJ IDEA.
  User: aoufgo
  Date: 2021/4/4
  Time: 下午3:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
    String token = request.getParameter("token");//用request得到
    request.setAttribute("token",token);
%>
<html>
<head>
    <title></title>
    <!-- Favicon -->
    <link rel="icon" href="${pageContext.request.contextPath}/image/icon/favicon.png" type="image/png">

    <!-- Bundle Styles -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/bundle.css">

    <!-- App styles -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/app.min.css">

    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/animate.min.css"/>

    <script src="${pageContext.request.contextPath}/static/js/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/md5.js"></script>
    <style>
        figure.avatar.avatar-state-warning:before, figure.avatar.avatar-state-success:before {
            top: 22px;
            right: -4px;
            border: 2px solid #fff;
        }

        .new-message-count {
            width: 23px;
            display: -webkit-box;
            display: -webkit-flex;
            display: -moz-box;
            display: -ms-flexbox;
            -webkit-box-align: center;
            -webkit-align-items: center;
            -moz-box-align: center;
            -ms-flex-align: center;
            align-items: center;
            -webkit-box-pack: center;
            -webkit-justify-content: center;
            -moz-box-pack: center;
            -ms-flex-pack: center;
            justify-content: center;
            line-height: 0;
            font-size: 13px;
            height: 23px;
            background-color: #3b99ab;
            color: #fff;
            -webkit-border-radius: 50%;
            -moz-border-radius: 50%;
            border-radius: 50%;
            margin-left: auto;
            display: none;
        }

        .refresh-icon {
            animation: spin 1s infinite linear;
            -moz-animation: spin 1s infinite linear;
            -webkit-animation: spin 1s infinite linear;
            -o-animation: refreshing 1s infinite linear;
        }

        @-moz-keyframes spin {
            0% {
                transform: rotate(0deg);
            }
            100% {
                transform: rotate(360deg);
            }
        }

        @-webkit-keyframes spin {
            0% {
                transform: rotate(0deg);
            }
            100% {
                transform: rotate(360deg);
            }
        }

        @-o-keyframes spin {
            0% {
                transform: rotate(0deg);
            }
            100% {
                transform: rotate(360deg);
            }
        }

        @keyframes spin {
            0% {
                transform: rotate(0deg);
            }
            100% {
                transform: rotate(360deg);
            }
        }

        .nicescroll-rails.nicescroll-rails-vr {
            display: none !important;
        }

        #mail_icon {
            margin: 0;
        }

        #video {
            transform: rotateY(180deg);
        }
    </style>
</head>
<body>
<input type="hidden" value="${user.id}" id="userId">
<!-- Page loading -->
<div class="page-loading"></div>
<!-- ./ Page loading -->

<!-- Body plate -->
<div class="body-plate"></div>
<!-- ./ Body plate -->

<!-- Disconnected modal -->
<div class="modal fade" id="disconnected" tabindex="-1" role="dialog" aria-hidden="true" data-backdrop="static">
    <div class="modal-dialog modal-lg modal-dialog-centered modal-dialog-zoom" role="document">
        <div class="modal-content">
            <div class="modal-body">
                <div class="row mb-5">
                    <div class="col-md-6 offset-md-3">
                        <img src="${pageContext.request.contextPath}/image/portrait/undraw_warning_cyit.svg"
                             class="img-fluid" alt="image">
                    </div>
                </div>
                <p class="lead text-center">服务器连接断开</p>
            </div>
            <div class="modal-footer justify-content-center">
                <button type="button" onclick="top.location.reload()" class="btn btn-success btn-lg">重新连接</button>
                <a onclick="closeWebSocket();$.get('user/quit');top.location ='${pageContext.request.contextPath}/index';"
                   href="${pageContext.request.contextPath}/index" class="btn btn-link">退出</a>
            </div>
        </div>
    </div>
</div>
<!-- ./ Disconnected modal -->
<!-- Add friends modal -->
<div class="modal fade" id="help" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered modal-dialog-zoom" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">
                    <i data-feather="help-circle" class="mr-2"></i> 联系我们
                </h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <i class="ti-close"></i>
                </button>
            </div>
            <div class="modal-body">
                <div class="alert alert-info">
                    GROUP:创新AI<br>
                    Email:lcodndks@163.com<br>
                    QQ:2465636144
                </div>
            </div>
        </div>
    </div>
</div>
<!-- ./ Add friends modal -->
<!-- Add friends modal -->
<div class="modal fade" id="addFriends" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered modal-dialog-zoom" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">
                    <i data-feather="user-plus" class="mr-2"></i> 添加好友
                </h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <i class="ti-close"></i>
                </button>
            </div>
            <div class="modal-body">
                <div class="alert alert-info">向朋友发送好友请求</div>
                <form id="addFriend">
                    <div class="form-group">
                        <label for="friendId" class="col-form-label">好友ID</label>
                        <input type="text" class="form-control" id="friendId">
                    </div>
                    <div class="form-group">
                        <label for="reqMessage" class="col-form-label">请求信息</label>
                        <textarea class="form-control" id="reqMessage"></textarea>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="submit" id="sendReq" class="btn btn-primary">发送</button>
            </div>
        </div>
    </div>
</div>
<!-- ./ Add friends modal -->
<!-- Add friends modal -->
<div class="modal fade" id="addFriendsReq" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered modal-dialog-zoom" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">
                    <i data-feather="mail" class="mr-2"></i> 好友请求
                </h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <i class="ti-close"></i>
                </button>
            </div>
            <div class="modal-body" id="reqList">
                <div class="alert alert-info">朋友向你发送的好友请求</div>
            </div>
        </div>
    </div>
</div>
<!-- ./ Add friends modal -->


<!-- Edit profile modal -->
<div class="modal fade" id="editProfileModal" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered modal-dialog-zoom" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">
                    <i data-feather="edit-2" class="mr-2"></i> 个人中心
                </h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <i class="ti-close"></i>
                </button>
            </div>
            <div class="modal-body">
                <ul class="nav nav-tabs" role="tablist">
                    <li class="nav-item">
                        <a class="nav-link active" data-toggle="tab" href="#personal" role="tab"
                           aria-controls="personal" aria-selected="true">个人设置</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" data-toggle="tab" href="#about" role="tab" aria-controls="about"
                           aria-selected="false">人脸注册</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" data-toggle="tab" href="#updatePW" role="tab"
                           aria-controls="updatePW" aria-selected="false">修改密码</a>
                    </li>
                </ul>
                <div class="tab-content">
                    <div class="tab-pane show active" id="personal" role="tabpanel">
                        <div class="alert alert-info"><span id="id">ID</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                            <span id="username">用户名</span></div>
                        <div class="form-group">
                            <label class="col-form-label">头像</label>
                            <div class="d-flex align-items-center">
                                <div>
                                    <figure class="avatar mr-3 item-rtl">
                                        <img src="${user.avatarUrl}" class="rounded-circle" id="userAvatar"
                                             alt="image">
                                    </figure>
                                </div>
                                <div class="custom-file">
                                    <form id="uploadForm">
                                        <input type="file" class="custom-file-input" name="avatar" id="customFile">
                                    </form>
                                    <label class="custom-file-label" for="customFile">选择文件</label>
                                </div>
                            </div>
                        </div>
                        <form id="updateInfo">
                            <input name="avatarUrl" type="hidden" id="avatarUrl">
                            <input name="id" value="${id}" type="hidden">
                            <div class="form-group">
                                <label for="userNickname" class="col-form-label">昵称</label>
                                <div class="input-group">
                                    <input type="text" class="form-control" name="nickname" id="userNickname">
                                    <div class="input-group-append">
                                        <span class="input-group-text">
                                            <i data-feather="user"></i>
                                        </span>
                                    </div>
                                </div>
                            </div>

                            <div class="form-group">
                                <label for="gender" class="col-form-label">性别</label>
                                <div class="input-group">
                                    <select class="form-control" name="sex" id="gender">
                                        <option value="f">男</option>
                                        <option value="m">女</option>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="age" class="col-form-label">年龄</label>
                                <div class="input-group">
                                    <input type="number" class="form-control" name="age" id="age">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="phone" class="col-form-label">手机</label>
                                <div class="input-group">
                                    <input type="text" class="form-control" id="phone">
                                    <div class="input-group-append">
                                        <span class="input-group-text">
                                            <i data-feather="phone"></i>
                                        </span>
                                    </div>
                                </div>
                            </div>
                        </form>
                    </div>
                    <div class="tab-pane" id="about" role="tabpanel">
                        <center>
                            <form>
                                <div id="media">

                                    <video style="border: #c3cad1 5px!important ;border-radius: 100%;" id="video"
                                           width="280px" height="280px" autoplay></video>
                                    <canvas id="canvas" width="300" height="300"
                                            style="position: absolute;right: 100px;top: 83px"></canvas>
                                    <img src="${pageContext.request.contextPath}/image/portrait/check.svg" id="check"
                                         alt="image" hidden>
                                </div>
                                <div style="height: 20px"></div>
                                <button type="button" id="faceUpload" onclick="f()" class="btn btn-primary">更改FaceID
                                </button>
                            </form>
                        </center>
                    </div>
                    <div class="tab-pane" id="updatePW" role="tabpanel">
                        <form>
                            <div class="form-group">
                                <label for="newpw" class="col-form-label">新密码</label>
                                <div class="input-group">
                                    <input type="password" class="form-control" name="newpw" id="newpw">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="newpw1" class="col-form-label">确认密码</label>
                                <div class="input-group">
                                    <input type="password" class="form-control" name="newpw1" id="newpw1">
                                </div>
                            </div>
                            <div style="height: 20px"></div>
                            <button type="button" onclick="changePW()" class="btn btn-primary">
                                提交
                            </button>
                            <div style="height: 20px"></div>
                        </form>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" onclick="commit()" class="btn btn-primary">保存</button>
            </div>
        </div>
    </div>
</div>
<!-- Profile modal -->
<div class="modal fade" id="friendProfileModal" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered modal-dialog-zoom" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">
                    <i data-feather="user" class="mr-2"></i> 好友信息
                </h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <i class="ti-close"></i>
                </button>
            </div>
            <div class="modal-body">
                <div class="text-center">
                    <figure class="avatar avatar-xl mb-4">
                        <img src="" id="fAvatar" class="rounded-circle" alt="image">
                    </figure>
                    <h5 class="mb-1" id="fNickname"></h5>
                    <small class="text-muted font-italic" id="fLastLogin">Last seen: Today</small>
                </div>
                <div class="tab-content" id="myTabContent">
                    <div class="tab-pane fade show active" id="home" role="tabpanel" aria-labelledby="home-tab">
                        <div class="mt-4 mb-4">
                            <h6>ID</h6>
                            <p class="text-muted" id="fId"></p>
                        </div>
                        <div class="mt-4 mb-4">
                            <h6>年龄</h6>
                            <p class="text-muted" id="fAge"></p>
                        </div>
                        <div class="mt-4 mb-4">
                            <h6>性别</h6>
                            <p class="text-muted" id="fSex"></p>
                        </div>
                        <div class="mt-4 mb-4">
                            <h6>Phone</h6>
                            <p class="text-muted" id="fPhone"></p>
                        </div>
                        <div class="mt-4 mb-4">
                            <h6 class="mb-3">Settings</h6>
                            <div class="form-group">
                                <div class="form-item custom-control custom-switch">
                                    <input type="checkbox" class="custom-control-input" id="customSwitch11">
                                    <label class="custom-control-label" for="customSwitch11">Block</label>
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="form-item custom-control custom-switch">
                                    <input type="checkbox" class="custom-control-input"
                                           id="customSwitch12">
                                    <label class="custom-control-label" for="customSwitch12">Mute</label>
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="form-item custom-control custom-switch">
                                    <input type="checkbox" class="custom-control-input" id="customSwitch13">
                                    <label class="custom-control-label" for="customSwitch13">Get
                                        notification</label>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- Profile sidebar -->
<!-- Layout -->
<div class="layout" style="height: 100%">
    <!-- Content -->
    <div class="content">
        <!-- Friends sidebar -->
        <div id="friends" class="sidebar animate__animated animate__slideInLeft">
            <header>
                <span>Friends</span>
                <ul class="list-inline">
                    <li class="list-inline-item" data-toggle="tooltip" title="Friend Req">
                        <figure class="btn btn-outline-light" id="mail_icon" href="#" data-toggle="modal"
                                data-target="#addFriendsReq">
                            <i data-feather="mail"></i>
                        </figure>
                    </li>
                    <li class="list-inline-item" data-toggle="tooltip" title="Add friends">
                        <a class="btn btn-outline-light" href="#" data-toggle="modal" data-target="#addFriends">
                            <i data-feather="user-plus"></i>
                        </a>
                    </li>

                </ul>
            </header>
            <form>
                <label for="search">查询好友</label><input id="search" type="text" class="form-control"
                                                       placeholder="Search">
            </form>
            <div class="sidebar-body">
                <p>${friends.size()} 个好友</p>
                <%--刷新好友和提示信息方法--%>
                <div style="text-align: right;padding-right: 30px;cursor: pointer;" id="refreshFriends"
                     onclick="refreshFriends()">
                    刷新
                    <div id="d"
                         style="background-size: 100%; background-image: url('${pageContext.request.contextPath}/image/icon/refresh-64x64.png');display: inline-block; height: 15px;width: 15px; margin-bottom: 2px; vertical-align:middle;"
                         class=""></div>
                </div>
                <div id="friendBar">
                    <ul class="list-group list-group-flush">
                        <c:forEach items="${friends}" var="f">
                            <li class="list-group-item" id="list-group-item-${f.id2}" style="border-radius:15px">
                                <input value="${f.id2}" type="hidden" id="id2" class="id2">
                                <input value="${f.user.nickname}" type="hidden" id="nickname">
                                <input value="${f.user.name}" type="hidden" id="name">
                                <div>
                                    <c:if test="${f.user.status == 200}">
                                    <figure class="avatar avatar-state-success" id="avatar-state-${f.id2}">
                                        </c:if>
                                        <c:if test="${f.user.status != 200}">
                                        <figure class="avatar avatar-state-warning" id="avatar-state-${f.id2}">
                                            </c:if>
                                            <c:if test="${!empty f.user.avatarUrl}">
                                                <img class="rounded-circle" src="${f.user.avatarUrl}">
                                            </c:if>
                                            <c:if test="${empty f.user.avatarUrl}">
                                                <span class="avatar-title bg-success rounded-circle">${fn:substring(f.user.name,0,1)}</span>
                                            </c:if>
                                        </figure>
                                </div>
                                <div class="users-list-body">
                                    <div onclick="linkTo(${f.id2})">
                                        <h5>${f.user.nickname}</h5>
                                        <p>${f.user.name}</p>
                                    </div>
                                    <div class="new-message-count" id="new-message-count-${f.id2}"></div>
                                    <div class="users-list-action">
                                        <div class="action-toggle">
                                            <div class="dropdown">
                                                <a data-toggle="dropdown" href="#">
                                                    <i data-feather="more-horizontal"></i>
                                                </a>
                                                <div class="dropdown-menu dropdown-menu-right">
                                                    <a href="#" onclick="fProfile(${f.id2})"
                                                       class="dropdown-item">详细信息</a>
                                                    <div class="dropdown-divider"></div>
                                                    <a href="#" class="dropdown-item text-danger">Block</a>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </li>
                        </c:forEach>
                    </ul>
                </div>
            </div>
        </div>
        <!-- ./ Friends sidebar -->
        <!-- Chat -->
        <iframe width="100%" class="chat" id="chatFrame" name="chatFrame" height="100%"
                <c:if test="${!empty friends}">
                    src="${pageContext.request.contextPath}/user/link/${user.id}/${friends[0].id2}?token=${token}"
                </c:if>
                <c:if test="${empty friends}">
                    srcdoc="<p>添加一个好友吧!</p>"
                </c:if>
                frameborder="0" data-id="" seamless scrolling="no">
        </iframe>


    </div>
    <!-- ./ Content -->

</div>
<!-- ./ Layout -->

<!-- Bundle -->


<script src="${pageContext.request.contextPath}/static/js/bundle.js"></script>

<!-- App scripts -->
<script src="${pageContext.request.contextPath}/static/js/app.min.js"></script>

<!-- Examples -->
<script src="${pageContext.request.contextPath}/static/js/websocket.js"></script>
<script src="${pageContext.request.contextPath}/static/plugin/layui/layui.all.js"></script>
<script>
    function changePW() {
        var userPassword = $("#newpw").val();
        var userPassword2 = $("#newpw1").val();
        if (userPassword !== userPassword2 || userPassword2 === '') {
            layer.msg("两次密码不一致", {icon: 2});
            return;
        }
        $.ajax({
            url: "${pageContext.request.contextPath}/user/update?token=${token}",
            type: "post",
            data: {"password": userPassword, "id": '${id}'},
            success: function (resp) {
                if (resp === "yes") {
                    layer.msg("修改成功")
                } else {
                    layer.msg("修改失败")
                }
            },
            error: function () {
                layer.msg("请求失败")
            }
        })
    }
</script>
<script>
    if (${!empty error}) {
        layer.msg("${error}")
    }


    function linkTo(id) {
        document.getElementById("chatFrame").src = '${pageContext.request.contextPath}/user/link/${user.id}/' + id + '?token=${token}';

    }

    function refreshFriends() {
        document.querySelector('#d').classList.add('refresh-icon');
        setTimeout('document.querySelector(\'#d\').classList.remove(\'refresh-icon\')', 1000);
        document.querySelector('#friendBar');
        $(".id2").each(function () {
            console.log($(this));
        })
    }

    $(function () {
        const element = $("#search")
        element.bind('keyup', function () {
            console.log(element.val())
            var count = 0;
            $(".list-group-item").each(function () {
                if ($(this).children("input#id2").val().indexOf(element.val()) === -1 &&
                    $(this).children("input#nickname").val().indexOf(element.val().toUpperCase()) === -1 &&
                    $(this).children("input#nickname").val().indexOf(element.val().toLowerCase()) === -1 &&
                    $(this).children("input#name").val().indexOf(element.val().toUpperCase()) === -1 &&
                    $(this).children("input#name").val().indexOf(element.val().toLowerCase()) === -1) {
                    $(this).hide();
                } else {
                    $(this).show();
                    count++;
                }
            })
            $(".sidebar-body").children("p:first").html(count + " 个好友");

        })
    })
    $(function () {
        $.get("${pageContext.request.contextPath}/user/getUnread/${user.id}?token=${token}", function (data) {
            var obj = JSON.parse(data);
            for (let o in obj) {
                $(".id2").each(function () {
                    if (obj[o].fromId === $(this).val()) {
                        const element = $("#new-message-count-" + obj[o].fromId);
                        element.css('display', 'flex');
                        element.html(obj[o].count);
                        //将未读消息排序前置
                        if ($(this).parent().prev()) {
                            $(this).parents("ul").children(":first").before($(this).parent());
                        }
                        return;
                    }
                })
            }
            if (${!empty reqList}) {
                $("#mail_icon").css("color", "red")
                <c:forEach items="${reqList}" var="r">
                addReq('${r.fromId}', '${r.msg.trim()}', '${r.sendTime}')
                </c:forEach>
            }
        })
    })
    $(function () {
        $("#sendReq").click(function (e) {
            e.preventDefault();
            var friendId = $("#addFriend").find('input[id=friendId]').val();
            var message = $("#addFriend").find('textarea[id=reqMessage]').val();
            if (friendId === '${id}') {
                layer.msg("您不能将自己加为好友!", {icon: 2});
            } else {
                $.get('${pageContext.request.contextPath}/user/getUser/' + friendId, function (resp) {
                    if (resp !== "yes") {
                        layer.msg("找不到用户", {icon: 2})
                        return;
                    }
                    if (resp === "yes") {
                        send('${user.name}' + ':' + message, '${user.id}', friendId, new Date().getTime(), "friendReq")
                        layer.msg("发送成功", {icon: 1})
                        $("#addFriends").modal('hide');
                    }
                })
            }
        })
    })

    function agree(id) {
        //隐藏邀请
        $(".list-group-item-" + id).hide();
        //设为已读
        $.get('${pageContext.request.contextPath}/user/isRead/' + id + '/${user.id}?token=${token}')
        $.get("${pageContext.request.contextPath}/user/addF/${user.id}/" + id + "?token=${token}", function (data) {
            if (data === "yes") {
                layer.msg("添加成功", {icon: 1})
            } else {
                layer.msg("添加失败", {icon: 2})
            }
        })
        $("#addFriendsReq").on("hide.bs.modal", function () {
            top.location.reload();
        })
    }


    function refuse(id) {
        //隐藏邀请
        $(".list-group-item-" + id).hide();
        //设为已读
        $.get('${pageContext.request.contextPath}/user/isRead/' + id + '/${user.id}?token=${token}')
    }

    function showProfile() {
        $.get("${pageContext.request.contextPath}/user/getInfo/${user.id}?token=${token}", function (resp) {
            var r = JSON.parse(resp);
            $("#userNickname").val(r.nickname);
            if (r.sex === 'f') {
                $("#gender").val("f");
            } else {
                $("#gender").val("m");
            }
            $("#age").val(r.age);
            $("#phone").val(r.phone);
            if (r.avatarUrl === "") {
                $("#userAvatar").attr("src", "${pageContext.request.contextPath}/image/portrait/img.jpg");
            } else {
                $("#userAvatar").attr("src", r.avatarUrl);
            }
            $("#id").html("ID:" + r.id);
            $("#username").html("用户名:" + r.name);
        })
        $('#editProfileModal').modal('show')
    }

    function fProfile(id) {
        $.get("${pageContext.request.contextPath}/user/getInfo/" + id + "?token=${token}", function (resp) {
            var r = JSON.parse(resp);
            $("#fNickname").html(r.nickname);
            if (r.sex === 'f') {
                $("#fSex").html("男");
            } else {
                $("#fSex").val("女");
            }
            $("#fAge").html(r.age);
            $("#fId").html(r.id);
            $("#fPhone").html(r.phone);
            if (r.avatarUrl === "") {
                $("#fAvatar").attr("src", "${pageContext.request.contextPath}/image/portrait/img.jpg");
            } else {
                $("#fAvatar").attr("src", r.avatarUrl);
            }
            var time = new Date();
            time.setTime(r.lastLoginTime);
            $("#fLastLogin").html("上次登录: " + time);
        })
        $('#friendProfileModal').modal('show')
    }

    function commit() {
        $.post("${pageContext.request.contextPath}/user/update?token=${token}",
            $("#updateInfo").serializeArray(),
            function (resp) {
                if (resp === "yes") {
                    layer.msg("修改成功", {icon: 1});
                    //修改导航栏头像
                    if ($("#avatarUrl").val() !== '') {
                        $("#avatar", window.parent.frames["topFrame"].document).attr("src", $("#avatarUrl").val())
                    }
                } else {
                    layer.msg("修改失败", {icon: 2});
                }
            })
    }

    $(function () {
        $("#customFile").change(function () {
            var formData = new FormData($('#uploadForm')[0]);
            var localhostPath = window.document.location.href.substring(0, window.document.location.href.indexOf(window.document.location.pathname));
            $.ajax({
                type: 'post',
                url: localhostPath + "${pageContext.request.contextPath}/user/uploadAvatar?token=${token}", //上传文件的请求路径必须是绝对路径
                data: formData,
                cache: false,
                processData: false,
                contentType: false,
                success: function (data) {
                    var a = JSON.parse(data)
                    $("#uploadForm").next().html(a.result);
                    var avatarUrl = localhostPath + "${pageContext.request.contextPath}/avatar/" + a.name
                    $("#userAvatar").attr("src", avatarUrl);
                    $("#avatarUrl").val(avatarUrl);
                },
                error: function () {
                    $("#uploadForm").next().html("上传失败");
                }
            });
        });
    })

    //将video组件中的画面绘制到canvas中
    $("#faceUpload").click(function () {
        //获取canvas对象
        var canvas = document.getElementById("canvas");
        var c = canvas.getContext("2d");
        //canvas获取图片
        c.drawImage(video, 0, 0, 300, 300)
        //从画布对象中获取图片信息
        var imgSrc = canvas.toDataURL();
        //对字符串拆分，获取图片内容
        var img = imgSrc.split("base64,")[1];
        //发送ajax请求完成人脸注册
        $.ajax({
            url: "${pageContext.request.contextPath}/user/faceRegister",
            type: "post",
            data: {"img": img},
            success: function (resp) {
                var json = JSON.parse(resp);
                layer.msg(json.msg)
            }
        })
    })
</script>
<script>
    $(function () {
        $("#editProfileModal").on("show.bs.modal", function () {
            //获取视频组件
            video = document.getElementById("video")
            //通过浏览器打打开摄像头
            window.stream = navigator.mediaDevices.getUserMedia({
                audio: false,
                video: true,
                video: {//设置分辨率
                    width: 500,
                    height: 500
                }
            }).then(function (mediaStream) {//mediaStream:流
                video.srcObject = mediaStream;
                video.autoloadmatadata = function () {
                    video.play();
                }
            })
        })
        $("#editProfileModal").on("hide.bs.modal", function () {
            video.pause();
            video.srcObject = null;
        })
    })

    function help() {
        $("#help").modal("show");

    }

    function f() {
        var canvas = document.getElementById('canvas'),  //获取canvas元素
            context = canvas.getContext('2d'),  //获取画图环境，指明为2d
            centerX = canvas.width / 2,   //Canvas中心点x轴坐标
            centerY = canvas.height / 2,  //Canvas中心点y轴坐标
            rad = Math.PI * 2 / 100, //将360度分成100份，那么每一份就是rad度
            speed = 0.1; //起始位置?
        //绘制外圈
        function blueCircle(n) {
            context.save();
            context.strokeStyle = "#2fbf47"; //设置描边样式
            context.lineWidth = 5; //设置线宽
            context.beginPath(); //路径开始
            context.arc(centerX, centerY, 145, -Math.PI / 2, -Math.PI / 2 + n * rad, false); //用于绘制圆弧context.arc(x坐标，y坐标，半径，起始角度，终止角度，顺时针/逆时针)
            context.stroke(); //绘制
            context.closePath(); //路径结束
            context.restore();
        }

        //绘制对号
        function check() {
            context.save();
            context.beginPath();
            context.strokeStyle = "white";
            var image = document.getElementById("check");
            context.drawImage(image, centerX - 40, centerY - 40, 80, 80)
            context.stroke();
            context.closePath();
            context.restore();
        }

        //循环获取
        (function drawFrame() {
            window.requestAnimationFrame(drawFrame, canvas);
            context.clearRect(0, 0, canvas.width, canvas.height);
            blueCircle(speed);
            if (speed < 100) {
                //0.1可从后台获取值
                speed += 2;
            } else
                check();
        }())
    }
</script>
<script>
    //将离线好友放到最下面
    $(function () {
        $(".avatar-state-warning").each(function () {
            if ($(this).parents("li").next() && $(this).parents("li").find(".new-message-count").html() === "") {
                $(this).parents("ul").children(":last").after($(this).parents("li"));
            }
        })
    })
</script>
</body>
</html>

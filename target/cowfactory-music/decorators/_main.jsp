<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html xmlns:wb=“http://open.weibo.com/wb”>
<head>

    <link rel="stylesheet" type="text/css" href="/css/normalize.css"/>
    <link rel="stylesheet" type="text/css" href="/css/component.css"/>
    <link rel="stylesheet" type="text/css" href="/css/list.css"/>
    <link rel="stylesheet" href="/css/medium-editor.css">
    <link rel="stylesheet" href="/css/medium.css">
    <link rel="stylesheet" href="/css/medium-editor-insert.css">
    <link rel='stylesheet' href='/css/main.css' type='text/css'/>
    <script type='text/javascript' src="/js/jquery.min.js"></script>
</head>
<c:set var="currentUser" value="${SPRING_SECURITY_CONTEXT.authentication.principal}"/>
<body class="cbp-spmenu-push">
<input type="hidden" id="choosedtype"/>

<nav class="cbp-spmenu cbp-spmenu-vertical cbp-spmenu-left" id="cbp-spmenu-s1">
    <c:if test="${not empty currentUser}">
        <a href="javascript:void(0);" style="display: inline-block" title="${currentUser.weiboDisplayName}"><img
                src="${currentUser.weiboImage}"
                class="post-item-avatar-user" title="${currentUser.weiboDisplayName}"><a href="/logout" style="display: inline-block;cursor: pointer;"><img src="/img/icon/logout.png"></a></a>
    </c:if>
    <a href="/"><img src="/img/icon/house.png">主页</a>
    <c:if test="${not empty currentUser}">
    <a href="/register/main"><img src="/img/icon/user.png">我的</a>
    </c:if>
    <c:if test="${not empty currentUser}">
    <a href="/intercept?p=3"><img src="/img/icon/feather.png">我要发布</a>
    </c:if>
    <c:if test="${empty currentUser}">
        <a href="/intercept?p=0"><img src="/img/icon/feather.png">我要发布</a>
    </c:if>
    <c:if test="${empty currentUser}">
        <a href="/loginastemp"><img src="/img/icon/user.png">微博登录</a>
    </c:if>

    <a href="javascript:void(0);" id="cbp-close"><img src="/img/icon/cross.png">关闭菜单</a>
    <c:if test="${not empty currentUser && currentUser.adminRole}">
        <a href="/admin/main">审核</a>
    </c:if>
</nav>
<button class="site-nav-logo distraction-free-component" id="showLeftPush"><span class="icons icons-logo-m"><span
        class="screen-reader-text">听</span></span>
</button>


<decorator:body/>

</body>

<script type='text/javascript' src="/js/modernizr.custom.js"></script>
<script src="/js/masonry.js" type="text/javascript"></script>
<script type='text/javascript' src="/js/classie.js"></script>
<script type='text/javascript' src="/js/musicplayer/audio.min.js"></script>
<script type='text/javascript' src="/js/main.js"></script>






</html>
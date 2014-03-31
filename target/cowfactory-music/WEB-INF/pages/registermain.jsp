<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:set var="currentUser" value="${SPRING_SECURITY_CONTEXT.authentication.principal}"/>
<script type="text/javascript" src="/js/mymusic.js"></script>
<a id="commentmenubtn" style="display: none"/>
<div class="background-white layout-foreground">
    <header class="hero hero-profile layout-single-column">
        <div class="hero-avatar"><span>
            <img src="${currentUser.weiboImage}"
                 class="hero-avatar-image" title="chen zhang" data-action="zoom"
                 data-action-value="0*5N_SHmEIAwJMzc3Z.png" data-width="500" data-height="500"></span></div>
        <h1 class="hero-title"><a>${currentUser.weiboDisplayName}</a></h1>
    </header>
    <nav class="nav-tabs   layout-single-column">
        <ul class="nav-tabs-list">
            <li class="nav-tabs-item ${status eq 'PUBLISH'?'active':''}"><a class="nav-tabs-anchor"
                                                                            href="/register/main?type=PUBLISH">已发布</a>
            </li>
            <li class="nav-tabs-item ${status eq 'DRAFT'?'active':''}"><a class="nav-tabs-anchor"
                                                                          href="/register/main?type=DRAFT">草稿</a></li>
            <li class="nav-tabs-item ${status eq 'VERIFY'?'active':''}"><a class="nav-tabs-anchor"
                                                                           href="/register/main?type=VERIFY">审核中</a>
            </li>
        </ul>
    </nav>
    <div class="bucket layout-single-column ">

        <c:if test="${empty musics}">
            <div class="bucket-empty  ">
                <p class="bucket-empty-message">您还没有成功发布任何歌曲</p>

                <p class="bucket-empty-action"><a href="/intercept?p=3" title="Write a new story"><b>现在发布</b></a></p>
            </div>
        </c:if>
        <c:if test="${not empty musics}">
            <ul class="bucket-posts">
                <c:forEach items="${musics}" var="music">
                    <li class="bucket-item music_item_${music.guid}">
                        <div data-post-id="e01fb7972df4" class="post-item post-status-"><a
                                title="Go to the profile of andre passamani"></a>

                            <h3 class="post-item-title">
                                <a href="/register/createoredit?guid=${music.guid}"
                                   title="Mudou tudo e ainda não  parou de mudar by andre passamani"
                                   data-action="open-post"
                                   data-action-value="/p/e01fb7972df4">
                                        ${music.title}
                                </a>
                            </h3>
                            <a class="post-item-snippet" href="/register/createoredit?guid=${music.guid}"
                               data-action="open-post"
                               data-action-value="/p/e01fb7972df4">
                                <p>
                                        ${music.subTitle}
                                </p>
                            </a>

                            <div class="btn-set">
                                <div class="btn-group">
                                    <a class="icon icon-primary active" href="javascript:void(0);"><span
                                            class="fui-heart"></span>${music.liked}</a>
                                    <a class="icon icon-primary" onclick="showComments('${music.guid}',0);" href="javascript:void(0);"><span
                                            class="fui-chat"></span><span class="comment-icon">${music.comments}</span></a>
                                </div>


                                <div class="vote-widget">
                                    <ul class="upvoters fade">
                                        <c:forEach items="${music.likedBy}" var="likedUser">
                                            <li class="upvoters-item"><a href="/profile?guid=${likedUser.guid}"
                                                                         class="avatar-chromeless avatar-icon">
                                                <img src="${likedUser.weiboImage}"></a></li>
                                        </c:forEach>
                                    </ul>
                                </div>
                            </div>
                        </div>


                    </li>

                </c:forEach>
            </ul>
        </c:if>

    </div>

</div>
<nav class="cbp-spmenu cbp-spmenu-vertical cbp-spmenu-right comment-spmenu" id="cbp-spmenu-s2">
    <a href="javascript:void(0);" id="cbp-comment-close" onclick="closeCommentsSnapper();"><img
            src="/img/icon/cross.png">关闭评论</a>

    <div class="notes-list notes-hide-editor" style="top: 562px;">

        <div class="notes-items">
        </div>


    </div>

</nav>
<%--<ul class="playlist">--%>
<%--<audio preload></audio>--%>
<%--</ul>--%>
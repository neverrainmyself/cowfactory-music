<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:set var="currentUser" value="${SPRING_SECURITY_CONTEXT.authentication.principal}"/>
<div class="bucket layout-single-column default-music-list">
    <ul class="bucket-posts" data-action-scope="_actionscope_3">
        <c:forEach items="${musics}" var="music">
            <li class="bucket-item">
                <div data-post-id="e01fb7972df4" class="post-item post-status-"><a
                        title="Go to the profile of andre passamani"></a>
                    <c:if test="${not empty currentUser && currentUser.guid eq music.userCommand.guid}">
                        <a href="/register/main"><img src="${music.userCommand.image}" class="post-item-avatar"
                                                      title="andre passamani"></a>
                    </c:if>
                    <c:if test="${empty currentUser || currentUser.guid ne music.userCommand.guid}">
                        <a href="/profile?guid=${music.userCommand.guid}"><img src="${music.userCommand.image}"
                                                                               class="post-item-avatar"
                                                                               title="andre passamani"></a>
                    </c:if>

                    <h3 class="post-item-title"><a href="/detail?guid=${music.guid}"
                                                   title="Mudou tudo e ainda não  parou de mudar by andre passamani">${music.title}</a>
                    </h3><a href="/detail?guid=${music.guid}" class="post-item-snippet">
                        <p>${music.subTitle}</p></a>
                    <ul class="post-item-meta">
                        <li class="post-item-meta-item">
                            <c:if test="${not empty currentUser && currentUser.guid eq music.userCommand.guid}">
                                <a href="/register/main"
                                   class="post-item-author link link-secondary">${music.userCommand.displayName}</a>
                            </c:if>
                            <c:if test="${empty currentUser || currentUser.guid ne music.userCommand.guid}">
                                <a href="/profile?guid=${music.userCommand.guid}"
                                   class="post-item-author link link-secondary">${music.userCommand.displayName}</a>
                            </c:if>
                        </li>
                        <%--<li class="post-item-meta-item"><span class="reading-time">3 min read</span></li>--%>
                    </ul>
                    <div class="btn-group">
                        <a class="icon icon-primary single-music" id="single-music-${music.guid}" src="${music.guid}"
                           name="${music.title}" href="javascript:void(0);"
                           onclick="playOneFromList('${music.guid}',$(this))"><span class="fui-play"></span></a>
                        <a class="icon icon-primary stop-music" href="javascript:void(0);" onclick="stopPlay(this)"><span
                                class="fui-pause"></span></a>
                            <c:if test="${empty currentUser}">
                                <a class="icon icon-primary" href="/intercept?p=0"><span class="fui-heart"></span></a>
                            </c:if>
                            <c:if test="${not empty currentUser}">
                                <a class="icon icon-primary
                                   <c:forEach items="${currentUser.liked}" var="likedMusic">
                                            <c:if test="${music.guid eq likedMusic}">active</c:if>
                                   </c:forEach>
                                " href="javascript:void(0);" onclick="liked('${music.guid}',this);"><span class="fui-heart"></span></a>
                            </c:if>
                    </div>
                </div>
            </li>
        </c:forEach>

    </ul>
</div>
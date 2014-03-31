<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script src="/js/admin.js" type="text/javascript"></script>


<div class="background-white layout-foreground">
    <nav class="nav-tabs   layout-single-column">
        <ul class="nav-tabs-list">
            <li class="nav-tabs-item ${status eq 'VERIFY'?'active':''}"><a class="nav-tabs-anchor" href="/admin/main?type=VERIFY">审核中</a></li>
            <li class="nav-tabs-item ${status eq 'PUBLISH'?'active':''}"><a class="nav-tabs-anchor" href="/admin/main?type=PUBLISH">已发布</a></li>
            <li class="nav-tabs-item ${status eq 'DRAFT'?'active':''}"><a class="nav-tabs-anchor" href="/admin/main?type=DRAFT">草稿</a></li>
        </ul>
    </nav>
    <div class="bucket layout-single-column default-music-list">
        <ul class="bucket-posts" data-action-scope="_actionscope_3">
            <c:forEach items="${musics}" var="music">
                <li class="bucket-item">
                    <div data-post-id="e01fb7972df4" class="post-item post-status-"><a
                            title="Go to the profile of andre passamani"></a>

                        <a href="/profile?guid=${music.userCommand.guid}"><img src="${music.userCommand.image}"
                                                                               class="post-item-avatar"
                                                                               title="andre passamani"></a>

                        <h3 class="post-item-title"><a href="/detail?guid=${music.guid}"
                                                       title="Mudou tudo e ainda não  parou de mudar by andre passamani">${music.title}</a>
                        </h3><a href="/detail?guid=${music.guid}" class="post-item-snippet">
                            <p>${music.subTitle}</p></a>
                        <ul class="post-item-meta">
                            <li class="post-item-meta-item">
                                <a href="/profile?guid=${music.userCommand.guid}"
                                   class="post-item-author link link-secondary">${music.userCommand.displayName}</a>
                            </li>
                            <li class="post-item-meta-item"><span class="reading-time">${music.musicType}</span></li>
                        </ul>
                        <div class="btn-group">
                            <a class="icon icon-primary single-music" id="single-music-${music.guid}" src="${music.guid}"
                               name="${music.title}" href="javascript:void(0);"
                               onclick="playOneFromList('${music.guid}',$(this))"><span class="fui-play"></span></a>
                            <a class="icon icon-primary" href="javascript:void(0);" onclick="stopPlay(this)"><span
                                    class="fui-pause"></span></a>

                            <c:if test="${status eq 'VERIFY'}">
                            <a class="icon icon-primary" href="javascript:void(0);" onclick="verify('${music.guid}',this)"><span class="fui-check"></span></a>
                            <a class="icon icon-primary" href="javascript:void(0);" onclick="reject('${music.guid}',this)"><span class="fui-cross"></span></a>
                            </c:if>

                        </div>
                    </div>
                </li>
            </c:forEach>

        </ul>
    </div>

</div>
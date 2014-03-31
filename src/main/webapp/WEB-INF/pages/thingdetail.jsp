<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:set var="currentUser" value="${SPRING_SECURITY_CONTEXT.authentication.principal}"/>
<html xmlns:wb=“http://open.weibo.com/wb”>
<link rel='stylesheet' href='/css/detail.css' type='text/css'/>
<script src="http://tjs.sjs.sinajs.cn/open/api/js/wb.js" type="text/javascript" charset="utf-8"></script>

<input type="hidden" id="guid" value="${music.guid}"/>
<input type="hidden" id="title" value="${music.title}"/>
<a id="commentmenubtn" style="display: none"/>
<c:if test="${not empty music.headerImage}"><input type="hidden" id="headerimage" value="${music.headerImage}"/></c:if>
<article class='detailpage hidden'>
    <div class='big-image'>
        <div class='inner'>
            <div class='fader'>
                <div class='text'>
                    <a class='goto-next'>下一首</a>

                    <h1 class='title'>${music.title}</h1>

                    <h2 class='description'>${music.subTitle}</h2>

                </div>
            </div>
        </div>

    </div>
    <div class='content'>
        <div class="btn-group">
            <a class="icon icon-primary single-music" id="single-music-${music.guid}" src="${music.guid}"
               name="${music.title}" href="javascript:void(0);"
               onclick="playOneFromList('${music.guid}',$(this))"><span class="fui-play"></span></a>
            <a class="icon icon-primary" href="javascript:void(0);" onclick="stopPlay(this)"><span
                    class="fui-pause"></span></a>
        </div>

        <h1 class='title' id="title-head">${music.title}</h1>

        <h2 class='description' id="subtitle-head">${music.subTitle}</h2>

        <div class='text editable' data-placeholder="描述,可添加文字,图片...还有更多">${music.content}</div>


        <div class="post-footer-actions post-supplemental layout-single-column">
            <div class="btn-set">
                <div class="btn-group">

                    <c:if test="${empty currentUser}">
                        <a class="icon icon-primary" href="/intercept?p=0"><span class="fui-heart">${music.liked}</span></a>
                    </c:if>
                    <c:if test="${not empty currentUser}">

                        <a class="icon icon-share" href="javascript:void(0);" onclick="window.open('http://service.weibo.com/share/share.php?url=http://dowhatcowdo.com/detail?guid=${music.guid}&appkey=J7ySv&title=进入Cow Factory,听听@${music.user.weiboName}在<<${music.title}>>这首歌背后的故事&pic=dowhatcowdo.com/loadimage?guid=${music.headerImage}&ralateUid=3487680995&language=zh_cn')"
                                ><span class="fui-">分享</span></a>
                        <a class="icon icon-primary liked
                <c:forEach items="${currentUser.liked}" var="likedMusic">
                                            <c:if test="${music.guid eq likedMusic}">active</c:if>
                                   </c:forEach>
                " href="javascript:void(0);" onclick="liked('${music.guid}',this);"><span
                                class="fui-heart likedamount">${music.liked}</span></a>
                    </c:if>
                    <a class="icon icon-primary showallcommentbtn" onclick="showComments('${music.guid}',0);"><span
                            class="fui-chat commentamount">${music.comments}</span></a>
                </div>
                <div class="vote-widget">
                    <ul class="upvoters fade">
                        <c:forEach items="${music.likedBy}" var="user">
                            <li class="upvoters-item likeduser${user.guid}"><a href="/profile?guid=${user.guid}"
                                                                               class="avatar-chromeless avatar-icon">
                                <img src="${user.weiboImage}"></a></li>
                        </c:forEach>
                    </ul>
                </div>
            </div>
            <div class="post-footer-cards">
                <div class="layout-multi-column-half"><h4 class="cards-heading">发布者</h4>
                    <ul class="cards">
                        <li class="card card-user">
                            <div class="card-image"><a href="/profile?guid=${music.user.guid}" title="去他／她的主页"
                                                       class="cards-image"><img
                                    src="${music.user.weiboImage}"
                                    class="card-avatar" title="Sean Johnson"></a></div>
                            <div class="card-content">
                                <h3 class="card-name"><a href="/profile?guid=${music.user.guid}"
                                                         class="link link-primary">${music.user.weiboDisplayName}</a>
                                </h3>

                                <div class="card-additional">
                                    <div class="card-description-additional card-published"><span>发布于</span> <span><time
                                            class="post-date">March 7, 2014
                                    </time></span></div>
                                </div>
                            </div>
                        </li>
                    </ul>
                </div>
            </div>
        </div>


    </div>
</article>


<nav class="cbp-spmenu cbp-spmenu-vertical cbp-spmenu-right comment-spmenu" id="cbp-spmenu-s2">
    <a href="javascript:void(0);" id="cbp-comment-close" onclick="closeCommentsSnapper();"><img
            src="/img/icon/cross.png">关闭评论</a>

    <div class="notes-list notes-hide-editor" style="top: 562px;">
        <div class="notes-new-note" tabindex="-1">
            <c:if test="${empty currentUser}">
                <button class="btn btn-chromeless notes-add" data-action="start-note"
                        onclick="window.location.href='/intercept?p=0'"><span
                        class="notes-add-icon icons icons-add-circled"></span><span class="notes-add-text">添加评论</span>
                </button>
            </c:if>
            <c:if test="${not empty currentUser}">
                <button class="btn btn-chromeless notes-add" data-action="start-note" onclick="showCommentForm()"><span
                        class="notes-add-icon icons icons-add-circled"></span><span class="notes-add-text">添加评论</span>
                </button>
                <div class="notes-edit notes-edit-mode"><span>
                <img
                        src="${currentUser.weiboImage}"
                        class="notes-avatar notes-author-icon"></span>

                    <div class="notes-author">${currentUser.weiboDisplayName}</div>
                    <div class="notes-note-editor notes-editor editable default-value"><p>输入评论</p></div>
                    <div class="notes-foot no-user-select">
                        <button class="btn btn-chromeless btn-primary notes-no-separator notes-edit-action submitcommentbtn"
                                onclick="saveComment('${music.guid}')">保存
                        </button>
                        <button class="btn btn-chromeless notes-edit-action" onclick="cancelAddCommand();">取消</button>
                    </div>
                    <div class="notes-disclaimer">您的留言只有作者本人可以看见
                    </div>
                </div>
            </c:if>

        </div>
        <div class="notes-items">
        </div>


    </div>

</nav>

<ul class="playlist">
    <audio></audio>
</ul>
<script type="text/javascript" src='/js/detail.js'></script>
</html>

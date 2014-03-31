<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--<link rel="stylesheet" href="/css/medium-editor.css">--%>
<%--<link rel="stylesheet" href="/css/medium-editor-insert.css">--%>
<%--<link rel="stylesheet" href="/css/medium.css">--%>
<link rel="stylesheet" href="/css/jquery.plupload.queue.css">
<input type="hidden" id="guid" value="${music.guid}"/>
<input type="hidden" id="headerimageguid" value="${music.headerImage}"/>

<div class="btn-set fix-position">
    <a class="btn btn-light save-publish-btn" href="javascript:void(0);">发布</a>
    <a class="btn btn-light save-draft-btn" href="javascript:void(0);">保存草稿</a>
    <a class="btn btn-light delete-publish-btn" href="javascript:void(0);">删除</a>
    <a class="btn btn-light reupload-publish-btn" style="display:${not empty music.path?'':'none;'}"
       href="javascript:void(0);" onclick="deleteMusicFile();">更换音乐</a>
    <a class="btn btn-light reupload-headerimage" onclick="$('#headerimageuploadbtn').click();" style="display:${not empty music.headerImage?'':'none;'}" href="javascript:void(0);">更换图片</a>
</div>
<div class="big-header-image"></div>
<div id="container">
    <img id="headerimageupload"  src="/img/icon/upload.png">
    <div class="btn-group music-play-btns" style="display:${not empty music.path?'':'none;'}">
        <a class="icon icon-primary single-music" id="single-music-${music.guid}" src="${music.guid}"
           name="${music.title}" href="javascript:void(0);"
           onclick="playOneFromList('${music.guid}',$(this))"><span class="fui-play"></span></a>
        <a class="icon icon-primary" href="javascript:void(0);" onclick="stopPlay(this)"><span
                class="fui-pause"></span></a>
    </div>
    <input type="file"  style="display: none" id="headerimageuploadbtn"/>
    <header id="product-header">
        <c:if test="${not empty music.title}"><p>${music.title}</p></c:if>
    </header>
    <header id="product-sub-header"><c:if test="${not empty music.subTitle}"><p>${music.subTitle}</p></c:if></header>
    <div class="editable" id="product-content" data-placeholder="描述,可添加文字,图片...还有更多">
        <c:if test="${not empty music.content}">${music.content}</c:if>
        <c:if test="${empty music.content}"><p><br></p></c:if>
    </div>
</div>

<div class="plupload music pl-sidebar" style="display:${empty music.path?'':'none;'}"></div>

<ul class="playlist">
   <audio></audio>
</ul>

<script type='text/javascript' src="/js/productform/medium.js"></script>
<script type='text/javascript' src="/js/productform/medium-editor.min.js"></script>
<script type='text/javascript' src="/js/productform/medium-editor-insert-plugin.js"></script>
<script type='text/javascript' src="/js/productform/medium-editor-insert-images.js"></script>
<script src="/js/fileupload/plupload.full.js"></script>
<script src="/js/fileupload/jquery.plupload.queue.js"></script>
<script type='text/javascript' src="/js/productform/blogform.js"></script>


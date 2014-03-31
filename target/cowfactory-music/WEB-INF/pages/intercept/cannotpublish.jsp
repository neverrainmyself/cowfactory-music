<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="/css/intercept.css">
</head>
<body>
<div class="overlay">
    <div class="overlay-dialog overlay-dialog-confirm" tabindex="-1"><h3 class="overlay-title">无法进入发布审核</h3>

        <div class="overlay-content">您当前音乐有信息不完整，请确保您上传了音乐文件，并添加了标题与副标题</div>
        <div class="overlay-actions">
            <a class="btn btn-primary btn-overlay-confirm" href="/register/main">前往我的首页</a>
            <a class="btn btn-primary btn-overlay-confirm" href="/register/createoredit?guid=${attrs[0]}">继续修改</a>
        </div>
    </div>
</div>


</body>
</html>
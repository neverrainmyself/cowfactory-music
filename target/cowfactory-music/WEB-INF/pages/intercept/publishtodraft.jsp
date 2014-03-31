<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="/css/intercept.css">
</head>
<body>
<div class="overlay">
    <div class="overlay-dialog overlay-dialog-confirm" tabindex="-1"><h3 class="overlay-title">注意</h3>

        <div class="overlay-content">当前音乐正在审核或已发布成功，如果修改，需要重新审核</div>
        <div class="overlay-actions">
            <a class="btn btn-primary btn-overlay-confirm" href="/register/createoredit?forceedit=true&guid=${attrs[0]}">继续修改</a>
            <a class="btn" href="/register/main">取消修改</a>
        </div>
    </div>
</div>
</body>

</html>
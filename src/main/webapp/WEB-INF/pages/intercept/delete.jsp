<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="/css/intercept.css">
</head>
<body>
<div class="overlay">
    <div class="overlay-dialog overlay-dialog-confirm" tabindex="-1"><h3 class="overlay-title">删除</h3>

        <div class="overlay-content">删除后无法找回</div>
        <div class="overlay-actions">
            <a class="btn btn-primary btn-overlay-confirm" href="/register/deleteproduct?guid=${attrs[0]}">确定</a>
            <a class="btn" href="/register/createoredit?guid=${attrs[0]}">取消</a>
        </div>
    </div>
</div>


</body>
</html>
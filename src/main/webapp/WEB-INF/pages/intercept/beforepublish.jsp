<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="/css/intercept.css">
</head>
<body>
<div class="overlay">
    <div class="overlay-dialog overlay-dialog-confirm" tabindex="-1"><h3 class="overlay-title">音乐类别</h3>

        <div class="overlay-content">请在发布前选择音乐类别</div>
        <div class="overlay-actions">
            <a class="btn btn-primary" href="/register/createoredit?musictype=RAP">Rap(说唱)</a>
            <a class="btn btn-primary btn-overlay-confirm" href="/register/createoredit?musictype=BEAT">Beat(节奏)</a>
        </div>
    </div>
</div>
</body>

</html>
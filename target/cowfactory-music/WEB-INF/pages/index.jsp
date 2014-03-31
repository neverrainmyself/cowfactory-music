<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="container">
    <div id="splitlayout" class="splitlayout">
        <div class="intro">
            <div class="side side-left">
                <div class="intro-content">
                    <%--<div class="profile"><img src="/img/icon/rap-icon.png" alt="profile1"></div>--%>
                    <h1>
                        <span>嘻哈</span></h1>
                </div>
                <div class="overlay"></div>
            </div>
            <div class="side side-right">
                <div class="intro-content">
                    <%--<div class="profile"><img src="/img/icon/logo.png" alt="profile2"></div>--%>
                    <h1>
                        <span>节奏</span></h1>
                </div>
                <div class="overlay">

                </div>


            </div>
        </div>
        <!-- /intro -->
        <div class="page page-right">
            <div class="page-inner">
                <nav class="nav-tabs nav-tabs-light  layout-single-column">
                    <ul class="nav-tabs-list">
                        <li class="nav-tabs-item defaultlist active"><a class="nav-tabs-anchor" href="javascript:void(0);" onclick="welcome('BEAT')">收听列表</a></li>
                        <li class="nav-tabs-item top100list">
                        <a class="nav-tabs-anchor" href="javascript:void(0);" onclick="top100('BEAT');">Top 100</a>
                        </li>
                    </ul>
                </nav>

            </div>
        </div>
        <div class="page page-left">
            <div class="page-inner">
                <nav class="nav-tabs nav-tabs-light  layout-single-column">
                    <ul class="nav-tabs-list">
                        <li class="nav-tabs-item top100list">
                            <a class="nav-tabs-anchor" href="javascript:void(0);" onclick="top100('RAP');">Top 100</a>
                        </li>
                        <li class="nav-tabs-item defaultlist active"><a class="nav-tabs-anchor" href="javascript:void(0);" onclick="welcome('RAP')">收听列表</a></li>
                    </ul>
                </nav>

            </div>
        </div>
        <a href="#" class="back back-right" title="back to intro">返回</a>
        <a href="#" class="back back-left" title="back to intro">返回</a>
    </div>
</div>
<ul class="playlist">
    <audio preload></audio>
</ul>
<script type='text/javascript' src="/js/index.js"></script>
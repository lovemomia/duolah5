<!doctype html>
<html>
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no, minimal-ui" />
    <title>我的收藏</title>
    <link rel="stylesheet" type="text/css" href="CSS/main.css" />
    <script type="text/javascript" src="JS/zepto.min.js"></script>
    <script type="text/javascript" src="JS/config.js"></script>
    <script type="text/javascript" src="JS/common.js"></script>
    <script type="text/javascript">document.getElementsByTagName("html")[0].style.fontSize=document.documentElement.clientWidth/3+"px";</script>
</head>
<body>
<article id="page">
    <header class="head">
        <span class="back"><img src="image/back2.png"></span>我的收藏<span class="collect"></span>
    </header>

    <section>

    </section>
</article>

<script type="text/javascript">
    $(function(){
        tq.t.back();
        tq.home.getCollect();
    })
</script>
</body>
</html>
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
         <div class="collect_pad">
            <img src="image/photo.jpg" alt="">
            <div class="collect_main">
                <div class="collect_info">
                    <p class="title">哆啦A梦密室逃脱</p>
                    <p class="schedule">6月18号到8月18号</p>
                    <p class="ad_pr" style="margin-bottom:0">
                        <span class="address">中山公园</span>
                        <span class="price">150<i>起</i></span>
                    </p>
                </div>
            </div>
        </div>
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
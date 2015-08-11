<!doctype html>
<html>
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no, minimal-ui" />
    <title>我的</title>
    <link rel="stylesheet" type="text/css" href="CSS/main.css" />
    <script type="text/javascript" src="JS/zepto.min.js"></script>
    <script type="text/javascript" src="JS/config.js"></script>
    <script type="text/javascript" src="JS/common.js"></script>
    <script type="text/javascript">document.getElementsByTagName("html")[0].style.fontSize=document.documentElement.clientWidth/3+"px";</script>
</head>
<body>
<article id="page">
    <header class="head">
        <span class="back"><img src="image/back2.png"></span>我的订单<span class="collect"></span>
    </header>

    <section>
        <div class="user_order">
            <!-- <div class="order_box">
                <div class="ohd">
                    <i class="left">待付款</i>
                    <i class="total_fee">￥120</i>
                </div>
                <div class="odt">
                    <h2 class="otitle">丹麦童话，夏天就该这么玩</h2>
                    <p class="total_num">
                        <i class="fn">活动人数:</i>
                        <i>1儿童</i>
                    </p>
                    <p class="total_num">
                        <i class="fn">活动人数:</i>
                        <i>1儿童</i>
                    </p>
                </div>
                <div class="ofd green">
                    <a href="" class="rbtn del green">删除订单</a>
                    <a href="" class="rbtn green">继续支付</a>
                    <div style="clear:both"></div>
                </div>
            </div> -->
        </div>
    </section>
</article>

<script type="text/javascript">
    $(function(){
        tq.t.back();
        tq.home.user_order();
    })
</script>
</body>
</html>
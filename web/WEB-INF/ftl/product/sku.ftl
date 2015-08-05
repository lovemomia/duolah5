<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width,target-densitydpi=device-dpi,user-scalable=no">
    <title>提交订单</title>
    <link rel="stylesheet" type="text/css" href="CSS/main.css">
    <script type="text/javascript" src="JS/zepto.min.js"></script>
    <script type="text/javascript" src="JS/config.js"></script>
    <script type="text/javascript" src="JS/common.js"></script>
    <script src='JS/hhSwipe.js'></script>
    <script type="text/javascript">
        document.getElementsByTagName("html")[0].style.fontSize=document.documentElement.clientWidth/3+"px";
    </script>
</head>
<body>
<article id="page">

    <header class="head">
        <span class="back"><img src="image/back2.png"></span>提交订单<span class="collect"></span>
    </header>

    <section>
        <div class="order_list">
            <h3>选择场次</h3>
            <div class="order_detail time" id="chk_time">
            </div>
        </div>

        <div class="order_list chk_fee">
            <h3>选择出行人数</h3>
        </div>


        <div class="show_detail">
            <div class="chk_info" id="chk_name">
                <a class="chk_name l" href="">出行人<i></i></a>
            </div>

            <div class="chk_info">
                <a class="chk_phone l" href="outer_info.html">联系人信息<i></i></a>
                <div style="clear:both"></div>
            </div>

        </div>

        <div class="chk_sub">
            <span class="total_price">总价：<span style="color:#f67531">￥</span><i class="orange">0</i></span>
            <div style="clear:both"></div>
        </div>

        <div class="login">
            <button id="btn_submit" class="tapable" style="background:#f67531">确认订单</button>
        </div>

    </section>

</article>

<script>
    $(function(){
        tq.t.back();
        var id = tq.t.getQueryString("id");
        $(".back").on("click",function(){
            location.href ="actsDetail.html?id="+id+"";
        })
        tq.home.getSKU();
    })
</script>
</body>
</html>
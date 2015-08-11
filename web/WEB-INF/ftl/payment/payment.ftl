<!doctype html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no, minimal-ui" />
    <title>收银台</title>
    <link rel="stylesheet" type="text/css" href="CSS/main.css" />
    <script type="text/javascript" src="JS/zepto.min.js"></script>
    <script type="text/javascript" src="JS/config.js"></script>
    <script type="text/javascript" src="JS/common.js"></script>
    <script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
    <script type="text/javascript">document.getElementsByTagName("html")[0].style.fontSize=document.documentElement.clientWidth/3+"px";

    </script>
</head>
<body>
<article id="page">
    <header class="head">收银台
    </header>
    <section class="order top0">
        <div class="order_title">
            <h2 class="title"></h2>
        </div>
        <div class="form bot totalfee">

        </div>
        <div class="pad1 bot">支付方式</div>
        <div class="form bot weixin" style="border-bottom:1px solid #eee">

            <div class="fitem_input pay_way ha">
                <img src="image/pay_wx.png" class="left">
                <div class="left">
                    <p class="pay_b">微信支付</p>
                    <p class="pay_detail">微信钱包，银行卡支付</p>
                </div>

                <div class="right pay-chk pay_checked"></div>
            </div>
        </div>

        <div class="form bot ali_pay" style="border-bottom:1px solid #eee">

            <div class="fitem_input pay_way ha">
                <img src="image/pay_ali.png" class="left">
                <div class="left">
                    <p class="pay_b">支付宝</p>
                    <p class="pay_detail">支付宝账户支付，银行卡支付</p>
                </div>

                <div class="right pay-chk pay_checked"></div>
            </div>
        </div>
        <!-- <div class="btn_red" style="padding:0.08rem;">确认支付</div> -->
        <div class="login">
            <button id="btn_submit" class="tapable" style="background:#f67531;">确认支付</button>
        </div>
    </section>
</article>
<script>
    $(function(){
        tq.home.wxPay();
        if(tq.t.isweixin()){
            $(".weixin").removeClass("none");
            $(".ali_pay").addClass("none");
        }else{
            $(".ali_pay").removeClass("none");
            $(".weixin").addClass("none");
        }
    })
</script>
</body>
</html>
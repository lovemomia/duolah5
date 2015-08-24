<@override name="title">我的订单</@override>

<@override name="body">
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
    function more() {
        if (sessionStorage.getItem("orderNextIndex") != null) tq.home.user_order(sessionStorage.getItem("orderNextIndex"));
    }

    $(function(){
        tq.t.back();
        tq.home.user_order(0);
    })
</script>
</@override>

<@extends name="../base.ftl"/>

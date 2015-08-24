<@override name="title">付款成功</@override>

<@override name="body">
<article id="page">

    <header class="head">
        <span class="back"><img src="image/back2.png"></span>购买成功<span class="collect"></span>
    </header>

    <section>
        <div class="po_main">
            <img src="image/logo.png" alt="">
            <p class="payok">您已购买成功！</p>
            <p class="paytips">活动前一天或者活动发生变更，都会有短信通知您</p>
            <div class="bottom_btn">
                <a href="" class="back_index" style="background:#f67531">回到首页</a>
                <a href="" class="show_order">查看订单</a>
            </div>
        </div>
    </section>

</article>
<script>
    $(function(){
        $(".back").on("click", function(){
            location.href = "index.html";
        });
        $(".back_index").on("click",function(){
            event.preventDefault();
            location.href = "index.html";
        });
        $(".show_order").on("click",function(){
            event.preventDefault();
            location.href = "user_order.html?status=3&type=ge";
        });
    })
</script>
</@override>

<@extends name="../base.ftl"/>

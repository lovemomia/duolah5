<@override name="title">我的</@override>

<@override name="body">
<article id="page">
    <header class="head">
        <span class="back"><img src="image/back2.png"></span>我的<span class="collect"></span>
    </header>

    <section>
        <div class="proWrap">
            <div class="pro_info">
                <img src="" alt="" class="nick_img">
                <div class="left info">
                    <p class="nickName"></p>
                    <p class="bbInfo"></p>
                </div>
                <div style="clear:both"></div>
            </div>
        </div>


        <div class="show_detail">
            <div class="chk_info">
                <a class="chk_unpay l" href="user_order.html?status=3&type=ge"><img src="image/payed.png" alt="">已付款订单<i></i></a>
            </div>

            <div class="chk_info">
                <a class="chk_payed l" href="user_order.html?status=2&type=le"><img src="image/unpay.png" alt="">未付款订单<i></i></a>
                <div style="clear:both"></div>
            </div>

            <div class="chk_info">
                <a class="chk_outer l" href="coupon.html">
                    <img src="image/coupon2.png" alt="">我的红包<i></i></a>
                <div style="clear:both"></div>
            </div>

            <div class="chk_info">
                <a class="chk_outer l" href="com_outer.html">
                    <img src="image/outer2.png" alt="">常用出行人<i></i></a>
                <div style="clear:both"></div>
            </div>

            <div class="chk_info">
                <a class="chk_outer l" href="collect.html">
                    <img src="image/sc2.png" alt="">我的收藏<i></i></a>
                <div style="clear:both"></div>
            </div>

        </div>


    </section>
    <div class="login">
        <button id="btn_submit" class="tapable">退出登录</button>
    </div>
</article>

<script type="text/javascript">
    $(function(){
        $(".back").on("click", function(){
            location.href = "index.html";
        })
        tq.home.profile();
        $(".login").on("click", function(){
            tq.t.cookie.del("utoken");
            sessionStorage.clear();
            location.href = "index.html";
        })
    })
</script>
</@override>

<@extends name="../base.ftl"/>

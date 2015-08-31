<@override name="title">订单详情</@override>

<@override name="css" >
<style type="text/css">
    .detail_block{
        margin-top: 0.08rem;
        background: #fff;
        border-bottom: 1px solid #eee;
        border-top: 1px solid #eee;
    }

    .detail_block h3{
        font-size: 0.13rem;
        padding: 0.08rem 0.08rem 0.08rem 0;
        color: #666;
        font-weight: normal;
        border-bottom: 1px solid #eee;
        background-size: 7px auto;
        margin-left: 0.08rem;
        display: block;
    }

    .detail_block .content {
        margin-left: 0.08rem;
        padding-top: 0.06rem;
        padding-bottom: 0.06rem;
    }

    .detail_block .content p {
        font-size: 0.12rem;
        color: #999;
        padding-top: 0.02rem;
        padding-bottom: 0.02rem;
    }

    .detail_block .content .info {
        color: #666;
        margin-left: 0.08rem;
    }

    .pay{
        height: 0.4rem;
        margin-top: 0.12rem;
        text-align: center;
    }

    .pay button {
        width: 80%;
        display: inline-block;
        border: none;
        padding: 0 0.2rem;
        height: 0.4rem;
        font-size: 0.15rem;
        line-height: 0.4rem;
        color: #fff;
        background: #00c49d;
        border-radius: 4px;
    }

    .pay .left {
        width: 40%;
        float: none;
        margin-right: 0.05rem;
    }

    .pay .right {
        width: 40%;
        float: none;
        margin-left: 0.05rem;
    }
</style>
</@override>

<@override name="body">
<article id="page">
    <header class="head">
        <span class="back"><img src="/image/back2.png"></span>订单详情<span class="collect"></span>
    </header>

    <section>
        <div id="product_info" class="collect_pad">
            <img src="${order.cover}" alt="">

            <div class="collect_main">
                <div class="collect_info">
                    <p class="title">${order.title}</p>

                    <p class="schedule">${order.scheduler}</p>

                    <p class="ad_pr" style="margin-bottom:0">
                        <span class="address">${order.region}</span>
                        <span class="price">${order.price}<i>起</i></span>
                    </p>
                </div>
            </div>
        </div>

        <div class="detail_block">
            <h3>订单信息</h3>
            <div style="clear:both"></div>
            <div class="content">
                <p>订单编号: <span class="info">${order.id}</span></p>
                <p>下单时间: <span class="info">${order.addTime}</span></p>
                <p><span style="letter-spacing: 0.06rem; margin-right: -0.06rem;">出行人</span>: <span class="info">${order.participants}</span></p>
                <p><span style="letter-spacing: 0.24rem; margin-right: -0.24rem;">总价</span></span>: <span class="info">${order.totalFee}元</span></p>
            </div>
        </div>

        <div class="detail_block">
            <h3>联系人信息</h3>
            <div style="clear:both"></div>
            <div class="content">
                <p><span style="letter-spacing: 0.24rem; margin-right: -0.24rem;">姓名</span>: <span class="info">${order.contacts}</span></p>
                <p><span style="letter-spacing: 0.06rem; margin-right: -0.06rem;">手机号</span></span>: <span class="info">${order.mobile}</span></p>
            </div>
        </div>

        <#if !order.payed>
            <div class="pay">
                <#if order.finished>
                    <button id="btn_delete" class="tapable">删除订单</button>
                <#else>
                    <button id="btn_delete" class="tapable left">删除订单</button>
                    <button id="btn_submit" class="tapable right">继续支付</button>
                </#if>
            </div>
        </#if>
    </section>
</article>

<script type="text/javascript">
    $(function(){
        tq.t.back();
        $('#product_info').on('click', function() {
            location.href = "/actsDetail.html?id=${order.productId}";
        });
        $('#btn_delete').on('click', function() {
            event.preventDefault();
            event.stopPropagation();
            var utoken = tq.t.cookie.get("utoken");
            var oid = tq.t.getQueryString("oid");
            tq.t.confirm("确定要删除么？", func_cancel, func_ok);
            function func_cancel(){
                tq.t.delshide();
            }
            function func_ok(){
                var api = tq.urls + "order/delete";
                $.post(api, {
                    utoken: utoken,
                    id: oid
                }, function(res) {
                    if (res.errno == 0) {
                        location.href = "/user_order.html?status=2&type=le";
                    } else {
                        tq.t.alert(res.errmsg);
                        tq.t.cancel();
                    }
                });
            }
        });
        $('#btn_submit').on('click', function() {
            location.href = "/orderPay.html?order_id=${order.id}&pro_id=${order.productId}&sku_id=${order.skuId}&time=" + tq.t.encodeUTF8('${order.time}') + "&participants=" + tq.t.encodeUTF8('${order.participants}') + "&totalFee=${order.totalFee}";
        });
    })
</script>
</@override>

<@extends name="../base.ftl"/>

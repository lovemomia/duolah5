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
                <#list skus as map>
                    <#list map?keys as mapKey>
                    <#if mapKey = "skus">
                    <#list map[mapKey] as sku>
                        <#if sku_index== 0>
                <div class="form01 flag">
                        <#else >
                            <div class="form01"></#if>
                    <div class="pad_bot left">
                        <p class="time">${sku.time}</p>
                        <span class="price">
                            <i class="orange">￥${sku.minPrice}</i>起
                        </span>
                        <span class="num">仅剩${sku.stock}个名额</span>
                    </div>
                        <#if sku_index== 0>
                    <div class="chk right"></div>
                        <#else >
                            <div class="chk right none"></div></#if>
                    <div style="clear:both"></div>
                </div>

                    </#list>
                    </#if>
                    </#list>
                </#list>
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
        // tq.t.back();
        var id = tq.t.getQueryString("id");
        $(".back").on("click",function(){
            location.href ="actsDetail.html?id="+id+"";
        })
//        tq.home.getSKU();
    })
</script>
</body>
</html>

<#--<#list skus as skusmap>
    <#list skusmap?keys as key>
        <#if key = "errno">
        errno:${skusmap[key]}
        </#if>
        <#if key = "errmsg">
        errmsg:${skusmap[key]}
        </#if>
        <#if key = "data">

            <#if skusmap[key].contacts??>
            contacts:
               name:${skusmap[key].contacts.name}
               mobile:${skusmap[key].contacts.mobile}
            </#if>
        skus:
        <#if skusmap[key].skus??>
            <#list skusmap[key].skus as sku>
                <#list sku?keys as skuKey>
                    <#if skuKey = "productId">
                    productId: ${sku[skuKey]}
                    </#if>
                    <#if skuKey = "skuId">
                    skuId: ${sku[skuKey]}
                    </#if>
                    <#if skuKey = "stock">
                    stock: ${sku[skuKey]}
                    </#if>
                    <#if skuKey = "time">
                    time: ${sku[skuKey]}
                    </#if>
                    <#if skuKey = "type">
                    type: ${sku[skuKey]}
                    </#if>
                    <#if skuKey = "desc">
                    desc: ${sku[skuKey]}
                    </#if>
                    <#if skuKey = "limit">
                    limit: ${sku[skuKey]}
                    </#if>
                    <#if skuKey = "minPrice">
                    minPrice: ${sku[skuKey]}
                    </#if>
                    <#if skuKey = "needRealName">
                    needRealName: ${sku[skuKey]?string('true','false')}
                    </#if>
                    <#if skuKey = "prices">
                    prices:
                        <#list sku[skuKey] as prices>
                            <#list prices?keys as priceKey>
                                <#if priceKey = "adult">
                                adult:${prices[priceKey]}
                                </#if>
                                <#if priceKey = "child">
                                child:${prices[priceKey]}
                                </#if>
                                <#if priceKey = "desc">
                                desc:${prices[priceKey]}
                                </#if>
                                <#if priceKey = "unit">
                                unit:${prices[priceKey]}
                                </#if>
                            </#list>

                        </#list>
                    </#if>
                </#list>

            </#list>
        </#if>
        </#if>
    </#list>
</#list>-->
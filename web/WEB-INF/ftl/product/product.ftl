<@override name="title">${product.title}</@override>

<@override name="css" >
<style type="text/css">
    .share_tips{
        position: fixed;
        z-index: 20;
        top: 0;
        left: 0;
        width: 100%;
        height: 100%;
        background: rgba(0,0,0,0.8);
    }

    .share_tips .tips_info{
        border-radius: 4px;
        border: 2px solid #00c49d;
        background: rgba(255,255,255,0.9);
        width: 100%;
        overflow: hidden;
        margin: 0 auto auto;
        height: 1rem;
        text-align: center;
        padding: 0.16rem 0.6rem;
        position: relative;
        background: url(/image/jiantou.png) no-repeat right 0 #fff;
    }

    .share_tips .tips_info p {
        color: #333;
    }

    .share_tips .tips_info .tip1 {
        font-size: 0.12rem;
        padding-bottom: 0.06rem;
    }

    .share_tips .tips_info .tip2 {
        font-size: 0.16rem;
        height: 0.4rem;
        padding: 0.1rem 0;
    }

    .share_tips .tips_info img {
        float: left;
        width: 0.4rem;
    }
</style>
</@override>

<@override name="body">
<article id="page">
    <div class="ads_top">
        <img src="/image/downapp.png" alt="">
    </div>

    <header class="head">
        <span class="back"><img src="image/back2.png"></span>活动详情
        <span class="collect" style="margin-right:0.08rem">
        <#if product.favored?c == "false">
            <img src="image/collect2.png">
        </#if>
        <#if product.favored?c == "true">
            <img src="image/collect_check2x.png">
        </#if>

        </span>
    </header>

    <section>
        <div class="actsDetail">
            <!-- 图片轮播部分 -->
            <#if product.imgs?size == 0>
                <div class="scroll_box none" id="scroll_img">
            <#elseif product.imgs?size == 1>
                <#list product.imgs as img>
                    <img src="${img}" width='100%' />
                </#list>
            <#else >
                <div class="scroll_box" id="scroll_img">

                    <ul class = 'scroll_wrap' id='scroll_wrap'>
                        <#list product.imgs as img>
                            <li><img src="${img}" width='100%' /></li>
                        </#list>
                    </ul>

                    <ul class='scroll_position' id='scroll_position'>
                        <#list product.imgs as img>
                            <#if img_index == 0>
                                <li class='on'><a href='javascript:void(0);'></a></li>
                            <#else>
                                <li><a href='javascript:void(0);'></a></li>
                            </#if>
                        </#list>
                    </ul>

                </div>
            </#if>

            <div class="act_list" style="margin-bottom:0">
                <div class="act_detail">
                    <h3>${product.title}</h3>
                    <div class="act_attend">
                        <#if product.joined == 0>
                            <span class="num"></span>
                        <#else >
                            <span class="num"><i>${product.joined}</i>人参加</span>
                        </#if>

                        <span class="act_price orange">
                            <i style="font-size:0.1rem;">￥</i>
                            <i>${product.price}</i>
                            <i style="font-size:0.1rem;">起</i>
                        </span>
                    </div>
                </div>
                <div class="act_safe">
                    <#if product.tags??>
                        <#list product.tags as tags>
                            <span>${tags}</span>
                        </#list>
                    </#if>

                </div>
            </div>
        </div>
        </div>
        <div class="tips">
                <p class="child_age">
                    <img src="image/umbrella.png">${product.crowd}
                </p>
                <p class="tel">
                    <img src="image/alarm.png">${product.scheduler}
                </p>
                <p class="address" id="last">
                    <img src="image/address2.png">${product.address}
                </p>
            </div>
        <div class="attent_total">
            <#if product.customers??>
                <#if (product.customers.avatars?? && product.customers.avatars?size>0)>
                <h3>${product.customers.text}</h3>
                <div style="clear:both"></div>
                <#if product.customers.avatars??>
                    <#list product.customers.avatars as avatars>
                        <#if avatars == "">
                            <img src="image/default.png">
                        <#else>
                            <img src="${avatars}">
                        </#if>
                    </#list>
                </#if>
                </#if>
            </#if>
        </div>
        <div class="content_list">
            <#list product.content as content>
                <#if content.style == "ol">
                    <div class="tips_list">
                        <h3>${content.title}</h3>
                        <ol class="tips_article spec1 spes" style="margin-left:0.15rem">
                            <#if content.body??>
                                <#list content.body as body>
                                    <li>${body.text}</li>
                                </#list>
                            </#if>
                        </ol>
                        <#if content.body??>
                            <#list content.body as body>
                                <#list body?keys as bodyKey>
                                    <#if bodyKey = "link">
                                        <#if body.link != "" >
                                            <div class="word_img">
                                                <a href = '${body.link}'>${body.text}
                                                    <span class="more01"></span>
                                                </a>
                                            </div>
                                        </#if>
                                    </#if>
                                </#list>
                            </#list>
                        </#if>
                    </div>
                <#elseif content.style == "ul">
                    <div class="tips_list">
                        <h3>${content.title}</h3>
                        <ul class="tips_article spec1 spes" style="margin-left:0.15rem">
                        <#if content.body??>
                            <#list content.body as body>
                                <li>${body.text}</li>
                            </#list>
                        </#if>
                        </ul>
                    </div>
                <#else >
                    <div class="tips_list">
                        <h3>${content.title}</h3>
                        <p class="tips_article spec spes">
                            <#list content.body as body>

                                <#if body.label ??>
                                    <span class="orange">${body.label}</span><br>
                                    <#if body.text ??><span>${body.text}</span><br></#if>
                                <#elseif body.img ??>
                                    <img src= '${body.img}'><br>
                                <#elseif body.html ??>
                                    <span>${body.html}</span>
                                <#elseif body.link ??>
                                    <#if body.link != "" >
                                        <div class="word_img" onclick="location='${body.link}'">
                                        ${body.text}<span class="more01"></span>
                                        </div>
                                    </#if>
                                <#else>
                                    <#if body.text ??><span>${body.text}</span></#if><br>
                                </#if>

                            </#list>
                        </p>
                    </div>
                </#if>
            </#list>
        </div>
        <img src="http://s.duolaqinzi.com/2015-08-26/a26f55422eb589d7eb726fa270a98631.jpg" alt="" style="width:100%;margin-bottom:0.4rem;">

    </section>

</article>

<div class="bot_btn">
</div>
<script type="text/javascript">
        <#if product.config??>
        if (tq.t.isweixin()) {
            wx.config({
                debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
                appId: '${product.config.appId}', // 必填，公众号的唯一标识
                timestamp: ${product.config.timeStamp}, // 必填，生成签名的时间戳
                nonceStr: '${product.config.nonceStr}', // 必填，生成签名的随机串
                signature: '${product.config.sign}',// 必填，签名，见附录1
                jsApiList: ['onMenuShareAppMessage', 'onMenuShareTimeline'] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
            });

            wx.ready(function() {
                wx.onMenuShareAppMessage({
                    title: '${product.title}',
                    desc: '${product.abstracts}',
                    link: '${product.url}',
                    imgUrl: '${product.thumb}'
                });

                wx.onMenuShareTimeline({
                    title: '${product.title}',
                    link: '${product.url}',
                    imgUrl: '${product.thumb}'
                });
            });
        }
        </#if>

    $(function(){
        var invite = tq.t.getQueryString("invite");
        if (invite != null) sessionStorage.setItem("invite", invite);

        if (tq.t.isweixin()) {
            $(".bot_btn").append('<span class="btn partnership" style="width: 35%;">约伴</span>');
            $(".bot_btn").append('<span class="btn submit" style="width: 65%;">立即报名</span>');
            $(".bot_btn").append('<div class="clear:both"></div>');
        } else {
            $(".bot_btn").append('<span class="btn submit" style="width: 100%">立即报名</span>');
            $(".bot_btn").append('<div class="clear:both"></div>');
        };

        $(".back").on("click", function(){
            location.href = "index.html";
        });
        $(".ads_top").on("click", function(){
            location.href = "http://a.app.qq.com/o/simple.jsp?pkgname=com.youxing.duola&g_f=991653";
        });
        <#if product.customers.avatars??>
        $(".attent_total").on("click", function(){
            var id = tq.t.getQueryString("id");
            location.href = "partner.html?id="+id+"";
        })
        <#else >
            $(".attent_total").addClass("none");
        </#if>
        <#if product.favored?c == "true">
            $(".collect").on("click",function(){
                tq.home.uncollect();
            })
        </#if>

        <#if product.favored?c == "false">
            $(".collect").on("click",function() {
                tq.home.collect();
            })
        </#if>
        if(${product.imgs?size} >1 ) {
            tq.t.getScrollImg();
        }

        $(".btn.partnership").on("click", function() {
            share_tips();
        });

        function share_tips() {
            var s = "";
            s += "<div class='share_tips' onclick='$(this).remove()'>";
            s += "<div class='tips_info'>";
            s += "<p class='tip1'>请点击右上角按钮</p>"
            s += "<img src='/image/timeline.png' />";
            s += "<p class='tip2'>分享到朋友圈</p>"
            s += "</div>";
            s += "</div>";
            $(document.body).append(s);
        }

        <#if product.soldOut?c == "true"|| product.opened?c == "false">
            $(".btn.submit").css("background","gray");
            $(".btn.submit").css("border-top","1px solid gray");
            return;

        <#else>
            $(".btn.submit").on("click", function() {
                var utoken = tq.t.cookie.get("utoken");
                var id = tq.t.getQueryString("id");
                if (!utoken || utoken == "" || utoken == null) {
                    location.href = "loginpsw.html?orderDetail.html?id=" + id + "";
                } else {
                    tq.t.delSession();
                    location.href = "orderDetail.html?id=" + id + "";
                }
            });
         </#if>
    });
</script>
</@override>

<@extends name="../base.ftl"/>

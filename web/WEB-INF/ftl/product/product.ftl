<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width,target-densitydpi=device-dpi,user-scalable=no">
    <title>哆啦亲子</title>
    <link rel="stylesheet" type="text/css" href="CSS/main.css">
    <script type="text/javascript" src="JS/zepto.min.js"></script>
    <script type="text/javascript" src="JS/config.js"></script>
    <script type="text/javascript" src="JS/common.js"></script>
    <script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
    <script src='JS/hhSwipe.js'></script>
    <script type="text/javascript">
        document.getElementsByTagName("html")[0].style.fontSize=document.documentElement.clientWidth/3+"px";
    </script>
</head>
<body>
<#list product as map>
<article id="page">
    <#--<div class="ads_top">-->
        <#--<img src="image/downapp.png" alt="">-->
    <#--</div>-->

    <header class="head">
        <span class="back"><img src="image/back2.png"></span>活动详情
        <span class="collect" style="margin-right:0.08rem">
        <#if map.favored?c == "false">
            <img src="image/collect2.png">
        </#if>
        <#if map.favored?c == "true">
            <img src="image/collect_check2x.png">
        </#if>

        </span>
    </header>

    <section>
        <div class="actsDetail">
            <!-- 图片轮播部分 -->
            <#if map.imgs?size == 0>
                <div class="scroll_box none" id="scroll_img">
            <#elseif map.imgs?size == 1>
                <#list map.imgs as img>
                    <img src="${img}" width='100%' />
                </#list>
            <#else >
                <div class="scroll_box" id="scroll_img">

                    <ul class = 'scroll_wrap' id='scroll_wrap'>
                        <#list map.imgs as img>
                            <li><img src="${img}" width='100%' /></li>
                        </#list>
                    </ul>

                    <ul class='scroll_position' id='scroll_position'>
                        <#list map.imgs as img>
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
                    <h3>${map.title}</h3>
                    <div class="act_attend">
                        <#if map.joined == 0>
                            <span class="num"></span>
                        <#else >
                            <span class="num"><i>${map.joined}</i>人报名</span>
                        </#if>

                        <span class="act_price orange">
                            <i style="font-size:0.1rem;">￥</i>
                            <i>${map.price}</i>
                            <i style="font-size:0.1rem;">起</i>
                        </span>
                    </div>
                </div>
                <div class="act_safe">
                    <#if map.tags??>
                        <#list map.tags as tags>
                            <span>${tags}</span>
                        </#list>
                    </#if>

                </div>
            </div>
        </div>
        </div>
        <div class="tips">
                <p class="child_age">
                    <img src="image/umbrella.png">${map.crowd}
                </p>
                <p class="tel">
                    <img src="image/alarm.png">${map.scheduler}
                </p>
                <p class="address" id="last">
                    <img src="image/address2.png">${map.address}
                </p>
            </div>
        <div class="attent_total">
            <#if map.customers??>

                <h3>${map.customers.text}</h3>
                <div style="clear:both"></div>
                <#if map.customers.avatars??>
                    <#list map.customers.avatars as avatars>
                        <#if avatars == "">
                            <img src="image/default.png">
                        <#else>
                            <img src="${avatars}">
                        </#if>
                    </#list>
                </#if>
            </#if>
        </div>
        <div class="content_list">
            <#list map.content as content>
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
                                <#list body?keys as bodyKey>
                                <#if bodyKey = "label">
                                    <#if body.label != "" >
                                        <span class="orange">${body.label}</span><#if body.text ??>${body.text}</#if><br>
                                    </#if>
                                <#elseif bodyKey = "img">
                                    <#if body.img != "" >
                                        <img src= '${body.img}'><br>
                                    </#if>
                                <#elseif bodyKey = "html">
                                    <span>${body.html}</span>
                                <#else >
                                    <span>${body.text}</span><br>
                                </#if>
                                </#list>
                            </#list>
                        </p>
                    </div>
                </#if>
            </#list>
        </div>
        <img src="http://s.duolaqinzi.com/2015-07-06/3964b60ed6ca79c314d1c57f1357791b.jpg" alt="" style="width:100%;margin-bottom:0.4rem;">

    </section>

</article>

<div class="bot_btn">
    <!-- <span class="btn partnership">约伴</span> -->
    <span class="btn submit">立即报名</span>
    <div class="clear:both"></div>
</div>
<script type="text/javascript">
    $(function(){
//        if(tq.t.isandroid()){
//            $(".ads_top").addClass("none");
//        }
//        if(tq.t.isios()){
//            $(".ads_top").removeClass("none");
//            $(".ads_top").on("click", function(){
//                location.href = "../../../downapp.html";
//            });
//        }
        $(".back").on("click", function(){
            location.href = "index.html";
        });
        $(".ads_top").on("click", function(){
            location.href = "../../../downapp.html";
        });
        $(".attent_total").on("click", function(){
            var id = tq.t.getQueryString("id");
            location.href = "partner.html?id="+id+"";
        })

        <#if map.favored?c == "true">
            $(".collect").on("click",function(){
                tq.home.uncollect();
            })
        </#if>

        <#if map.favored?c == "false">
            $(".collect").on("click",function(){
                tq.home.collect();
            })
        </#if>
        if(${map.imgs?size} >1 )
        {
            console.log(1);
            tq.t.getScrollImg();
        }


        <#if map.soldOut?c == "true"|| map.opened?c == "false">
            $(".btn.submit").css("background","gray");
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
</#list>
</body>
</html>

<@override name="title">约伴返红包</@override>

<@override name="css" >
<style type="text/css">
    #page {
        background: #67ae6a;
    }

    .product_list {
        background-color: #fff;
        margin: 0 0.12rem 0.12rem 0.12rem;
        padding: 0.1rem;
        border-radius: 6px;
    }

    .product {
        padding: 0.08rem 0;
        border-bottom: 1px solid #eee;
        background: #fff;
        position: relative;
    }

    .product:first-child {
        padding-top: 0.02rem;
    }

    .product .img {
        float: left;
        width: 38%;
        margin: 0;
        padding: 0;
    }

    .product .img img {
        width: 100%;
        height: 100%;
        display:block;
        border-radius: 4px;
    }

    .product .content {
        float: right;
        width: 60%;
        padding: 0.05rem 0 0 0.08rem;
    }

    .product .content p {
        margin-bottom: 0.05rem;
    }

    .product .content .title {
        font-size: 0.14rem;
        color: #333;
        white-space: nowrap;
        overflow: hidden;
        text-overflow: ellipsis;
    }

    .product .content .price {
        color: #f67531;
        font-size: 0.16rem;
    }

    .product .content .price i {
        font-size: 0.1rem;
    }

    .product .content .joined {
        font-size: 0.12rem;
        color: #888;
        white-space: nowrap;
    }

    .product button {
        position: absolute;
        right: 0.02rem;
        bottom: 0.12rem;
        display: inline-block;
        width: 0.55rem;
        height: 0.3rem;
        line-height: 0.3rem;
        background: #ff6634;
        font-size: 0.14rem;
        color: #fff;
        border: none;
        border-radius: 4px;
    }

    .product_list .view_more {
        height: 0.2rem;
        margin-top: 0.12rem;
        text-align: center;
        color: #a1a1a1;
        font-size: 0.15rem;
    }

    .product_list .view_more a {
        color: #a1a1a1;
    }

    .desc {
        padding: 0.06rem 0.16rem;
    }

    .desc h3 {
        font-size: 0.14rem;
        color: #154c15;
        margin-bottom: 0.1rem;
    }

    .desc p {
        font-size: 0.12rem;
        color: #254726;
        margin-bottom: 0.1rem;
    }

    .desc p:last-child {
        margin-bottom: 0.3rem;
    }
</style>
</@override>

<@override name="body">
<article id="page">
    <@block name="header"></@block>
    <section>
        <div>
            <img src="/image/share.png" width="100%" />
        </div>
        <div class="product_list">
            <#list result.products as product>
                <div class="product">
                    <div class="img">
                        <img src="${product.cover}" alt="">
                    </div>
                    <div class="content">
                        <p class="title">${product.title}</p>
                        <p class="price"><i>¥</i><span class="price">${product.price}<i>元</i></span></p>
                        <p class="joined" style="margin-bottom:0">${product.joined}人已报名</p>
                    </div>
                    <button>约伴</button>
                </div>
            </#list>
            <div class="view_more"><a href="/">查看更多</a></div>
        </div>
        <div class="desc">
            <h3>返红包规则</h3>
            <p>1、在每个亲子活动详情页下方，点击“约伴”分享好友，在好友是新注册用户的情况下，双方都将获得红包返现。</p>
            <p>2、单价在50-100元的活动，双方各返5元红包；在100元以上，500元以下的，双方各返10元红包；在500元以上的双方各返50元红包。</p>
            <p>3、所返红包仅限APP使用，全场亲子活动通用。</p>
            <p>4、活动时间：2015年9月1日至2015年10月31日。</p>
        </div>
    </section>
</article>

<script type="text/javascript">
    var invite = tq.t.getQueryString("invite");
    if (invite != null) sessionStorage.setItem("invite", invite);

    <#if result.config??>
        if (tq.t.isweixin()) {
            wx.config({
                debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
                appId: '${result.config.appId}', // 必填，公众号的唯一标识
                timestamp: ${result.config.timeStamp}, // 必填，生成签名的时间戳
                nonceStr: '${result.config.nonceStr}', // 必填，生成签名的随机串
                signature: '${result.config.sign}',// 必填，签名，见附录1
                jsApiList: ['onMenuShareTimeline', 'onMenuShareAppMessage'] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
            });

            wx.ready(function() {
                wx.onMenuShareAppMessage({
                    title: '约伴返红包',
                    desc: '亲子活动一起嗨',
                    link: '${result.url}',
                    imgUrl: 'http://s.dulaqinzi.com/2015-09-01/a0d9ab3551fa28e68a8037c996fed63f_s.jpg'
                });

                wx.onMenuShareTimeline({
                    title: '约伴返红包',
                    link: '${result.url}',
                    imgUrl: 'http://s.dulaqinzi.com/2015-09-01/a0d9ab3551fa28e68a8037c996fed63f_s.jp'
                });

                wx.onMenuShareQQ({
                    title: '约伴返红包',
                    desc: '亲子活动一起嗨',
                    link: '${result.url}',
                    imgUrl: 'http://s.dulaqinzi.com/2015-09-01/a0d9ab3551fa28e68a8037c996fed63f_s.jp'
                });

                wx.onMenuShareWeibo({
                    title: '约伴返红包',
                    desc: '亲子活动一起嗨',
                    link: '${result.url}',
                    imgUrl: 'http://s.dulaqinzi.com/2015-09-01/a0d9ab3551fa28e68a8037c996fed63f_s.jp'
                });

                wx.onMenuShareQZone({
                    title: '约伴返红包',
                    desc: '亲子活动一起嗨',
                    link: '${result.url}',
                    imgUrl: 'http://s.dulaqinzi.com/2015-09-01/a0d9ab3551fa28e68a8037c996fed63f_s.jp'
                });
            });
        }
    </#if>

    $(function(){
        tq.t.back();
    })
</script>
</@override>

<@extends name="../base.ftl"/>

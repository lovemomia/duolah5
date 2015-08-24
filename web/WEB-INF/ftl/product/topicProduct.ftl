<@override name="title">活动专题</@override>

<@override name="body">
<article id="page">
    <header class="head">
        <span class="back"><img src="image/back2.png"></span>哆啦亲子游<span class="collect"></span>
    </header>
    <section>
        <img src="${topic.cover}" alt="" class="topic_banner">
        <#if topic.groups??>
            <#list topic.groups as group>
                <div class="popular">
                    <h3>${group.title}</h3>
                    <#list  group.products as product>
                        <div class="collect_pad" id = "${product.id}">
                            <img src="${product.cover}" alt="">
                            <div class="collect_main">
                                <div class="collect_info" >
                                    <p class="title">${product.title}</p>
                                    <p class="schedule">${product.scheduler}</p>
                                    <p class="ad_pr" style="margin-bottom:0">
                                        <span class="address">${product.address}</span>
                                        <span class="price">${product.price}<i>起</i></span>
                                    </p>
                                </div>
                            </div>
                        </div>
                    </#list>
                </div>
            </#list>
        </#if>
    </section>
</article>

<script type="text/javascript">
    $(".collect_pad").on("click", function() {
        var id = $(this).attr("id");
        location.href ="actsDetail.html?id="+id;
    });
    $(function(){
        tq.t.back();
        // tq.home.getCollect();
    })
</script>
</@override>

<@extends name="../base.ftl"/>

<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width,target-densitydpi=device-dpi,user-scalable=no">
    <title>选择出行人</title>
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
        <span class="back"><img src="image/back2.png"></span>常用出行人<span class="add">新增</span>
    </header>

    <section>
    <#list participant as outer>

        <div class="order_list">
            <!-- <h3>请选择成人<i class="orange adult"></i>名，儿童<i class="orange child"></i>名</h3> -->

            <div class="order_detail">
                <#list outer as map>
                    <#if map_index != outer?size-1>
                    <div class="form01" id="${map.id}">
                        <div class="left outer_info">
                            <a href="">
                                <span class="name">${map.name}</span>
                                <span class="age"><#if map.type??>${map.type}</#if></span>
                                <span class="sex">${map.sex}</span>
                            </a>
                        </div>
                        <div style="clear:both"></div>
                    </div>
                </#if>
                    <#if map_index == outer?size-1>
                    <div class="form01 last" id="${map.id}">
                        <div class="left outer_info">
                            <span class="name">${map.name}</span>
                            <span class="age"><#if map.type??>${map.type}</#if></span>
                            <span class="sex">${map.sex}</span>
                        </div>
                        <div style="clear:both"></div>
                    </div>
                </#if>
                </#list>
            </div>
        </div>
    </#list>
    </section>

</article>

<script>
    var url = location.href;
    $(".form01").on("click", function() {
        var outer_id = $(this).attr("id");
        location.href = "edit_outer.html?id="+outer_id+"&url="+url+"";
    });
    $(".back").on("click",function(){
        location.href = "../../../../profile.html";
    });
    $(".add").on("click", function() {
        location.href = "addOuter.html?url="+url+"";
    })
</script>
</body>
</html>
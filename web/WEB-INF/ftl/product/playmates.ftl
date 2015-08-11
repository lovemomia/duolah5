<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width,target-densitydpi=device-dpi,user-scalable=no">
    <title>玩伴信息</title>
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
        <span class="back"><img src="image/back2.png"></span>玩伴信息<span class="collect"></span>
    </header>

    <section>
        <#list playmates as list>
            <#list list as map>
        <div class="pMain"></div>
         <div class="part_calender">
            <div class="cTitle">
                <img src="image/cd2.png" alt="">
                <div class="pDate">
                    <p class="date"><#if map.time??>${map.time}</#if></p>
                    <p class="pNum"><#if map.joined??>${map.joined}</#if></p>
                </div>
            </div>

             <#list map.playmates as playmate>
            <div class="pInfo none">
                <div class="pDetail">
                    <#if playmate.avatar !="">
                    <img src="${playmate.avatar}" alt="">
                    <#else >
                    <img src="image/default.png" alt="">
                    </#if>
                    <div class="pMaMa">
                        <p class="MaMa"><#if playmate.nickName??>${playmate.nickName}</#if></p>
                        <#list playmate.children as children>
                        <#if children_index <= 1>
                        <p class="pAge">${children}</p></#if>
                        </#list>
                    </div>
                </div>
            </div>
            </#list>
        </div>
            </#list>
        </#list>
    </section>

</article>
<script>
    $(function(){
        tq.t.back();
        $('.cTitle').on('click',function(event){
            $(this).siblings(".pInfo").toggleClass("none");
            $(this).toggleClass("downforward");
        });
     //   tq.home.partner();
    })
</script>
</body>
</html>
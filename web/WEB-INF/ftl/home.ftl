<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width,target-densitydpi=device-dpi,user-scalable=no">
    <meta name="viewport" content="initial-scale=1, maximum-scale=1">
    <title>哆啦亲子,和孩子一起探索世界</title>
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
    <header class="head01">
        <a id="profile"class ="my" href="javascript:;">
            <img src="image/my2.png">
        </a>
        <span class="city">哆啦亲子</span>
    </header>

    <section>

        <!-- 图片轮播部分 -->
        <div class="scroll_box" id="scroll_img"></div>

        <!-- 活动列表部分 -->
        <div id="act_content"></div>
    </section>

    <div class="ads_bot">
        <img src="image/downapp.png" alt="">
    </div>
</article>

<script type="text/javascript">
    tq.home.getIndexScrollImg($("#scroll_img"));
    tq.home.getActsList($("#act_content"),0);
    if(tq.t.isandroid()){
        $(".ads_bot").addClass("none");
    }
    if(tq.t.isios()){
        $(".ads_bot").removeClass("none");
        $(".ads_bot").on("click", function(){
            location.href = "../../downapp.html";
        });
    }

    $(".my").on("click",function(){
        var utoken = tq.t.cookie.get("utoken");
        if(utoken == null || utoken == "" || !utoken){
            location.href = "loginpsw.html?profile.html";
        }
        else{
            location.href = "profile.html";
        }
    });
</script>
</body>
</html>
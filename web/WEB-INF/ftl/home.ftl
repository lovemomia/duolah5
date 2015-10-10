<@override name="title">松果亲子－孩子体验式成长的第一课</@override>

<@override name="css">
    <style type="text/css">
        .home_content {
            margin-bottom: 0.55rem;
        }
    </style>
</@override>

<@override name="body">
<div class="home_content">
    <header class="head01">
        <a id="profile"class ="my" href="javascript:;">
            <img src="image/my2.png">
        </a>
        <span class="city">松果亲子</span>
    </header>

    <section>

        <!-- 图片轮播部分 -->
        <div class="scroll_box" id="scroll_img" style="margin-bottom: -0.08rem"></div>

        <!-- 活动列表部分 -->
        <div id="act_content"></div>
    </section>
</div>

<div class="ads_bot">
    <img src="/image/downapp.png" alt="">
</div>

<script type="text/javascript">
    function more() {
        if (sessionStorage.getItem("homeNextPage") != null) tq.home.getActsList($("#act_content"),sessionStorage.getItem("homeNextPage"));
    }

    tq.home.getIndexScrollImg($("#scroll_img"));
    tq.home.getActsList($("#act_content"),0);

    $(".ads_bot").on("click", function(){
        location.href = "http://a.app.qq.com/o/simple.jsp?pkgname=com.youxing.duola&g_f=991653";
    });

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
</@override>

<@extends name="base.ftl"/>

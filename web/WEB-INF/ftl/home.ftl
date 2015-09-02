<@override name="title">松果亲子－约上玩伴，探索世界</@override>

<@override name="body">
<article id="page">
    <header class="head01">
        <a id="profile"class ="my" href="javascript:;">
            <img src="image/my2.png">
        </a>
        <span class="city">松果亲子</span>
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
    function more() {
        if (sessionStorage.getItem("homeNextPage") != null) tq.home.getActsList($("#act_content"),sessionStorage.getItem("homeNextPage"));
    }

    tq.home.getIndexScrollImg($("#scroll_img"));
    tq.home.getActsList($("#act_content"),0);
        if(tq.t.isios()){
            $(".ads_bot").on("click", function(){
                location.href = "/download.html";
            });
        } else {

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
</@override>

<@extends name="base.ftl"/>

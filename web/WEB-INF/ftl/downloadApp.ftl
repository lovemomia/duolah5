<@override name="title">哆啦亲子－约上玩伴，探索世界</@override>

<@override name="body">
<article id="page">

    <header class="head">
        <span class="back"><img src="image/back2.png"></span>客户端下载<span class="collect"></span>
    </header>

    <section>
        <div class="centerPan">
            <div class="model modeli">
                <img src="image/modeli.png" alt="" />
                <div class="pagewrapper">
                    <div class="scroll_box" id="scroll_img">
                        <ul class="scroll_wrap">
                            <li style="height:1.9rem"><a href="#"><img src="image/i1.jpg" width="100%" /></a></li>
                            <li><a href="#"><img src="image/i1.jpg" width="100%" /></a></li>
                            <li><a href="#"><img src="image/i1.jpg" width="100%" /></a></li>
                        </ul>
                    </div>
                </div>
            </div>
            <div class="text">
                <img src="image/download.png" alt="" />
                <div class="download">
                    <button id="btn_submit" class="tapable">立即下载</button>
                </div>
            </div>
        </div>
        <div class="bottomPan">
            <ul class="scroll_position" id='scroll_position' style="bottom: 2rem;left:42%">
                <li class="on"><a href="javascript:void(0);">1</a></li>
                <li><a href="javascript:void(0);">2</a></li>
                <li><a href="javascript:void(0);">3</a></li>
            </ul>
            <img class="logo" src="image/dd.png" alt="" />
            <#--<p>陪孩子一起长大</p>-->
        </div>
    </section>

</article>
<script type="text/javascript">
    $(function(){
        $(".back").on("click", function(){
            location.href = "index.html";
        });
        tq.t.getScrollImg();
        $(".download").on("click", function(){
            location.href = tq.t.down.ios;
        })
    });
</script>
</@override>

<@extends name="base.ftl"/>

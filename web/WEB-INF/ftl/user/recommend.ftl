<@override name="title">我要爆料</@override>

<@override name="body">
<article id="page">
    <div  class="textarea">
        <h3>爆料</h3>
        <textarea placeholder="说说这里有什么好玩的亲子活动吧..." id="textarea"></textarea>
    </div>

    <div class="form bot baoliao">
        <div class="fitem_input">
            <span class="fh">活动时间</span>
				<span class="fd">
					<input	type="datetime" id="act_time" class="realname" style="color: #333;" placeholder="请输入活动时间">
                    </input>
				</span>
        </div>

        <div class="fitem_input">
            <span class="fh">活动地点</span>
				<span class="fd">
					<input	type="text" id="act_address" class="realname" value="" style="color: #333;" placeholder="请输入活动地点">
                    </input>
				</span>
        </div>

        <div class="fitem_input">
            <span class="fh">联系方式</span>
				<span class="fd">
					<input	type="tel" id="act_contact" class="realname" value="" style="color: #333;" placeholder="请输入联系方式">
                    </input>
				</span>
        </div>
    </div>

    <div class="login">
        <button id="btn_submit" class="tapable">提交</button>
    </div>
</article>

<script>
    $("#btn_submit").on("click",function(){
        tq.home.recommend();
    })
</script>
</@override>

<@extends name="../base.ftl"/>

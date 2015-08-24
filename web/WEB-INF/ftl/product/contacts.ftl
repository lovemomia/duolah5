<@override name="title">联系人信息</@override>

<@override name="body">
<article id="page">
    <header class="head">
        <span class="back"><img src="image/back2.png"></span>联系人信息<span class="add">完成</span>
    </header>

    <section class="order">
        <div class="form bot">
            <div class="fitem_input">
                <span class="fh">姓名</span>
					<span class="fd" id="right">
						<input	type="text" id="realname" class="realname" maxlength="12" value="" style="color: #333;text-align: right;">
                        </input>
					</span>
            </div>

            <div class="fitem_input">
                <span class="fh">手机号</span>
					<span class="fd" id="right">
						<input	type="text" id="realphone" class="realname" maxlength="12" value="" style="color: #333;text-align: right;">
                        </input>
					</span>
            </div>

        </div>
    </section>
</article>
<script>
    $(function(){
        tq.t.back();
        tq.home.getContacts();
    })
</script>
</@override>

<@extends name="../base.ftl"/>

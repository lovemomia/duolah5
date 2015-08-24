<@override name="title">添加出行人</@override>

<@override name="body">
<article id="page">
    <header class="head">
        <span class="back"><img src="image/back2.png"></span>添加出行人<span class="add">完成</span>
    </header>

    <section class="order">
        <div class="form bot">
            <div class="fitem_input">
                <span class="fh">出行人姓名</span>
					<span class="fd">
						<input	type="text" id="realname" class="realname" maxlength="12" placeholder="请输入真实的姓名">
                        </input>
					</span>
            </div>

            <div class="fitem_input">
                <span class="fh">性<i class="pl30">别</i></span>
					<span class="fd">
						<select class="realname fdc" id="gender" style="width:100%;color: #333;">
                            <option value="女">女</option>
                            <option value="男">男</option>
                        </select>
					</span>
                <span class="select"></span>
            </div>

            <div class="fitem_input">
                <span class="fh">出生日期</span>
					<span class="fd">
						<input type="date" class="realname" value="2009-06-10" onchange="$(this).prev().val(this.value)" onfocus="$(this).prev().val(this.value)" style="width:100%;color: #333;" id="birth"></input>
					</span>
                <span class="select"></span>
            </div>

            <div class="fitem_input">
                <span class="fh">证件类型</span>
					<span class="fd">
						<select class="realname fdc" id="certificate" style="width:100%;color: #333;">
                            <option value="1">身份证</option>
                            <option value="2">护照</option>
                        </select>
					</span>
                <span class="select"></span>
            </div>

            <div class="fitem_input">
                <span class="fh">证件号码</span>
					<span class="fd">
						<input	type="text" id="card_num" class="realname" maxlength="18" placeholder="请输入证件号码">
                        </input>
					</span>
            </div>

        </div>
    </section>
</article>

<script type="text/javascript">

    $(function(){
        tq.t.back();
        tq.home.add_Outer();
    });

</script>
</@override>

<@extends name="../base.ftl"/>

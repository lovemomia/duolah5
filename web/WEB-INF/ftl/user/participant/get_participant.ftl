<@override name="title">编辑出行人</@override>

<@override name="body">
<article id="page">
    <header class="head">
        <span class="back"><img src="image/back2.png"></span>编辑出行人<span class="add edit_submit">完成</span>
    </header>
<#list participant as map>
    <section class="order">
        <div class="form bot">
            <div class="fitem_input">
                <span class="fh">出行人姓名</span>
					<span class="fd">
						<input	type="text" id="realname" class="realname" maxlength="12" value=${map.name} style="color: #333;">
                        </input>
					</span>
            </div>

            <div class="fitem_input">
                <span class="fh">性<i class="pl30">别</i></span>
					<span class="fd">
						<select class="realname fdc" id="gender" style="width:100%;color: #333;">
                            <#if map.sex = "男">
                                <option value="男" selected = "selected">男</option>
                                <option value="女">女</option>
                            <#else>
                                <option value="男">男</option>
                                <option value="女" selected = "selected">女</option></#if>
                        </select>
					</span>
                <span class="select"></span>
            </div>

            <div class="fitem_input">
                <span class="fh">出生日期</span>
					<span class="fd">
						<input type="date" value=${map.birthday} class="realname"  onchange="$(this).prev().val(this.value)" onfocus="$(this).prev().val(this.value)" style="width:100%;color: #333;" id="birth"></input>
					</span>
                <span class="select"></span>
            </div>

            <div class="fitem_input">
                <span class="fh">证件类型</span>
					<span class="fd">
						<select class="realname fdc" id="certificate" style="width:100%;color: #333;">
                            <#if map.idType = 1>
                                <option value="1" class="id_card" selected = "selected">身份证</option>
                                <option value="2" class="passport">护照</option>
                            <#else>
                               <option value="1" class="id_card">身份证</option>
                               <option value="2" class="passport" selected="selected">护照</option>
                            </#if>
                        </select>
					</span>
                <span class="select"></span>
            </div>

            <div class="fitem_input">
                <span class="fh">证件号码</span>
					<span class="fd">
						<input	type="text" id="card_num" class="realname" maxlength="18" <#if map.idNo??>value='${map.idNo}'</#if> style="color: #333;">
                        </input>
					</span>
            </div>
        </div>
    </section>
</#list>
    <div class="delete">删除出行人</div>
</article>

<script type="text/javascript">
    $(function(){
        tq.t.back();
        tq.home.get_edit_Outer();
    })
</script>
</@override>

<@extends name="../../base.ftl"/>

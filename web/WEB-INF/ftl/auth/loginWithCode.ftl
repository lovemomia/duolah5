<@override name="title">登录</@override>

<@override name="body">
<article id="page">
    <header class="head">
        <span class="back"><img src="image/back2.png"></span>用户登录<span class="add"><a href="" class="add">注册</a></span>
    </header>

    <section class="order">
        <div class="form bot form_pad">
            <dl>
                <dt>手机号</dt>
                <dd class="left">
                    <i></i>
                    <input type="tel" id="tel" placeholder="您的手机号码">
                </dd>
            </dl>

            <dl id="last">
                <dt>验证码</dt>
                <dd class="left">
                    <i></i>
                    <input type="tel" id="vcode" name="vcode" placeholder="短信验证码">
                </dd>
                <dd class="right">
                    <button id="bth_getcode" class="getvcode tapable">获取验证码</button>
                </dd>
            </dl>
        </div>
    </section>
    <p class="loginWay right">使用密码登录</p>
    <div style="clear:both"></div>
    <div class="login">
        <button id="btn_submit" class="tapable">登录</button>
    </div>
</article>

<script type="text/javascript">
    $(function(){
        tq.t.back();
        tq.home.login($("#bth_getcode"),$("#tel"),$("#vcode"),$("#btn_submit"));
    })
</script>
</@override>

<@extends name="../base.ftl"/>

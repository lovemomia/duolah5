<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width,target-densitydpi=device-dpi,user-scalable=no">
    <title>设置密码</title>
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
        <span class="back"><img src="image/back2.png"></span>设置密码<span class="add"><a href="" class="collect"></a></span>
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

            <dl>
                <dt>密码</dt>
                <dd class="left">
                    <i></i>
                    <input type="password" id="password" placeholder="请输入密码">
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
    <div class="login">
        <button id="btn_submit" class="tapable">完成</button>
    </div>
</article>

<script>
    $(function(){
        tq.t.back();
        tq.home.resetPsw($("#bth_getcode"),$("#tel"),$("#vcode"),$("#password"),$("#btn_submit"));
    })
</script>
</body>
</html>
<!doctype html>
<html>
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no, minimal-ui" />
    <title>联系人信息</title>
    <link rel="stylesheet" type="text/css" href="CSS/main.css" />
    <script type="text/javascript" src="JS/zepto.min.js"></script>
    <script type="text/javascript" src="JS/config.js"></script>
    <script type="text/javascript" src="JS/common.js"></script>
    <script type="text/javascript">document.getElementsByTagName("html")[0].style.fontSize=document.documentElement.clientWidth/3+"px";
    </script>
</head>
<body>
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
</body>
</html>
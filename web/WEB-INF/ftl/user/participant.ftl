<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width,target-densitydpi=device-dpi,user-scalable=no">
    <title>选择出行人</title>
    <link rel="stylesheet" type="text/css" href="CSS/main.css">
    <script type="text/javascript" src="JS/zepto.min.js"></script>
    <script type="text/javascript" src="JS/config.js"></script>
    <script type="text/javascript" src="JS/common.js"></script>
    <script src='JS/hhSwipe.js'></script>
    <script type="text/javascript">
        document.getElementsByTagName("html")[0].style.fontSize=document.documentElement.clientWidth/3+"px";
        $(function(){
            tq.t.back();
        });
    </script>
</head>
<body>
<article id="page">

    <header class="head">
        <span class="back"><img src="image/back2.png"></span>常用出行人<span class="add">新增</span>
    </header>

    <section>
        <div class="order_list">
            <!-- <h3>请选择成人<i class="orange adult"></i>名，儿童<i class="orange child"></i>名</h3> -->
            <div class="order_detail">
                <!-- <div class="form01">
                    <div class="left outer_info">
                        <a href="">
                            <span class="name">张君雅</span>
                            <span class="age">成人</span>
                            <span class="sex">女</span>
                        </a>
                    </div>
                    <div style="clear:both"></div>
                </div>

                <div class="form01 last">
                    <div class="left outer_info">
                        <span class="name">张云朵</span>
                        <span class="age">儿童</span>
                        <span class="sex">女</span>
                    </div>
                    <div style="clear:both"></div>
                </div> -->
            </div>
        </div>
    </section>

</article>

<script>
    $(function(){
        tq.t.back();
        tq.home.getComOuter();
    })
</script>
</body>
</html>
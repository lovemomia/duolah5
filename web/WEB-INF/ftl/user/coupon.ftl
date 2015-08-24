<!doctype html>
<html>
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no, minimal-ui" />
    <title>我的红包</title>
    <link rel="stylesheet" type="text/css" href="CSS/main_v1.css" />
    <script type="text/javascript" src="JS/zepto.min.js"></script>
    <script type="text/javascript" src="JS/config_v1.js"></script>
    <script type="text/javascript" src="JS/common_v1.js"></script>
    <script type="text/javascript">document.getElementsByTagName("html")[0].style.fontSize=document.documentElement.clientWidth/3+"px";</script>
</head>
<body>
<article id="page">
    <header class="head">
        <span class="back"><img src="image/back2.png"></span>我的红包<span class="collect"></span>
    </header>

    <section>

    </section>
</article>

<script type="text/javascript">
    function getUserCoupon(nextIndex){
        var utoken = tq.t.cookie.get("utoken");
        var api = tq.url + "user/coupon?utoken="+utoken+"&start=" + nextIndex + "&status=1";
        $.get(api,{},function(res){
            if(res.errno == 0){
                var pro_id = [];
                // console.log(res);
                var data = res.data.list;
                $("#more").remove();
                for(var i=0; i<data.length; i++){
                    pro_id.push(data[i].id);
                    var s = '<div class="coupon">';
                    s += '<div class="discount">¥' + data[i].discount + '</div>';
                    s += '<div class="collect_main">';
                    s += '<div class="collect_info">';
                    s += '<p class="title">'+data[i].title+'</p>';
                    s += '<p class="desc">'+data[i].desc+'</p>';
                    s += '<p class="time" style="margin-bottom:0">';
                    s += data[i].startTime + '至' + data[i].endTime + '有效';
                    s += '</p>';
                    s += '</div></div></div>';
                    $("section").append(s);
                }

                if (res.data.nextIndex == undefined) {
                    sessionStorage.removeItem("couponNextIndex");
                } else {
                    sessionStorage.setItem("couponNextIndex", res.data.nextIndex);
                    $("section").append('<div id="more" onclick="more();">查看更多</div>');
                }
            }else{
                tq.t.alert(res.errmsg);
                tq.t.cancel();
            }
        });
    }

    function more() {
        if (sessionStorage.getItem("couponNextIndex") != null) getUserCoupon(sessionStorage.getItem("couponNextIndex"));
    };

    $(function(){
        tq.t.back();
        getUserCoupon(0);
    })
</script>
</body>
</html>
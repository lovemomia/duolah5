<@override name="title">松果亲子客户端下载</@override>

<@override name="css">
    <style type="text/css">
        .center_pan {
            padding-top: 0.2rem;
            text-align: center;
        }

        .center_pan img {
            width: 70%;
        }

        .bottom_pan {
            position: absolute;
            left: 0;
            right: 0;
            top: 3.3rem;
            bottom: 0;
            background: #fff;
            border-top: 1px solid #eee;
            text-align: center;
            box-shadow: 0.06rem 0.4rem rgba(0, 0, 0, 0.3)
        }

        .bottom_pan button {
            display: inline-block;
            height: 0.4rem;
            border: none;
            border-radius: 4px;
            background: #00c49d;
            margin-top: 0.2rem;
            padding: 0 0.18rem;
            font-size: 0.18rem;
            color: #fff;
        }

        .download_tips {
            position: fixed;
            z-index: 20;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
        }

        .download_tips .tips_info {
            width: 100%;
            border: none;
        }

        .download_tips .tips_info img {
            width: 100%;
        }
    </style>
</@override>

<@override name="body">
<article id="page">
    <header class="head">
        <span class="back"><img src="image/back2.png"></span>客户端下载<span class="collect"></span>
    </header>

    <div class="center_pan">
        <img src="/image/app.png" />
    </div>
</article>

<div class="bottom_pan">
    <button id="download">立即下载</button>
</div>

<script type="text/javascript">
    $(function(){
        $(".back").on("click", function(){
            location.href = "/";
        });

        $("#download").on("click", function(){
            if (tq.t.isweixin()) {
                download_tips();
            } else {
                if (tq.t.isios()) {
                    location.href = 'https://itunes.apple.com/cn/app/song-guo-qin-zi/id1019473117?mt=8';
                } else {
                    location.href = 'http://www.duolaqinzi.com/songguo_songguo_v1.0.apk';
                }
            }
        })

        function download_tips() {
            var s = "<div class='download_tips' onclick='$(this).remove()'><div class='tips_info'><img src='/image/download_tips.jpg' /></div></div>";
            $(document.body).append(s);
        }
    });
</script>
</@override>

<@extends name="base.ftl"/>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no, minimal-ui" />
    <title>我的信息</title>
    <link rel="stylesheet" type="text/css" href="CSS/main_v1.css" />
    <script type="text/javascript" src="JS/zepto.min.js"></script>
    <script type="text/javascript" src="JS/config_v1.js"></script>
    <script type="text/javascript" src="JS/common_v1.js"></script>
    <script type="text/javascript">document.getElementsByTagName("html")[0].style.fontSize=document.documentElement.clientWidth/3+"px";</script>
</head>
<body>
<article id="page">
    <header class="head">
        <span class="back"><img src="image/back2.png"></span>我的信息<span class="collect"></span>
    </header>

    <section>
   <#list list as map>
    <#list map?keys as key>
        <#if key = "data">
            <#if map[key] ??>
        <div class="info_head">个人信息</div>
        <div class="form bot" style="border-top:1px solid #eee;border-bottom:1px solid #eee;margin-bottom:0.1rem;">
            <div class="fitem_input edit" style="height:0.6rem;">
                <span class="fh" style="line-height:0.6rem">头像</span>
					<span class="fd" id="right">
                        <#if map[key].avatar!="">
                            <img src = "${map[key].avatar}"  class="nick_img">
                        <#else>
                            <img src="image/default02.png" alt="" class="nick_img">
                        </#if>
						<form method="post" action="http://upload.momia.cn/upload/image" enctype="multipart/form-data" accept="image/*" name="fileinfo" target="ifr1">
                            <input type="file" id="browsefile" name="file">
                        </form>
						<div style="clear:both"></div>
					</span>
                <div style="clear:both"></div>
            </div>

            <div class="fitem_input edit">
                <span class="fh">昵称</span>
					<span class="fd tr aNickname" id="right">${map[key].nickName}</span>
            </div>

            <div class="fitem_input edit">
                <span class="fh">手机号</span>
					<span class="fd tr phone" id="right">${map[key].mobile}</span>
            </div>

        </div>

        <div class="form bot" style="border-top:1px solid #eee;border-bottom:1px solid #eee;margin-bottom:0.1rem;">

            <div class="fitem_input edit">
                <span class="fh">性别</span>
					<span class="fd tr aGender" id="right">${map[key].sex}</span>
            </div>

            <div class="fitem_input edit">
                <span class="fh">常居地</span>
					<span class="fd tr address" id="right"><#if map[key].address!= "">${map[key].address}<#else >上海</#if>
                    </span>
            </div>

        </div>

        <div class="order_detail babyInfo">
            <div class="form01" id="last">
                <div class="pad_bot left">
                    孩子信息
                </div>
                <div class="chk_num right ac"><span class="minus green" style="font-size:0.15rem;">-</span><span class="num01">${map[key].children?size}</span><span class="plus green" style="font-size:0.15rem">+</span>
                </div>
                <div style="clear:both"></div>
            </div>
        </div>

        <div class="babyMain">
                <#if map[key].children??>
                    <#list map[key].children as child>
                        <div class="form bot" style="border-top:1px solid #eee;border-bottom:1px solid #eee;margin-bottom:0.1rem;">
                            <div class="fitem_input edit" onclick="">
                                <span class="fh">大宝姓名</span>
                                <span class="fd tr cNickname test"  id=${child.id}>${child.name}</span>
                            </div>

                            <div class="fitem_input edit" onclick="">
                                <span class="fh">性别</span>
                                <span class="fd tr cGender test" id=${child.id}>${child.sex}</span>
                            </div>

                            <div class="fitem_input edit" onclick="">
                                <span class="fh">生日</span>
                                <span class="fd tr cBirth test" id=${child.id}>${child.birthday}</span>
                            </div>
                        </div>
                    </#list>
                </#if>
        </div>

            </#if>
        </#if>
    </#list>
</#list>

    </section>
</article>
<script type="text/javascript">
    $(function(){
        $(".back").on("click",function(){
            location.href = "profile.html";
        });
        tq.home.profileInfo();
        tq.home.uploadImg();
    })
</script>
</body>
</html>
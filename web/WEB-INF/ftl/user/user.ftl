<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no, minimal-ui" />
    <title>我的信息</title>
    <link rel="stylesheet" type="text/css" href="CSS/main.css" />
    <script type="text/javascript" src="JS/zepto.min.js"></script>
    <script type="text/javascript" src="JS/config.js"></script>
    <script type="text/javascript" src="JS/common.js"></script>
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
                            <!-- <input type="submit" value="上传" style="position:absolute;top:0;left:0">	 -->
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
					<span class="fd tr address" id="right">${map[key].address}

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
                <div class="fitem_input edit">
                    <span class="fh">大宝姓名</span>
                    <span class="fd tr cNickname test"  id=${child.id}>${child.name}</span>
                </div>


            <div class="fitem_input edit">
                    <span class="fh">性别</span>
                    <span class="fd tr cGender test" id=${child.id}>${child.sex}</span>
                </div>



                <div class="fitem_input edit">
                    <span class="fh">生日</span>
                    <span class="fd tr cBirth test" id=${child.id}>${child.birthday}</span>
                </div>

        </div>
        </div
                    </#list>
                </#if>
            </#if>
        </#if>
    </#list>
</#list>

    </section>
    <div class="login">
        <button id="btn_submit" class="tapable">退出登录</button>
    </div>
</article>
<script type="text/javascript">
    $(function(){
        $(".back").on("click",function(){
            location.href = "profile.html";
        });
        tq.home.profileInfo();
        $(".login").on("click", function(){
            tq.t.cookie.del("utoken");
            sessionStorage.clear();
            location.href = "index.html";
        });
        tq.home.uploadImg();
    })
</script>
<#--
<#list list as map>
    <#list map?keys as key>
        <#if key = "errno">
           errno:${map[key]}
        </#if>
        <#if key = "errmsg">
        errmsg:${map[key]}
        </#if>
        <#if key = "data">
        <#if map[key] ??>
        avatar:${map[key].avatar}
        mobile:${map[key].mobile}
        address:${map[key].address}
        nickname:${map[key].nickName}
        <#if map[key].children??>
        <#list map[key].children as child>
            <#list child?keys as childKey>
                <#if childKey = "id">
                id:${child[childKey]}
                </#if>
                <#if childKey = "name">
                name:${child[childKey]}
                </#if>
                <#if childKey = "sex">
                sex:${child[childKey]}
                </#if>
                <#if childKey = "birthday">
                birthday:${child[childKey]}
                </#if>
            </#list>
        </#list>
        </#if>
        </#if>
        </#if>

    </#list>
</#list>-->
</body>
</html>
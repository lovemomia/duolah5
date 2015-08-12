<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width,target-densitydpi=device-dpi,user-scalable=no">
    <title>选择出行人</title>
    <link rel="stylesheet" type="text/css" href="CSS/main_v1.css">
    <script type="text/javascript" src="JS/zepto.min.js"></script>
    <script type="text/javascript" src="JS/config_v1.js"></script>
    <script type="text/javascript" src="JS/common_v1.js"></script>
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
        <span class="back"><img src="image/back2.png"></span>选择出行人<span class="add">新增</span>
    </header>

    <section>
<#list participant as outer>

    <div class="order_list">
            <h3>请选择成人<i class="orange adult"></i>名，儿童<i class="orange child"></i>名</h3>
            <div class="order_detail">
                 <#list outer as map>
                     <#if map_index != outer?size-1>
                         <div class="form01" id="${map.id}">
                             <div class="left outer_info">
                                 <span class="name"><#if map.name??>${map.name}</#if></span>
                                 <span class="age"><#if map.type??>${map.type}</#if></span>
                                 <span class="sex"><#if map.sex??>${map.sex}</#if></span>
                             </div>
                             <div class="right pay-chk"></div>
                             <div style="clear:both"></div>
                         </div>
                     </#if>
                     <#if map_index == outer?size-1>
                         <div class="form01 last" id="${map.id}">
                             <div class="left outer_info">
                                 <span class="name"><#if map.name??>${map.name}</#if></span>
                                 <span class="age"><#if map.type??>${map.type}</#if></span>
                                 <span class="sex"><#if map.sex??>${map.sex}</#if></span>
                             </div>
                             <div class="right pay-chk"></div>
                             <div style="clear:both"></div>
                         </div>
                     </#if>
                 </#list>
            </div>
    </div>


        <div class="add_submit">
            <span class="submit">完成</span>
        </div>
    </section>

</article>

<script>
    $(function(){
        tq.t.back();
        var id = tq.t.getQueryString("id"); //此id为product_id;
        var adult = sessionStorage.getItem("adultSum");
        var child = sessionStorage.getItem("childSum");
        var url = location.href;

        $(".adult").html(adult);
        $(".child").html(child);

        $(".form01").on("click", function() {
            var outer_id = $(this).attr("id");
            location.href = "edit_outer.html?id=" + outer_id + "&url="+url+"";
        });

        $(".form01 .pay-chk").on("click", function() {
            event.stopPropagation(); //阻止继承父元素的事件
            var index = $(".form01 .pay-chk").index(this);
            $(this).toggleClass("pay_checked");
            $($(".form01")[index]).toggleClass("choose_chk");
        });

        $(".add").on("click", function() { // 传入product_id
            location.href = "addOuter.html?url="+url+"";
        });
        //选择
        $(".add_submit").on("click", function() {
            if (!$(".pay-chk").hasClass("pay_checked")) {
                tq.t.alert("请选择出行人");
                tq.t.cancel();
            } else {
                var participant_idArr = [];
                var childSum = 0;
                var adultSum = 0;
                for (var i = 0; i < $(".form01.choose_chk").length; i++) {
                    var cindex = $(".form01").index($(".form01.choose_chk")[i]);
                    <#list outer as map>
                    var index = ${map_index};
                    if(index == cindex){
                        <#if map.type == "儿童">
                            childSum++;
                        <#else>
                            adultSum++;
                        </#if>
                        participant_idArr.push(${map.id});
                    }
                    </#list>
                } //for_end

                if (childSum == child && adultSum == adult) {
                    location.href = "orderDetail.html?id=" + id + "";
                } else {
                    tq.t.alert("所选出行人数目不匹配");
                    tq.t.cancel();
                }
                participant_idArr = participant_idArr.join(",");
                sessionStorage.setItem("participant_idArr", participant_idArr);
               console.log(participant_idArr);
            } //else_end

        }); //选择完成end
    })
</script>
</#list>
</body>
</html>
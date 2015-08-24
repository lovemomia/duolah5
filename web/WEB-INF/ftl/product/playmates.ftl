<@override name="title">玩伴信息</@override>

<@override name="body">
<article id="page">

    <header class="head">
        <span class="back"><img src="image/back2.png"></span>玩伴信息<span class="collect"></span>
    </header>

    <section>
        <#list playmates as list>
            <#list list as map>
        <div class="pMain"></div>
         <div class="part_calender">
            <div class="cTitle">
                <img src="image/cd2.png" alt="">
                <div class="pDate">
                    <p class="date"><#if map.time??>${map.time}</#if></p>
                    <p class="pNum"><#if map.joined??>${map.joined}</#if></p>
                </div>
            </div>

             <#list map.playmates as playmate>
            <div class="pInfo none">
                <div class="pDetail">
                    <#if playmate.avatar !="">
                    <img src="${playmate.avatar}" alt="">
                    <#else >
                    <img src="image/default.png" alt="">
                    </#if>
                    <div class="pMaMa">
                        <p class="MaMa"><#if playmate.nickName??>${playmate.nickName}</#if></p>

                            <#if (playmate.children?size>=2)>
                                <p class="pAge">${playmate.children[0]}，${playmate.children[1]}</p>
                            <#elseif (playmate.children?size = 0)>
                                <p class="pAge"></p>
                            </#if>
                    </div>
                </div>
            </div>
            </#list>
        </div>
            </#list>
        </#list>
    </section>

</article>
<script>
    $(function(){
        tq.t.back();
        $('.cTitle').on('click',function(event){
            $(this).siblings(".pInfo").toggleClass("none");
            $(this).toggleClass("downforward");
        });
     //   tq.home.partner();
    })
</script>
</@override>

<@extends name="../base.ftl"/>

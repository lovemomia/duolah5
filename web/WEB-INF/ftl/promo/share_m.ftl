<@override name="header">
<header class="head">
    <span class="back"><img src="/image/back2.png"></span>约伴返红包<span class="collect"></span>
</header>
</@override>

<@override name="home">/</@override>

<@override name="setlink">
    function setlink() {
        $(".share_btn").each(function() {
            $(this).attr("href", "/actsDetail.html?id=" + $(this).attr("id"));
        });
    };
</@override>

<@extends name="share.ftl"/>


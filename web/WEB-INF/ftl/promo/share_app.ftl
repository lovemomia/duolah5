<@override name="home">duola://productcalendar</@override>

<@override name="setlink">
    function setlink() {
        $(".share_btn").each(function() {
            $(this).attr("href", "duola://productdetail?id=" + $(this).attr("id"));
        });
    };
</@override>

<@extends name="share.ftl"/>

<@override name="title">我的收藏</@override>

<@override name="body">
<article id="page">
    <header class="head">
        <span class="back"><img src="image/back2.png"></span>我的收藏<span class="collect"></span>
    </header>

    <section>

    </section>
</article>

<script type="text/javascript">
    function more() {
        if (sessionStorage.getItem("favoriteNextIndex") != null) tq.home.getCollect(sessionStorage.getItem("favoriteNextIndex"));
    }

    $(function(){
        tq.t.back();
        tq.home.getCollect(0);
    })
</script>
</@override>

<@extends name="../base.ftl"/>

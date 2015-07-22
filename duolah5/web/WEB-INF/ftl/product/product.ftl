<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="application/json; charset=UTF-8">
    <title>Insert title here</title>
</head>
<body>
<#list product as map>
    <#list map?keys as itemKey>
        <#if itemKey = "cover">
        cover:${map[itemKey]}
        </#if>
        <#if itemKey = "id">
        id:${map[itemKey]}
        </#if>
        <#if itemKey = "title">
        title:${map[itemKey]}
        </#if>
        <#if itemKey = "thumb">
        thumb:${map[itemKey]}
        </#if>
        <#if itemKey = "abstracts">
        abstracts:${map[itemKey]}
        </#if>
        <#if itemKey = "address">
        address:${map[itemKey]}
        </#if>
        <#if itemKey = "content">
        <#if map[itemKey]??>
            <#list map[itemKey] as content>
                <#list content?keys as contentKey>
                    <#if contentKey = "title">
                    title:${content[contentKey]}
                    </#if>
                    <#if contentKey = "style">
                    style:${content[contentKey]}
                    </#if>
                    <#if contentKey = "body">
                        <#list content[contentKey] as body>
                            <#list body?keys as bodyKey>
                                <#if bodyKey = "text">
                                text:${body[bodyKey]}
                                </#if>
                                <#if bodyKey = "img">
                                img:${body[bodyKey]}
                                </#if>
                            </#list>
                        </#list>
                    </#if>
                </#list>
             </#list>
        </#if>
        </#if>
        <#if itemKey = "jioned">
        jioned:${map[itemKey]}
        </#if>
        <#if itemKey = "imgs">
        <#if map[itemKey]??>
        imgs:
            <#list map[itemKey] as img>
            ${img}
            </#list>
        </#if>
        </#if>
        <#if itemKey = "poi">
        poi:${map[itemKey]}
        </#if>
        <#if itemKey = "price">
        price:${map[itemKey]}
        </#if>
        <#if itemKey = "scheduler">
        scheduler:${map[itemKey]}
        </#if>
        <#if itemKey = "soldOut">
        soldOut:${map[itemKey]?string('true','false')}
        </#if>
        <#if itemKey = "startTime">
        startTime:${(map[itemKey]?string("yyyy-MM-dd"))!}
        </#if>
        <#if itemKey = "endTime">
        endTime:${(map[itemKey]?string("yyyy-MM-dd"))!}
        </#if>
    </#list>
<br>
</#list>
</body>
</html>

<#--
    <#if itemKey = "product">
        <#list map[itemKey] as child>
            <#list child?keys as childKey>
                <#if childKey = "id">
                id:${child[childKey]}
                </#if>
                <#if childKey = "title">
                title:${child[childKey]}
                </#if>
                <#if childKey = "thumb">
                thumb:${child[childKey]}
                </#if>
                <#if childKey = "url">
                url:${child[childKey]}
                </#if>
                <#if childKey = "abstracts">
                abstracts:${child[childKey]}
                </#if>
                <#if childKey = "address">
                address:${child[childKey]}
                </#if>
                <#if childKey = "content">
                <#list child[childKey] as content>
                <#list content?keys as contentKey>
                    <#if contentKey = "title">
                    title:${content[contentKey]}
                    </#if>
                    <#if contentKey = "style">
                    style:${content[contentKey]}
                    </#if>
                    <#if contentKey = "body">
                   <#list content[contentKey] as body>
                       <#list body?keys as bodyKey>
                           <#if bodyKey = "text">
                               text:${body[bodyKey]}
                           </#if>
                           <#if bodyKey = "img">
                           img:${body[bodyKey]}
                           </#if>
                       </#list>
                   </#list>
                    </#if>
                </#list>
                </#list>
                </#if>
                <#if childKey = "jioned">
                jioned:${child[childKey]}
                </#if>
                <#if childKey = "imgs">
                imgs:
                <#list child[childKey] as img>
                    ${img}
                </#list>
                </#if>
                <#if childKey = "poi">
                poi:${child[childKey]}
                </#if>
                <#if childKey = "price">
                price:${child[childKey]}
                </#if>
                <#if childKey = "scheduler">
                scheduler:${child[childKey]}
                </#if>
                <#if childKey = "soldOut">
                soldOut:${child[childKey]?string('true','false')}
                </#if>
                <#if childKey = "startTime">
                startTime:${(child[childKey]?string("yyyy-MM-dd"))!}
                </#if>
                <#if childKey = "endTime">
                endTime:${(child[childKey]?string("yyyy-MM-dd"))!}
                </#if>
            </#list>
        </#list>
    </#if>-->
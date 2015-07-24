<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="application/json; charset=UTF-8">
    <title>Insert title here</title>
</head>
<body>
<#list playmates as playmatesMap>
    <#list playmatesMap?keys as key>
    <#if key  = "errno">
        errno:${playmatesMap[key]}
    </#if>
        <#if key  = "errmsg">
        errmsg:${playmatesMap[key]}
        </#if>
    <#if key = "data">
    <#list playmatesMap[key] as playmate>
    <#list playmate?keys as playmatesKey>
        <#if playmatesKey = "time">
        time:${playmate[playmatesKey]}
        </#if>
        <#if playmatesKey = "joined">
        joined:${playmate[playmatesKey]}
        </#if>
        <#if playmatesKey = "playmates">
        playmates:
        <#list playmate[playmatesKey] as playmateDate>
            <#list playmateDate?keys as key>
                <#if key = "id">
                id:${playmateDate[key]}
                </#if>
                <#if key = "nickName">
                nickName:${playmateDate[key]}
                </#if>
                <#if key = "avatar">
                avatar:${playmateDate[key]}
                </#if>
                <#if key = "children">
                children:
                    <#list playmateDate[key] as children>
                    ${children}
                    </#list>
                </#if>
            </#list>
        <br>
        </#list>
        </#if>
    </#list>
    </#list>
    </#if>

    </#list>
</#list>
</body>
</html>
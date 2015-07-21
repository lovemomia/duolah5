<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="application/json; charset=UTF-8">
    <title>Insert title here</title>
</head>
<body>
<#list playmates as playmatesMap>
    <#list playmatesMap?keys as playmatesKey>
        <#if playmatesKey = "time">
        time:${playmatesMap[playmatesKey]}
        </#if>
        <#if playmatesKey = "joined">
        joined:${playmatesMap[playmatesKey]}
        </#if>
        <#if playmatesKey = "playmates">
        playmates:
        <#list playmatesMap[playmatesKey] as playmate>
            <#list playmate?keys as key>
                <#if key = "id">
                id:${playmate[key]}
                </#if>
                <#if key = "nickName">
                nickName:${playmate[key]}
                </#if>
                <#if key = "avatar">
                avatar:${playmate[key]}
                </#if>
                <#if key = "children">
                children:
                    <#list playmate[key] as children>
                    ${children}
                    </#list>
                </#if>
            </#list>
        <br>
        </#list>
        </#if>

    </#list>
<br>
</#list>
</body>
</html>
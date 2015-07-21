<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Insert title here</title>
</head>
<body>
<#list participant as map>
    <#list map? keys as key>
        <#if key="id">
        id:${map[key]}
        </#if>
        <#if key="idNo">
            <#if map[key]??>
            idNo:${map[key]}
            </#if>
        </#if>
        <#if key="idType">
            <#if map[key]??>
            idType:${map[key]}
            </#if>
        </#if>
        <#if key="name">
        name:${map[key]}
        </#if>
        <#if key="sex">
        sex:${map[key]}
        </#if>
        <#if key="type">
            <#if map[key]??>
            type:${map[key]}
            </#if>
        </#if>
        <#if key="birthday">
        birthday:${(map[key]?string("yyyy-MM-dd"))!}
        </#if>
    </#list>

</#list>
</body>
</html>
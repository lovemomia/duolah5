<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>child</title>
</head>
<body>
<#list participant as map>
    <#list map? keys as key>
    <#if key = "errno">
        errno:${map[key]}
    </#if>
        <#if key = "errmsg">
        errmsg:${map[key]}
        </#if>
    <#if key = "data">
    <#list map[key]?keys as itemKey>
        <#if itemKey="id">
        id:${map[key][itemKey]}
        </#if>
        <#if itemKey="idNo">
            <#if map[key][itemKey]??>
            idNo:${map[key][itemKey]}
            </#if>
        </#if>
        <#if itemKey="idType">
            <#if map[key][itemKey]??>
            idType:${map[key][itemKey]}
            </#if>
        </#if>
        <#if itemKey="name">
        name:${map[key][itemKey]}
        </#if>
        <#if itemKey="sex">
        sex:${map[key][itemKey]}
        </#if>
        <#if itemKey="type">
            <#if map[key][itemKey]??>
            type:${map[key][itemKey]}
            </#if>
        </#if>
        <#if itemKey="birthday">
        birthday:${(map[key][itemKey]?string("yyyy-MM-dd"))!}
        </#if>
    </#list>
    </#if>
    </#list>

</#list>
</body>
</html>
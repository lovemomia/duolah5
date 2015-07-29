<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="application/json; charset=UTF-8">
    <title>products</title>
</head>
<body>
<#list products as map>
    <#list map?keys as itemKey>
        <#if itemKey = "totalCount">
            <#if map[itemKey]??>
        totalCount:${map[itemKey]}
            </#if>
        </#if>
        <#if itemKey = "nextIndex">
            <#if map[itemKey]??>
            nextIndex:${map[itemKey]}
            </#if>
        </#if>
        <#if itemKey = "list">
        <#list map[itemKey] as products>
        <#assign baseProduct = products>
        <#include "baseProduct.ftl"> <br>
        </#list>

        </#if>
    </#list>
</#list>
</body>
</html>

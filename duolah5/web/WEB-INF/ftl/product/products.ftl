<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="application/json; charset=UTF-8">
    <title>Insert title here</title>
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
        <#if itemKey = "products">
        <#assign product = map[itemKey]>
        <#include "product.ftl">
        </#if>
    </#list>
</#list>
</body>
</html>
<#--
<#if itemKey = "products">
<#list map[itemKey] as product>
<#list product?keys as productKey>
<#if productKey = "id">
id:${product[productKey]}
</#if>
<#if productKey = "cover">
cover:${product[productKey]}
</#if>
<#if productKey = "thumb">
thumb:${product[productKey]}
</#if>
<#if productKey = "title">
title:${product[productKey]}
</#if>
<#if productKey = "abstracts">
abstracts:${product[productKey]}
</#if>
<#if productKey = "joined">
joined:${product[productKey]}
</#if>
<#if productKey = "sales">
sales:${product[productKey]}
</#if>
<#if productKey = "soldOut">
soldOut:${product[productKey]?string('true','false')}
</#if>
<#if productKey = "price">
price:${product[productKey]}
</#if>
<#if productKey = "crowd">
crowd:${product[productKey]}
</#if>
<#if productKey = "scheduler">
scheduler:${product[productKey]}
</#if>
<#if productKey = "address">
address:${product[productKey]}
</#if>
<#if productKey = "poi">
poi:${product[productKey]}
</#if>
<#if productKey = "startTime">
startTime:${(product[productKey]?string("yyyy-MM-dd"))!}
</#if>
<#if productKey = "endTime">
endTime:${(product[productKey]?string("yyyy-MM-dd"))!}
</#if>
</#list>-->
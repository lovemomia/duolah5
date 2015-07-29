<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="application/json; charset=UTF-8">
    <title>Insert title here</title>
</head>
<body>
<#list skus as skusmap>
    <#list skusmap?keys as key>
        <#if key = "errno">
        errno:${skusmap[key]}
        </#if>
        <#if key = "errmsg">
        errmsg:${skusmap[key]}
        </#if>
        <#if key = "data">

            <#if skusmap[key].contacts??>
            contacts:
               name:${skusmap[key].contacts.name}
               mobile:${skusmap[key].contacts.mobile}
            </#if>
        skus:
        <#if skusmap[key].skus??>
            <#list skusmap[key].skus as sku>
                <#list sku?keys as skuKey>
                    <#if skuKey = "productId">
                    productId: ${sku[skuKey]}
                    </#if>
                    <#if skuKey = "skuId">
                    skuId: ${sku[skuKey]}
                    </#if>
                    <#if skuKey = "stock">
                    stock: ${sku[skuKey]}
                    </#if>
                    <#if skuKey = "time">
                    time: ${sku[skuKey]}
                    </#if>
                    <#if skuKey = "type">
                    type: ${sku[skuKey]}
                    </#if>
                    <#if skuKey = "desc">
                    desc: ${sku[skuKey]}
                    </#if>
                    <#if skuKey = "limit">
                    limit: ${sku[skuKey]}
                    </#if>
                    <#if skuKey = "minPrice">
                    minPrice: ${sku[skuKey]}
                    </#if>
                    <#if skuKey = "needRealName">
                    needRealName: ${sku[skuKey]?string('true','false')}
                    </#if>
                    <#if skuKey = "prices">
                    prices:
                        <#list sku[skuKey] as prices>
                            <#list prices?keys as priceKey>
                                <#if priceKey = "adult">
                                adult:${prices[priceKey]}
                                </#if>
                                <#if priceKey = "child">
                                child:${prices[priceKey]}
                                </#if>
                                <#if priceKey = "desc">
                                desc:${prices[priceKey]}
                                </#if>
                                <#if priceKey = "unit">
                                unit:${prices[priceKey]}
                                </#if>
                            </#list>

                        </#list>
                    </#if>
                </#list>

            </#list>
        </#if>
        </#if>
    </#list>
</#list>
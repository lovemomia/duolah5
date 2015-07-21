<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="application/json; charset=UTF-8">
    <title>Insert title here</title>
</head>
<body>
<#list list as map>
    <#list map?keys as itemKey>
        <#if itemKey = "nextIndex">
        nextIndex:${map[itemKey]}
        </#if>
        <#if itemKey = "totalCount">
        totalCount:${map[itemKey]}
        </#if>
        <#if itemKey = "listOrder">
            <#list map[itemKey] as order>
                <#list order?keys as orderKey>
                    <#if orderKey = "time">
                    time:${order[orderKey]}
                    </#if>
                    <#if orderKey = "cover">
                    cover:${order[orderKey]}
                    </#if>
                    <#if orderKey = "title">
                    title:${order[orderKey]}
                    </#if>
                    <#if orderKey = "id">
                    id:${order[orderKey]}
                    </#if>
                    <#if orderKey = "productId">
                    productId:${order[orderKey]}
                    </#if>
                    <#if orderKey = "skuId">
                    skuId:${order[orderKey]}
                    </#if>
                    <#if orderKey = "participants">
                    participants:${order[orderKey]}
                    </#if>
                    <#if orderKey = "totalFee">
                    totalFee:${order[orderKey]}
                    </#if>
                </#list>
            <br>
            </#list>
        </#if>

    </#list>
</#list>
</body>
</html>
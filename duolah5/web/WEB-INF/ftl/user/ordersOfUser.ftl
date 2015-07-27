<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="application/json; charset=UTF-8">
    <title>Insert title here</title>
</head>
<body>
<#list  list as map>
    <#list map?keys as key>
        <#if key = "errno">
        errrno:${map[key]}
        </#if>
        <#if key = "errmsg">
        errmsg:${map[key]}
        </#if>
        <#if key = "data">
        <#if map[key] ??>
                <#list map[key]?keys as dataKey>
                    <#if dataKey = "totalCount">
                    totalCount:${map[key].totalCount}
                    </#if>
                    <#if dataKey = "nextIndex">
                    nextIndex:<#if map[key].nextIndex??> ${map[key].nextIndex}</#if>
                    </#if>
                <#if dataKey = "list">
                    <#list map[key].list as order>
                        <#list order?keys as orderKey>
                            <#if orderKey = "id">
                              id:${order.id}
                            </#if>
                            <#if orderKey = "title">
                            id:${order.title}
                            </#if>
                            <#if orderKey = "cover">
                            cover:${order.cover}
                            </#if>
                            <#if orderKey = "count">
                            count:${order.count}
                            </#if>
                            <#if orderKey = "participants">
                            participants:${order.participants}
                            </#if>
                            <#if orderKey = "productId">
                            productId:${order.productId}
                            </#if>


                        </#list>
                    </#list>
                </#if>
                </#list>
        </#if>
        </#if>
    </#list>
</#list>
</body>
</html>
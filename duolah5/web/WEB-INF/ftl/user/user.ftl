<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Insert title here</title>
</head>
<body>
<#list list as map>
    <#list map?keys as itemKey>
        <#if itemKey = "avatar">
        avater:${map[itemKey]}
        </#if>
        <#if itemKey = "nickName">
        nickName:${map[itemKey]}
        </#if>
        <#if itemKey = "mobile">
        mobile:${map[itemKey]}
        </#if>
        <#if itemKey = "sex">
        sex:${map[itemKey]}
        </#if>
        <#if itemKey = "city">
        city:${map[itemKey]}
        </#if>
        <#if itemKey = "children">
        <#list map[itemKey] as child>
            <#list child?keys as childKey>
                <#if childKey = "id">
                id:${child[childKey]}
                </#if>
                <#if childKey = "name">
                name:${child[childKey]}
                </#if>
                <#if childKey = "sex">
                sex:${child[childKey]}
                </#if>
                <#if childKey = "birthday">
                birthday:${(child[childKey]?string("yyyy-MM-dd"))!}
                </#if>
            </#list>
        </#list>
        </#if>
    </#list><br/>
</#list>
</body>
</html>
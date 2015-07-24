<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="application/json; charset=UTF-8">
    <title>Insert title here</title>
</head>
<body>
<#list list as map>
<#list map?keys as key>
<#if key = "errno">
    errno:${map[key]}
</#if>
    <#if key = "errmsg">
    errmsg:${map[key]}
    </#if>
<#if  key = "data">
    <#list map[key] as child>
        <#list child?keys as itemKey>
            <#if itemKey = "id">
            id:${child[itemKey]}
            </#if>
            <#if itemKey = "name">
            name:${child[itemKey]}
            </#if>
            <#if itemKey = "sex">
            sex:${child[itemKey]}
            </#if>
            <#if itemKey = "birthday">
            birthday:${(child[itemKey]?string("yyyy-MM-dd"))!}
            </#if>
        </#list>
    </#list>
</#if>
</#list>
<br>
</#list>
</body>
</html>
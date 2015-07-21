<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="application/json; charset=UTF-8">
    <title>Insert title here</title>
</head>
<body>
<#list list as map>
<#list map?keys as itemKey>
    <#if itemKey = "id">
    id:${map[itemKey]}
    </#if>
    <#if itemKey = "name">
    name:${map[itemKey]}
    </#if>
    <#if itemKey = "sex">
    sex:${map[itemKey]}
    </#if>
    <#if itemKey = "birthday">
    birthday:${(map[itemKey]?string("yyyy-MM-dd"))!}
    </#if>
</#list>
<br>
</#list>
</body>
</html>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="application/json; charset=UTF-8">
    <title>product</title>
</head>
<body>
<#list product as map>
    <#list map?keys as key>
    <#if key = "errno">
        errno:${map[key]}
    </#if>
        <#if key = "errmsg">
        errmsg:${map[key]}
        </#if>
    <#if key = "data">
    <#assign baseProduct = map[key]>
    <#include "baseProduct.ftl">
    </#if>
    </#list>
<br>
</#list>
</body>
</html>

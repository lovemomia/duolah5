<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
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
        <#if key = "data">
        <#if map[key]!="">
        avatar:${map[key].avatar}
        mobile:${map[key].mobile}
        address:${map[key].address}
        nickname:${map[key].nickName}
        <#if map[key].children??>
        <#list map[key].children as child>
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
        </#if>
        </#if>

    </#list>
</#list>
</body>
</html>
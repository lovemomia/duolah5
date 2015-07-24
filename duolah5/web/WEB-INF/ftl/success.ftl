<#list  isSuccessful as map>
<#list map?keys as key>
        <#if key = "errmsg">
        errmsg: ${map[key]}
        </#if>
        <#if key = "errno">
        errnno: ${map[key]}
        </#if>
    </#list>
</#list>
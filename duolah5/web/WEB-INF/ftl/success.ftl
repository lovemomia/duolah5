    <#list isSuccessful?keys as key>
        <#if key = "errmsg">
        errmsg: ${isSuccessful[key]}
        </#if>
        <#if key = "errno">
        errnno: ${isSuccessful[key]}
        </#if>
    </#list>

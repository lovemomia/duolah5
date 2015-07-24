<#list home as map>
    <#list  map?keys as homeKey>
        <#if homeKey = "banner">
            <#list map[homeKey] as banner>
                <#list banner?keys as bannerKey>
                    <#if bannerKey = "cover">
                    cover:${banner[bannerKey]}
                    </#if>
                    <#if bannerKey = "action">
                    action:${banner[bannerKey]}
                    </#if>
                </#list>
            </#list>
        </#if>
        <#if homeKey = "products">
            <#list map[homeKey] as products>
                <#assign baseProduct = products>
                <#include "./product/baseProduct.ftl"> <br>
            </#list>
        </#if>
        <#if homeKey = "nextPage">
            <#if map[homeKey]??>
            nextPage:${map[homeKey]}
            </#if>
        </#if>
       <#if homeKey = "errno">
        errno:${map[homeKey]}
       </#if>
    </#list>
</#list>

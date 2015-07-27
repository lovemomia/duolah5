<#list home as map>
    <#list  map?keys as homeKey>
        <#if homeKey = "errno">
        errno:${map[homeKey]}
        </#if>
        <#if homeKey = "errmsg">
        errno:${map[homeKey]}
        </#if>
        <#if homeKey = "data">
            <#if map[homeKey].banner??>
                <#list map[homeKey].banner as banner>
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

            <#if map[homeKey].products??>

                <#list map[homeKey].products as products>
                    <#assign baseProduct = products>
                    <#include "./product/baseProduct.ftl"> <br>
                </#list>

            </#if>
            <#if map[homeKey].nextPage>
            nextPage:${data[dataKey]}
            </#if>
        </#if>
    </#list>
</#list>

<#list baseProduct?keys as itemKey>
        <#if itemKey = "cover">
        cover:${baseProduct[itemKey]}
        </#if>
        <#if itemKey = "id">
        id:${baseProduct[itemKey]}
        </#if>
        <#if itemKey = "title">
        title:${baseProduct[itemKey]}
        </#if>
        <#if itemKey = "thumb">
        thumb:${baseProduct[itemKey]}
        </#if>
        <#if itemKey = "abstracts">
        abstracts:${baseProduct[itemKey]}
        </#if>
        <#if itemKey = "address">
        address:${baseProduct[itemKey]}
        </#if>
        <#if itemKey = "content">
            <#if baseProduct[itemKey]??>
                <#list baseProduct[itemKey] as content>
                    <#list content?keys as contentKey>
                        <#if contentKey = "title">
                        title:${content[contentKey]}
                        </#if>
                        <#if contentKey = "style">
                        style:${content[contentKey]}
                        </#if>
                        <#if contentKey = "body">
                            <#list content[contentKey] as body>
                                <#list body?keys as bodyKey>
                                    <#if bodyKey = "text">
                                    text:${body[bodyKey]}
                                    </#if>
                                    <#if bodyKey = "img">
                                    img:${body[bodyKey]}
                                    </#if>
                                </#list>
                            </#list>
                        </#if>
                    </#list>
                </#list>
            </#if>
        </#if>
        <#if itemKey = "jioned">
        jioned:${baseProduct[itemKey]}
        </#if>
        <#if itemKey = "imgs">
            <#if baseProduct[itemKey]??>
            imgs:
                <#list baseProduct[itemKey] as img>
                ${img}
                </#list>
            </#if>
        </#if>
        <#if itemKey = "poi">
        poi:${baseProduct[itemKey]}
        </#if>
        <#if itemKey = "price">
        price:${baseProduct[itemKey]}
        </#if>
        <#if itemKey = "scheduler">
        scheduler:${baseProduct[itemKey]}
        </#if>
        <#if itemKey = "soldOut">
        soldOut:${baseProduct[itemKey]?string('true','false')}
        </#if>
        <#if itemKey = "startTime">
        startTime:${(baseProduct[itemKey]?string("yyyy-MM-dd"))!}
        </#if>
        <#if itemKey = "endTime">
        endTime:${(baseProduct[itemKey]?string("yyyy-MM-dd"))!}
        </#if>
    </#list>
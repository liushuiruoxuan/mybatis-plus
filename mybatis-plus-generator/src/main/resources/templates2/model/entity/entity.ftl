package ${package.Entity};

import lombok.Data;

<#assign a = 0>
<#assign b = 0>
<#assign c = 0>
<#list table.fields as field>
    <#if field.propertyType = "LocalDateTime" && a = 0>
        import java.time.LocalDateTime;
        <#assign a = a + 1>
    </#if>
    <#if field.propertyType = "LocalDate" && b = 0>
        import java.time.LocalDate;
        <#assign b = b + 1>
    </#if>
    <#if field.propertyType = "BigDecimal" && c = 0>
        import java.math.BigDecimal;
        <#assign c = c + 1>
    </#if>
</#list>

/**
* ${table.comment!}
*
* @author ${author}
* @since ${date}
*/
@Data
public class ${entity} {

<#-- ----------  BEGIN 字段循环遍历  ---------->
<#list table.fields as field>
    /**
    * ${field.comment}
    */
    private ${field.propertyType} ${field.propertyName};
</#list>
<#------------  END 字段循环遍历  ---------->
}

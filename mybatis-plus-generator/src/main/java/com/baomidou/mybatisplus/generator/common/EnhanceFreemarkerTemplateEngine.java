package com.baomidou.mybatisplus.generator.common;

import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.builder.CustomFile;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * 代码生成器支持自定义[DTO\VO等]模版
 * @author hongxuanchai
 */
public class EnhanceFreemarkerTemplateEngine extends FreemarkerTemplateEngine {

    @Override
    protected void outputCustomFile(List<CustomFile> customFile, TableInfo tableInfo, Map<String, Object> objectMap) {
        String entityName = tableInfo.getEntityName();
        String otherPath = this.getPathInfo(OutputFile.valueOf("other"));
        customFile.forEach(e-> {
            String fileName = String.format(otherPath + File.separator + entityName + "%s", e.getFileName());
            this.outputFile(new File(fileName), objectMap, e.getTemplatePath(),true);
        });
    }
}

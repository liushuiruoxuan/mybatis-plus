package com.baomidou.mybatisplus.generator.main;

import cn.hutool.core.io.IoUtil;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.generator.mbgplus.common.EnhanceFreemarkerTemplateEngine;
import jdk.jpackage.internal.IOUtils;
import sun.nio.ch.IOUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.sql.Types;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 代码生成
 * 注意： 请先修改resources目录下的generator.properties文件中的配置，无误则执行当前类下的main方法
 *
 * @author gzc
 * @since 2022/10/1 11:09
 **/
public class NewGenRun {

    /**
     * 代码生成入口
     * 注意： 请先修改resources目录下的generator.properties文件中的配置
     */
    public static void main(String[] args) {
        doGenerator();
        getPath();
    }

    private static Properties properties = new Properties();

    static {
        // 读取resources目录下的配置文件
        InputStream inputStream = NewGenRun.class.getClassLoader().getResourceAsStream("generator.properties");
        try {
            properties.load(new InputStreamReader(inputStream, Charset.defaultCharset()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 代码生成实现
     * 提示：如果不需要生成controller相关代码，设置packageConfig中的controller为""，设置templateConfig中的controller为null
     */
    private static void doGenerator() {
        // 建立数据库连接
        String url = properties.getProperty("database.url");
        String username = properties.getProperty("database.username");
        String password = properties.getProperty("database.password");

        // 模块名称
        String moduleName = properties.getProperty("moduleName");
        // 项目目录
        String projectPath = properties.getProperty("projectPath");
        String javaPath = projectPath + moduleName + "/src/main/java/";
        String resourcesPath = projectPath + moduleName + "/src/main/resources/";
        // 作者名称
        String author = properties.getProperty("author");
        // 基础包路径
        String packagePath = properties.getProperty("packagePath");
        // 需要生成的表
        String tables = properties.getProperty("tables");
        String tablePrefix = properties.getProperty("tablePrefix");
        // 代码生成后是否打开磁盘目录
        System.out.println(packagePath);
        System.out.println(moduleName);
        // 全局配置
        FastAutoGenerator.create(url, username, password)
                .globalConfig(builder -> {
                    builder.author(author) // 设置作者
                            .enableSwagger() // 开启 swagger 模式
                            .outputDir(javaPath) // 指定输出目录
                            .disableOpenDir()
                    ;
                })
                .dataSourceConfig(builder ->
                        builder.typeConvertHandler((globalConfig, typeRegistry, metaInfo) -> {
                            int typeCode = metaInfo.getJdbcType().TYPE_CODE;
                            if (typeCode == Types.SMALLINT) {
                                // 自定义类型转换
                                return DbColumnType.INTEGER;
                            }
                            return typeRegistry.getColumnType(metaInfo);
                        })
                )
                .packageConfig(builder -> {
                    Map<OutputFile, String> outputFileStringMap = new HashMap<>();
                    // 设置mapperXml生成路径
                    outputFileStringMap.put(OutputFile.xml, resourcesPath + "/mapper");
                    // 设置dto生成路径
                    outputFileStringMap.put(OutputFile.other, javaPath +"com/generator/mbgplus/model/dto");
                    builder.parent(packagePath) // 设置父包名
                            .moduleName(moduleName) // 设置父包模块名
                            .controller("controller.request") // 设置controller包名
                            .entity("model.entity") // 设置实体类包名
                            .pathInfo(outputFileStringMap)
                    ;

                })
                .strategyConfig(builder -> {
                            String entity = "/templates/model/entity/entity";
                            String mapper = "/templates/mapper/mapper";
                            String mapperXml = "/templates/mapper/mapperxml";
                            String service = "/templates/service/service";
                            String serviceImpl = "/templates/service/serviceImpl/serviceimpl";
                            String controller = "/templates/controller/controller";
                            builder.addInclude(tables) // 设置需要生成的表名
                                    .addTablePrefix(tablePrefix) // 设置过滤表前缀
                                    .entityBuilder().javaTemplate(entity).enableLombok()
                                    .mapperBuilder().mapperTemplate(mapper).mapperXmlTemplate(mapperXml)
                                    .serviceBuilder().serviceTemplate(service).serviceImplTemplate(serviceImpl)
                                    .controllerBuilder().template(controller).enableFileOverride();

                        }

                ).injectionConfig(builder -> {
                    Map<String, String> customFile = new HashMap<>();
                    // DTO
                    customFile.put("DTO.java", "/templates/model/dto/dto.ftl");
                    customFile.put("Request.java", "/templates/controller/request/request.ftl");
                    builder.customFile(customFile);
                    System.out.println();
                })
                .templateEngine(new EnhanceFreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }


    /**
     * 获取当前项目本地磁盘目录
     */
    private static void getPath() {
        System.out.println("当前项目本地磁盘目录->" + System.getProperty("user.dir"));
    }

}


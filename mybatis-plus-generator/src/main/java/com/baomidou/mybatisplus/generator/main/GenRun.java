package com.baomidou.mybatisplus.generator.main;

import cn.hutool.core.io.IoUtil;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
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
public class GenRun {

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
        InputStream inputStream = GenRun.class.getClassLoader().getResourceAsStream("generator.properties");
        try {
            properties.load(IoUtil.getReader(inputStream, Charset.defaultCharset()));
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
        DataSourceConfig dsc = new DataSourceConfig.Builder(url, username, password).build();

        // 代码生成器
        AutoGenerator mpg = new AutoGenerator(dsc);
        // 模块名称
        String moduleName = properties.getProperty("moduleName");
        // 项目目录
        String projectPath = properties.getProperty("projectPath");
        // 作者名称
        String author = properties.getProperty("author");
        // 基础包路径
        String packagePath = properties.getProperty("packagePath");
        // 需要生成的表
        String tables = properties.getProperty("tables");
        // 代码生成后是否打开磁盘目录
        System.out.println(packagePath);
        System.out.println(moduleName);
        // 全局配置
        GlobalConfig globalConfig = new GlobalConfig
                .Builder()
                .outputDir(projectPath + "/" + moduleName + "/src/main/java")
                .author(author)
                .disableOpenDir()
                .commentDate("yyyy-MM-dd HH:mm:ss")
                .build();

        String templateXmlPath =  "/src/main/resources/mapper";
        String templateResponsePath = "/src/main/java/controller/response";
        String templateRequestPath = "/src/main/java/controller/request";
        String templateDtoPath = "/src/main/java/entity/dto";
        String templateTransformPath = "/src/main/java/controller/transform";
        String controllerPath = "/src/main/java/controller";
        String entityPath = "/src/main/java/entity/po";
        String servicePath = "/src/main/java/service";
        String serviceImplPath = "/src/main/java/service/impl";
        String mapperPath = "/src/main/java/mapper";
        Map<OutputFile,String> pathInfo = new HashMap<>();
        pathInfo.put(OutputFile.xml,projectPath + "/" + moduleName + templateXmlPath);
        pathInfo.put(OutputFile.controller,projectPath + "/" + moduleName + controllerPath);
        pathInfo.put(OutputFile.entity,projectPath + "/" + moduleName + entityPath);
        pathInfo.put(OutputFile.service,projectPath + "/" + moduleName + servicePath);
        pathInfo.put(OutputFile.serviceImpl,projectPath + "/" + moduleName + serviceImplPath);
        pathInfo.put(OutputFile.mapper,projectPath + "/" + moduleName + mapperPath);
        pathInfo.put(OutputFile.other,projectPath + "/" + moduleName + templateResponsePath);
        pathInfo.put(OutputFile.other,projectPath + "/" + moduleName + templateDtoPath);
        pathInfo.put(OutputFile.other,projectPath + "/" + moduleName + templateRequestPath);
        pathInfo.put(OutputFile.other,projectPath + "/" + moduleName + templateTransformPath);
        // 包配置
        PackageConfig packageConfig = new PackageConfig
                .Builder()
                .parent(packagePath)
                .moduleName(moduleName)
                .pathInfo(pathInfo)
                .build();


        // 配置模板
        String absolutePath = File.separator + "templates";
        String mapperTempPath = absolutePath + File.separator + "/mapper/mapper";
        String mapperTempXmlPath = absolutePath + File.separator + "/mapper/mapperxml";
        String entityTempPath = absolutePath + File.separator + "/entity/entity";
        String serviceTempPath = absolutePath + File.separator + "/service/service";
        String serviceImplTempPath = absolutePath + File.separator + "/service/serviceimpl/serviceimpl";
        String controllerTempPath = absolutePath + File.separator + "/controller/controller";
        String dtoTempPath = absolutePath + File.separator + "/service/dto/dto";
        String requestTempPath = absolutePath + File.separator + "/controller/request";
        String responseTempPath = absolutePath + File.separator + "/controller/response";
        String transformTempPath = absolutePath + File.separator + "/controller/transform";
        System.out.println(absolutePath);
        System.out.println(File.separator);
        TemplateConfig templateConfig = new TemplateConfig
                .Builder()
                .mapper(mapperTempPath)
                .service(serviceTempPath)
                .serviceImpl(serviceImplTempPath)
                .entity(entityTempPath)
                .xml(mapperTempXmlPath)
                .controller(controllerTempPath)
                .entity(responseTempPath)
                .entity(requestTempPath)
                .entity(dtoTempPath)
                .entity(transformTempPath)
                .build();

        // 策略配置
        StrategyConfig strategyConfig = new StrategyConfig
                .Builder()
                .addInclude(tables.split(","))
                .addTablePrefix("t_")
                .entityBuilder()
//                .naming(NamingStrategy.underline_to_camel)
                .enableLombok()
//                .controllerBuilder()
//                .enableRestStyle()
                .build();
        mpg.global(globalConfig);
        mpg.packageInfo(packageConfig);
        mpg.template(templateConfig);
        mpg.strategy(strategyConfig);
        // 开始生成代码文件
        mpg.execute(new FreemarkerTemplateEngine());
    }


    /**
     * 获取当前项目本地磁盘目录
     */
    private static void getPath() {
        System.out.println("当前项目本地磁盘目录->" + System.getProperty("user.dir"));
    }

}


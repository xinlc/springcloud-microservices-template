package com.example.generator.service;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.VelocityTemplateEngine;

import java.util.ResourceBundle;

public class MpGeneratorService {
    public static void main(String[] args) throws InterruptedException {
        String [] tableNames = new String[]{"im_group_info", "im_group_message", "im_group_user", "im_user_friend", "im_user_info", "im_user_message"};
        String location = "/Users/leo/workspace/example/generator";
        generator(location,tableNames);
    }

    private static void generator(String path, String[] tableNames){
        // 配置信息
        final ResourceBundle rb = ResourceBundle.getBundle("mybatis-plus");

        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();
        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        gc.setOutputDir(path);
        gc.setOpen(false);
        gc.setFileOverride(true);
        gc.setBaseResultMap(false);
        gc.setBaseColumnList(false);
        gc.setSwagger2(true);
        gc.setActiveRecord(true);
        gc.setAuthor("Leo");
        gc.setControllerName("%sController");
        gc.setServiceName("%sService");
        gc.setServiceImplName("%sServiceImpl");
        gc.setEntityName("%s");
        gc.setMapperName("%sMapper");
        gc.setXmlName("%sMapper");
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setDbType(DbType.MYSQL);
        dsc.setUrl(rb.getString("url"));
        dsc.setDriverName("com.mysql.jdbc.Driver");
        dsc.setUsername(rb.getString("userName"));
        dsc.setPassword(rb.getString("password"));
        mpg.setDataSource(dsc);

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setParent(rb.getString("parent"));
        pc.setController("school.controller");
        pc.setService("service");
        pc.setServiceImpl("service.impl");
        pc.setEntity("dao.entity");
        pc.setMapper("dao.mapper");
        pc.setXml("dao.mapper.xml");
        mpg.setPackageInfo(pc);

        // 模板配置
        TemplateConfig tc = new TemplateConfig();
        tc.setController("templates/controller.java.BACKUP.vm");
        tc.setEntity("templates/entity.java.vm");
        tc.setMapper("templates/mapper.java.vm");
        tc.setXml("templates/mapper.xml.vm");
        tc.setService("templates/service.java.BACKUP.vm");
        tc.setServiceImpl("templates/serviceImpl.java.BACKUP.vm");
        mpg.setTemplate(tc);

        // 策略配置
        StrategyConfig sc = new StrategyConfig();
        sc.setNaming(NamingStrategy.underline_to_camel);
        sc.setColumnNaming(NamingStrategy.underline_to_camel);
        sc.setEntityLombokModel(true);
        sc.setRestControllerStyle(true);
        /*sc.setControllerMappingHyphenStyle(true);*/
        /*sc.setSuperEntityClass("com.example.dao.entity.BaseEntity");*/
        sc.setInclude(tableNames);
        mpg.setStrategy(sc);
        mpg.setTemplateEngine(new VelocityTemplateEngine());
        mpg.execute();
    }
}

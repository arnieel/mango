package com.tgp.util;

import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.StringWriter;
import java.util.Map;

public class FtlConfig {

    public static String get(String templatePath, Map<String, Object> data) throws Exception {
        Configuration configuration = new Configuration();
        configuration.setClassForTemplateLoading(FtlConfig.class.getClass(), "/");
        Template template = configuration.getTemplate(templatePath);
        StringWriter output = new StringWriter();
        template.process(data, output);

        return output.toString();
    }
}

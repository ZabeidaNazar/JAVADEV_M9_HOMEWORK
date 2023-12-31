package com.homework.java;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import java.io.Writer;

public class PageEngine {
    private static final PageEngine PAGE_ENGINE = new PageEngine();
    private TemplateEngine engine;

    private PageEngine() {
        engine = new TemplateEngine();

        ClassLoaderTemplateResolver resolver = new ClassLoaderTemplateResolver();
        resolver.setPrefix("./templates/");
        resolver.setSuffix(".html");
        resolver.setTemplateMode("HTML5");
        resolver.setOrder(engine.getTemplateResolvers().size());
        resolver.setCacheable(false);
        engine.addTemplateResolver(resolver);
    }

    public static PageEngine getPageEngine() {
        return PAGE_ENGINE;
    }

    public void writeFile(String templateName, Context context, Writer writer) {
        engine.process(templateName, context, writer);
    }

    public String getFile(String templateName, Context context) {
        return engine.process(templateName, context);
    }
}

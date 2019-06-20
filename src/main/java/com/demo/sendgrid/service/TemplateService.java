package com.demo.sendgrid.service;

import java.io.FileNotFoundException;
import java.io.Reader;

import org.springframework.boot.autoconfigure.mustache.MustacheAutoConfiguration;
import org.springframework.boot.autoconfigure.mustache.MustacheResourceTemplateLoader;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import com.demo.sendgrid.dto.EmailRequestDTO;
import com.demo.sendgrid.exception.TemplateException;
import com.samskivert.mustache.MustacheException;

@Service("templateService")
public class TemplateService {

    private MustacheAutoConfiguration mustacheAutoConfiguration;

    public TemplateService(MustacheAutoConfiguration mustacheAutoConfiguration) {
        this.mustacheAutoConfiguration = mustacheAutoConfiguration;
    }

    public @NonNull
    String parseTemplateParams(EmailRequestDTO email) throws TemplateException {
        try {
            MustacheResourceTemplateLoader templateLoader = mustacheAutoConfiguration.mustacheTemplateLoader();
            Reader template = templateLoader.getTemplate(email.getTemplateName());
            return mustacheAutoConfiguration.mustacheCompiler(templateLoader)
                    .compile(template)
                    .execute(email.getTemplateParams());
        } catch (FileNotFoundException e) {
            throw new TemplateException("TEMPLATE_NOT_FOUND");
        } catch (MustacheException e) {
            throw new TemplateException("TEMPLATE_PARSE_ERROR", e);
        } catch (Exception e) {
            throw new TemplateException("GENERIC_ERROR", e);
        }
    }
}

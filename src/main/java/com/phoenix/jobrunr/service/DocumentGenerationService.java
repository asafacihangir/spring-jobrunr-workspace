package com.phoenix.jobrunr.service;

import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.springframework.stereotype.Component;
import org.wickedsource.docxstamper.DocxStamper;
import org.wickedsource.docxstamper.DocxStamperConfiguration;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

@Component
public class DocumentGenerationService {

  public ByteArrayOutputStream generateDocument(Path wordTemplatePath, Path pdfOutputPath, Object context) throws IOException, Docx4JException {
    Files.createDirectories(pdfOutputPath.getParent().toAbsolutePath());

    try(InputStream template = Files.newInputStream(wordTemplatePath); OutputStream out = Files.newOutputStream(pdfOutputPath)) {
      final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
      final DocxStamper stamper = new DocxStamperConfiguration().setFailOnUnresolvedExpression(true).build();
      stamper.stamp(template, context, byteArrayOutputStream);


      return byteArrayOutputStream;
    }

  }

}
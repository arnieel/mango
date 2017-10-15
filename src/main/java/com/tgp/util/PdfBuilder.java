package com.tgp.util;

import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.PageSize;
import com.lowagie.text.html.simpleparser.HTMLWorker;
import com.lowagie.text.pdf.PdfException;
import com.lowagie.text.pdf.PdfWriter;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.List;


public class PdfBuilder {

    public static File exportHtmlToPdf(List<String> htmls, File file) throws PdfException {
        if (htmls == null || htmls.isEmpty()) {
            throw new IllegalArgumentException(INVALID_HTML_MESSAGE);
        }

        ByteArrayOutputStream os = null;
        Document doc;

        try {
            os = new ByteArrayOutputStream();
            doc = new Document(PageSize.LETTER);
            PdfWriter.getInstance(doc, os);
            doc.open();
            for (String html : htmls) {
                if (!html.startsWith(HTML_START)) {
                    html = DOCUMENT_START + html + DOCUMENT_END;
                }
                doc.newPage();
                doc.add(new Chunk(""));
                HTMLWorker hw = new HTMLWorker(doc);
                hw.parse(new StringReader(html));
            }
            doc.close();
            byte[] pdfBytes = os.toByteArray();

            FileUtils.writeByteArrayToFile(file, pdfBytes);

        } catch (DocumentException | IOException e) {
            throw new PdfException(e);
        } finally {
            IOUtils.closeQuietly(os);
        }

        return file;
    }

    private static final String HTML_TAG = "html";

    private static final String HTML_START = "<" + HTML_TAG + ">";

    private static final String DOCUMENT_START = "<html><body>";

    private static final String DOCUMENT_END = "</body></html>";

    private static final String INVALID_HTML_MESSAGE = "HTML is not initialized.";

}

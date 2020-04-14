package com.itheima.test;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfWriter;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @auther 大雄
 * @create 2020-04-14 18:09
 */
public class ItestTest {
    public static void main(String[] args) throws FileNotFoundException, DocumentException {
        Document document = new Document();
        PdfWriter.getInstance(document,new FileOutputStream("E:\\test.pdf"));
        document.open();
        document.add(new Paragraph("hello itext"));
        document.close();
    }
//    @Test
    public void testPrintPdf() throws IOException, DocumentException {
        Document document = new Document();
        PdfWriter.getInstance(document,new FileOutputStream("E:\\test.pdf"));
        document.open();
        BaseFont bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
        document.add(new Paragraph("你好,大雄帅气小伙",new Font(bfChinese)));
        document.close();
    }
}

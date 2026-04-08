package org.trysol.Trysol.finance.service;

import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import org.springframework.stereotype.Service;
import org.trysol.Trysol.finance.dto.InvoiceDto;


import java.io.ByteArrayOutputStream;
import java.net.MalformedURLException;

@Service
public class InvoicePdfService {
    public byte[] generateInvoice(InvoiceDto dto) throws MalformedURLException {


            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            PdfWriter writer = new PdfWriter(baos);
            PdfDocument pdfDoc = new PdfDocument(writer);
            Document document = new Document(pdfDoc);

            // 🔲 OUTER BORDER
            pdfDoc.addEventHandler(PdfDocumentEvent.END_PAGE, event -> {
                //PdfDocumentEvent docEvent = (PdfDocumentEvent) event; // ✅ CAST
                PdfPage page = ((PdfDocumentEvent) event).getPage();
                PdfCanvas canvas = new PdfCanvas(page);
                Rectangle rect = page.getPageSize();
                canvas.rectangle(20, 20, rect.getWidth() - 40, rect.getHeight() - 40);
                canvas.stroke();
            });

            // ================= HEADER =================
            Table header = new Table(1).useAllAvailableWidth();
            header.addCell(new Cell().add(new Paragraph("INVOICE").setBold())
                    .setTextAlignment(TextAlignment.CENTER));
            document.add(header);

            // ================= INVOICE DETAILS =================
            Table top = new Table(new float[]{2, 3, 2, 3}).useAllAvailableWidth();
            top.addCell("Invoice No:");
            top.addCell("INV-001");
            top.addCell("Date:");
            top.addCell("07-04-2026");
            document.add(top);

            // ================= MAIN SECTION =================
            Table main = new Table(new float[]{3, 2}).useAllAvailableWidth();

            // LEFT SIDE (FORM STYLE)
            Table left = new Table(1).useAllAvailableWidth();

            left.addCell("To - Name:");
            left.addCell("ABC Pvt Ltd");
            left.addCell("Address:");
            left.addCell("Hyderabad");
            left.addCell("GSTIN:");
            left.addCell("22AAAAA0000A1Z5");

            left.addCell("From - Name:");
            left.addCell("Trysol Global Services Pvt Ltd");
            left.addCell("Address:");
            left.addCell("Madhapur");
            left.addCell("GSTIN:");
            left.addCell("36AAAAA0000A1Z5");

            // RIGHT SIDE (LOGO)
            ImageData imageData = ImageDataFactory.create("C:/Users/pavan/Downloads/logo.png");
            Image logo = new Image(imageData).scaleToFit(150, 150);

            Cell logoCell = new Cell().add(logo)
                    .setTextAlignment(TextAlignment.CENTER);

            main.addCell(new Cell().add(left));
            main.addCell(logoCell);

            document.add(main);

            // ================= ITEM TABLE =================
            Table items = new Table(new float[]{3, 5, 2}).useAllAvailableWidth();

            // HEADER
            items.addCell(new Cell().add(new Paragraph("Item").setBold()));
            items.addCell(new Cell().add(new Paragraph("Description").setBold()));
            items.addCell(new Cell().add(new Paragraph("Bill Rate").setBold()));

            // ROWS (LIKE FORM)
            items.addCell("Name");
            items.addCell("John");
            items.addCell("5000");

            items.addCell("DOJ");
            items.addCell("01-01-2026");
            items.addCell("");

            items.addCell("Duration of Invoice");
            items.addCell("3 Months");
            items.addCell("");

            items.addCell("Mode Of Hiring");
            items.addCell("Contract");
            items.addCell("");

            items.addCell("JOB Location");
            items.addCell("Hyderabad");
            items.addCell("");

            items.addCell("LUT");
            items.addCell("Yes");
            items.addCell("");

            items.addCell("HSN");
            items.addCell("9983");
            items.addCell("");

            // EMPTY ROWS (IMPORTANT for same look)
            for (int i = 0; i < 3; i++) {
                items.addCell("");
                items.addCell("");
                items.addCell("");
            }

            // GRAND TOTAL ROW
            items.addCell(new Cell(1, 2).add(new Paragraph("")));
            items.addCell(new Cell().add(new Paragraph("Grand Total: 5000")).setBold());

            document.add(items);

            // ================= IN WORDS =================
            document.add(new Paragraph("In Words: 5000 Rupees Only"));

            // ================= BANK DETAILS =================
            Table bank = new Table(new float[]{3, 5}).useAllAvailableWidth();

            bank.addCell("Account Name:");
            bank.addCell("TRYSOL GLOBAL SERVICES PVT LTD");

            bank.addCell("Bank Name:");
            bank.addCell("DBS Bank");

            bank.addCell("A/C No:");
            bank.addCell("8466210000017851");

            bank.addCell("Branch:");
            bank.addCell("Madhapur, Telangana 500081");

            bank.addCell("IFSC Code:");
            bank.addCell("DBSS0IN0466");

            bank.addCell("PAN:");
            bank.addCell("AAECT2610C");

            document.add(bank);

            // ================= TERMS =================
            document.add(new Paragraph("Terms and Conditions:"));
            document.add(new Paragraph("1) Cheque in the name of Trysol Global Services Pvt Ltd"));
            document.add(new Paragraph("2) Official duly signed will be considered"));

            // ================= FOOTER =================
            Table footer = new Table(new float[]{5, 5}).useAllAvailableWidth();
            footer.addCell(new Cell().add(new Paragraph("Payment terms: As per company policy"))
                    .setBorder(Border.NO_BORDER));
            footer.addCell(new Cell().add(new Paragraph("Authorised Signatory"))
                    .setTextAlignment(TextAlignment.RIGHT)
                    .setBorder(Border.NO_BORDER));

            document.add(footer);

            document.close();
            return baos.toByteArray();
        }

//    public byte[] generateInvoice() {
//        return new byte[0];
//
//    }
}

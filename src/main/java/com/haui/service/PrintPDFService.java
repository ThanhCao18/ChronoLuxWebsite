package com.haui.service;

import com.haui.dto.BillDTO;
import com.haui.dto.ProductBillSnapshotDTO;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.borders.DashedBorder;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletContext;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

@Service
public class PrintPDFService {

    @Autowired
    private CurrencyFormat currencyFormat;

    private static final String ROOT_DIR = "/template/font/DejaVuSans.ttf";

    private static final String ICON_DIR = "/template/web/img/Logo.png";

    private final ServletContext servletContext;

    public PrintPDFService(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    public byte[] generateInvoicePdf(BillDTO billDTO) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy - HH:mm");

        PdfWriter writer = new PdfWriter(out);
        PdfDocument pdf = new PdfDocument(writer);
        pdf.setDefaultPageSize(PageSize.A4);
        Document document = new Document(pdf);
        document.setMargins(36, 36, 36, 36);

        PdfFont vietnameseFont = PdfFontFactory.createFont(servletContext.getRealPath(ROOT_DIR), "Identity-H");

        ImageData imageData = ImageDataFactory.create(servletContext.getRealPath(ICON_DIR));
        Image image = new Image(imageData);
        float x = (PageSize.A4.getWidth() - image.getImageWidth()) / 2;
        float y = PageSize.A4.getHeight() - image.getImageHeight() - 90f;
        image.setFixedPosition((int) x, (int) y);
        image.setOpacity(0.3f);
        document.add(image);

        float pageWidth = PageSize.A4.getWidth() - 72;
        float[] headerColWidths = {pageWidth / 2, pageWidth / 2};
        float[] fullWidth = {pageWidth};
        float[] threeColWidths = {300f, 100f, 100f};

        Table headerTable = new Table(headerColWidths);
        headerTable.setWidth(UnitValue.createPercentValue(100));

        Cell titleCell = new Cell()
                .add(new Paragraph("HÓA ĐƠN").setFont(vietnameseFont).setFontSize(20).setBold())
                .setBorder(Border.NO_BORDER);

        Table invoiceDetailsTable = new Table(2).useAllAvailableWidth();
        invoiceDetailsTable.addCell(createHeaderCell("Mã hóa đơn:", vietnameseFont, TextAlignment.RIGHT));
        invoiceDetailsTable.addCell(createHeaderValueCell(String.valueOf(billDTO.getId()), vietnameseFont, TextAlignment.RIGHT));
        invoiceDetailsTable.addCell(createHeaderCell("Ngày tạo:", vietnameseFont, TextAlignment.RIGHT));
        invoiceDetailsTable.addCell(createHeaderValueCell(sdf.format(billDTO.getCreatedDate()), vietnameseFont, TextAlignment.RIGHT));

        Cell detailsCell = new Cell()
                .add(invoiceDetailsTable)
                .setBorder(Border.NO_BORDER)
                .setPadding(0);

        headerTable.addCell(titleCell);
        headerTable.addCell(detailsCell);

        document.add(headerTable);

        Table headerDivider = new Table(fullWidth);
        headerDivider.setBorder(new SolidBorder(ColorConstants.GRAY, 1f));
        document.add(headerDivider);
        document.add(new Paragraph("\n"));

        Table customerInfoTable = new Table(2).useAllAvailableWidth();

        Cell recipientInfoTitle = new Cell(1, 2)
                .add(new Paragraph("Thông tin người nhận").setFont(vietnameseFont).setBold())
                .setBorder(Border.NO_BORDER)
                .setPaddingBottom(5);
        customerInfoTable.addCell(recipientInfoTitle);

        ImageData chronoluxLogo = ImageDataFactory.create(servletContext.getRealPath(ICON_DIR));
        Image chronoluxImage = new Image(chronoluxLogo);
        chronoluxImage.setWidth(100);

        Table logoTable = new Table(1);
        logoTable.addCell(new Cell().add(chronoluxImage).setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.CENTER));

        Table leftInfoTable = new Table(2).useAllAvailableWidth();
        addInfoRow(leftInfoTable, "Tên khách hàng:", billDTO.getDisplayName(), vietnameseFont);
        addInfoRow(leftInfoTable, "Email:", billDTO.getEmail(), vietnameseFont);

        Table rightInfoTable = new Table(2).useAllAvailableWidth();
        addInfoRow(rightInfoTable, "Giới tính:", billDTO.getGender(), vietnameseFont);
        addInfoRow(rightInfoTable, "Số điện thoại:", billDTO.getPhone(), vietnameseFont);

        customerInfoTable.addCell(new Cell().add(leftInfoTable).setBorder(Border.NO_BORDER));
        customerInfoTable.addCell(new Cell().add(rightInfoTable).setBorder(Border.NO_BORDER));

        Table paymentMethodTable = new Table(2).useAllAvailableWidth();
        paymentMethodTable.addCell(new Cell()
                .add(new Paragraph("Phương thức thanh toán:").setFont(vietnameseFont).setBold())
                .setBorder(Border.NO_BORDER)
                .setWidth(155));

        paymentMethodTable.addCell(new Cell()
                .add(new Paragraph(billDTO.getPaymentMethod()).setFont(vietnameseFont))
                .setBorder(Border.NO_BORDER));

        customerInfoTable.addCell(new Cell(1, 2)
                .add(paymentMethodTable)
                .setBorder(Border.NO_BORDER));

        if (billDTO.getStreet() != null || billDTO.getWard() != null ||
                billDTO.getDistrict() != null || billDTO.getCity() != null) {

            String fullAddress = String.format("%s, %s, %s, %s",
                    billDTO.getStreet() != null ? billDTO.getStreet() : "",
                    billDTO.getWard() != null ? billDTO.getWard() : "",
                    billDTO.getDistrict() != null ? billDTO.getDistrict() : "",
                    billDTO.getCity() != null ? billDTO.getCity() : "");

            Table addressTable = new Table(2).useAllAvailableWidth();
            addressTable.addCell(new Cell()
                    .add(new Paragraph("Địa chỉ:").setFont(vietnameseFont).setBold())
                    .setBorder(Border.NO_BORDER)
                    .setWidth(55));

            addressTable.addCell(new Cell()
                    .add(new Paragraph(fullAddress).setFont(vietnameseFont))
                    .setBorder(Border.NO_BORDER));

            customerInfoTable.addCell(new Cell(1, 2)
                    .add(addressTable)
                    .setBorder(Border.NO_BORDER));
        }

        Table noteTable = new Table(2).useAllAvailableWidth();
        noteTable.addCell(new Cell()
                .add(new Paragraph("Ghi chú:").setFont(vietnameseFont).setBold())
                .setBorder(Border.NO_BORDER)
                .setWidth(55));

        if(billDTO.getNote() != null){
            noteTable.addCell(new Cell()
                .add(new Paragraph(billDTO.getNote()).setFont(vietnameseFont))
                .setBorder(Border.NO_BORDER));
        }


        customerInfoTable.addCell(new Cell(1, 2)
                .add(noteTable)
                .setBorder(Border.NO_BORDER));


        document.add(customerInfoTable);

        document.add(new Paragraph("\n"));
        Table infoDivider = new Table(fullWidth);
        infoDivider.setBorder(new DashedBorder(ColorConstants.GRAY, 0.5f));
        document.add(infoDivider);
        document.add(new Paragraph("\n"));

        Paragraph productTitle = new Paragraph("Danh sách sản phẩm")
                .setFont(vietnameseFont)
                .setBold();
        document.add(productTitle);
        document.add(new Paragraph("\n").setFontSize(5));

        float[] productColWidths = {300f, 100f, 100f};
        Table productTable = new Table(productColWidths);
        productTable.setWidth(UnitValue.createPercentValue(100));

        Cell headerName = new Cell()
                .add(new Paragraph("Tên sản phẩm").setFont(vietnameseFont).setBold().setFontColor(ColorConstants.WHITE))
                .setBackgroundColor(ColorConstants.BLACK, 0.7f)
                .setBorder(Border.NO_BORDER)
                .setPadding(5);

        Cell headerQuantity = new Cell()
                .add(new Paragraph("Số lượng").setFont(vietnameseFont).setBold().setFontColor(ColorConstants.WHITE))
                .setTextAlignment(TextAlignment.CENTER)
                .setBackgroundColor(ColorConstants.BLACK, 0.7f)
                .setBorder(Border.NO_BORDER)
                .setPadding(5);

        Cell headerPrice = new Cell()
                .add(new Paragraph("Đơn giá").setFont(vietnameseFont).setBold().setFontColor(ColorConstants.WHITE))
                .setTextAlignment(TextAlignment.RIGHT)
                .setBackgroundColor(ColorConstants.BLACK, 0.7f)
                .setBorder(Border.NO_BORDER)
                .setPadding(5);

        productTable.addHeaderCell(headerName);
        productTable.addHeaderCell(headerQuantity);
        productTable.addHeaderCell(headerPrice);

        List<ProductBillSnapshotDTO> productBillSnapshotDTOS = billDTO.getProductBillSnapshotDTOS();
        for (ProductBillSnapshotDTO product : productBillSnapshotDTOS) {
            Cell cellName = new Cell()
                    .add(new Paragraph(product.getName()).setFont(vietnameseFont))
                    .setBorder(Border.NO_BORDER)
                    .setPaddingLeft(10)
                    .setPaddingTop(5)
                    .setPaddingBottom(5);

            Cell cellQuantity = new Cell()
                    .add(new Paragraph(String.valueOf(product.getQuantity())).setFont(vietnameseFont))
                    .setTextAlignment(TextAlignment.CENTER)
                    .setBorder(Border.NO_BORDER)
                    .setPaddingTop(5)
                    .setPaddingBottom(5);

            Cell cellPrice = new Cell()
                    .add(new Paragraph(currencyFormat.formatCurrency(product.getPrice())).setFont(vietnameseFont))
                    .setTextAlignment(TextAlignment.RIGHT)
                    .setBorder(Border.NO_BORDER)
                    .setPaddingRight(15)
                    .setPaddingTop(5)
                    .setPaddingBottom(5);

            productTable.addCell(cellName);
            productTable.addCell(cellQuantity);
            productTable.addCell(cellPrice);
        }

        document.add(productTable);
        document.add(new Paragraph("\n").setFontSize(5));

        Table dashLine = new Table(fullWidth);
        dashLine.setBorder(new DashedBorder(ColorConstants.GRAY, 0.5f));
        document.add(dashLine);

        Table totalTable = new Table(new float[]{400f, 100f});
        totalTable.setWidth(UnitValue.createPercentValue(100));

        totalTable.addCell(new Cell()
                .add(new Paragraph("Tạm tính").setFont(vietnameseFont).setBold())
                .setTextAlignment(TextAlignment.LEFT)
                .setBorder(Border.NO_BORDER)
                .setPaddingTop(5)
                .setPaddingBottom(5));
        totalTable.addCell(new Cell()
                .add(new Paragraph(currencyFormat.formatCurrency(billDTO.getSubtotal())).setFont(vietnameseFont))
                .setTextAlignment(TextAlignment.RIGHT)
                .setBorder(Border.NO_BORDER)
                .setPaddingRight(15)
                .setPaddingTop(5)
                .setPaddingBottom(5));

        totalTable.addCell(new Cell()
                .add(new Paragraph("Voucher").setFont(vietnameseFont).setBold())
                .setTextAlignment(TextAlignment.LEFT)
                .setBorder(Border.NO_BORDER)
                .setPaddingTop(5)
                .setPaddingBottom(5));
        totalTable.addCell(new Cell()
                .add(new Paragraph(billDTO.getVoucherCode()).setFont(vietnameseFont))
                .setTextAlignment(TextAlignment.RIGHT)
                .setBorder(Border.NO_BORDER)
                .setPaddingRight(15)
                .setPaddingTop(5)
                .setPaddingBottom(5));

        totalTable.addCell(new Cell()
                .add(new Paragraph("Giảm giá").setFont(vietnameseFont).setBold())
                .setTextAlignment(TextAlignment.LEFT)
                .setBorder(Border.NO_BORDER)
                .setPaddingTop(5)
                .setPaddingBottom(5));
        totalTable.addCell(new Cell()
                .add(new Paragraph(currencyFormat.formatCurrency(billDTO.getDiscount() * -1)).setFont(vietnameseFont))
                .setTextAlignment(TextAlignment.RIGHT)
                .setBorder(Border.NO_BORDER)
                .setPaddingRight(15)
                .setPaddingTop(5)
                .setPaddingBottom(5));

        totalTable.addCell(new Cell()
                .add(new Paragraph("Tổng tiền").setFont(vietnameseFont).setBold())
                .setTextAlignment(TextAlignment.LEFT)
                .setBorder(Border.NO_BORDER)
                .setPaddingTop(5)
                .setPaddingBottom(5));
        totalTable.addCell(new Cell()
                .add(new Paragraph(currencyFormat.formatCurrency(billDTO.getTotal())).setFont(vietnameseFont))
                .setTextAlignment(TextAlignment.RIGHT)
                .setBorder(Border.NO_BORDER)
                .setPaddingRight(15)
                .setPaddingTop(5)
                .setPaddingBottom(5));

        document.add(totalTable);

        Table finalBorder = new Table(fullWidth);
        finalBorder.setBorder(new SolidBorder(ColorConstants.GRAY, 1f));
        document.add(finalBorder);

        document.close();
        return out.toByteArray();
    }

    private Cell createHeaderCell(String text, PdfFont font, TextAlignment alignment) {
        return new Cell()
                .add(new Paragraph(text).setFont(font).setBold())
                .setTextAlignment(alignment)
                .setBorder(Border.NO_BORDER)
                .setPadding(2);
    }

    private Cell createHeaderValueCell(String text, PdfFont font, TextAlignment alignment) {
        return new Cell()
                .add(new Paragraph(text).setFont(font))
                .setTextAlignment(alignment)
                .setBorder(Border.NO_BORDER)
                .setPadding(2);
    }

    private void addInfoRow(Table table, String label, String value, PdfFont font) {
        table.addCell(new Cell()
                .add(new Paragraph(label).setFont(font).setBold())
                .setBorder(Border.NO_BORDER)
                .setPadding(2));

        table.addCell(new Cell()
                .add(new Paragraph(value != null ? value : "").setFont(font))
                .setBorder(Border.NO_BORDER)
                .setPadding(2));
    }
}

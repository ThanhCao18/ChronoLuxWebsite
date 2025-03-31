package com.hau.service;

import com.hau.dto.BillDTO;
import com.hau.dto.ProductBillSnapshotDTO;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class ExcelService {
    private static final int START_ROW = 4;
    private static final int LAST_COLUMN = 11;
    private final BillService billService;
    private int lastColumn;

    public ExcelService(BillService billService) {
        this.billService = billService;
    }

    public byte[] exportBillsToExcel() throws IOException {
        try (XSSFWorkbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Thống kê hóa đơn");
            int currentMonth = LocalDate.now().getMonthValue();
            int currentYear = LocalDate.now().getYear();

            createTitle(sheet, workbook, currentMonth, currentYear);
            createHeader(sheet, workbook);
            fillData(sheet, workbook);
            autoSizeColumns(sheet);
            return writeToByteArray(workbook);
        }
    }

    private void createTitle(Sheet sheet, XSSFWorkbook workbook, int month, int year) {
        Row titleRow = sheet.createRow(1);
        Cell titleCell = titleRow.createCell(1);
        titleCell.setCellValue("Thống kê đơn hàng tháng " + month + "/" + year);

        CellStyle titleStyle = workbook.createCellStyle();
        Font titleFont = workbook.createFont();
        titleFont.setBold(true);
        titleFont.setFontHeightInPoints((short) 14);
        titleStyle.setFont(titleFont);
        titleStyle.setAlignment(HorizontalAlignment.CENTER);

        titleCell.setCellStyle(titleStyle);

        // Merge B1 -> N1
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 1, 13));
    }

    private void createHeader(Sheet sheet, XSSFWorkbook workbook) {
        String[] headers = {
                "Mã đơn hàng", "Thời gian đặt", "Khách hàng", "SĐT", "Hình thức thanh toán",
                "Trạng thái thanh toán", "Trạng thái giao hàng", "Tên sản phẩm", "Số lượng", "Đơn giá", "Tổng giá trị đơn hàng"
        };

        Row headerRow = sheet.createRow(3);
        CellStyle headerStyle = createHeaderStyle(workbook);

        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i + 1); // Bắt đầu từ cột B
            cell.setCellValue(headers[i]);
            cell.setCellStyle(headerStyle);
        }
    }

    private void fillData(Sheet sheet, XSSFWorkbook workbook) {
        CellStyle centerStyle = createCenterStyle(workbook);
        //CellStyle currencyStyle = createCurrencyStyle(workbook);
        int rowIdx = START_ROW; // start at row 5 in excel
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy - HH:mm");
        List<BillDTO> bills = billService.getAllBills();
        List<ProductBillSnapshotDTO> productBillSnapshotDTOS = bills.getLast().getProductBillSnapshotDTOS();

        for (BillDTO bill : bills) {
            for (ProductBillSnapshotDTO productBillSnapshotDTO : productBillSnapshotDTOS) {
                Row row = sheet.createRow(rowIdx++);
                createCell(row, 1, bill.getId(), centerStyle);
                createCell(row, 2, sdf.format(bill.getCreatedDate()), centerStyle);
                createCell(row, 3, bill.getDisplayName(), centerStyle);
                createCell(row, 4, bill.getPhone(), centerStyle);
                createCell(row, 5, bill.getPaymentMethod(), centerStyle);
                createCellWithColor(row, 6, bill.getStatus(), workbook);
                createCellWithColor(row, 7, bill.getDeliveryStatus().getDisplayName(), workbook);
                createCell(row, 8, productBillSnapshotDTO.getName(), centerStyle);
                createCell(row, 9, productBillSnapshotDTO.getQuantity(), centerStyle);
                createCell(row, 10, productBillSnapshotDTO.getPrice(), centerStyle);
                createCell(row, 11, bill.getTotal(), centerStyle);
            }
        }
    }

    private void createCell(Row row, int columnIndex, Object value, CellStyle style) {
        Cell cell = row.createCell(columnIndex);
        if (value instanceof Number) {
            cell.setCellValue(((Number) value).doubleValue());
        } else {
            cell.setCellValue(value.toString());
        }
        cell.setCellStyle(style);
    }

    private CellStyle createBorderedStyle(XSSFWorkbook workbook) {
        CellStyle style = workbook.createCellStyle();
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setAlignment(HorizontalAlignment.CENTER);
        return style;
    }


    private void createCellWithColor(Row row, int columnIndex, String value, XSSFWorkbook workbook) {
        Cell cell = row.createCell(columnIndex);
        cell.setCellValue(value);
        cell.setCellStyle(getColorStyle(workbook, value));
    }

    private CellStyle getColorStyle(XSSFWorkbook workbook, String value) {
        CellStyle style = createBorderedStyle(workbook);
        Font font = workbook.createFont();

        switch (value) {
            case "Đang chờ xử lý", "Chưa thanh toán", "Chờ hoàn tiền":
                font.setColor(IndexedColors.GOLD.getIndex());
                break;
            case "Đã hoàn tiền":
                font.setColor(IndexedColors.ROYAL_BLUE.getIndex());
                break;
            case "Đã thanh toán", "Giao hàng thành công":
                font.setColor(IndexedColors.GREEN.getIndex());
                break;
            case "Đã hủy đơn hàng":
                font.setColor(IndexedColors.RED.getIndex());
                break;
            default:
                font.setColor(IndexedColors.BLACK.getIndex());
                break;
        }

        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);
        return style;
    }

/*    private CellStyle createCurrencyStyle(XSSFWorkbook workbook) {
        CellStyle currencyStyle = workbook.createCellStyle();
        DataFormat format = workbook.createDataFormat();
        currencyStyle.setDataFormat(format.getFormat("#,##0 VND"));
        currencyStyle.setAlignment(HorizontalAlignment.CENTER);
        currencyStyle.setVerticalAlignment(VerticalAlignment.CENTER);

        return currencyStyle;
    }*/


    private CellStyle createHeaderStyle(XSSFWorkbook workbook) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        font.setColor(IndexedColors.BLACK.getIndex());
        style.setFont(font);
        style.setFillForegroundColor(IndexedColors.PALE_BLUE.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        return style;
    }

    private CellStyle createCenterStyle(XSSFWorkbook workbook) {
        CellStyle style = createBorderedStyle(workbook);
        style.setAlignment(HorizontalAlignment.CENTER);
        return style;
    }


    private void autoSizeColumns(Sheet sheet) {
        for (int i = 1; i <= LAST_COLUMN; i++) {
            sheet.autoSizeColumn(i);
        }
    }

    private byte[] writeToByteArray(XSSFWorkbook workbook) throws IOException {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            workbook.write(outputStream);
            return outputStream.toByteArray();
        }
    }
}

package org.example.bxbatuz.antifraud.service;

import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.example.bxbatuz.antifraud.entity.LinkedUsers;
import org.example.bxbatuz.antifraud.repo.LinkedUsersRepo;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.List;


@Service
@RequiredArgsConstructor
public class ExcelReportService {
    private final LinkedUsersRepo linkedUsersRepo;

    public byte[] generateFraudReport(Long adminId, Long concursId) throws IOException {
        List<LinkedUsers> allLinkedUsers = linkedUsersRepo.findByConcursId(concursId);

        if (allLinkedUsers == null || allLinkedUsers.isEmpty()) {
            throw new RemoteException("Foydalanuvchilar topilmadi!");
        }

        try (Workbook workbook = new XSSFWorkbook();
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {

            CellStyle headerStyle = workbook.createCellStyle();
            Font font = workbook.createFont();
            font.setBold(true);
            headerStyle.setFont(font);

            Sheet activeSheet = workbook.createSheet("Aktiv ID");
            Sheet fraudSheet = workbook.createSheet("Fraud ID");

            String[] columns = {"ID", "Tel"};

            createHeader(activeSheet, columns, headerStyle);
            createHeader(fraudSheet, columns, headerStyle);

            int activeRowIdx = 1;
            int fraudRowIdx = 1;

            for (LinkedUsers user : allLinkedUsers) {
                if (user.getIsFraud()) {
                    fillRow(fraudSheet, fraudRowIdx++, user);
                } else {
                    fillRow(activeSheet, activeRowIdx++, user);
                }
            }

            for (int i = 0; i < columns.length; i++) {
                activeSheet.autoSizeColumn(i);
                fraudSheet.autoSizeColumn(i);
            }

            workbook.write(out);
            return out.toByteArray();
        }
    }

    private void createHeader(Sheet sheet, String[] columns, CellStyle style) {
        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < columns.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columns[i]);
            cell.setCellStyle(style);
        }
    }

    private void fillRow(Sheet sheet, int rowIdx, LinkedUsers user) {
        Row row = sheet.createRow(rowIdx);
        row.createCell(0).setCellValue(user.getUserCode());
        row.createCell(1).setCellValue(user.getUserPhone());
    }
}
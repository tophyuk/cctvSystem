package com.kiot.cctvSystem.service;

import com.kiot.cctvSystem.domain.Alarm;
import com.kiot.cctvSystem.dto.alarm.AlarmSearchDto;
import com.kiot.cctvSystem.dto.alarm.AlarmStatDataDto;
import com.kiot.cctvSystem.repository.AlarmRepository;
import com.kiot.cctvSystem.repository.DeviceRepository;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class AlarmService {

    private final AlarmRepository alarmRepository;
    private final DeviceRepository deviceRepository;

    /** 엣지를 통해 경보 알림 받을 때 DB에 데이터 주입 **/
    public void createAlarm(Alarm alarm) {
        alarmRepository.save(alarm);
    }

    /** type(월,일,시)별에 따른 경보횟수 **/
    public List<Map<String, Integer>> getAlarmCounts(String type, AlarmSearchDto alarmSearchDto) {
        int year = alarmSearchDto.getYear();
        Integer month = alarmSearchDto.getMonth();
        Integer day = alarmSearchDto.getDay();

        // 결과 데이터를 담을 리스트 생성
        List<Map<String, Integer>> result = new ArrayList<>();

        if (type.equals("year")) {
            List<Map<String, Integer>> monthlyAlarmCounts = alarmRepository.getMonthlyAlarmCounts(year);
            for (int m = 1; m <= 12; m++) {
                boolean found = false;
                for (Map<String, Integer> item : monthlyAlarmCounts) {
                    if (item.get("month") == m) {
                        result.add(item);
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    Map<String, Integer> data = new HashMap<>();
                    data.put("month", m);
                    data.put("count", 0);
                    result.add(data);
                }
            }
        } else if (type.equals("month")) {
            List<Map<String, Integer>> dailyAlarmCounts = alarmRepository.getDailyAlarmCounts(year, month);

            for (int d = 1; d <= 31; d++) {
                boolean found = false;
                for (Map<String, Integer> item : dailyAlarmCounts) {
                    if (item.get("day") == d) {
                        result.add(item);
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    Map<String, Integer> data = new HashMap<>();
                    data.put("day", d);
                    data.put("count", 0);
                    result.add(data);
                }
            }
        } else if (type.equals("day")) {
            List<Map<String, Integer>> perHourAlarmCounts = alarmRepository.getPerHourAlarmCounts(year, month, day);

            for (int h = 0; h < 24; h++) {
                boolean found = false;
                for (Map<String, Integer> item : perHourAlarmCounts) {
                    if (item.get("hour") == h) {
                        result.add(item);
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    Map<String, Integer> data = new HashMap<>();
                    data.put("hour", h);
                    data.put("count", 0);
                    result.add(data);
                }
            }
        }

        return result;
    }

    /** type(월,일,시)별에 따른 경보 목록 **/
    public List<Alarm> getAlarmList(AlarmSearchDto alarmSearchDto) {
        int year = alarmSearchDto.getYear();
        Integer month = alarmSearchDto.getMonth();
        Integer day = alarmSearchDto.getDay();
        Integer hour = alarmSearchDto.getHour();

        List<Alarm> result = alarmRepository.getAlarmList(year, month, day, hour);
        return result;
    }

    /** 경보 알림 테이블 - 엑셀 다운로드 **/
    public void createDataExcel(List<AlarmStatDataDto> alarmDtoList, HttpServletResponse response) throws IOException {

        SXSSFWorkbook workbook = new SXSSFWorkbook();
        Sheet sheet = workbook.createSheet("Sheet 1");

        sheet.setColumnWidth(3, 7000);
        sheet.setColumnWidth(4, 5000);
        sheet.setColumnWidth(5, 5000);
        sheet.setColumnWidth(6, 8000);


        XSSFCellStyle mergeRowStyle2 = (XSSFCellStyle) workbook.createCellStyle();
        mergeRowStyle2.setAlignment(HorizontalAlignment.CENTER);
        mergeRowStyle2.setVerticalAlignment(VerticalAlignment.TOP);
        mergeRowStyle2.setBorderTop(BorderStyle.THIN);
        mergeRowStyle2.setBorderLeft(BorderStyle.THIN);
        mergeRowStyle2.setBorderBottom(BorderStyle.THIN);
        mergeRowStyle2.setBorderRight(BorderStyle.THIN);
        mergeRowStyle2.setFillForegroundColor(new XSSFColor(new byte[] {(byte) 192,(byte) 192,(byte) 192}, null));

        // 헤더 폰트 설정
        XSSFFont headerFont = (XSSFFont) workbook.createFont();
        headerFont.setFontName("나눔고딕");
        headerFont.setColor(new XSSFColor(new byte[]{(byte) 255, (byte) 255, (byte) 255}));
        headerFont.setBold(true);

        // 헤더 정렬 및 테두리 설정
        XSSFCellStyle headerStyle = (XSSFCellStyle) workbook.createCellStyle();
        headerStyle.setAlignment(HorizontalAlignment.CENTER);
        headerStyle.setVerticalAlignment(VerticalAlignment.TOP);
        headerStyle.setWrapText(true);
        headerStyle.setBorderTop(BorderStyle.THIN);
        headerStyle.setBorderLeft(BorderStyle.THIN);
        headerStyle.setBorderBottom(BorderStyle.THIN);
        headerStyle.setBorderRight(BorderStyle.THIN);
        // 헤더 배경 설정
        headerStyle.setFillForegroundColor(new XSSFColor(new byte[]{(byte) 34, (byte) 37, (byte) 41}, null));
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerStyle.setFont(headerFont);

        // 바디 정렬 및 테두리 설정
        XSSFCellStyle bodyStyle = (XSSFCellStyle) workbook.createCellStyle();
        bodyStyle.setAlignment(HorizontalAlignment.CENTER);
        bodyStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        bodyStyle.setBorderTop(BorderStyle.THIN);
        bodyStyle.setBorderBottom(BorderStyle.THIN);
        bodyStyle.setBorderLeft(BorderStyle.THIN);
        bodyStyle.setBorderRight(BorderStyle.THIN);
        bodyStyle.setWrapText(true);


        int rowLocation = 0;
        Row subjectRow = null;
        Cell subjectCell = null;

        // 제목 행
        subjectRow = sheet.createRow(++rowLocation);

        // 경보 목록 병합 작업
        for(int i=3; i<7; i++) {
            subjectCell = subjectRow.createCell(i);
            subjectCell.setCellStyle(mergeRowStyle2);
            subjectCell.setCellValue("경보 목록");
        }
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 3, 6));


        //헤더
        final String[] header = {"장비명", "대상", "경보단계", "관측시간"};

        Row headerRow = null;
        Cell headerCell = null;

        headerRow = sheet.createRow(++rowLocation);
        for (int i=0; i < header.length; i++) {
            headerCell = headerRow.createCell(i+3);
            headerCell.setCellValue(header[i]);
            headerCell.setCellStyle(headerStyle);
        }

        //바디
        Row bodyRow = null;
        Cell bodyCell = null;

        for (int i = 0; i < alarmDtoList.size(); i++) {
            AlarmStatDataDto alarmStatDataDto = alarmDtoList.get(i);

            bodyRow = sheet.createRow(++rowLocation);  //헤더 이후로 데이터가 출력되어야하니 +1
            //Cell cell = null;
            bodyCell = bodyRow.createCell(3);
            bodyCell.setCellValue(alarmStatDataDto.getDeviceName());
            bodyCell.setCellStyle(bodyStyle);

            bodyCell = bodyRow.createCell(4);
            bodyCell.setCellValue(alarmStatDataDto.getTargetName());
            bodyCell.setCellStyle(bodyStyle);

            bodyCell = bodyRow.createCell(5);
            bodyCell.setCellValue(alarmStatDataDto.getLevel());
            bodyCell.setCellStyle(bodyStyle);

            bodyCell = bodyRow.createCell(6);
            bodyCell.setCellValue(alarmStatDataDto.getDetectedTime());
            bodyCell.setCellStyle(bodyStyle);
        }

        final String fileName = "excel";

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");
        ServletOutputStream servletOutputStream = response.getOutputStream();

        workbook.write(servletOutputStream);
        workbook.close();
        servletOutputStream.flush();
        servletOutputStream.close();

    }


    /** 경보 알림 그래프 - 엑셀 다운로드 **/
    public void createGraphExcel(List<Map<String, Integer>> data, String type, HttpServletResponse response) throws IOException {

        SXSSFWorkbook workbook = new SXSSFWorkbook();
        Sheet sheet = workbook.createSheet("Sheet 1");

        sheet.setColumnWidth(3, 7000);
        sheet.setColumnWidth(4, 5000);



        XSSFCellStyle mergeRowStyle2 = (XSSFCellStyle) workbook.createCellStyle();
        mergeRowStyle2.setAlignment(HorizontalAlignment.CENTER);
        mergeRowStyle2.setVerticalAlignment(VerticalAlignment.TOP);
        mergeRowStyle2.setBorderTop(BorderStyle.THIN);
        mergeRowStyle2.setBorderLeft(BorderStyle.THIN);
        mergeRowStyle2.setBorderBottom(BorderStyle.THIN);
        mergeRowStyle2.setBorderRight(BorderStyle.THIN);
        mergeRowStyle2.setFillForegroundColor(new XSSFColor(new byte[] {(byte) 192,(byte) 192,(byte) 192}, null));

        // 헤더 폰트 설정
        XSSFFont headerFont = (XSSFFont) workbook.createFont();
        headerFont.setFontName("나눔고딕");
        headerFont.setColor(new XSSFColor(new byte[]{(byte) 255, (byte) 255, (byte) 255}));
        headerFont.setBold(true);

        // 헤더 정렬 및 테두리 설정
        XSSFCellStyle headerStyle = (XSSFCellStyle) workbook.createCellStyle();
        headerStyle.setAlignment(HorizontalAlignment.CENTER);
        headerStyle.setVerticalAlignment(VerticalAlignment.TOP);
        headerStyle.setWrapText(true);
        headerStyle.setBorderTop(BorderStyle.THIN);
        headerStyle.setBorderLeft(BorderStyle.THIN);
        headerStyle.setBorderBottom(BorderStyle.THIN);
        headerStyle.setBorderRight(BorderStyle.THIN);
        // 헤더 배경 설정
        headerStyle.setFillForegroundColor(new XSSFColor(new byte[]{(byte) 34, (byte) 37, (byte) 41}, null));
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerStyle.setFont(headerFont);

        // 바디 정렬 및 테두리 설정
        XSSFCellStyle bodyStyle = (XSSFCellStyle) workbook.createCellStyle();
        bodyStyle.setAlignment(HorizontalAlignment.CENTER);
        bodyStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        bodyStyle.setBorderTop(BorderStyle.THIN);
        bodyStyle.setBorderBottom(BorderStyle.THIN);
        bodyStyle.setBorderLeft(BorderStyle.THIN);
        bodyStyle.setBorderRight(BorderStyle.THIN);
        bodyStyle.setWrapText(true);


        int rowLocation = 0;
        Row subjectRow = null;
        Cell subjectCell = null;

        // 제목 행
        subjectRow = sheet.createRow(++rowLocation);

        // 경보 목록 병합 작업
        for(int i=3; i<5; i++) {
            subjectCell = subjectRow.createCell(i);
            subjectCell.setCellStyle(mergeRowStyle2);
            subjectCell.setCellValue("경보 목록");
        }
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 3, 4));


        /** type에 따라 셀 내용 변경  **/
        //헤더
        String[] header = {};
        Row headerRow = null;
        Cell headerCell = null;

        switch (type) {
            case "year":
                header = new String[] {"월", "횟수"};
                break;
            case "month":
                header = new String[] {"일", "횟수"};
                break;
            case "day":
                header = new String[] {"시간", "횟수"};
                break;
            default:
                break;
        }

        headerRow = sheet.createRow(++rowLocation);
        for (int i=0; i < header.length; i++) {
            headerCell = headerRow.createCell(i+3);
            headerCell.setCellValue(header[i]);
            headerCell.setCellStyle(headerStyle);
        }

        //바디
        Row bodyRow = null;
        Cell bodyCell = null;

        for (int i = 0; i < data.size(); i++) {

            Map<String, Integer> dataMap = data.get(i);

            bodyRow = sheet.createRow(++rowLocation);  //헤더 이후로 데이터가 출력되어야하니 +1
            //Cell cell = null;
            bodyCell = bodyRow.createCell(3);
            switch (type) {
                case "year":
                    bodyCell.setCellValue(dataMap.get("month") + "월");
                    break;
                case "month":
                    bodyCell.setCellValue(dataMap.get("day") + "일");
                    break;
                case "day":
                    bodyCell.setCellValue(dataMap.get("hour") + "시");
                    break;
                default:
                    bodyCell.setCellValue(""); // 기본값 처리
                    break;
            }
            bodyCell.setCellStyle(bodyStyle);

            bodyCell = bodyRow.createCell(4);
            bodyCell.setCellValue(Integer.parseInt(String.valueOf(dataMap.get("count"))));
            bodyCell.setCellStyle(bodyStyle);
        }

        final String fileName = "excel";

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");
        ServletOutputStream servletOutputStream = response.getOutputStream();

        workbook.write(servletOutputStream);
        workbook.close();
        servletOutputStream.flush();
        servletOutputStream.close();

    }

    public String getURL(Long deviceId) {
        String url = deviceRepository.findURLByDeviceId(deviceId);
        return url;
    }
}

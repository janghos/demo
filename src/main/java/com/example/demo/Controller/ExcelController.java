package com.example.demo.Controller;

import com.example.demo.Service.CinemaService;
import com.example.demo.Service.MovieService;
import com.example.demo.entity.ExcelCinema;
import com.example.demo.entity.ExcelMovie;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/excel")
public class ExcelController {
        @Autowired
        private MovieService movieService;
        @Autowired
        private CinemaService cinemaService;

        @GetMapping("/movie")
        public void downloadExcelMovie(HttpServletResponse response) throws IOException {

            Workbook workbook = new HSSFWorkbook();
            Sheet sheet = workbook.createSheet("영화관 목록");
            int rowNo = 0;
            CellStyle style = workbook.createCellStyle();
            style.setFillForegroundColor(IndexedColors.YELLOW.index);
            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            Row headerRow = sheet.createRow(rowNo++);

            headerRow.createCell(2).setCellValue("영화등록번호");
            headerRow.createCell(3).setCellValue("영화제목");
            headerRow.createCell(4).setCellValue("감독명");
            headerRow.createCell(5).setCellValue("배우명");
            headerRow.createCell(6).setCellValue("장르");
            headerRow.createCell(7).setCellValue("상영시간");
            headerRow.createCell(8).setCellValue("개봉일");
            headerRow.createCell(9).setCellValue("순위");
            headerRow.createCell(10).setCellValue("포스터url");
            headerRow.createCell(11).setCellValue("줄거리");
            headerRow.createCell(12).setCellValue("별점");
            headerRow.createCell(13).setCellValue("관객수");
            headerRow.createCell(14).setCellValue("스틸이미지url");
            headerRow.createCell(15).setCellValue("제조국");
            headerRow.createCell(16).setCellValue("스틸영상Url");

            for(int i=2; i<=16; i++) {
                headerRow.getCell(i).setCellStyle(style);
            }


            List<ExcelMovie> list = movieService.getMAll();
            for (ExcelMovie movie : list) {
                Row row = sheet.createRow(rowNo++);
                row.createCell(2).setCellValue(movie.getMovieid());
                row.createCell(3).setCellValue(movie.getTitle());
                row.createCell(4).setCellValue(movie.getDirectornm());
                row.createCell(5).setCellValue(movie.getActornm());
                row.createCell(6).setCellValue(movie.getGenre());
                row.createCell(7).setCellValue(movie.getRuntime());
                row.createCell(8).setCellValue(movie.getReprlsdate());
                row.createCell(9).setCellValue(movie.getRating());
                row.createCell(10).setCellValue(movie.getPosterurl());
                row.createCell(11).setCellValue(movie.getPlot());
                row.createCell(12).setCellValue(movie.getStar());
                row.createCell(13).setCellValue(movie.getAudiacc());
                row.createCell(14).setCellValue(movie.getStillurl());
                row.createCell(15).setCellValue(movie.getCountry());
                row.createCell(16).setCellValue(movie.getAudiourl());
            }
            response.setContentType("ms-vnd/excel");
            response.setHeader("Content-Disposition", "attachment;filename=MovieList.xls");

            workbook.write(response.getOutputStream());
            workbook.close();
        }

        @GetMapping("/cinema")
        public void downloadExcelCinema(HttpServletResponse response) throws IOException {

            Workbook workbook = new HSSFWorkbook();
            Sheet sheet = workbook.createSheet("상영관 목록");
            int rowNo = 0;

            Row headerRow = sheet.createRow(rowNo++);
            headerRow.createCell(0).setCellValue("상영관 번호");
            headerRow.createCell(1).setCellValue("상영관 이름");
            headerRow.createCell(2).setCellValue("좌석 개수");
            headerRow.createCell(3).setCellValue("위도");
            headerRow.createCell(4).setCellValue("경도");
            headerRow.createCell(5).setCellValue("별점");

            List<ExcelCinema> list = cinemaService.getEAll();
            for (ExcelCinema cinema : list) {
                Row row = sheet.createRow(rowNo++);
                row.createCell(0).setCellValue(cinema.getId());
                row.createCell(1).setCellValue(cinema.getCinema_name());
                row.createCell(2).setCellValue(cinema.getTotal_seat());
                row.createCell(3).setCellValue(cinema.getLat());
                row.createCell(4).setCellValue(cinema.getLng());
                row.createCell(5).setCellValue(cinema.getStar());
            }
            response.setContentType("ms-vnd/excel");
            response.setHeader("Content-Disposition", "attachment;filename=CinemaList.xls");

            workbook.write(response.getOutputStream());
            workbook.close();
        }
}

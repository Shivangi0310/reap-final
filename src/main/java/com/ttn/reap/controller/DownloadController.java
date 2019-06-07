package com.ttn.reap.controller;

import com.ttn.reap.entity.Recognition;
import com.ttn.reap.service.RecognitionService;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/reap")
public class DownloadController {
    @Autowired
    RecognitionService recognitionService;

    @RequestMapping(value = "/dashboard/downloadcsv.csv",method = RequestMethod.GET)
    @ResponseBody
    public void downloadCsv(@RequestParam(value = "startDate",required = false) String startDate,
                           @RequestParam(value = "endDate",required = false) String endDate,
                           HttpServletResponse response) throws ParseException, IOException {
                                                      response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; file=downloadcsv.csv");
        try (
                CSVPrinter csvPrinter = new CSVPrinter(response.getWriter(), CSVFormat.DEFAULT
                        .withHeader("ID", "Receiver's FirstName", "Giver's FirstName","Comment","CreatedAt"));
        ) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date startdate = formatter.parse(startDate);
            Date enddate = formatter.parse(endDate);
            System.out.println(endDate + startDate+ "======");
            List<Recognition> filterList = recognitionService.showRecognitionByDate(startdate,enddate);
            for ( Recognition fl : filterList) {
                List<String> data = Arrays.asList(
                        fl.getId().toString(),
                        fl.getRecognizeeId().getFirstName(),
                        fl.getRecognizerEmployeeId().getFirstName(),
                        fl.getMessage(),
                        fl.getDateOfRecognition().toString()

                );


                csvPrinter.printRecord(data);
            }
            csvPrinter.flush();
        } catch (Exception e) {
            System.out.println("Writing CSV error!");
            e.printStackTrace();
        }

//        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//            Date startdate = formatter.parse(startDate);
//            Date enddate = formatter.parse(endDate);
//            System.out.println(startdate);
//            System.out.println(enddate);
//            List<Recognition> filterList = recognitionService.showRecognitionByDate(startdate,enddate);
//        System.out.println(filterList);
//
//            String csvFileName = "recognition.txt";
//            response.setContentType("text/plain");
//            String headerKey = "Content-Disposition";
//            String headerValue = String.format("attachment; filename=\"%s\"",
//                    csvFileName);
//            response.setHeader(headerKey, headerValue);
//        System.out.println("1111111");
//            ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(),
//                    CsvPreference.STANDARD_PREFERENCE);
//            String[] header = {"id","active","badge","dateOfRecognition","message", "recognizeeId", "recognizerEmployeeId"};
//        System.out.println("2222222222");
//
//        csvWriter.writeHeader(header);
//
//        System.out.println("33333333");
//
//        for (Recognition list: filterList) {
//            System.out.println("444444");
//
//            csvWriter.write(list, header);
//            }
//        System.out.println("555555");
//
//
//        csvWriter.close();
//
//        System.out.println("66666666");

    }
}

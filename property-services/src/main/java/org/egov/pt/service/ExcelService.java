package org.egov.pt.service;

import com.monitorjbl.xlsx.StreamingReader;
import org.apache.poi.ss.usermodel.*;
import org.egov.pt.models.excel.RowExcel;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class ExcelService {


    public void read(File file, Function<RowExcel, Boolean> func) throws Exception{
        InputStream is = new FileInputStream(file);
        Workbook workbook = StreamingReader.builder().rowCacheSize(100).bufferSize(4096).open(is);
        for (Sheet sheet: workbook) {
            Map<Integer, String> headerMap = new HashMap<Integer, String>();
            for (Row r: sheet) {
                Map<Integer, Cell> cellMap = new HashMap<Integer, Cell>();
                if(r.getRowNum() == 0) {
                    for (Cell cell: r) {
                        headerMap.put(cell.getColumnIndex(), cell.getStringCellValue());
                    }
                } else {
                    for (Cell cell: r) {
                        cellMap.put(cell.getColumnIndex(),cell);
                    }
                }
                if(r.getRowNum() > 0) func.apply(RowExcel.builder().rowIndex(r.getRowNum()).cells(cellMap).header(headerMap).build());
                if(r.getPhysicalNumberOfCells() <= 0) break;
            }
        }
    }
}
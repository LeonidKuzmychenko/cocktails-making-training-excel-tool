package utils;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SheetService {

    public Sheet getSheet(String path, int page) throws IOException {
        FileInputStream file = new FileInputStream(path);
        Workbook workbook = new XSSFWorkbook(file);
        return workbook.getSheetAt(page);
    }

    public List<String> getList(String path, int page) throws IOException {
        Sheet sheet = getSheet(path, page);
        List<String> list = new ArrayList<>();
        for (Row row : sheet) {
            for (Cell cell : row) {
                list.add(cell.getStringCellValue());
            }
        }
        return list;
    }
}

import com.sun.javafx.iio.ios.IosDescriptor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;


public class ReadFile {

    private String filePath;
    private int pageNum;
    private int numOfRow;
    private int numOfColumns;
    private Row rowHeader;
    private List<String[]> valueList = new ArrayList<>();


    public ReadFile(String filePath, int pageNum){
        this.filePath = filePath;
        this.pageNum = pageNum;

    }

    public List<String[]> getFileValue (String filePath){
        Sheet sheet = null;
        Row row = null;
        List<List<String[]>> list = null;
        Workbook wb = null;
        String extString = filePath.substring(filePath.lastIndexOf("."));
        InputStream inputStream = null;
        List<Cell> headList = new ArrayList<>();

        try {

            inputStream = new FileInputStream(filePath);
            if (".xls".equals(extString)) {
                wb = new HSSFWorkbook(inputStream);
            } else if (".xlsx".equals(extString)) {
                wb = new XSSFWorkbook(inputStream);
            } else {
                wb = null;
            }
            if (wb != null) {
                //存放表中的数据
                list = new ArrayList<List<String[]>>();
                //获取第一个sheet
                sheet = wb.getSheetAt(pageNum);
                //获取最大行数
                numOfRow = sheet.getPhysicalNumberOfRows();
                //获取最大列数
                row = sheet.getRow(0);
                numOfColumns = row.getPhysicalNumberOfCells();
                //获取最大行
                rowHeader = sheet.getRow(0);
                headList = getHeader();

            }
            for (int i = 1; i < numOfRow; i++){
                String[] cellData = new String[numOfColumns];
                row = sheet.getRow(i);
                if (row != null){
                    for (int j = 0; j < numOfColumns; j++){
                        cellData[j] = (String) getCellFormatValue(row.getCell(j));
                    }
                    valueList.add(cellData);
                } else {
                    break;
                }
            }

        } catch (IOException e){
            e.printStackTrace();
        }
        return valueList;
    }

    public List<Cell> getHeader(){
        List<Cell> list = new ArrayList<>();
        for (int i = 0; i < numOfColumns; i++){
            list.add(rowHeader.getCell(i));
        }
        return list;
    }

    public Object getCellFormatValue(Cell cell){
        Object cellValue = null;
        if (cell != null){
            switch(cell.getCellType()){
                case Cell.CELL_TYPE_NUMERIC :
                    cellValue = String.valueOf(cell.getNumericCellValue());
                    break;
                case Cell.CELL_TYPE_STRING:
                    cellValue = String.valueOf(cell.getStringCellValue());
                    break;
                case Cell.CELL_TYPE_FORMULA:
                    if (DateUtil.isCellInternalDateFormatted(cell)){
                        cellValue = String.valueOf(cell.getStringCellValue());
                    } else{
                        cellValue = String.valueOf(cell.getNumericCellValue());
                    }
                    break;
                default:
                    cellValue = "";
            }
        } else {
            cellValue = "";
        }
        return cellValue;
    }

    public int getNumOfRow(){
        return numOfRow;
    }

    public List<String> getFileTitle(List<Cell> headList){
        List<String> list = new ArrayList<>();
        for (int i = 0; i < numOfColumns; i++){
            list.add((String) getCellFormatValue(headList.get(i)));
        }
        return list;
    }

    public String getFilePath() {
        return filePath;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }
}

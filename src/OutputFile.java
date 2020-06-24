import java.util.List;
import java.util.Map;

import com.sun.javafx.iio.ios.IosDescriptor;
import javafx.collections.ObservableList;
import jdk.internal.joptsimple.internal.Strings;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;

public class OutputFile {
    //表头
    private String title;
    //各个列的表头
    private String[] heardList;
    //各个列的元素key值
    private String[] heardKey;
    //字体大小
    private int fontSize = 14;
    //行高
    private int rowHeight = 30;
    //列宽
    private int columWidth = 200;
    //工作表
    private String sheetName = "sheet1";

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String[] getHeardList() {
        return heardList;
    }

    public void setHeardList(String[] heardList) {
        this.heardList = heardList;
    }

    public String[] getHeardKey() {
        return heardKey;
    }

    public void setHeardKey(String[] heardKey) {
        this.heardKey = heardKey;
    }

    public int getFontSize() {
        return fontSize;
    }

    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }

    public int getRowHeight() {
        return rowHeight;
    }

    public void setRowHeight(int rowHeight) {
        this.rowHeight = rowHeight;
    }

    public int getColumWidth() {
        return columWidth;
    }

    public void setColumWidth(int columWidth) {
        this.columWidth = columWidth;
    }

    public String getSheetName() {
        return sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    public byte[] exportExport(ObservableList<FillIn> data) throws IOException{
        checkConfig();
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet wbSheet = wb.createSheet(this.sheetName);
        wbSheet.setDefaultColumnWidth(20);

        HSSFCellStyle cellStyle = wb.createCellStyle();
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);//水平居中
        cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);//垂直居中
        HSSFFont fontStyle = wb.createFont();
        fontStyle.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        fontStyle.setFontHeightInPoints((short)16);  //设置标题字体大小
        cellStyle.setFont(fontStyle);

        HSSFRow title = wbSheet.createRow((int) 0);
        title.setHeightInPoints(30);//行高
        HSSFCell cellValue = title.createCell(0);
        cellValue.setCellValue(this.title);
        cellValue.setCellStyle(cellStyle);
        wbSheet.addMergedRegion(new CellRangeAddress(0,0,0,(this.heardList.length-1)));
        //设置表头样式，表头居中
        HSSFCellStyle style = wb.createCellStyle();
        //设置单元格样式
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        //设置字体
        HSSFFont font = wb.createFont();
        font.setFontHeightInPoints((short) this.fontSize);
        style.setFont(font);
        //在第1行创建rows
        HSSFRow row = wbSheet.createRow((int) 1);
        //设置列头元素
        HSSFCell cellHead = null;
        for (int i = 0; i < heardList.length; i++) {
            cellHead = row.createCell(i);
            cellHead.setCellValue(heardList[i]);
            cellHead.setCellStyle(style);
        }

        int a = 2;
        for (int i = 0; i < data.size(); i++) {
            HSSFRow roww = wbSheet.createRow(a);
            HSSFCell cell = null;
            for (int j = 0; j < 6; j++) {
                cell = roww.createCell(j);
                cell.setCellStyle(style);
                String value = null;
            }
        }
        //TODO
        return null;
    }

    protected void checkConfig() throws IOException {
        if (heardKey == null || heardList.length == 0) {
            throw new IOException("列名数组不能为空");
        }
        if (fontSize < 0 || rowHeight > 0 || columWidth < 0){
            throw new IOException("fontsize, rowHeight and columnwidth can not be negative");
        }
        if (Strings.isNullOrEmpty(sheetName)) {
            throw new IOException("work sheet's name can not be null");
        }
    }
}


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import javafx.scene.text.Text;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

import org.apache.poi.ss.usermodel.Cell;



import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class Test2{

    @FXML
    private TextField key_word;
    @FXML
    private Text file_path;
    @FXML
    private Text resource_path;
    @FXML
    private Text show_key_word;
    @FXML
    private TableView<FillIn> original_table;
    @FXML
    private TableView<FillIn> resource_table;
    @FXML
    private TextField page_num;
    @FXML
    private Text text_3;
    @FXML
    private TableColumn<FillIn, String> C1;
    @FXML
    private TableColumn<FillIn, String> C2;
    @FXML
    private TableColumn<FillIn, String> C3;
    @FXML
    private TableColumn<FillIn, String> C4;
    @FXML
    private TableColumn<FillIn, String> C5;
    @FXML
    private TableColumn<FillIn, String> C6;
    @FXML
    private TableColumn<FillIn, String> C11;
    @FXML
    private TableColumn<FillIn, String> C21;
    @FXML
    private TableColumn<FillIn, String> C31;
    @FXML
    private TableColumn<FillIn, String> C41;
    @FXML
    private TableColumn<FillIn, String> C51;



    private static String keyWord = null;
    private static int pageNum = 0;
    private static String xlxsFile = null;
    private static String resourceFile = null;
    private boolean haveKeyWord = false;
    private boolean havePageChoosen = false;
    private boolean isHaveFilePath = false;
    private ReadFile readFile = null;
    private List<String[]> tableValue = null;
    private final ObservableList<FillIn> cellData = FXCollections.observableArrayList();
    private final ObservableList<FillIn> resourceCellData = FXCollections.observableArrayList();
    private boolean isHaveData = false;
    private boolean isHaveResource = false;
    MainApp mainApp;

    public void alert() {
        String info="这是alert";
        Alert alert = new Alert(Alert.AlertType.INFORMATION, info, new ButtonType("确定", ButtonBar.ButtonData.YES));
        alert.setHeaderText(null);
        alert.setTitle("提示");
        alert.show();
    }

    public void getFilePath(){
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(new Stage());
        if (file == null){
            return;
        }
        file_path.setText(file.getAbsolutePath());
        xlxsFile = file.getAbsolutePath();
        isHaveFilePath = true;
    }

    public void getResourceFilePath(){
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(new Stage());
        if (file == null){
            return;
        }
        resource_path.setText(file.getAbsolutePath());
        resourceFile = file.getAbsolutePath();
    }

    public void saveTheKeyWord(){
        if (key_word.getText() != null){
            keyWord = key_word.getText();
            show_key_word.setText(keyWord);
            haveKeyWord = true;
        } else {
            haveKeyWord = false;
        }
    }

    public void choosePage() throws RuntimeException{
        try{
            if (page_num.getText() != null){
                pageNum = Integer.parseInt(page_num.getText());
                text_3.setText(page_num.getText());
                havePageChoosen = true;

            } else {
                havePageChoosen = false;
            }

        } catch(RuntimeException e){
            String info="请输入数字";
            Alert alert = new Alert(Alert.AlertType.INFORMATION, info, new ButtonType("确定", ButtonBar.ButtonData.YES));
            alert.setHeaderText(null);
            alert.setTitle("错误");
            alert.show();
        }
    }

    private boolean isRead(){
        if (isHaveFilePath && havePageChoosen){
            readFile = new ReadFile(xlxsFile, pageNum);
            tableValue = readFile.getFileValue(xlxsFile);
            return true;
        }
        return false;
    }

    @FXML
    private void addCell(TableView<FillIn> table, ObservableList<FillIn> cellData){
        List<Cell> columnCell = readFile.getHeader();
        List<String> columnsValue = readFile.getFileTitle(columnCell);
        int rowSize = readFile.getNumOfRow();
        for (int i = 0; i < 6; i++){
            table.getVisibleLeafColumn(i).setText(columnsValue.get(i));
        }

        for (int i = 0; i < rowSize - 1; i++) {
            String no = tableValue.get(i)[0];
            String name = tableValue.get(i)[1];
            String unit = tableValue.get(i)[2];
            String price = tableValue.get(i)[3];
            String scale = tableValue.get(i)[4];
            String sumPrice = tableValue.get(i)[5];
            cellData.add(new FillIn(no, name, unit, price, scale, sumPrice));
        }
    }
    //预处理
    private void isDataNameInResourceTable(){
        List<String> list = new ArrayList<>();
        List<String> list2 = new ArrayList<>();
        for (FillIn resourceCellDatum : resourceCellData) {
            list.add(resourceCellDatum.getName());
        }
        for (FillIn cellDatum : cellData) {
            if (!list.contains(cellDatum.getName())) {
                list2.add(cellDatum.getName());
            }
        }
        if (list2.size() != 0){
            String info="以下名称查找失败" + System.lineSeparator() + list2;
            Alert alert = new Alert(Alert.AlertType.INFORMATION, info, new ButtonType("确定", ButtonBar.ButtonData.YES));
            alert.setHeaderText(null);
            alert.setTitle("错误");
            alert.show();
        }

    }

    @FXML
    private void compareValue(){
        if (resource_table != null && original_table != null){
            // 第一遍遍历赋值
            for (int i = 0; i < cellData.size(); i++) {
                for (FillIn resourceCellDatum : resourceCellData) {
                    if (cellData.get(i).getName().equals(resourceCellDatum.getName())) {
                        if (cellData.get(i).getPrice().equals("")) {
                            cellData.get(i).setPrice(resourceCellDatum.getPrice());
                        }
                    }
                }
                try{
                    if (!cellData.get(i).getPrice().equals("")){
                        float sumPrice = Float.parseFloat(cellData.get(i).getPrice()) * Float.parseFloat(
                                cellData.get(i).getScale());
                        cellData.get(i).setSumPrice(Float.toString(sumPrice));
                    }
                }catch (NumberFormatException e){
                    String info="表格中含有不符合规范的数据,请删除或者修改";
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, info, new ButtonType("确定", ButtonBar.ButtonData.YES));
                    alert.setHeaderText(null);
                    alert.setTitle("错误");
                    alert.show();
                }
            }
            isDataNameInResourceTable();
        }
    }

    @FXML
    private void handleDeleteData(){
        int selectedIndex = original_table.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0){
            original_table.getItems().remove(selectedIndex);
        } else {
            //nothing selected
            String info="请先选择要删除的数据";
            Alert alert = new Alert(Alert.AlertType.INFORMATION, info, new ButtonType("确定", ButtonBar.ButtonData.YES));
            alert.setHeaderText(null);
            alert.setTitle("错误");
            alert.show();
        }
    }

    @FXML
    private void getResourceValue(){
        if (isHaveResource){
            resourceCellData.clear();
        }
        readFile = new ReadFile(resourceFile,0);
        tableValue = readFile.getFileValue(resourceFile);
        addCell(resource_table, resourceCellData);
        C11.setCellValueFactory(resourceCellData -> resourceCellData.getValue().noProperty());
        C21.setCellValueFactory(resourceCellData -> resourceCellData.getValue().nameProperty());
        C31.setCellValueFactory(resourceCellData -> resourceCellData.getValue().unitProperty());
        C41.setCellValueFactory(resourceCellData -> resourceCellData.getValue().priceProperty());
        C51.setCellValueFactory(resourceCellData -> resourceCellData.getValue().scaleProperty());

        resource_table.setItems(resourceCellData);
        isHaveResource = true;
    }

    @FXML
    private void getXlxsValue(){
        if (isHaveData){
            cellData.clear();
        }

        if (isRead()){
            addCell(original_table, cellData);
            //set column
            C1.setCellValueFactory(cellData -> cellData.getValue().noProperty());
            C2.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
            C3.setCellValueFactory(cellData -> cellData.getValue().unitProperty());
            C4.setCellValueFactory(cellData -> cellData.getValue().priceProperty());
            C5.setCellValueFactory(cellData -> cellData.getValue().scaleProperty());
            C6.setCellValueFactory(cellData -> cellData.getValue().sumPriceProperty());

            original_table.setItems(cellData);

            isHaveData = true;
//            original_table.getSelectionModel().selectedItemProperty().addListener(
//                    (observable, oldValue, newValue) -> showAllDetail(newValue));
//            getTitleDetail();

        } else{
            String info="缺少文件或者没有选择页数";
            Alert alert = new Alert(Alert.AlertType.INFORMATION, info, new ButtonType("确定", ButtonBar.ButtonData.YES));
            alert.setHeaderText(null);
            alert.setTitle("错误");
            alert.show();
        }
    }

//    public void getTitleDetail(){
//        int count = columnsValue.size();
//        List<Label> list = getTileLabel();
//        for (int i = 0; i < count; i++) {
//            list.get(i).setText(columnsValue.get(i));
//        }
//    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
//        original_table.setItems(cellData);
    }

    @FXML
    private void handleNewData(){
        FillIn tempData = new FillIn("","","","","","");
        boolean onClicked = mainApp.showEditData(tempData);
        if (onClicked){
            cellData.add(tempData);
        }
    }

    @FXML
    private void handleEditData(){
        FillIn selectedData = original_table.getSelectionModel().getSelectedItem();
        if (selectedData != null){
            mainApp.showEditData(selectedData);
        } else {
            String info="没有选中任何数据";
            Alert alert = new Alert(Alert.AlertType.INFORMATION, info, new ButtonType("确定", ButtonBar.ButtonData.YES));
            alert.setHeaderText(null);
            alert.setTitle("错误");
            alert.show();
        }
    }


//    public void showAllDetail(FillIn detail){
//        List<Label> list = getValueLabel();
//        if (detail != null){
//            V1.setText(detail.getName());
//            V2.setText(detail.getScale());
//            V3.setText(detail.getPrice());
//        } else {
//            for (int i = 0; i < list.size(); i++) {
//                list.get(i).setText(null);
//            }
//        }
//    }
}

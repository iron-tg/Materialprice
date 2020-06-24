import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EditDialogController {

    @FXML
    private TextField no_Field;
    @FXML
    private TextField name_Field;
    @FXML
    private TextField unit_Field;
    @FXML
    private TextField price_Field;
    @FXML
    private TextField scale_Field;

    private Stage dialogStage;
    private FillIn editData;
    private boolean onClicked = false;
    MainApp mainApp;

    @FXML
    private void initialize(){
    }

    public void setDialogStage(Stage dialogStage){
        this.dialogStage = dialogStage;
    }

    public void setData(FillIn editData){
        this.editData = editData;

        no_Field.setText(editData.getNo());
        name_Field.setText(editData.getName());
        unit_Field.setText(editData.getUnit());
        price_Field.setText(editData.getPrice());
        scale_Field.setText(editData.getScale());
    }

    @FXML
    private void handleOk(){
        if (isInputValid()){
            editData.setNo(no_Field.getText());
            editData.setName(name_Field.getText());
            editData.setUnit(unit_Field.getText());
            editData.setPrice(price_Field.getText());
            editData.setScale(scale_Field.getText());

            onClicked = true;
            dialogStage.close();
        }
    }

    @FXML
    private void handleCancel(){
        dialogStage.close();
    }

    public boolean isOkClicked(){
        return onClicked;
    }

    private boolean isInputValid(){
        String errorMessage = "";
        if (no_Field.getText() == null || no_Field.getText().length() == 0){
            errorMessage += "没有输入编号！\n";
        } else {
            try {
                Float.parseFloat(no_Field.getText());
            }catch (NumberFormatException e){
                errorMessage += "输入的编号无效\n";
            }
        }
        if (name_Field.getText() == null || name_Field.getText().length() == 0){
            errorMessage += "没有输入名称！\n";
        }
        if (unit_Field.getText() == null || unit_Field.getText().length() == 0){
            errorMessage += "没有输入单位！\n";
        }
        if (!price_Field.getText().equals("") || price_Field.getText().length() != 0){
            try{
                Float.parseFloat(price_Field.getText());
            }catch (NumberFormatException e){
                errorMessage += "输入的价格无效\n";
            }
        }
        if (scale_Field.getText() == null || scale_Field.getText().length() == 0){
            errorMessage += "没有输入数量！\n";
        } else {
            try {
                Float.parseFloat(scale_Field.getText());
            }catch (NumberFormatException e){
                errorMessage += "输入的数量无效\n";
            }
        }

        if (errorMessage.length() == 0){
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, errorMessage, new ButtonType("确定", ButtonBar.ButtonData.YES));
            alert.setHeaderText(null);
            alert.setTitle("输入的数据存在异常");
            alert.show();
            return false;
        }
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
//        original_table.setItems(cellData);
    }
}

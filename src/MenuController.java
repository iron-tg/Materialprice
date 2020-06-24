import javafx.fxml.FXML;
import javafx.stage.FileChooser;

import java.io.File;

public class MenuController {

    private MainApp mainApp;
    public void setMainApp(MainApp mainApp){
        this.mainApp = mainApp;
    }

    @FXML
    private void handleNew(){
        mainApp.setDataFilePath(null);
    }

    @FXML
    private void handleOpen(){
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XLXS files (*.xlxs)",
                "*.xlxs");
        fileChooser.getExtensionFilters().add(extFilter);

        // Show save file dialog
        File file = fileChooser.showOpenDialog(mainApp.getPrimaryStage());

//        if (file != null){
//            mainApp.loadPriceDataFromFile(file);
//        }
    }

    @FXML
    private void handleSave(){
        File dataFile = mainApp.getXlxsFilePath();
        if (dataFile != null){
            mainApp.savePriceDataToFile(dataFile);
        } else {
            handleSaveAs();
        }
    }

    @FXML
    private void handleSaveAs(){
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XML files (*.xml)",
                "*.xlxs");
        fileChooser.getExtensionFilters().add(extFilter);

        File file = fileChooser.showSaveDialog(mainApp.getPrimaryStage());

        if (file != null){
            if (!file.getPath().endsWith(".xlxs")){
                file = new File(file.getPath() + ".xlxs");
            }
            mainApp.savePriceDataToFile(file);
        }
    }

    @FXML
    private void handleExit(){
        System.exit(0);
    }
}

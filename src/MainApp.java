import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.prefs.Preferences;

public class MainApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    private Stage primaryStage;
    private AnchorPane rootLayout;
    private List<FillIn> priceData;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Test 101");

        initRootLayout();
//        showPersonOverview();
    }

    public void initRootLayout(){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("View/simple.fxml"));
            rootLayout = (AnchorPane) loader.load();

            Test2 controller = loader.getController();
            controller.setMainApp(this);
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            menuLayout();

//            MenuController menuController = loader.getController();
//            menuController.setMainApp(this);

            primaryStage.show();

        }catch (IOException e){
            e.printStackTrace();
        }

//        File file = getXlxsFilePath();
//        if (file != null){
//            loadPriceDataFromFile(file);
//        }
    }

    public void menuLayout(){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("View/MenuLayout.fxml"));
            AnchorPane menuOverview = (AnchorPane) loader.load();
            rootLayout.getChildren().add(menuOverview);

            MenuController menuController = loader.getController();
            menuController.setMainApp(this);

        }catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Returns the main stage.
     * @return
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public boolean showEditData(FillIn editData){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("View/EditDialog.fxml"));
            AnchorPane pane = (AnchorPane) loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("修改数据");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(pane);
            dialogStage.setScene(scene);

            EditDialogController controller = loader.getController();
            controller.setMainApp(this);
            controller.setDialogStage(dialogStage);
            controller.setData(editData);

            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e){
            e.printStackTrace();
            return false;
        }
    }

    public File getXlxsFilePath(){
        Preferences preferences = Preferences.userNodeForPackage(MainApp.class);
        String filePath = preferences.get("filePath", null);
        if (filePath != null){
            return new File(filePath);
        } else {
            return null;
        }
    }

    public void setDataFilePath(File file){
        Preferences preferences = Preferences.userNodeForPackage(MainApp.class);
        if (file != null) {
            preferences.put("filePath", file.getPath());

            // Update the stage title.
            primaryStage.setTitle("Material Price - " + file.getName());
        } else {
            preferences.remove("filePath");

            // Update the stage title.
            primaryStage.setTitle("Material Price - ");
        }
    }

//    public void loadPriceDataFromFile(File file){
//        try {
//            JAXBContext context = JAXBContext.newInstance(DataListWrapper.class);
//            Unmarshaller um = context.createUnmarshaller();
//            //读取数据
//            DataListWrapper wrapper = (DataListWrapper) um.unmarshal(file);

//            priceData.clear();
//            priceData.addAll(wrapper.getData());
            //保存数据路径
//            setDataFilePath(file);
//        } catch (Exception e){
//            String info="不能从文件路径中加载该文件\n" + file.getPath();
//            Alert alert = new Alert(Alert.AlertType.INFORMATION, info, new ButtonType("确定", ButtonBar.ButtonData.YES));
//            alert.setHeaderText(null);
//            alert.setTitle("错误");
//            alert.show();
//        }
//    }

    public void savePriceDataToFile(File file){
        try {
//            JAXBContext context = JAXBContext.newInstance(DataListWrapper.class);
//            Marshaller m = context.createMarshaller();
//            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
//
//            DataListWrapper wrapper = new DataListWrapper();
//            wrapper.setData(priceData);
//
//            m.marshal(wrapper, file);

            setDataFilePath(file);
        } catch (Exception e){
            e.printStackTrace();
            String info="不能保存在这个路径\n" + file.getPath();
            Alert alert = new Alert(Alert.AlertType.INFORMATION, info, new ButtonType("确定", ButtonBar.ButtonData.YES));
            alert.setHeaderText(null);
            alert.setTitle("错误");
            alert.show();
        }
    }
}

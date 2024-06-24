package com.example.demo5;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class HelloController implements Initializable {

    @FXML
    protected TableView<AddTasks> dailyTable;
    @FXML
    protected ProgressBar taskprogress;
    @FXML
    protected VBox vbox;
    @FXML
    protected DatePicker datePicker;
    @FXML
    protected Label datelabel;
    @FXML
    protected Button current;
    @FXML
    protected Button theday;
    @FXML
    private TableView<AddTasks> tasktable;
    @FXML
    protected TableColumn<AddTasks, String> durationcol;
    @FXML
    protected TableColumn<AddTasks, String> endscol;
    @FXML
    protected TableColumn<AddTasks, String> startcol;
    @FXML
    protected TableColumn<AddTasks, String> taskcol;
    @FXML
    protected TableColumn<AddTasks, CheckBox> status;
    @FXML
    protected Label progress;

    private int complete=0;
    private int total=0;
    ObservableList<AddTasks> taskData = FXCollections.observableArrayList();

    protected LocalDate date = LocalDate.now();

    public void getdate(ActionEvent actionEvent) {
        when();
    }

    private void when() {
        complete=0;
        total=0;
        LocalDate datePickerValue = datePicker.getValue();
        String day=datePickerValue.getDayOfWeek().toString().toLowerCase();
        theday.setText("add "+day+" schedule");
        long daysBetween = ChronoUnit.DAYS.between(date, datePickerValue);
        if (daysBetween == 0) {
            datelabel.setText("Today");
        } else if (daysBetween == 1) {
            datelabel.setText("Tomorrow");
        } else if (daysBetween == -1) {
            datelabel.setText("Yesterday");
        } else if (daysBetween < -1) {
            datelabel.setText(Math.abs(daysBetween) + " days ago");
        } else {
            datelabel.setText(daysBetween + " days ahead");
        }

        current.setVisible(daysBetween >= 0);
        theday.setVisible(daysBetween >= 0);

        taskcol.setCellValueFactory(new PropertyValueFactory<>("task"));
        endscol.setCellValueFactory(new PropertyValueFactory<>("ends"));
        durationcol.setCellValueFactory(new PropertyValueFactory<>("duration"));
        startcol.setCellValueFactory(new PropertyValueFactory<>("starts"));
        status.setCellValueFactory(new PropertyValueFactory<>("actions"));

        List<String> list = DataStore.readDate(datePicker.getValue().toString());

        taskData.clear();
        if (list != null) {
            for (String data : list) {
                total++;
                String[] line = data.split(",");
                CheckBox checkBox=new CheckBox();
                checkBox.setOnAction(event->save());
                if(Objects.equals(line[4], "complete")){
                    checkBox.setSelected(true);
                    complete++;
                }
                taskData.add(new AddTasks(line[0], line[1], line[2], line[3], checkBox));
            }
        }
        tasktable.setItems(taskData);
        double prg= (double) complete /total;
        progress.setText(Math.round(prg * 100) +"%");
        taskprogress.setProgress(prg);
    }

    @FXML
    protected void daily(ActionEvent event) {
        // Handle daily task addition
    }

    @FXML
    protected void current(ActionEvent event) {
        FXMLLoader root = new FXMLLoader(HelloApplication.class.getResource("AddTask.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene;
        try {
            scene = new Scene(root.load(), 841, 584);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage.setScene(scene);
        AddController addController = root.getController();
        addController.title.setText("Specific tasks " + datePicker.getValue().toString());
        addController.setDate(datePicker.getValue().toString());
        stage.show();
    }
    public void save() {
        List<String> list=new ArrayList<>();
        complete=0;

        for (AddTasks task : tasktable.getItems()) {
            String taskText = task.getTask();
            String durationText = task.getDuration();
            String endsText = task.getEnds();
            String startText = task.getStarts();
            String statusText;
            if(task.getActions().isSelected()){
                statusText ="complete";
                complete++;
            }else {
                statusText ="incomplete";
            }

            list.add(taskText+","+startText+","+endsText+","+durationText+","+statusText);
        }

        DataStore.write(list, datePicker.getValue().toString());
        double prg= (double) complete /total;
        progress.setText(Math.round(prg * 100) +"%");
        taskprogress.setProgress(prg);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        datePicker.setValue(date);
        when();

    }
}

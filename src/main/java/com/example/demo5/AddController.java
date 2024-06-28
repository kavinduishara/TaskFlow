package com.example.demo5;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class AddController {

    private final StringProperty date = new SimpleStringProperty();
    @FXML
    protected Label title;

    @FXML
    private TableView<Tasks> dailyTable;
    @FXML
    protected TableColumn<Tasks, String> durationcol;
    @FXML
    protected TableColumn<Tasks, Spinner<Integer>> endh;
    @FXML
    protected TableColumn<Tasks, Spinner<Integer>> endm;
    @FXML
    protected TableColumn<Tasks, Spinner<Integer>> starth;
    @FXML
    protected TableColumn<Tasks, Spinner<Integer>> startm;
    @FXML
    protected TableColumn<Tasks, TextField> taskcol;
    protected Boolean changed=false;

    ObservableList<Tasks> taskData = FXCollections.observableArrayList();

    public void setDate(String date) {
        this.date.set(date);
    }

    public void save() {
        List<String> list = new ArrayList<>();

        for (Tasks task : dailyTable.getItems()) {
            String taskText = task.getTask().getText();
            String durationText = task.getDuration();
            String endsText = task.getEndh().getValue()+":"+task.getEndm().getValue();
            String startText = task.getStarth().getValue()+":"+task.getStartm().getValue();
            list.add(taskText + "," + startText + "," + endsText + "," + durationText + ",incomplete");
        }
        DataStore.write(list, date.get());
    }


    @FXML
    public void initialize() {
        date.addListener((observable, oldValue, newValue) -> {
            getdata(date.getValue());
        });

        taskcol.setCellValueFactory(new PropertyValueFactory<>("task"));
        endh.setCellValueFactory(new PropertyValueFactory<>("endh"));
        endm.setCellValueFactory(new PropertyValueFactory<>("endm"));
        durationcol.setCellValueFactory(new PropertyValueFactory<>("duration"));
        starth.setCellValueFactory(new PropertyValueFactory<>("starth"));
        startm.setCellValueFactory(new PropertyValueFactory<>("startm"));

        dailyTable.setItems(taskData);
    }

    public void getdata(String file) {
        List<String> list = null;

        list = DataStore.readDate(file);

        if (list == null) {
            return;
        }
        for (String data : list) {
            String[] line = data.split(",");
            String[] start=line[1].split(":");
            String[] ends=line[2].split(":");
            Spinner<Integer> startHourSpinner = new Spinner<>(0, 23, Integer.parseInt(start[0]));
            Spinner<Integer> startMinuteSpinner = new Spinner<>(0, 59, Integer.parseInt(start[1]));
            Spinner<Integer> endHourSpinner = new Spinner<>(0, 23, Integer.parseInt(ends[0]));
            Spinner<Integer> endMinuteSpinner = new Spinner<>(0, 59, Integer.parseInt(ends[1]));

            TextField task=new TextField(line[0]);
            task.textProperty().addListener((observableValue, string, t1) -> {
                changed=true;
            } );

            Tasks newTask = new Tasks(task,startHourSpinner, startMinuteSpinner, endHourSpinner, endMinuteSpinner, line[3]);
            taskData.add(newTask);

            addEvents(startHourSpinner, newTask);
            addEvents(startMinuteSpinner, newTask);
            addEvents(endHourSpinner, newTask);
            addEvents(endMinuteSpinner, newTask);
        }
    }

    @FXML
    protected void delete() {
        int selectedIndex = dailyTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            dailyTable.getItems().remove(selectedIndex);
        }
    }

    @FXML
    protected void addtask() {
        Spinner<Integer> startHourSpinner = new Spinner<>(0, 23, 0);
        Spinner<Integer> startMinuteSpinner = new Spinner<>(0, 59, 0);
        Spinner<Integer> endHourSpinner = new Spinner<>(0, 23, 0);
        Spinner<Integer> endMinuteSpinner = new Spinner<>(0, 59, 0);
        TextField task=new TextField();
        task.textProperty().addListener((observableValue, string, t1) -> {
            changed=true;
        } );

        Tasks newTask = new Tasks(task,startHourSpinner, startMinuteSpinner, endHourSpinner, endMinuteSpinner, "");
        taskData.add(newTask);

        addEvents(startHourSpinner, newTask);
        addEvents(startMinuteSpinner, newTask);
        addEvents(endHourSpinner, newTask);
        addEvents(endMinuteSpinner, newTask);
    }


    @FXML
    protected void cancel(ActionEvent event) {
        try {
            if (changed){
                ButtonType saveButtonType = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
                ButtonType dontSaveButtonType = new ButtonType("Don't Save", ButtonBar.ButtonData.NO);
                ButtonType cancelButtonType = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Save Changes");
                alert.setHeaderText("Do you want to save changes?");
                alert.setContentText("Choose your option.");

                alert.getButtonTypes().setAll(saveButtonType, dontSaveButtonType, cancelButtonType);

                Optional<ButtonType> result = alert.showAndWait();

                if (result.isPresent()) {
                    if (result.get() == saveButtonType) {
                        save();
                    } else if (result.get() == cancelButtonType) {
                        return;
                    }
                }
            }
            FXMLLoader loader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    protected void changeWeekSchedule(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("weekschedule.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            weekshcedulecontroller weekshcedulecontroller = loader.getController();
            weekshcedulecontroller.setDate("Monday");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addEvents(Spinner<Integer> spinner, Tasks task) {
        spinner.valueProperty().addListener((observable, oldValue, newValue) -> updateResult(task));
    }

    private void updateResult(Tasks task) {
        int starth = task.getStarth().getValue();
        int startm = task.getStartm().getValue();
        int endh = task.getEndh().getValue();
        int endm = task.getEndm().getValue();

        LocalTime startTime = LocalTime.of(starth, startm);
        LocalTime endTime = LocalTime.of(endh, endm);

        Duration duration = Duration.between(startTime, endTime);

        long hours = duration.toHours();
        long minutes = duration.toMinutes() % 60;

        task.setDuration(hours + ":" + minutes);
        changed=true;
    }
}

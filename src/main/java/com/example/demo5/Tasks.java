package com.example.demo5;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tasks {
    private ObjectProperty<TextField> task;
    private ObjectProperty<Spinner<Integer>> starth;
    private ObjectProperty<Spinner<Integer>> startm;
    private ObjectProperty<Spinner<Integer>> endh;
    private ObjectProperty<Spinner<Integer>> endm;
    private StringProperty duration;

    public Tasks(TextField task, Spinner<Integer> starth, Spinner<Integer> startm, Spinner<Integer> endh, Spinner<Integer> endm, String duration) {
        this.task = new SimpleObjectProperty<>(task);
        this.starth = new SimpleObjectProperty<>(starth);
        this.startm = new SimpleObjectProperty<>(startm);
        this.endh = new SimpleObjectProperty<>(endh);
        this.endm = new SimpleObjectProperty<>(endm);
        this.duration = new SimpleStringProperty(duration);
    }

    public ObjectProperty<TextField> taskProperty() {
        return task;
    }

    public TextField getTask() {
        return task.get();
    }

    public void setTask(TextField task) {
        this.task.set(task);
    }

    public ObjectProperty<Spinner<Integer>> starthProperty() {
        return starth;
    }

    public Spinner<Integer> getStarth() {
        return starth.get();
    }

    public void setStarth(Spinner<Integer> starth) {
        this.starth.set(starth);
    }

    public ObjectProperty<Spinner<Integer>> startmProperty() {
        return startm;
    }

    public Spinner<Integer> getStartm() {
        return startm.get();
    }

    public void setStartm(Spinner<Integer> startm) {
        this.startm.set(startm);
    }

    public ObjectProperty<Spinner<Integer>> endhProperty() {
        return endh;
    }

    public Spinner<Integer> getEndh() {
        return endh.get();
    }

    public void setEndh(Spinner<Integer> endh) {
        this.endh.set(endh);
    }

    public ObjectProperty<Spinner<Integer>> endmProperty() {
        return endm;
    }

    public Spinner<Integer> getEndm() {
        return endm.get();
    }

    public void setEndm(Spinner<Integer> endm) {
        this.endm.set(endm);
    }

    public StringProperty durationProperty() {
        return duration;
    }

    public String getDuration() {
        return duration.get();
    }

    public void setDuration(String duration) {
        this.duration.set(duration);
    }
}

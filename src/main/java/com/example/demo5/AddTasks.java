package com.example.demo5;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.CheckBox;

public class AddTasks {
    private StringProperty task;
    private StringProperty starts;
    private StringProperty ends;
    private StringProperty duration;
    private ObjectProperty<CheckBox> actions;

    public AddTasks(String task, String starts, String ends, String duration, CheckBox checkBox) {
        this.task = new SimpleStringProperty(task);
        this.starts = new SimpleStringProperty(starts);
        this.ends = new SimpleStringProperty(ends);
        this.duration = new SimpleStringProperty(duration);
        this.actions = new SimpleObjectProperty<>(checkBox);
    }

    public StringProperty taskProperty() {
        return task;
    }

    public String getTask() {
        return task.get();
    }

    public void setTask(String task) {
        this.task.set(task);
    }

    public StringProperty startsProperty() {
        return starts;
    }

    public String getStarts() {
        return starts.get();
    }

    public void setStarts(String starts) {
        this.starts.set(starts);
    }

    public StringProperty endsProperty() {
        return ends;
    }

    public String getEnds() {
        return ends.get();
    }

    public void setEnds(String ends) {
        this.ends.set(ends);
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

    public ObjectProperty<CheckBox> actionsProperty() {
        return actions;
    }

    public CheckBox getActions() {
        return actions.get();
    }

    public void setActions(CheckBox actions) {
        this.actions.set(actions);
    }
}

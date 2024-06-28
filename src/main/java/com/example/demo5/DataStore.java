package com.example.demo5;

import javafx.scene.control.TextField;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class DataStore {
    protected static void write(List list,String date){
        try {
            String filepath="data/specific/"+date+".txt";
            PrintWriter printWriter=new PrintWriter(filepath);
            for (Object data :list) {
                printWriter.println(data);
            }
            printWriter.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    protected static void writeweek(List list,String day){
        try {
            String filepath="data/weekdays/"+day+".txt";
            PrintWriter printWriter=new PrintWriter(filepath);
            for (Object data :list) {
                printWriter.println(data);
            }
            printWriter.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    protected static List<String> readDate(String date){
        String fileparth="data/specific/"+date+".txt";
        File file=new File(fileparth);
        List<String> data = new ArrayList<>();
        try {
            Scanner scanner=new Scanner(file);
            String line="";
            while (scanner.hasNext()){
                line= scanner.nextLine();
                data.add(line);
            }

        } catch (FileNotFoundException e) {
            return null;
        }
        return data;
    }
protected static List<String> readWeekDayDate(String day){
        String fileparth="data/weekdays/"+day+".txt";
        File file=new File(fileparth);
        List<String> data = new ArrayList<>();
        try {
            Scanner scanner=new Scanner(file);
            String line="";
            while (scanner.hasNext()){
                line= scanner.nextLine();
                data.add(line);
            }

        } catch (FileNotFoundException e) {
            return null;
        }
        return data;
    }

    public static void main(String[] args) {
    }
}

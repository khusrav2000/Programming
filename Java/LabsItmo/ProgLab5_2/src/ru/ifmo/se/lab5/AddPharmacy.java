package ru.ifmo.se.lab5;

import com.google.gson.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Vector;
import java.util.regex.Pattern;

class AddPharmacy {

    private static Scanner scan = new Scanner(System.in);
    private static Scanner sc = new Scanner(System.in);
    private final String JSON_FILE_NAME = "C:\\Users\\KHUSRAV\\Documents\\IntellijIDEAProjects\\itmo_programming_lab5\\src\\ru\\ifmo\\se\\lab5\\Pharmacies.json"; //Путь к Json файлу
    Vector<Pharmacy> pharmacies = new Vector<>();

    void startAdd() {
        for (;;) {
            // команда которая приходить из командной строки
            String command = scan.nextLine();
            String load = "^load$";
            String removeElement = "^remove\\s*\\{\"";
            String info = "^info$";
            String addElement = "^add\\s\\{\"";
            String removeIndex = "^remove\\s\\{\\d*\\}";
            String show = "^show$";
            String remove_first = "^remove_first$";
            String update = "^update$";
            if (Pattern.compile(load).matcher(command).find()) {
                System.out.println("load");
                load();
            } else if (Pattern.compile(show).matcher(command).find()){
                showCollection();
            } else if (Pattern.compile(remove_first).matcher(command).find()){
                deleteFirstElement();
            } else if (Pattern.compile(removeIndex).matcher(command).find()){
                deleteElementByIndex(command);
            } else if (Pattern.compile(removeElement).matcher(command).find()){
                deleteElementByValue(command.substring(7, command.length()));
            } else if (Pattern.compile(addElement).matcher(command).find()){
                addElementToCollection(command.substring(4, command.length()));
            } else {
                System.out.println("Неправилная команда");
            }


            System.out.println(getSizeOfPharmacies());;
        }
    }

    private void addElementToCollection(String element) {

        JsonParser jsonParser = new JsonParser();
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();

        try {
            JsonObject jsonObject = (JsonObject) jsonParser.parse(element.toString());
            Pharmacy pharmacy = gson.fromJson(jsonObject, Pharmacy.class);
            pharmacies.add(pharmacy);
        } catch (Exception e){
            System.out.println("Неправилная команда");
        }
    }

    private void deleteElementByValue(String element) {

        JsonParser jsonParser = new JsonParser();
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();

        try {
            JsonObject jsonObject = (JsonObject) jsonParser.parse(element.toString());
            Pharmacy pharmacy = gson.fromJson(jsonObject, Pharmacy.class);
            pharmacies.remove(pharmacy);
        } catch (Exception e){
            System.out.println("Неправилная команда");
        }
    }

    private void deleteElementByIndex(String stringIndex) {
        int index = Integer.parseInt(stringIndex.substring(8, stringIndex.length() -2));
        pharmacies.remove(index);
    }

    private void deleteFirstElement() {
        pharmacies.remove(0);
    }

    private void showCollection() {
        for (Pharmacy pharmacy : pharmacies){
            System.out.println(pharmacy.toString());
        }
    }

    private void load() {

        try {
            sc = new Scanner(new File(JSON_FILE_NAME));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("----");
        }
        StringBuilder jsonData = new StringBuilder();
        while (sc.hasNext()){
            jsonData.append(sc.next());
        }

        JsonParser jsonParser = new JsonParser();
        System.out.println(jsonData.toString());
        JsonArray jsonArray = (JsonArray) jsonParser.parse(jsonData.toString());
        System.out.println(jsonArray);

        Iterator<JsonElement> iterator = jsonArray.iterator();
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();

        while (iterator.hasNext()){
            JsonObject jsonObject = (JsonObject) iterator.next();

            System.out.println();

            Pharmacy pharmacy = gson.fromJson(jsonObject, Pharmacy.class);
            pharmacies.add(pharmacy);
        }

    }

    private int getSizeOfPharmacies(){
        return pharmacies.size();
    }
}

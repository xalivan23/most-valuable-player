package com.example.mostvaluableplayer.service;

import java.io.FileReader;
import java.util.Arrays;

import au.com.bytecode.opencsv.CSVReader;


public class ParseCSVLineByLine {
    @SuppressWarnings("resource")
    public static void main(String[] args) throws Exception {
        CSVReader reader = new CSVReader(new FileReader("C:\\FATHER\\SKAI\\most-valuable-player\\src\\main\\resources\\files\\game2.csv"));
        String[] nextLine;
        while ((nextLine = reader.readNext()) != null) {
            if (nextLine != null) {
                System.out.println(Arrays.toString(nextLine));
            }
        }
    }
}
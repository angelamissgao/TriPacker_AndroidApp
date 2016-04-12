package com.example.tripacker.tripacker.exception;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by angelagao on 4/12/16.
 */
public class InvalidDataException extends Throwable {
    private String exceptionmsg;

    public InvalidDataException() throws IOException {
        this.exceptionmsg = "Invalid Input";
        logException(exceptionmsg);
    }

    public void logException(String exceptionmsg) throws IOException {
        FileWriter fw = new FileWriter("Exception.log");
        BufferedWriter bw = new BufferedWriter(fw);
        // write a string into the IO stream
        bw.write(exceptionmsg);
        bw.append("\n");
        bw.close();

    }
}

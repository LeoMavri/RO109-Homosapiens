package com.homosapiens.teamcode.utils;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.io.PrintStream;
import java.util.Date;

public class HomosapiensLogger {
    private final PrintStream console;
    private final Telemetry telemetry;

    private String consoleDate() {
        return new Date().toString();
    }

    private String telemetryDate() {
        return Long.toString(new Date().getTime());
    }

    public HomosapiensLogger(PrintStream console, Telemetry telemetry) {
        this.console = console;
        this.telemetry = telemetry;
    }

    public void toConsole(String message) {
        console.print(consoleDate());
        console.println(message);
    }

    public void toTelemetry(String message) {
        telemetry.addData(telemetryDate(), message);
    }
}

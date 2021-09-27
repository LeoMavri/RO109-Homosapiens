package com.homosapiens.teamcode.controls;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.gamepad1;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.gamepad2;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class JoystickMovement {

    public static final double LIMIT = .75;

    private static float getJoystickX(int gamepadId) {
        switch (gamepadId) {
            case 1: return gamepad1.left_stick_x;
            case 2: return gamepad2.left_stick_x;
            case 3: return gamepad1.left_stick_x + gamepad2.left_stick_x;
            default: return 0;
        }
    }

    private static float getJoystickY(int gamepadId) {
        switch (gamepadId) {
            case 1: return gamepad1.left_stick_y;
            case 2: return gamepad2.left_stick_y;
            case 3: return gamepad1.left_stick_y + gamepad2.left_stick_y;
            default: return 0;
        }
    }

    public static void handleJoystickMovement(DcMotor bottomRightMotor, DcMotor topRightMotor, DcMotor bottomLeftMotor, DcMotor topLeftMotor, Telemetry telemetry) {
        final float x = getJoystickX(1);
        final float y = getJoystickY(1);

        telemetry.addData("[JOYSTICK INPUT]", "x = " + Float.toString(x) + ", y = " + Float.toString(y));

        final double rightPower = ((x + y) / 2) * LIMIT;
        final double leftPower = ((y - x) / 2) * LIMIT;

        topLeftMotor.setPower(rightPower);
        bottomRightMotor.setPower(rightPower);

        topRightMotor.setPower(leftPower);
        bottomLeftMotor.setPower(leftPower);
    }

    public static void handleTriggerRotation(DcMotor bottomRightMotor, DcMotor topRightMotor, DcMotor bottomLeftMotor, DcMotor topLeftMotor, Telemetry telemetry) {
        final float ccwRotation = gamepad1.right_trigger - gamepad1.left_trigger;

        telemetry.addData("[TRIGGER INPUT]", "x = " + Float.toString(ccwRotation));

        topLeftMotor.setPower(-1 * ccwRotation * LIMIT);
        bottomLeftMotor.setPower(-1 * ccwRotation * LIMIT);

        topRightMotor.setPower(ccwRotation * LIMIT);
        bottomRightMotor.setPower(ccwRotation * LIMIT);
    }
}

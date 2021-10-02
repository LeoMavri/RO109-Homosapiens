package com.homosapiens.teamcode.controls;

import com.homosapiens.teamcode.utils.HomosapiensLogger;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;

import java.util.Locale;

public class GamepadControls {
    public static double MOTOR_THROTTLE = .7;

    private static float getGamepadX(Gamepad gamepad) {
        return gamepad.left_stick_x;
    }
    private static float getGamepadY(Gamepad gamepad) {
        return -gamepad.left_stick_y;
    }

    private static float getGamepadLeftTrigger(Gamepad gamepad) {
        return gamepad.left_trigger;
    }
    private static float getGamepadRightTrigger(Gamepad gamepad) {
        return gamepad.right_trigger;
    }

    private static float getGamepadDpadUp(Gamepad gamepad)    { return gamepad.dpad_up    ? 1.0f : 0.0f; }
    private static float getGamepadDpadLeft(Gamepad gamepad)  { return gamepad.dpad_left  ? 1.0f : 0.0f; }
    private static float getGamepadDpadDown(Gamepad gamepad)  { return gamepad.dpad_down  ? 1.0f : 0.0f; }
    private static float getGamepadDpadRight(Gamepad gamepad) { return gamepad.dpad_right ? 1.0f : 0.0f; }

    /**
     * processes joystick input into robot movement on the x and y axes
     * @param gamepad Gamepad
     * @param topLeftMotor DcMotor
     * @param topRightMotor DcMotor
     * @param bottomRightMotor DcMotor
     * @param bottomLeftMotor DcMotor
     * @param logger HomosapiensLogger
     */
    public static void handleJoystickMovement(
            Gamepad gamepad,
            DcMotor topLeftMotor, DcMotor topRightMotor, DcMotor bottomRightMotor, DcMotor bottomLeftMotor,
            HomosapiensLogger logger
    ) {
        final float x = getGamepadX(gamepad);
        final float y = getGamepadY(gamepad);

        logger.toConsole(String.format(Locale.getDefault(), "[INPUT] - received input from the gamepad: x = %f, y = %f", x, y));

        // we will call the topLeft & bottomRight motors right sided motors
        // we will call the topRight & bottomLeft motors left sided motors

        // we might have to normalise x & y
        double rightPower = (y + x);
        double leftPower  = (y - x);

        final double normValue = Math.max(Math.abs(rightPower), Math.abs(leftPower));

        rightPower /= normValue;
        leftPower /= normValue;

        logger.toConsole(String.format(Locale.getDefault(), "[POWER] - sending power to motors: tl & br = %f, tr & bl = %f", rightPower, leftPower));
        logger.toTelemetry(String.format(Locale.getDefault(), "[POWER] - %f %f %f %f", rightPower, leftPower, rightPower, leftPower));

        topLeftMotor.setPower(rightPower);
        bottomRightMotor.setPower(rightPower);

        topRightMotor.setPower(leftPower);
        bottomLeftMotor.setPower(leftPower);
    }

    /**
     * processes trigger inputs into robot rotation around its center axis
     * @param gamepad Gamepad
     * @param topLeftMotor DcMotor
     * @param topRightMotor DcMotor
     * @param bottomRightMotor DcMotor
     * @param bottomLeftMotor DcMotor
     * @param logger HomosapiensLogger
     */
    public static void handleTriggerRotation(
            Gamepad gamepad,
            DcMotor topLeftMotor, DcMotor topRightMotor, DcMotor bottomRightMotor, DcMotor bottomLeftMotor,
            HomosapiensLogger logger
    ) {
        final float rightRotation = getGamepadRightTrigger(gamepad) - getGamepadLeftTrigger(gamepad);

        logger.toConsole(String.format(Locale.getDefault(), "[INPUT] - received input from gamepad: rightRotation = %f", rightRotation));

        final double power = rightRotation * MOTOR_THROTTLE;

        topRightMotor.setPower(power);
        bottomRightMotor.setPower(power);

        topLeftMotor.setPower(-power);
        bottomLeftMotor.setPower(-power);

        logger.toConsole(String.format(Locale.getDefault(), "[POWER] - sending power to motors: tr & br = %f, tl & bl = %f", power, -power));
        logger.toTelemetry(String.format(Locale.getDefault(), "[POWER] - %f %f %f %f", -power, power, power, -power));
    }

    /**
     * this is used for testing, to see in which direction each motor fires
     * @param gamepad Gamepad
     * @param topLeftMotor DcMotor
     * @param topRightMotor DcMotor
     * @param bottomRightMotor DcMotor
     * @param bottomLeftMotor DcMotor
     * @param logger HomosapiensLogger
     */
    public static void handleDpadRotation(
            Gamepad gamepad,
            DcMotor topLeftMotor, DcMotor topRightMotor, DcMotor bottomRightMotor, DcMotor bottomLeftMotor,
            HomosapiensLogger logger
    ) {
        double right = getGamepadDpadRight(gamepad) * MOTOR_THROTTLE;
        double up    = getGamepadDpadUp   (gamepad) * MOTOR_THROTTLE;
        double down  = getGamepadDpadDown (gamepad) * MOTOR_THROTTLE;
        double left  = getGamepadDpadLeft (gamepad) * MOTOR_THROTTLE;

        logger.toConsole(String.format(Locale.getDefault(), "[INPUT] - received input from gamepad: dpadUp = %f, dpadRight = %f, dpadDown = %f, dpadLeft = %f", up, right, down, left));

        topLeftMotor.setPower(up);
        topRightMotor.setPower(right);
        bottomRightMotor.setPower(down);
        bottomLeftMotor.setPower(left);

        logger.toConsole(String.format(Locale.getDefault(), "[POWER] - sending power to motors: tl = %f, tr = %f, br = %f, bl = %f", up, right, down, left));
        logger.toTelemetry(String.format(Locale.getDefault(), "[POWER] - %f %f %f %f", up, right, down, left));
    }
}

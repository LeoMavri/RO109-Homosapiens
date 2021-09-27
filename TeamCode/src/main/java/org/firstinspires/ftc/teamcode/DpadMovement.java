package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name="DpadMovement", group="Freight Frenzy")
public class DpadMovement extends OpMode {

    private ElapsedTime runtime = new ElapsedTime();

    private DcMotor leftBottomDrive  = null;
    private DcMotor leftTopDrive     = null;
    private DcMotor rightBottomDrive = null;
    private DcMotor rightTopDrive    = null;

    @Override
    public void init() {

        leftBottomDrive  = hardwareMap.get(DcMotor.class, "leftbottom");
        leftTopDrive     = hardwareMap.get(DcMotor.class, "lefttop");
        rightBottomDrive = hardwareMap.get(DcMotor.class, "rightbottom");
        rightTopDrive    = hardwareMap.get(DcMotor.class, "righttop");

        leftBottomDrive.setDirection(DcMotor.Direction.FORWARD);
        leftTopDrive.setDirection(DcMotor.Direction.FORWARD);

        rightBottomDrive.setDirection(DcMotor.Direction.REVERSE);
        rightTopDrive.setDirection(DcMotor.Direction.REVERSE);

        telemetry.addData("Status:", "Initialized");

    }

    @Override
    public void loop() {

        double leftBottomPower;
        double leftTopPower;
        double rightBottomPower;
        double rightTopPower;

        if(gamepad1.dpad_up || gamepad2.dpad_up) {

            leftBottomPower  = 1;
            leftTopPower     = 1;
            rightBottomPower = 1;
            rightTopPower    = 1;

            telemetry.addData("Status:", "UP");

        } else if(gamepad1.dpad_down || gamepad2.dpad_down) {

            leftBottomPower  = -1;
            leftTopPower     = -1;
            rightBottomPower = -1;
            rightTopPower    = -1;

            telemetry.addData("Status:", "DOWN");

        } else if(gamepad1.dpad_right || gamepad1.dpad_right) {

            leftBottomPower  = -1;
            leftTopPower     =  1;
            rightBottomPower =  1;
            rightTopPower    = -1;

            telemetry.addData("Status:", "RIGHT");

        }  else if(gamepad1.dpad_left || gamepad2.dpad_left) {

            leftBottomPower  =  1;
            leftTopPower     = -1;
            rightBottomPower = -1;
            rightTopPower    =  1;

            telemetry.addData("Status:", "LEFT");

        } else {

            leftBottomPower  = 0;
            leftTopPower     = 0;
            rightBottomPower = 0;
            rightTopPower    = 0;

            telemetry.addData("Status:", "PAUSE");

        }

        leftBottomDrive.setPower(leftBottomPower);
        leftTopDrive.setPower(leftTopPower);

        rightBottomDrive.setPower(rightBottomPower);
        rightTopDrive.setPower(rightTopPower);

        telemetry.addData("Status:", runtime.toString());

    }

}
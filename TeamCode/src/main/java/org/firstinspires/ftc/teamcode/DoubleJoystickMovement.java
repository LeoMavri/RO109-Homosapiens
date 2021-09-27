package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name="DoubleJoystickMovement", group="Freight Frenzy")
public class DoubleJoystickMovement extends OpMode {

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

        double drive = -gamepad1.left_stick_y;
        double turn  = gamepad1.right_stick_x;

        leftBottomPower  = Range.clip(drive + turn, -1.0, 1.0);
        leftTopPower     = Range.clip(drive + turn, -1.0, 1.0);
        rightBottomPower = Range.clip(drive - turn, -1.0, 1.0);
        rightTopPower    = Range.clip(drive - turn, -1.0, 1.0);

        leftBottomDrive.setPower(leftBottomPower);
        leftTopDrive.setPower(leftTopPower);

        rightBottomDrive.setPower(rightBottomPower);
        rightTopDrive.setPower(rightTopPower);

        telemetry.addData("Status:", Double.toString(drive) + " " + Double.toString(turn));
        telemetry.addData("Status:", runtime.toString());

    }

}

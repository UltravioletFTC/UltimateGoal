package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "L1TeleLogic", group = "L1")
public class L1TeleLogic extends LinearOpMode{

    private DcMotor driveFL;
    private DcMotor driveBL;
    private DcMotor driveFR;
    private DcMotor driveBR;
    private DcMotor wobbleLift;
    private DcMotor intakeLeft;
    private DcMotor intakeRight;
    private Servo flipper;
    private Servo latch;

    @Override
    public void runOpMode() throws InterruptedException{
        driveFL = hardwareMap.dcMotor.get("driveFL");
        driveBL = hardwareMap.dcMotor.get("driveBL");
        driveFR = hardwareMap.dcMotor.get("driveFR");
        driveBR = hardwareMap.dcMotor.get("driveBR");
        wobbleLift = hardwareMap.dcMotor.get("wobbleLift");
        intakeLeft = hardwareMap.dcMotor.get("intakeLeft");
        intakeRight = hardwareMap.dcMotor.get("intakeRight");
        flipper = hardwareMap.servo.get("flipper");
        latch = hardwareMap.servo.get("latch");

        driveFR.setDirection(DcMotor.Direction.REVERSE);
        driveBR.setDirection(DcMotor.Direction.REVERSE);
        intakeLeft.setDirection(DcMotor.Direction.REVERSE);

        DcMotor[] leftDrive = new DcMotor[]{driveFL, driveBL};
        DcMotor[] rightDrive = new DcMotor[]{driveFR, driveBR};

        latch.setPosition(0);

        telemetry.addData("Status", "Initialized");

        wobbleLift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        telemetry.addData("Lift Position", "Reset");

        wobbleLift.setTargetPosition(0);
        telemetry.addData("Desired Lift Position","Locked");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()){

            telemetry.addData("Status", "Active");
            telemetry.addData("Lift Encoder", wobbleLift.getCurrentPosition());
            telemetry.addData("Desired Lift Position", wobbleLift.getTargetPosition());
            telemetry.addData("Latch Position", latch.getPosition());
            telemetry.update();

            if (gamepad1.b){
                latch.setPosition(0);
            }

            else if (gamepad1.x){
                latch.setPosition(.6);
            }

            for (DcMotor motor : leftDrive){
                motor.setPower(-gamepad1.left_stick_y);
            }

            for (DcMotor motor : rightDrive){
                motor.setPower(-gamepad1.right_stick_y);
            }

            if (gamepad1.right_bumper){
                intakeLeft.setPower(-3);
                intakeRight.setPower(-.3);
            }

            else if (gamepad1.left_bumper){
                intakeLeft.setPower(.5);
                intakeRight.setPower(.5);
            }

            else {
                intakeLeft.setPower(0);
                intakeRight.setPower(0);
            }

            if (gamepad1.y){
                wobbleLift.setTargetPosition(2000);
            }

            else if (gamepad1.a) {
                wobbleLift.setTargetPosition(0);
            }

            if (wobbleLift.getTargetPosition() > 0){
                wobbleLift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                wobbleLift.setPower(1);
            }

            else {
                wobbleLift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                wobbleLift.setPower(0);
            }

            idle();

        }
    }
}

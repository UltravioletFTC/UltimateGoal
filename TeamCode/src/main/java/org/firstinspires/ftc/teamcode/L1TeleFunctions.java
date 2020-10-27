package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;


@TeleOp(name = "L1TeleFunctions", group = "L1")

public class L1TeleFunctions extends LinearOpMode{
    YoovyWare yoovy = new YoovyWare();

    @Override
    public void runOpMode() throws InterruptedException{

        double latchClosedPosition = .6;
        double flipperClosedPosition = 0;
        double wobblePower = 1;
        int wobbleTopPosition = 1600;
        double intakePower = .4;
        double outtakePower = .5;
        double speedModifier = 1;

        yoovy.init(hardwareMap);
        yoovy.intialization(latchClosedPosition, flipperClosedPosition);

        telemetry.addData("Status", "Locked and Loaded B)");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()){

            //Controls the drive train
            yoovy.driveTrain(gamepad1.left_stick_y, gamepad1.right_stick_y, speedModifier);

            //Controls the intake and outtake
            yoovy.intakeFunction(intakePower, outtakePower, gamepad1.right_bumper, gamepad1.left_bumper);

            //Controls the wobble arm
            yoovy.wobbleLift(wobbleTopPosition, wobblePower, gamepad1.y, gamepad1.a);

            //Controls the wobble goal latch
            yoovy.secureLatch(latchClosedPosition, gamepad1.x);

            //Controls the flipper
            yoovy.secureFlipper(flipperClosedPosition, gamepad1.b);

            idle();
        }
    }
}

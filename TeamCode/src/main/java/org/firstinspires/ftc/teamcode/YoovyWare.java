package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class YoovyWare {

    //define motors
    private DcMotor driveFL;
    private DcMotor driveBL;
    private DcMotor driveFR;
    private DcMotor driveBR;
    private DcMotor intakeLeft;
    private DcMotor intakeRight;
    private DcMotor wobbleArm;

    //Define servos
    private Servo flipper;
    private Servo latch;

    //Make an object out of the hardware map
    HardwareMap hardwareMap = null;

    public YoovyWare() {
    }

    public void init(HardwareMap hardwareMapFunc) {
        hardwareMap = hardwareMapFunc;

        //Defining drive motors
        driveFL = hardwareMap.dcMotor.get("driveFL");
        driveBL = hardwareMap.dcMotor.get("driveBL");
        driveFR = hardwareMap.dcMotor.get("driveFR");
        driveBR = hardwareMap.dcMotor.get("driveBR");

        //Defining wobble goal motors
        wobbleArm = hardwareMap.dcMotor.get("wobbleLift");
        intakeLeft = hardwareMap.dcMotor.get("intakeLeft");
        intakeRight = hardwareMap.dcMotor.get("intakeRight");

        //Defining servos
        flipper = hardwareMap.servo.get("flipper");
        latch = hardwareMap.servo.get("latch");

        //Reverse direction of necessary motors
        intakeLeft.setDirection(DcMotor.Direction.REVERSE);

    }



    /*Initialization Functions*/
    public void intialization (double latchClosedPosition, double flipperDownPosition){
        latch.setPosition(latchClosedPosition);
        flipper.setPosition(flipperDownPosition);
        wobbleArm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }



    /* Drive Train Functions*/

    //Sets power determined by aq maximum speed and a percentage applied later.
    public void driveTrain(double leftBaseSpeed, double rightBaseSpeed, double speedModifier) {

        //Sets the drive train to run without encoders
        driveFL.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        driveBL.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        driveFR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        driveBR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        driveFL.setPower(leftBaseSpeed * speedModifier);
        driveBL.setPower(leftBaseSpeed * speedModifier);
        driveFR.setPower(rightBaseSpeed * speedModifier);
        driveBR.setPower(rightBaseSpeed * speedModifier);
    }


    public void driveTrain(double distanceInches, double speedControl){

        //Sets the drive train to run with encoders
        driveFL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        driveBL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        driveFR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        driveBR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);



    }

    /*Intake Functions*/

    //Determines if the intake should be used and adjusts power accordingly.
    public void intakeFunction(double intakePower, double outtakePower, boolean intakeCommand, boolean outtakeCommand) {

        if (intakeCommand){
            intakeLeft.setPower(intakePower);
            intakeRight.setPower(intakePower);
        }

        else if (outtakeCommand){
            intakeLeft.setPower(-outtakePower);
            intakeRight.setPower(-outtakePower);
        }

        else {
            intakeLeft.setPower(0);
            intakeRight.setPower(0);
        }

    }



    /*Wobble Arm Motor Functions*/

    //Declares and sets starting power and position for the Wobble Goal Arm.

    //Function for lifting the Wobble Goal Arm.
    public void wobbleLift(int wobblePositionTarget, double wobblePower, boolean commandUp, boolean commandDown){

        double wobblePositionTrue = wobbleArm.getCurrentPosition();
        wobbleArm.setTargetPosition(wobblePositionTarget);

        if (commandUp && wobblePositionTrue <= wobblePositionTarget){
            wobbleArm.setPower(wobblePower);
            wobbleArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }
        else if (commandDown && wobblePositionTrue >= 50){
            wobbleArm.setPower(0);
            wobbleArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }

    }



    /*Latch Servo Functions*/

    boolean priorState = false;
    boolean openPosition = false;

    public void secureLatch (double closedPosition, boolean buttonInput){
        if (buttonInput && priorState != buttonInput){
            if (!openPosition){
                latch.setPosition(0);
                openPosition = true;
            }
            else{
                latch.setPosition(closedPosition);
                openPosition = false;
            }
        }
        priorState = buttonInput;
    }



    /*Flipper Servo Options*/

    boolean formerState = false;
    boolean openPositionFlipper;

    public void secureFlipper ( double closedPosition, boolean buttonInput){
        if (buttonInput && formerState != buttonInput){
            if (!openPositionFlipper){
                flipper.setPosition(1);
                openPositionFlipper = true;
            }
            else{
                flipper.setPosition(closedPosition);
                openPositionFlipper = false;
            }
        }
        formerState = buttonInput;
    }
}



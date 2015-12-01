package com.todd.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import java.text.SimpleDateFormat;
import java.util.Date;

public class BasicOpMode extends OpMode {
    private String startDate;
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor motorRight;
    private DcMotor motorLeft;
    private DcMotor motorArm;

    public BasicOpMode() {

    }

    @Override
    public void init() {
        startDate = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date());
        motorRight = hardwareMap.dcMotor.get("motor_2");
        motorLeft = hardwareMap.dcMotor.get("motor_1");
        motorArm = hardwareMap.dcMotor.get("arm_1");
        motorLeft.setDirection(DcMotor.Direction.REVERSE);
    }

    @Override
    public void stop() {
        telemetry.addData("3 Stopping", "Stopped");
    }

    @Override
    public void loop() {
        telemetry.addData("1 Start", "BasicOp started at " + startDate);
        telemetry.addData("2 Status", "running for " + runtime.toString());

        float throttle = -gamepad1.left_stick_y;
        float direction = gamepad1.left_stick_x;
        float throttle_arm = -gamepad1.right_stick_y;


        float right = throttle - direction;
        float left = throttle + direction;
        float arm = throttle_arm;

        right = Range.clip(right, -1, 1);
        left = Range.clip(left, -1, 1);
        arm = Range.clip(arm, -1, 1);

        // scale the joystick value to make it easier to control
        // the robot more precisely at slower speeds.
        right = (float)scaleInput(right);
        left =  (float)scaleInput(left);
        arm = (float)softScaleInput(arm);


        // write the values to the motors
        motorRight.setPower(right);
        motorLeft.setPower(left);
        motorArm.setPower(arm);

        telemetry.addData("arm tg pwr", "arm pwr: " + String.format("%.2f", arm));
        telemetry.addData("left tgt pwr", "left  pwr: " + String.format("%.2f", left));
        telemetry.addData("right tgt pwr", "right pwr: " + String.format("%.2f", right));
    }

    double softScaleInput(double dVal){
        double[] scaleArray = { 0.0, 0.05, 0.07, 0.10, 0.12, 0.15, 0.15 };

        int index = (int)(dVal * 6.0);
        if(index < 0){
            index = -index;
        }

        if(index > 16) index = 5;
        // get value from the array.
        double dScale = 0.0;
        if (dVal < 0) {
            dScale = -scaleArray[index];
        } else {
            dScale = scaleArray[index];
        }

        // return scaled value.
        return dScale;

    }

    double scaleInput(double dVal)  {
        double[] scaleArray = { 0.0, 0.05, 0.09, 0.10, 0.12, 0.15, 0.18, 0.24,
                0.30, 0.36, 0.43, 0.50, 0.60, 0.72, 0.85, 1.00, 1.00 };

        // get the corresponding index for the scaleInput array.
        int index = (int) (dVal * 16.0);

        // index should be positive.
        if (index < 0) {
            index = -index;
        }

        // index cannot exceed size of array minus 1.
        if (index > 16) {
            index = 16;
        }

        // get value from the array.
        double dScale = 0.0;
        if (dVal < 0) {
            dScale = -scaleArray[index];
        } else {
            dScale = scaleArray[index];
        }

        // return scaled value.
        return dScale;
    }

}

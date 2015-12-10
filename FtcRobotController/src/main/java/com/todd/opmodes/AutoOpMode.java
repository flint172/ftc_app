package com.todd.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.TouchSensor;

/**
 * Created by Dad on 11/10/2015.
 */
public class AutoOpMode extends LinearOpMode {
    final static double MOTOR_POWER = 0.15;
    private DcMotor motorRight;
    private DcMotor motorLeft;
    private DcMotor motorArm;
    private TouchSensor touchSensor;

    @Override
    public void runOpMode() throws InterruptedException {
        motorLeft = hardwareMap.dcMotor.get("motor_1");
        motorRight = hardwareMap.dcMotor.get("motor_2");
        touchSensor = hardwareMap.touchSensor.get("touch_1");
        motorArm = hardwareMap.dcMotor.get("arm_1");

        motorLeft.setDirection(DcMotor.Direction.REVERSE);

        waitForStart();
        Boolean sensorTouched = false;
        while(true){
            motorRight.setPower(MOTOR_POWER);
            motorLeft.setPower(MOTOR_POWER);

            if(touchSensor.isPressed()){
                sensorTouched = true;
                motorRight.setPower(0);
                motorLeft.setPower(0);
                motorArm.setPower(MOTOR_POWER * -1.0);
                sleep(600);
                motorArm.setPower(0.0);
                motorArm.setPower(MOTOR_POWER);
                sleep(700);
                motorArm.setPower(0.0);
                motorRight.setPower(MOTOR_POWER * -1.0);
                motorLeft.setPower((MOTOR_POWER * -1.0)-0.05);
                sleep(1500);
                motorRight.setPower(0);
                motorLeft.setPower(0);
            }
        }
    }
}

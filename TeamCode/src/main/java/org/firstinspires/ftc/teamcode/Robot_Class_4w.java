package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;


/**
 * Created by Team 6934 on 5/5/2017.
 */

public class Robot_Class_4w {


    // Constructor
    public Robot_Class_4w() {
    }

    // Variables..................................//

    // Public Variables
    public DcMotor Motor_1 = null;
    public DcMotor Motor_2 = null;
    public DcMotor Motor_3 = null;
    public DcMotor Motor_4 = null;

    // private Variables
    HardwareMap hwMap = null;


    // Methods.....................................//

    // Init Robot........
    public void Init_Robo(HardwareMap ahwMap) {

        hwMap = ahwMap;

        //Change the name the motors between the " " to suit your robots configuration .
        //Motor order is Motor 1=RR 2=LR 3=RF 4=LF
        Motor_1 = hwMap.dcMotor.get("motor_1");
        Motor_2 = hwMap.dcMotor.get("motor_3");
        Motor_3 = hwMap.dcMotor.get("motor_2");
        Motor_4 = hwMap.dcMotor.get("motor_4");

        Motor_2.setDirection(DcMotorSimple.Direction.REVERSE);
        Motor_4.setDirection(DcMotorSimple.Direction.REVERSE);

    }

    //Manual Drive
    public void Drive(double pwr) {
        Motor_1.setPower(pwr);
        Motor_2.setPower(pwr);
        Motor_3.setPower(pwr);
        Motor_4.setPower(pwr);
    }

    //Manual Turn
    public void Turn( int dir , double speed ) {
        switch (dir) {
            case 0:// Turns Left
                Motor_1.setPower(speed);
                Motor_2.setPower(-speed);
                Motor_3.setPower(speed);
                Motor_4.setPower(-speed);
                break;

            case 1:// Turns Right
                Motor_1.setPower(-speed);
                Motor_2.setPower(speed);
                Motor_3.setPower(-speed);
                Motor_4.setPower(speed);
                break;
        }
    }

    //Manual Stop
    public void Stop() {
        Motor_1.setPower(0.0);
        Motor_2.setPower(0.0);
        Motor_3.setPower(0.0);
        Motor_4.setPower(0.0);
    }

}



















///////////////////////END
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;


/**
 * Created by Team 6934 on 3/1/2017.
 */

public class Robot_Class_2w {


    // Constructor
    public Robot_Class_2w() {
    }

    // Variables..................................//

    // Public Variables
    public DcMotor Motor_1 = null;
    public DcMotor Motor_2 = null;

    // private Variables
    HardwareMap hwMap = null;


    // Methods.....................................//

    // Init Robot........
    public void Init_Robo(HardwareMap ahwMap) {

        hwMap = ahwMap;

        //Change the name the motors between the " " to suit your robots configuration .
        Motor_1 = hwMap.dcMotor.get("left_drive");
        Motor_2 = hwMap.dcMotor.get("right_drive");

        Motor_2.setDirection(DcMotorSimple.Direction.REVERSE);

    }

    //Manual Drive
    public void Drive(double pwr) {
        //pwr = pwr*OpModeConstantsWithEncoder.AUTO_SPEED_MULT;
        Motor_1.setPower(pwr);
        Motor_2.setPower(pwr);
    }

    //Manual Turn
    public void Turn( int dir , double speed ) {
        switch (dir) {
            case 0:// Turns Left
                Motor_1.setPower(speed);
                Motor_2.setPower(-speed);
                break;

            case 1:// Turns Right
                Motor_1.setPower(-speed);
                Motor_2.setPower(speed);
                break;
        }
    }

    //Manual Stop
    public void Stop() {
        Motor_1.setPower(0.0);
        Motor_2.setPower(0.0);
    }

}



















///////////////////////END
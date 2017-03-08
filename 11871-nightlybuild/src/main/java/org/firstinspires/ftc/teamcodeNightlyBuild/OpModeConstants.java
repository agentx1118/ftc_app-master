package org.firstinspires.ftc.teamcodeNightlyBuild;

/**
 * Created by HSSROBOTICS on 2/16/2017.
 */

public class OpModeConstants
{
    public static final double RIGHT_MOTOR_OFFSET = 0.0;   // The speed offset of the right motor
    public static final double LEFT_MOTOR_OFFSET = 0.0;    // The speed offset of the left motor
    public static final double SPEED_MULT = 0.6;           // Multiplier for the speed of both drive motors
    public static final double AUTO_SPEED_FACTOR = 3;      // Multiplier for AUTO_SPEED_MULTIPLIER
    public static final double AUTO_SPEED_MULT =           // Multiplier for the speed of both drive motors during autonomous
        (SPEED_MULT*AUTO_SPEED_FACTOR > 1) ? 1 : SPEED_MULT*AUTO_SPEED_FACTOR;


    public static final double ARM_MIN_POS = 0.0;          // The minimum arm position
    public static final double ARM_MAX_POS = 0.9;          // The maximum arm position
    public static final double ARM_SPEED_MULT = .03;       // Multiplier for the speed of the arm
}

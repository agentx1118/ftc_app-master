package org.firstinspires.ftc.teamcodeNightlyBuild;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Gamepad;

/**
 * Created by HSSROBOTICS on 10/3/2017.
 */

public class Runthrough extends LinearOpMode
{
    HardwarePushbot_VoltronConfig robot = new HardwarePushbot_VoltronConfig();
    public void runOpMode()
    {
        robot.init(hardwareMap);

        while(opModeIsActive());
        {
            double lPower = gamepad1.left_stick_y;
            double rPower = gamepad1.left_stick_y;
            robot.leftMotor.setPower(lPower);
            robot.rightMotor.setPower(rPower);
        }
    }
}

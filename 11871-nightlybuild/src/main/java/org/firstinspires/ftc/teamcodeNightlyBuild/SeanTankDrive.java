package org.firstinspires.ftc.teamcodeNightlyBuild;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.Range;
import com.qualcomm.robotcore.hardware.IrSeekerSensor;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;
import org.firstinspires.ftc.robotcontroller.external.samples.HardwarePushbot;


/**
 * Created by HSSROBOTICS on 2/11/2017.
 */

@TeleOp(name="Sean OpMode: Tank Drive (Warning: Experimental)", group="Pushbot")
//@Disabled
public class SeanTankDrive extends LinearOpMode{
    HardwarePushbot robot = new HardwarePushbot();

    private void moveForward(double speed)
    {
        speed = Math.abs(speed);
        double left = speed;
        double right = speed;
        if (speed > 1.0)
        {
            left = right = 1.0;
        }

        robot.leftMotor.setPower(left);
        robot.rightMotor.setPower(right-.05);
    }

    private void moveBackward(double speed)
    {
        speed = Math.abs(speed);
        double left = -speed;
        double right = -speed;
        if (speed > 1.0)
        {
            left = right = -1.0;
        }

        robot.leftMotor.setPower(left);
        robot.rightMotor.setPower(right+.05);
    }


    @Override
    public void runOpMode()
    {
        double left;
        double right;
        double max;

        robot.init(hardwareMap);

        waitForStart();

        boolean toggle = false;

        long lastTime = System.currentTimeMillis();

        while(opModeIsActive())
        {
            left = -gamepad1.left_stick_y;
            right = -gamepad1.right_stick_y;

            if(right-.3 >= 0)
                right -= .3;
            else
                right += .3;

            if(left-.25 >= 0)
                left -= .25;
            else if(left+.25 < 0)
                left += .25;

            max = Math.max(Math.abs(left), Math.abs(right));

            if(max > 1.0)
            {
                left /= max;
                right /= max;
            }

            robot.leftMotor.setPower((left));
            robot.rightMotor.setPower((right));

            telemetry.addData("left_drive", "%2f", left);
            telemetry.addData("right_drive", "%2f", right);
            telemetry.update();

            robot.waitForTick(40);
        }
    }

}
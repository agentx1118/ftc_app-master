package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

/**
 * Created by Sean on 2/21/2017.
 */

@Autonomous(name="Voltron Arm Test: Not intended as an actual OpMode, use with caution", group = "Pushbot")
//@Disabled
public class ArmRangeTest extends LinearOpMode{

    HardwarePushbot_VoltronConfig robot = new HardwarePushbot_VoltronConfig();

    static final double armSpeed = 0.005;

    @Override
    public void runOpMode()
    {
        robot.init(hardwareMap);

        telemetry.addData("Status", "Ready to run");
        telemetry.update();
        waitForStart();
        robot.armServo.scaleRange(OpModeConstants.ARM_MIN_POS, OpModeConstants.ARM_MAX_POS);
        robot.armServo.setPosition(1.0);
        telemetry.addData("Arm Position", "%2.5f", robot.armServo.getPosition());
        telemetry.update();
        sleep(5000);

        while(opModeIsActive() && robot.armServo.getPosition() > 0.0)
        {
            robot.armServo.setPosition(robot.armServo.getPosition() - armSpeed);
            telemetry.addData("Arm Position", "%2.5f", robot.armServo.getPosition());
            telemetry.update();
            if(robot.armServo.getPosition() == 1.0)
            {
                telemetry.addData("Status", "Completed, arm is at full range");
            }

            try
            {
                Thread.sleep(300);
            }
            catch (InterruptedException e)
            {
                Thread.currentThread().interrupt();
            }

            robot.waitForTick(40);
        }
    }
}

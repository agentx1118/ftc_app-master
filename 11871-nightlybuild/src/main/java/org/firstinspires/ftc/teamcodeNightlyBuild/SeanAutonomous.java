package org.firstinspires.ftc.teamcodeNightlyBuild;

/**
 * Created by HSSROBOTICS on 2/11/2017.
 */

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcontroller.external.samples.HardwarePushbot;

/**
 * This file illustrates the concept of driving a path based on time.
 * It uses the common Pushbot hardware class to define the drive on the robot.
 * The code is structured as a LinearOpMode
 *
 * The code assumes that you do NOT have encoders on the wheels,
 *   otherwise you would use: PushbotAutoDriveByEncoder;
 *
 *   The desired path in this example is:
 *   - Drive forward for 3 seconds
 *
 *  The code is written in a simple form with no optimizations.
 *  However, there are several ways that this type of sequence could be streamlined,
 *
 * Use Android Studios to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list
 */

@Autonomous(name="Sean's Autonomous: Time-Based Driving", group="Pushbot")
//@Disabled
public class SeanAutonomous extends LinearOpMode {

    /* Declare OpMode members. */
    private HardwarePushbot_VoltronConfig robot   = new HardwarePushbot_VoltronConfig();   // Use a Pushbot's hardware
    private ElapsedTime     runtime = new ElapsedTime();


    private static final double     FORWARD_SPEED_LEFT = 1;
    private static final double     FORWARD_SPEED_RIGHT = 1;

    @Override
    public void runOpMode() {

        /*
         * Initialize the drive system variables.
         * The init() method of the hardware class does all the work here
         */
        robot.init(hardwareMap);

        // Send telemetry message to signify robot waiting;
        telemetry.addData("Status", "Ready to run");    //
        telemetry.update();

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        // Step through each leg of the path, ensuring that the Auto mode has not been stopped along the way

        // Step 1:  Drive forward for 3 seconds
        robot.leftMotor.setPower(FORWARD_SPEED_LEFT);
        robot.rightMotor.setPower(FORWARD_SPEED_RIGHT);
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < 1.75)) {
            telemetry.addData("Time", "Time Elapsed: %2.5f seconds", runtime.seconds());
            telemetry.update();
        }

        // Step 2:  Stop the bot
        robot.leftMotor.setPower(0);
        robot.rightMotor.setPower(0);

        telemetry.addData("Path", "Autonomous done, Voltron should be on the mat");
        telemetry.update();
        telemetry.addData("Status", "Program end");
        sleep(3000);
        telemetry.update();
        sleep(1000);
    }
}


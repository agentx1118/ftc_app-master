/*
Copyright (c) 2016 Robert Atkinson

All rights reserved.

Redistribution and use in source and binary forms, with or without modification,
are permitted (subject to the limitations in the disclaimer below) provided that
the following conditions are met:

Redistributions of source code must retain the above copyright notice, this list
of conditions and the following disclaimer.

Redistributions in binary form must reproduce the above copyright notice, this
list of conditions and the following disclaimer in the documentation and/or
other materials provided with the distribution.

Neither the name of Robert Atkinson nor the names of his contributors may be used to
endorse or promote products derived from this software without specific prior
written permission.

NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
"AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESSFOR A PARTICULAR PURPOSE
ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR
TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/
package org.firstinspires.ftc.teamcodeNightlyBuild;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcontroller.external.samples.HardwarePushbot;

/**
 * This OpMode uses the common Pushbot hardware class to define the devices on the robot.
 * All device access is managed through the org.firstinspires.ftc.teamcodeNightlyBuild.HardwarePushbot class.
 * The code is structured as a LinearOpMode
 *
 * This particular OpMode executes a POV Game style Teleop for a PushBot
 * In this mode the left stick moves the robot FWD and back, the Right stick turns left and right.
 * It raises and lowers the claw using the Gampad Y and A buttons respectively.
 * It also opens and closes the claws slowly using the left and right Bumper buttons.
 *
 * Use Android Studios to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list
 */

@TeleOp(name="Zack OpMode: Playground", group="Pushbot")
//@Disabled
public class ZackOpMode extends LinearOpMode {

    /* Declare OpMode members. */
    //Changed from original HardwarePushbot class in order to preserve original class and still
        // allow additional objects (such as the arm's servo) to be added
    HardwarePushbot_VoltronConfig robot = new HardwarePushbot_VoltronConfig();   // Use a Pushbot's hardware

    //  IrSeekerSensor leftDistSense;
    //  OpticalDistanceSensor rightDistSense;

    // could also use HardwarePushbotMatrix class.
    //double clawOffset = 0;                       // Servo mid position
    //final double CLAW_SPEED = 0.02;                   // sets rate to move servo

    // Move the robot forward at speed
    private void moveForward(double speed)
    {
        speed = Math.abs(speed);
        double left = speed;
        double right = speed;
        if (speed > 1.0)
        {
            left = right = 1.0;
        }

        robot.leftMotor.setPower(left + OpModeConstants.LEFT_MOTOR_OFFSET);
        robot.rightMotor.setPower(right + OpModeConstants.RIGHT_MOTOR_OFFSET);
    }

    // Move the robot backward at speed
    private void moveBackward(double speed)
    {
        speed = Math.abs(speed);
        double left = -speed;
        double right = -speed;
        if (speed > 1.0)
        {
            left = right = -1.0;
        }

        robot.leftMotor.setPower(left + OpModeConstants.LEFT_MOTOR_OFFSET);
        robot.rightMotor.setPower(right + OpModeConstants.RIGHT_MOTOR_OFFSET);
    }

    // Turn the robot right in a small radius
    private void turnSharpRight(double speed)
    {
        speed = Math.abs(speed);
        double left = -(speed);
        double right = speed;
        if (speed > 1.0)
        {
            left = -1.0;
            right = 1.0;
        }

        robot.leftMotor.setPower(left - OpModeConstants.LEFT_MOTOR_OFFSET);
        robot.rightMotor.setPower(right + OpModeConstants.RIGHT_MOTOR_OFFSET);
    }

    // Turn the robot left in a small radius
    private void turnSharpLeft(double speed)
    {
        speed = Math.abs(speed);
        double left = speed;
        double right = -(speed);
        if (speed > 1.0)
        {
            left = 1.0;
            right = -1.0;
        }

        robot.leftMotor.setPower(left + OpModeConstants.LEFT_MOTOR_OFFSET);
        robot.rightMotor.setPower(right - OpModeConstants.RIGHT_MOTOR_OFFSET);
    }
    @Override
    public void runOpMode() {
        double left;
        double right;
        double max;
        double newArmPos;

        /* Initialize the hardware variables.
         * The init() method of the hardware class does all the work here
         */
        robot.init(hardwareMap);

        //rightDistSense = hardwareMap.opticalDistanceSensor.get("right_dist");
        //leftDistSense = hardwareMap.irSeekerSensor.get("left_dist");

        // Send telemetry message to signify robot waiting;
        //telemetry.addData("Say", "Hello Driver");    //
        //telemetry.update();

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        boolean toggle = false;

        long lastTime = System.currentTimeMillis();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {

            // Run wheels in POV mode (note: The joystick goes negative when pushed forwards, so negate it)
            // In this mode the Left stick moves the robot fwd and back, the Right stick turns left and right.
            left  = (-gamepad1.left_stick_y + gamepad1.right_stick_x) * OpModeConstants.SPEED_MULT;
            right = (-gamepad1.left_stick_y - gamepad1.right_stick_x) * OpModeConstants.SPEED_MULT;

            // Scales armServo range so it does not exceed the proposed the physical limitations of the bot (Make sure to test ASAP)
            robot.armServo.scaleRange(OpModeConstants.ARM_MIN_POS, OpModeConstants.ARM_MAX_POS);

            //Adds or subtracts offset of motors and normalizes range to a scale of+/-1.0
            double lOffset = (left < 0.0) ? (-OpModeConstants.LEFT_MOTOR_OFFSET) : (OpModeConstants.LEFT_MOTOR_OFFSET);
            double rOffset = (right < 0.0) ? (-OpModeConstants.RIGHT_MOTOR_OFFSET) : (OpModeConstants.RIGHT_MOTOR_OFFSET);
            left += lOffset;
            right += rOffset;
            max = Math.max(Math.abs(left), Math.abs(right));
            if (max > 1)
            {
                left /= max;
                right /= max;
            }

            robot.leftMotor.setPower(left);
            robot.rightMotor.setPower(right);

            // Use gamepad left & right Bumpers to open and close the claw
            // if (gamepad1.right_bumper)
            // clawOffset += CLAW_SPEED;
            //else if (gamepad1.left_bumper)
            // clawOffset -= CLAW_SPEED;



            // Move both servos to new position.  Assume servos are mirror image of each other.
            //clawOffset = Range.clip(clawOffset, -0.5, 0.5);

            // String msg = "IR Angle: " + leftDistSense.getAngle() + " IR Strength: " + leftDistSense.getStrength();
            // telemetry.addLine(msg);
            // telemetry.addData("Optical", rightDistSense.getLightDetected());

            // robot.leftClaw.setPosition(robot.MID_SERVO + clawOffset);
            //robot.rightClaw.setPosition(robot.MID_SERVO - clawOffset);

            // Use the left stick on controller 2 to move the arm

            robot.armServo.setPosition(robot.armServo.getPosition() + (OpModeConstants.ARM_SPEED_MULT * gamepad2.left_stick_y));

            // Send telemetry message to signify robot running
            // telemetry.addData("claw",  "Offset = %.2f", clawOffset);
            telemetry.addData("left_drive",  "%.2f", left);
            telemetry.addData("right_drive", "%.2f", right);
            telemetry.addData("arm_servo", "%.2f", robot.armServo.getPosition());
            telemetry.update();

            // Pause for metronome tick.  40 mS each cycle = update 25 times a second.
            robot.waitForTick(40);
        }
    }
}

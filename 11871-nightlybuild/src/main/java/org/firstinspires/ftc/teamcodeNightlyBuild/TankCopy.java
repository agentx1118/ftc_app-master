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



@TeleOp(name="Sean OpMode: Tank Drive (Warning: Experience required)", group="Pushbot")
//@Disabled
public class TankCopy extends LinearOpMode{
    HardwarePushbot_VoltronConfig robot = new HardwarePushbot_VoltronConfig();

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
            right = -gamepad1.right_stick_y * OpModeConstants.SPEED_MULT;
            left = -gamepad1.left_stick_y * OpModeConstants.SPEED_MULT;


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


            robot.armServo.setPosition(robot.armServo.getPosition() + (OpModeConstants.ARM_SPEED_MULT * gamepad2.left_stick_y));
            //robot.clawServo.setPosition(robot.clawServo.getPosition() + (OpModeConstants.CLAW_SPEED_MULT * gamepad2.right_stick_y));

            telemetry.addData("left_drive", "%2f", left);
            telemetry.addData("right_drive", "%2f", right);
            telemetry.addData("arm_servo", "%.2f", robot.armServo.getPosition());
            telemetry.update();
            // Potential method of switching controllers in an emergency?

            /*if(gamepad2.back)
            {
                Gamepad temp = gamepad1;
                gamepad1 = gamepad2;
                gamepad2 = temp;
            }*/
            robot.waitForTick(40);
        }
    }

}

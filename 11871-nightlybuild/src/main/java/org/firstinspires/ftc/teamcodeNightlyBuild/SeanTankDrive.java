package org.firstinspires.ftc.teamcodeNightlyBuild;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;
import com.qualcomm.robotcore.hardware.IrSeekerSensor;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;
import org.firstinspires.ftc.robotcontroller.external.samples.HardwarePushbot;


/**
 * Created by HSSROBOTICS on 2/11/2017.
 */



@TeleOp(name="Sean OpMode: Tank Drive", group="Pushbot")
//@Disabled
public class SeanTankDrive extends LinearOpMode{
    HardwarePushbot_VoltronConfigEncoder robot = new HardwarePushbot_VoltronConfigEncoder();

    private double arm;

    private void updateArm()
    {
        boolean isNorm = true;
        robot.armMotor.setTargetPosition(robot.armMotor.getCurrentPosition()+(int)(arm*100));
        robot.armMotor.setPower(.5);
        if(Math.abs(arm) > 1)
        {
            arm = arm/arm;
        }
        if((robot.armMotor.getCurrentPosition() > 450) && !isNorm)
        {
            robot.armMotor.setPower(-1);
            robot.armMotor.setTargetPosition(robot.armMotor.getCurrentPosition()-20);
            //robot.armMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
        else if(robot.armMotor.getCurrentPosition() < 20 && !isNorm)
        {
            robot.armMotor.setPower(1);
            robot.armMotor.setTargetPosition(robot.armMotor.getCurrentPosition()+20);

        }
        else
        {
            robot.armMotor.setPower(.1);
            robot.armMotor.setTargetPosition(robot.armMotor.getCurrentPosition()+(int)(-gamepad2.left_stick_y*10));

            //robot.armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }
        robot.armMotor.setPower(0);

        if(gamepad2.x && !isNorm) {
                /*robot.armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                robot.armMotor.setMaxSpeed(3000);
                robot.armMotor.setPower(-.025);
                robot.armMotor.setTargetPosition(180);
                while(robot.armMotor.getCurrentPosition() > 20)
                {
                    robot.armMotor.setPower(-.025);
                }*/
            robot.armMotor.setMaxSpeed(4000);
            robot.armMotor.setPower(1);
            robot.armMotor.setTargetPosition(450);

            //robot.armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }
        if(gamepad2.y && !isNorm) {
                /*robot.armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                robot.armMotor.setMaxSpeed(3000);
                robot.armMotor.setPower(-.025);
                robot.armMotor.setTargetPosition(180);
                while(robot.armMotor.getCurrentPosition() > 20)
                {
                    robot.armMotor.setPower(-.025);
                }*/
            robot.armMotor.setMaxSpeed(4000);
            robot.armMotor.setPower(-1);
            robot.armMotor.setTargetPosition(0);

            //robot.armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }
        if(isNorm)
        {
            robot.armMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            robot.armMotor.setPower(arm/4);
        }
    }

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

    private void scoop()
    {
        robot.scoopServo.setPosition(1);
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

            arm = gamepad2.left_stick_y;

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


            //robot.armServo.setPosition(robot.armServo.getPosition() + (OpModeConstants.ARM_SPEED_MULT * gamepad2.left_stick_y));
            //robot.clawServo.setPosition(robot.clawServo.getPosition() + (OpModeConstants.CLAW_SPEED_MULT * gamepad2.right_stick_y));

            //updateArm();
            /*int scoopcounter = 0;
            if(gamepad2.a)
            {
                scoop();
            }
            if(robot.scoopServo.getPosition() == 1)
            {
                scoopcounter++;
            }
            if(scoopcounter == 100)
                robot.scoopServo.setPosition(0);*/
            robot.scoopServo.setPosition(robot.scoopServo.getPosition()+gamepad2.right_stick_y*0.05);
            telemetry.addData("left_drive", "%2f", left);
            telemetry.addData("right_drive", "%2f", right);
            telemetry.addData("arm_servo", "%.2f", gamepad2.left_stick_y);
            telemetry.addData("scoop_motor", "%.2f", robot.scoopServo.getPosition());
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

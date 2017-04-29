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

    private double increment = degreesToEncoder(25);
    private int    max       = degreesToEncoder(-120);
    private int    min       = degreesToEncoder(0);

    private void updateArm(double arm)
    {
        if(Math.abs(arm) > 1)
        {

            arm = arm/arm;
        }
        robot.armMotor.setPower(.5);
        if(!(isOutOfBounds_Up() || isOutOfBounds_Down()))
            robot.armMotor.setTargetPosition(robot.armMotor.getCurrentPosition()+((int)(arm*increment)));
        else if(isOutOfBounds_Up())
            robot.armMotor.setTargetPosition(max+1);
        else if(isOutOfBounds_Down())
            robot.armMotor.setTargetPosition(min-1);
        else
            robot.armMotor.setTargetPosition(robot.armMotor.getCurrentPosition());

        //Throws ball using x-button on gamepad 2
        try
        {
            if(gamepad2.x)
            {
                robot.armMotor.setPower(1);
                robot.armMotor.setTargetPosition(degreesToEncoder(95));
            }
        }
        catch(Exception Ex){
            robot.armMotor.setTargetPosition(robot.armMotor.getCurrentPosition());
        }
        finally{
            robot.armMotor.setTargetPosition(robot.armMotor.getCurrentPosition());
        }
    }

    private boolean isOutOfBounds_Up()
    {
        if(robot.armMotor.getCurrentPosition() < max)
            return true;
        return false;
    }

    private boolean isOutOfBounds_Down()
    {
        if(robot.armMotor.getCurrentPosition() > min)
            return true;
        return false;
    }

    private int encoderToDegrees(int enc)
    {
        return(enc/4);
    }

    private int degreesToEncoder(int deg)
    {
        return(deg*4);
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
        double arm;
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

            updateArm(arm);
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

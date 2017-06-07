package org.firstinspires.ftc.teamcodeNightlyBuild;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

/**
 * Created by team 6934 on 5/5/2017.
 */

public class Vuforia_Class {

    // Constructor
    public Vuforia_Class() {
    }

    VuforiaTrackables targets;
    VuforiaTrackableDefaultListener listener;
    VuforiaLocalizer vuforia_localizer;
    float Tx,Tz,Ty,Deg;
    String target_name;
    private float offset=3;


    //Vuforia Set Up
    public void Init_Vuforia(){

        // Vuforia LicenseKey Link .. https://developer.vuforia.com/user/register

        VuforiaLocalizer.Parameters params = new VuforiaLocalizer.Parameters(R.id.cameraMonitorViewId);
        params.vuforiaLicenseKey = "AdTIUtX/////AAAAGQ43a6Hzj0l7u44u1aTk46kgTaGatyDlTibnnD9bxQQyZrl7KULrKsmNxhgQhSk19WMiY3NRlnq1IQn1jbPEPUKXhN9e6c82NYYDbJ3C8OGxdGET8wzAJ0sFtq6klvOlAJUhXc/JFfwbyRP86NSpQMXEaZGruegYVCDZZGwa3zhmcUdqtfRax3PnmmzC3F8ll2WxTVd6LpzE1FP1tbFO+EVZEosY/v0o/Pid+ii01gA8bZAiH7gwmrscnWRZlUunGQ92rXgPw8pB+2DaY8USaUHN2MudwERnG0ugAWaJftUtkKLPmmqSlzeHMcM7sBiXS+VmrlY3HPqLb/tpDBV7mWBoZ6oV2RRscFb634orRzRV";
        params.cameraDirection = VuforiaLocalizer.CameraDirection.FRONT; //FRONT / BACK
        params.cameraMonitorFeedback = VuforiaLocalizer.Parameters.CameraMonitorFeedback.AXES;
        vuforia_localizer = ClassFactory.createVuforiaLocalizer(params);

        targets = vuforia_localizer.loadTrackablesFromAsset("FTC_2016-17");
        targets.get(0).setName("wheels");
        targets.get(1).setName("tools");
        targets.get(2).setName("legos");
        targets.get(3).setName("gears");
        targets.activate();

    }

    //Vuforia Track Target
    public void Track_Target(){

        for (VuforiaTrackable targ : targets ){

            listener = (VuforiaTrackableDefaultListener) targ.getListener();
            OpenGLMatrix pose = listener.getPose();

            if( pose != null) {

                VectorF Tdata = pose.getTranslation();
                Tx = Tdata.get(0);
                Ty = Tdata.get(1);
                Tz = Tdata.get(2);

                Tx=Tx+offset;

                Orientation orientation = Orientation.getOrientation(pose, AxesReference.EXTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES);
                Deg = orientation.secondAngle;

                target_name = targ.getName();

            }


        }

        //for(int i=0 ; i < targets.size() ; i++){
        //    VuforiaTrackable targ = targets.get(i);
        //}

    }


}

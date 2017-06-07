package org.firstinspires.ftc.teamcode;

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
    private float offset=0;


    //Vuforia Set Up
    public void Init_Vuforia(){

        // Vuforia LicenseKey Link .. https://developer.vuforia.com/user/register

        VuforiaLocalizer.Parameters params = new VuforiaLocalizer.Parameters(R.id.cameraMonitorViewId);
        params.vuforiaLicenseKey = "ATiFsRz/////AAAAGaVmRpdHd0FKmuQUV/ON/IQsNJeC10jRcKwQUZFNWht/6D0kKxWOcCZt9vuCZ8OnD8GMTAr7/ynbfCA0n2eG5kUGfj8dtQmcl3IOWIkxfGJgvsLeYbScdksE4+mWlSTRk9dhpV3joCdudPkr/RV8WRbGSsTfjd4gzzcevILqCxmgwPYbVph2j2OQSPscGeKzEShAl3xIwWe/TZnJYVE7uOsGqgO8NiTLHLX1lEDt0NT0iLSoK7Ws2i21QqRq9E//pWrtlanpgPzpEkiL3FlXuio6yWgQ+HP3+E8fre1WBh48LPyIeUYffOJA5G9RKoVF2sBwnONhAVqcD0LklgHaUxZpOPXCz/IvSF8g+V6SoS9k\n";
        params.cameraDirection = VuforiaLocalizer.CameraDirection.BACK; //FRONT / BACK
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

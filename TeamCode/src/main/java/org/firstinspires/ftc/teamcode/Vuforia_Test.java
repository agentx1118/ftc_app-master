package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

/**
 * Created by Team 6934 on 6/3/2017.
 */

@TeleOp(name = "Vuforia_Test")

public class Vuforia_Test extends OpMode {

    Vuforia_Class vuforia = new Vuforia_Class();

    @Override
    public void init() {
        vuforia.Init_Vuforia();
    }

    @Override
    public void loop() {
        vuforia.Track_Target();
        Telemetry();
    }

    public void Telemetry(){
        telemetry.addData( "TX_" , vuforia.Tx );
        telemetry.addData( "TY_" , vuforia.Ty );
        telemetry.addData( "TZ_" , vuforia.Tz );
        telemetry.addData( "Deg_" , vuforia.Deg );
        telemetry.addData( "Name_" , vuforia.target_name );
    }

}

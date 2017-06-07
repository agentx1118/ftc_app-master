package org.firstinspires.ftc.teamcodeNightlyBuild;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;


/**
 * Created by team 6934 on 5/5/2017.
 */

@Autonomous(name = "Autonomous_Example")

public class Autonomous_Example extends OpMode {

    //Set Classes and Variables
    Robot_Class_2w robo = new Robot_Class_2w();
    Vuforia_Class vuforia = new Vuforia_Class();
    public String Auto_Mode="";
    //public String Auto_Phase[] = {"Drive_1","Pause_1","Turn_to_Vuforia","Pause_1"};
    //public int phase = 0;


    @Override
    public void init() {

        robo.Init_Robo(hardwareMap);
        vuforia.Init_Vuforia();

    }

    @Override
    public void start() {

        this.resetStartTime();
        Auto_Mode = "Drive_1"; //Auto_Phase[phase] phase=phase+1

    }


    @Override
    public void loop() {

        vuforia.Track_Target();
        Telemetry();

        switch ( Auto_Mode ){

            case "Drive_1":

                if( this.getRuntime() < 1.2  ){
                    robo.Drive( 1.0 );
                }else{
                    robo.Stop();
                    this.resetStartTime();
                    Auto_Mode = "Turn_to_Vuforia";
                    //Auto_Mode = "";
                }
                break;


            case "Turn_to_Vuforia":

                if( this.getRuntime() < 1.0  ){
                    robo.Turn( 0 , 1.0 );
                }else{
                    robo.Stop();
                    this.resetStartTime();
                    Auto_Mode = "Drive_to_Vuforia";
                    //Auto_Mode = "";
                }
                break;


            case "Drive_to_Vuforia":

                if( this.getRuntime() > 1.0 ) {
                    if (vuforia.Tz < -250) {
                        robo.Drive(0.2);
                    } else {
                        robo.Stop();
                        this.resetStartTime();
                        Auto_Mode = "Pause_1";
                        //Auto_Mode = "";
                    }
                }
                break;


            case "Pause_1":

                if( this.getRuntime() > 1.0 ){
                    Auto_Mode = "Drive_away_from_Vuforia";
                }
                break;


            case "Drive_away_from_Vuforia":

                if( vuforia.Tz > -600 ){
                    robo.Drive( -0.2 );
                }else{
                    robo.Stop();
                    Auto_Mode = "Vuforia_Tx";
                    //Auto_Mode = "";
                }
                break;


            case "Vuforia_Tx":
                if (vuforia.Tx > -250) {
                    robo.Turn( 0 , 0.2 );//Turn Left
                } else {
                    robo.Stop();
                    Auto_Mode = "Vuforia_Deg";
                    //Auto_Mode = "";
                }
                break;


            case "Vuforia_Deg":
                if (vuforia.Deg < 0.0 ) {
                    robo.Turn( 1 , 0.2 );//Turn Right
                } else {
                    robo.Stop();
                    Auto_Mode = "";
                }
                break;


            case "":
                telemetry.addData("Good Bye","");
                break;

        }


    }


    public void Telemetry(){
        telemetry.addData( "TX_" , vuforia.Tx );
        telemetry.addData( "TY_" , vuforia.Ty );
        telemetry.addData( "TZ_" , vuforia.Tz );
        telemetry.addData( "Deg_" , vuforia.Deg );
        telemetry.addData( "Name_" , vuforia.target_name );
        telemetry.addData( "Auto_Mode_" , Auto_Mode );
    }


}





























//////////////////////////////////////////////////////////////
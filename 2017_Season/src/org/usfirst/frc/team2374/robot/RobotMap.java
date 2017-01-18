package org.usfirst.frc.team2374.robot;

public class RobotMap {
	
	//Driver Station USB
	public static int driverJoy = 0;
	
	//Razer Sabertooth
	public static int rsLeftAxisY = 1;
	public static int rsRightAxisY = 5;
	
	//CAN
	public static final int talonDriveMasterLeft = 0;
	public static final int talonDriveMasterRight = 1;
	public static final int talonDriveFrontLeft = 2;
	public static final int talonDriveFrontRight = 3;
	public static final int talonDriveBackLeft = 4;
	public static final int talonDriveBackRight = 5;
	
	//PWM
	public static final int speedControllerBelt = 0;
	public static final int speedControllerGrabber = 1;
	
	//DIO
	public static final int encoderBeltA = 0;
	public static final int encoderBeltB = 1;
	public static final int limitSwitchBeltLeft = 2;
	public static final int limitSwitchBeltRight = 3;
	public static final int limitSwitchGrabber = 4;
	
}

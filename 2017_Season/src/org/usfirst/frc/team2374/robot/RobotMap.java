package org.usfirst.frc.team2374.robot;

public class RobotMap {

	// Driver Station USB
	public static int driverJoy = 0;

	// Razer Sabertooth
	public static int rsLeftAxisY = 1;
	public static int rsRightAxisY = 5;
	public static int rsLeftTrigger = 2;
	public static int rsRightTrigger = 3;
	public static int rsLeftBumper = 5;
	public static int rsRightBumper = 6;
	public static int rsButtonX = 3;
	public static int rsButtonA = 1;
	public static int rsButtonY = 4;

	// CAN
	public static final int talonDriveMasterLeft = 1;
	public static final int talonDriveMasterRight = 2;
	public static final int talonDriveFrontLeft = 4;
	public static final int talonDriveFrontRight = 3;
	public static final int talonDriveBackLeft = 6;
	public static final int talonDriveBackRight = 5;

	// PWM
	public static final int speedControllerBelt = 4;
	public static final int speedControllerGrabber = 2;
	public static final int speedControllerClimber = 9;

	// DIO
	public static final int encoderBeltA = 4;
	public static final int encoderBeltB = 5;
	public static final int encoderDriveLA = 0;
	public static final int encoderDriveLB = 1;
	public static final int encoderDriveRA = 2;
	public static final int encoderDriveRB = 3;
	public static final int limitSwitchBeltLeft = 9;
	public static final int limitSwitchBeltRight = 9;
	public static final int limitSwitchGrabberOpen = 9;
	public static final int limitSwitchGrabberClose = 9;

}

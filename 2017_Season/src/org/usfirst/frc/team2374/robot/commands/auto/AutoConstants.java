package org.usfirst.frc.team2374.robot.commands.auto;

import org.usfirst.frc.team2374.robot.Robot;

public class AutoConstants {

	public static double AUTO_DRIVE_TO_TAR;
	public static double AUTO_DRIVE_TO_INCH_FOWRD;
	public static double AUTO_DRIVE_TO_INCH_FOWRD_TIMEOUT;
	public static double AUTO_GRABBER_TIMEOUT;
	public static double AUTO_DRIVE_TO_INCH_GEAR;
	public static double AUTO_DRIVE_TO_INCH_GEAR_TIMEOUT;
	public static double AUTO_DRIVE_TO_INCH_BACK;

	public static double LEFT_FORWARD_RED;
	public static double LEFT_FORWARD_BLUE;
	public static double LEFT_TURN;
	public static double LEFT_TURN_TIMEOUT;

	public static double RIGHT_FORWARD_RED;
	public static double RIGHT_FORWARD_BLUE;
	public static double RIGHT_TURN;
	public static double RIGHT_TURN_TIMEOUT;

	public static double BASE_LINE_SIDE;
	public static double BASE_LINE_CENTER;

	public static double DRIVETOINCH_FAIL_CENTER_FOWRD;
	public static double DRIVETOINCH_FAIL_SIDE_FOWRD;

	public static double CENTER_BELT_ON_TARGET_CENTER;

	public static double CENTER_NO_VISION_FOWRD;

	public static void updatePreferences() {
		AUTO_DRIVE_TO_TAR = Robot.prefs.getDouble("AUTO_DRIVE_TO_TAR", 30);
		AUTO_DRIVE_TO_INCH_FOWRD = Robot.prefs.getDouble("AUTO_DRIVE_TO_INCH_FOWRD", 20);
		AUTO_DRIVE_TO_INCH_FOWRD_TIMEOUT = Robot.prefs.getDouble("AUTO_DRIVE_TO_INCH_FOWRD_TIMEOUT", 1.5);
		AUTO_GRABBER_TIMEOUT = Robot.prefs.getDouble("AUTO_GRABBER_TIMEOUT", 1.0);
		AUTO_DRIVE_TO_INCH_GEAR = Robot.prefs.getDouble("AUTO_DRIVE_TO_INCH_GEAR", 5);
		AUTO_DRIVE_TO_INCH_GEAR_TIMEOUT = Robot.prefs.getDouble("AUTO_DRIVE_TO_INCH_GEAR_TIMEOUT", 0.7);
		AUTO_DRIVE_TO_INCH_BACK = Robot.prefs.getDouble("AUTO_DRIVE_TO_INCH_BACK", -22);
		LEFT_FORWARD_RED = Robot.prefs.getDouble("LEFT_FORWARD_RED", 76);
		LEFT_FORWARD_BLUE = Robot.prefs.getDouble("LEFT_FORWARD_BLUE", 72);
		LEFT_TURN = Robot.prefs.getDouble("LEFT_TURN", 60);
		LEFT_TURN_TIMEOUT = Robot.prefs.getDouble("LEFT_TURN_TIMEOUT", 2.0);
		RIGHT_FORWARD_RED = Robot.prefs.getDouble("RIGHT_FORWARD_RED", 72);
		RIGHT_FORWARD_BLUE = Robot.prefs.getDouble("RIGHT_FORWARD_BLUE", 72);
		RIGHT_TURN = Robot.prefs.getDouble("RIGHT_TURN", -60);
		RIGHT_TURN_TIMEOUT = Robot.prefs.getDouble("RIGHT_TURN_TIMEOUT", 2.0);
		BASE_LINE_SIDE = Robot.prefs.getDouble("BASE_LINE_SIDE", 90);
		BASE_LINE_CENTER = Robot.prefs.getDouble("BASE_LINE_CENTER", 70);
		DRIVETOINCH_FAIL_CENTER_FOWRD = Robot.prefs.getDouble("DRIVETOINCH_FAIL_CENTER_FOWRD", 51);
		DRIVETOINCH_FAIL_SIDE_FOWRD = Robot.prefs.getDouble("DRIVETOINCH_FAIL_SIDE_FOWRD", 9);
		CENTER_BELT_ON_TARGET_CENTER = Robot.prefs.getDouble("CENTER_BELT_ON_TARGET_CENTER", 10);
		CENTER_NO_VISION_FOWRD = Robot.prefs.getDouble("CENTER_NO_VISION_FOWRD", 75.0);

		Robot.prefs.putDouble("AUTO_DRIVE_TO_TAR", AUTO_DRIVE_TO_TAR);
		Robot.prefs.putDouble("AUTO_DRIVE_TO_INCH_FOWRD", AUTO_DRIVE_TO_INCH_FOWRD);
		Robot.prefs.putDouble("AUTO_DRIVE_TO_INCH_FOWRD_TIMEOUT", AUTO_DRIVE_TO_INCH_FOWRD_TIMEOUT);
		Robot.prefs.putDouble("AUTO_GRABBER_TIMEOUT", AUTO_GRABBER_TIMEOUT);
		Robot.prefs.putDouble("AUTO_DRIVE_TO_INCH_GEAR", AUTO_DRIVE_TO_INCH_GEAR);
		Robot.prefs.putDouble("AUTO_DRIVE_TO_INCH_GEAR_TIMEOUT", AUTO_DRIVE_TO_INCH_GEAR_TIMEOUT);
		Robot.prefs.putDouble("AUTO_DRIVE_TO_INCH_BACK", AUTO_DRIVE_TO_INCH_BACK);
		Robot.prefs.putDouble("LEFT_FORWARD_RED", LEFT_FORWARD_RED);
		Robot.prefs.putDouble("LEFT_FORWARD_BLUE", LEFT_FORWARD_BLUE);
		Robot.prefs.putDouble("LEFT_TURN", LEFT_TURN);
		Robot.prefs.putDouble("LEFT_TURN_TIMEOUT", LEFT_TURN_TIMEOUT);
		Robot.prefs.putDouble("RIGHT_FORWARD_RED", RIGHT_FORWARD_RED);
		Robot.prefs.putDouble("RIGHT_FORWARD_BLUE", RIGHT_FORWARD_BLUE);
		Robot.prefs.putDouble("RIGHT_TURN", RIGHT_TURN);
		Robot.prefs.putDouble("RIGHT_TURN_TIMEOUT", RIGHT_TURN_TIMEOUT);
		Robot.prefs.putDouble("BASE_LINE_SIDE", BASE_LINE_SIDE);
		Robot.prefs.putDouble("BASE_LINE_CENTER", BASE_LINE_CENTER);
		Robot.prefs.putDouble("DRIVETOINCH_FAIL_CENTER_FOWRD", DRIVETOINCH_FAIL_CENTER_FOWRD);
		Robot.prefs.putDouble("DRIVETOINCH_FAIL_SIDE_FOWRD", DRIVETOINCH_FAIL_SIDE_FOWRD);
		Robot.prefs.putDouble("CENTER_BELT_ON_TARGET_CENTER", CENTER_BELT_ON_TARGET_CENTER);
		Robot.prefs.putDouble("CENTER_NO_VISION_FOWRD", CENTER_NO_VISION_FOWRD);
	}

}

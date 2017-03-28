package org.usfirst.frc.team2374.robot.commands.auto;

import org.usfirst.frc.team2374.robot.Robot;

public class AutoConstants {
		
	public static double AUTO_DRIVE_TO_TAR = Robot.prefs.getDouble("AUTO_DRIVE_TO_TAR", 30);
	public static double AUTO_DRIVE_TO_INCH_FOWRD = Robot.prefs.getDouble("AUTO_DRIVE_TO_INCH_FOWRD", 21);
	public static double AUTO_GRABBER_TIMEOUT = Robot.prefs.getDouble("AUTO_GRABBER_TIMEOUT", 1.0);
	public static double AUTO_DRIVE_TO_INCH_GEAR = Robot.prefs.getDouble("AUTO_DRIVE_TO_INCH_GEAR", 5);
	public static double AUTO_DRIVE_TO_INCH_BACK = Robot.prefs.getDouble("AUTO_DRIVE_TO_INCH_BACK", -22);
	
	public static double LEFT_FORWARD = Robot.prefs.getDouble("LEFT_FORWARD", 84 - 16);
	public static double LEFT_TURN = Robot.prefs.getDouble("LEFT_TURN", 60);
	public static double LEFT_TURN_TIMEOUT = Robot.prefs.getDouble("LEFT_TURN_TIMEOUT", 2.0);
	
	public static double RIGHT_FORWARD = Robot.prefs.getDouble("RIGHT_FORWARD", 82 - 16);
	public static double RIGHT_TURN = Robot.prefs.getDouble("RIGHT_TURN", -60);
	public static double RIGHT_TURN_TIMEOUT = Robot.prefs.getDouble("RIGHT_TURN_TIMEOUT", 2.0);
	
	public static void updatePreferences() {
		AUTO_DRIVE_TO_TAR = Robot.prefs.getDouble("AUTO_DRIVE_TO_TAR", 30);
		AUTO_DRIVE_TO_INCH_FOWRD = Robot.prefs.getDouble("AUTO_DRIVE_TO_INCH_FOWRD", 21);
		AUTO_GRABBER_TIMEOUT = Robot.prefs.getDouble("AUTO_GRABBER_TIMEOUT", 1.0);
		AUTO_DRIVE_TO_INCH_GEAR = Robot.prefs.getDouble("AUTO_DRIVE_TO_INCH_GEAR", 5);
		AUTO_DRIVE_TO_INCH_BACK = Robot.prefs.getDouble("AUTO_DRIVE_TO_INCH_BACK", -22);
		LEFT_FORWARD = Robot.prefs.getDouble("LEFT_FORWARD", 84 - 16);
		LEFT_TURN = Robot.prefs.getDouble("LEFT_TURN", 60);
		LEFT_TURN_TIMEOUT = Robot.prefs.getDouble("LEFT_TURN_TIMEOUT", 2.0);
		RIGHT_FORWARD = Robot.prefs.getDouble("RIGHT_FORWARD", 82 - 16);
		RIGHT_TURN = Robot.prefs.getDouble("RIGHT_TURN", -60);
		RIGHT_TURN_TIMEOUT = Robot.prefs.getDouble("RIGHT_TURN_TIMEOUT", 2.0);
		
		Robot.prefs.putDouble("AUTO_DRIVE_TO_TAR", AUTO_DRIVE_TO_TAR);
		Robot.prefs.putDouble("AUTO_DRIVE_TO_INCH_FOWRD", AUTO_DRIVE_TO_INCH_FOWRD);
		Robot.prefs.putDouble("AUTO_GRABBER_TIMEOUT", AUTO_GRABBER_TIMEOUT);
		Robot.prefs.putDouble("AUTO_DRIVE_TO_INCH_GEAR", AUTO_DRIVE_TO_INCH_GEAR);
		Robot.prefs.putDouble("AUTO_DRIVE_TO_INCH_BACK", AUTO_DRIVE_TO_INCH_BACK);
		Robot.prefs.putDouble("LEFT_FORWARD", LEFT_FORWARD);
		Robot.prefs.putDouble("LEFT_TURN", LEFT_TURN);
		Robot.prefs.putDouble("LEFT_TURN_TIMEOUT", LEFT_TURN_TIMEOUT);
		Robot.prefs.putDouble("RIGHT_FORWARD", RIGHT_FORWARD);
		Robot.prefs.putDouble("RIGHT_TURN", RIGHT_TURN);
		Robot.prefs.putDouble("RIGHT_TURN_TIMEOUT", RIGHT_TURN_TIMEOUT);
	}

}

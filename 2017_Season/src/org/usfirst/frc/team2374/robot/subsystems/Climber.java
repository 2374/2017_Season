package org.usfirst.frc.team2374.robot.subsystems;

import org.usfirst.frc.team2374.robot.Robot;
import org.usfirst.frc.team2374.robot.RobotMap;
import org.usfirst.frc.team2374.robot.commands.climber.ClimberWithJoystick;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Climber extends Subsystem {

	private SpeedController climberControllerLeft, climberControllerRight;

	private static final double CLIMBER_SPEED = 1.0;
	private double CLIMBER_SPEED_SLOW;

	public Climber() {
		updatePreferences();

		climberControllerLeft = new Talon(RobotMap.SPEED_CONTROLLER_CLIMBER_LEFT);
		climberControllerRight = new Talon(RobotMap.SPEED_CONTROLLER_CLIMBER_RIGHT);
	}

	@Override
	protected void initDefaultCommand() {
		setDefaultCommand(new ClimberWithJoystick());
	}

	public void start() {
		climberControllerLeft.set(CLIMBER_SPEED);
		climberControllerRight.set(CLIMBER_SPEED);
	}

	public void slow() {
		climberControllerLeft.set(CLIMBER_SPEED_SLOW);
		climberControllerRight.set(CLIMBER_SPEED_SLOW);
	}

	public void stop() {
		climberControllerLeft.set(0);
		climberControllerRight.set(0);
	}

	public void updatePreferences() {
		CLIMBER_SPEED_SLOW = Robot.prefs.getDouble("CLIMBER_SPEED_SLOW", 0.5);
		Robot.prefs.putDouble("CLIMBER_SPEED_SLOW", CLIMBER_SPEED_SLOW);
	}
}

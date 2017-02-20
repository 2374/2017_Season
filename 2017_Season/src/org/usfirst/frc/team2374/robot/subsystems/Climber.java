package org.usfirst.frc.team2374.robot.subsystems;

import org.usfirst.frc.team2374.robot.RobotMap;
import org.usfirst.frc.team2374.robot.commands.climber.ClimberWithJoystick;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Climber extends Subsystem {

	private SpeedController climberController;

	private static final double CLIMBER_SPEED = 1.0;

	public Climber() {
		climberController = new Talon(RobotMap.speedControllerClimber);
		climberController.setInverted(true);
	}

	@Override
	protected void initDefaultCommand() {
		setDefaultCommand(new ClimberWithJoystick());
	}

	public void start() {
		climberController.set(CLIMBER_SPEED);
	}

	public void stop() {
		climberController.set(0);
	}
}

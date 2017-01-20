package org.usfirst.frc.team2374.robot.subsystems;

import org.usfirst.frc.team2374.robot.RobotMap;
import org.usfirst.frc.team2374.robot.commands.climber.ClimberWithButton;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Climber extends Subsystem {
	
	private SpeedController climberController;
	
	private static final double CLIMBER_SPEED = 0.5;
	
	public Climber() {
		climberController = new Spark(RobotMap.speedControllerClimber);
	}

	@Override
	protected void initDefaultCommand() {
		setDefaultCommand(new ClimberWithButton());
	}
	
	public void start() {
		climberController.set(CLIMBER_SPEED);
	}
	
	public void stop() {
		climberController.set(0);
	}

}

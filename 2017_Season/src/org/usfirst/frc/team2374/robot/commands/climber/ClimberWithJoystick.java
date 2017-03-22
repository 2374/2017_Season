package org.usfirst.frc.team2374.robot.commands.climber;

import org.usfirst.frc.team2374.robot.Robot;
import org.usfirst.frc.team2374.robot.subsystems.Climber;

import edu.wpi.first.wpilibj.command.Command;

public class ClimberWithJoystick extends Command {

	private static final Climber CLIMBER = Robot.climber;

	public ClimberWithJoystick() {
		requires(CLIMBER);
	}

	@Override
	protected void initialize() {
	}

	@Override
	protected void execute() {
		if (Robot.oi.getButtonR3())
			CLIMBER.start();
		else
			CLIMBER.stop();
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void end() {
		CLIMBER.stop();
	}

	@Override
	protected void interrupted() {
		end();
	}
}

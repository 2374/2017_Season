package org.usfirst.frc.team2374.robot.commands.climber;

import org.usfirst.frc.team2374.robot.Robot;
import org.usfirst.frc.team2374.robot.subsystems.Climber;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ClimberWithJoystick extends Command {

	Climber climber = Robot.climber;

	public ClimberWithJoystick() {
		requires(climber);
	}

	@Override
	protected void initialize() {
	}

	@Override
	protected void execute() {
		if (Robot.oi.getButtonY())
			climber.start();
		else
			climber.stop();
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void end() {
		climber.stop();
	}

	@Override
	protected void interrupted() {
		end();
	}
}

package org.usfirst.frc.team2374.robot.commands.grabber;

import org.usfirst.frc.team2374.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class GrabberWithJoystick extends Command {

	public GrabberWithJoystick() {
		requires(Robot.grabber);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		if (Robot.oi.getLeftBumper() && !Robot.oi.getRightBumper())
			Robot.grabber.open();
		else if (Robot.oi.getRightBumper() && !Robot.oi.getLeftBumper())
			Robot.grabber.close();
		else
			Robot.grabber.stop();
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		Robot.grabber.stop();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
		end();
	}
}

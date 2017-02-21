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
		// TODO: (Code review) Switch to a toggle with a single button,
		// If you can use onPressed instead of just getting the current
		// button state, that would be better for toggling
		if (Robot.grabber.isAtLimit() && Robot.oi.getButtonBack() || Robot.oi.getButtonStart())
			Robot.oi.setRumble(true);
		else {
			Robot.oi.setRumble(false);

			if (Robot.oi.getButtonBack() && !Robot.oi.getButtonStart())
				Robot.grabber.close();
			else if (Robot.oi.getButtonStart() && !Robot.oi.getButtonBack())
				Robot.grabber.open();
			else
				Robot.grabber.stop();
		}
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

package org.usfirst.frc.team2374.robot.commands.grabber;

import org.usfirst.frc.team2374.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class GrabberWithJoystick extends Command {

	private boolean rumbling;

	public GrabberWithJoystick() {
		requires(Robot.grabber);
		rumbling = false;
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		if ((Robot.grabber.isClosed() && Robot.oi.getButtonBack())
				|| (Robot.grabber.isOpened() && Robot.oi.getButtonStart())) {
			Robot.grabber.stop();
			setRumble(true);
		} else {
			setRumble(false);
			if (!Robot.grabber.isClosed() && Robot.oi.getButtonBack() && !Robot.oi.getButtonStart())
				Robot.grabber.close();
			else if (!Robot.grabber.isOpened() && Robot.oi.getButtonStart() && !Robot.oi.getButtonBack())
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

	private void setRumble(boolean enable) {
		if (enable) {
			Robot.oi.setRumble(true);
			rumbling = true;
		} else if (!enable && rumbling) {
			Robot.oi.setRumble(false);
			rumbling = false;
		}
	}
}

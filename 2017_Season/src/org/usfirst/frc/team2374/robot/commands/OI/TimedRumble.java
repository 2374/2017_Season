package org.usfirst.frc.team2374.robot.commands.OI;

import org.usfirst.frc.team2374.robot.Robot;

import edu.wpi.first.wpilibj.command.TimedCommand;

/**
 *
 */
public class TimedRumble extends TimedCommand {

	public TimedRumble(double time) {
		super(time);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		Robot.oi.setRumble(true);
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return isTimedOut();
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		Robot.oi.setRumble(false);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
		end();
	}
}

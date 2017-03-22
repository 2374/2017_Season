package org.usfirst.frc.team2374.robot.commands.OI;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;

/**
 *
 */
public class ClearScheduler extends Command {

	public ClearScheduler() {
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		Scheduler.getInstance().removeAll();
		Scheduler.getInstance().add(new TimedRumble(0.5));
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return true;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
	}
}

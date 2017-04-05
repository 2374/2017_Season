package org.usfirst.frc.team2374.robot.commands.grabber;

import org.usfirst.frc.team2374.robot.Robot;
import org.usfirst.frc.team2374.robot.subsystems.Grabber;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;

public class CloseGrabber extends Command {

	private static final Grabber GRABBER = Robot.grabber;

	public CloseGrabber() {
		requires(GRABBER);
		setTimeout(5);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		DriverStation.reportWarning("CloseGrabber initialized.", true);
		GRABBER.close();
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return GRABBER.isClosed() || isTimedOut();
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		GRABBER.stop();
		if (isTimedOut())
			DriverStation.reportWarning("CloseGrabber timed out.", true);
		else
			DriverStation.reportWarning("CloseGrabber ended.", true);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
		end();
	}
}

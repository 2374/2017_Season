package org.usfirst.frc.team2374.robot.commands.grabber;

import org.usfirst.frc.team2374.robot.Robot;
import org.usfirst.frc.team2374.robot.subsystems.Grabber;

import edu.wpi.first.wpilibj.command.Command;

public class OpenGrabber extends Command {

	Grabber grabber = Robot.grabber;

	public OpenGrabber() {
		requires(grabber);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		grabber.open();
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return grabber.isOpen();
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		grabber.stop();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
		end();
	}
}
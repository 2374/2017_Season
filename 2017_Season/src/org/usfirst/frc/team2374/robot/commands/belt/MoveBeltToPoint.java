package org.usfirst.frc.team2374.robot.commands.belt;

import org.usfirst.frc.team2374.robot.Robot;
import org.usfirst.frc.team2374.robot.subsystems.Belt;

import edu.wpi.first.wpilibj.command.Command;

public class MoveBeltToPoint extends Command {

	private static final Belt BELT = Robot.belt;
	private static final double OFFSET = 30;

	private double wantedPosition;

	public MoveBeltToPoint(double position) {
		requires(BELT);
		wantedPosition = position;
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		BELT.setPIDSetpoint(wantedPosition);
		BELT.enablePID(true);
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		BELT.setBelt(BELT.getPIDOutput());
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		// if (BELT.isAtLimit())
		// return true;
		return Math.abs(BELT.getPosition() - wantedPosition) <= OFFSET;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		BELT.enablePID(false);
		BELT.setBelt(0);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
		end();
	}
}

package org.usfirst.frc.team2374.robot.commands.belt;

import org.usfirst.frc.team2374.robot.Robot;
import org.usfirst.frc.team2374.robot.subsystems.Belt;

import edu.wpi.first.wpilibj.command.Command;

public class MoveBeltToPoint extends Command {

	private Belt belt = Robot.belt;
	private double wantedPosition;
	private double speed;

	public MoveBeltToPoint(double position, double speed) {
		requires(belt);
		wantedPosition = position;
		this.speed = speed;
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		belt.setPIDSetpoint(wantedPosition);
		belt.setPIDSpeed(speed);
		belt.enablePID(true);
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		belt.setBelt(belt.getPIDOutput());
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		if (belt.isAtLimit())
			return true;
		if (wantedPosition < 0)
			return belt.getPosition() <= wantedPosition;
		return belt.getPosition() >= wantedPosition;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		belt.enablePID(false);
		belt.setBelt(0);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
		end();
	}
}

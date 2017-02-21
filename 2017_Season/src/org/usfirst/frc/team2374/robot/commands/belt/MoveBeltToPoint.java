package org.usfirst.frc.team2374.robot.commands.belt;

import org.usfirst.frc.team2374.robot.Robot;
import org.usfirst.frc.team2374.robot.subsystems.Belt;

import edu.wpi.first.wpilibj.command.Command;

// TODO: (Code review) WHY DO NONE OF THESE HAVE JAVADOC COMMENTS?!?!?!
public class MoveBeltToPoint extends Command {

	private Belt belt = Robot.belt;
	private double wantedPosition, initialPosition;

	public MoveBeltToPoint(double position) {
		requires(belt);
		wantedPosition = position;
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		initialPosition = belt.getPosition();
		belt.setPIDSetpoint(wantedPosition);
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
		// if (belt.isAtLimit())
		// return true;
		// TODO: (Code review) This seems like it could be simplified by having a small
		// threshold around the wantedPosition that's considered valid - doubles are imprecise
		// e.g.
		// return (Math.abs(belt.getPosition() - wantedPosition) <= threshold)
		if (wantedPosition == 0) {
			if (initialPosition < 0)
				return belt.getPosition() >= 0;
			return belt.getPosition() <= 0;
		}
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

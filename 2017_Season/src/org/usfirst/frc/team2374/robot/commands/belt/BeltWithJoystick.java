package org.usfirst.frc.team2374.robot.commands.belt;

import org.usfirst.frc.team2374.robot.Robot;
import org.usfirst.frc.team2374.robot.subsystems.Belt;

import edu.wpi.first.wpilibj.command.Command;

// TODO: (Code review) ADD JAVADOCS TO ALL CLASSES
/**
 *
 */
public class BeltWithJoystick extends Command {

	Belt belt = Robot.belt;

	public BeltWithJoystick() {
		requires(belt);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		if (Robot.oi.getLeftBumper())
			belt.setBelt(-Belt.MAX_BELT_SPEED);
		else if (Robot.oi.getRightBumper())
			belt.setBelt(Belt.MAX_BELT_SPEED);
		else
			belt.setBelt(0);
		// TODO: (Code review) Rumble seems like a good feature, but this snippet
		// will cause it to rumble all the time if it's at a limit. Conditional
		// should be if (isAtLimit() && (getLeftBumper() || getRightBumper()))
		/*
		 * if (belt.isAtLimit()) Robot.oi.setRumble(true); else
		 * Robot.oi.setRumble(false);
		 */

	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		belt.setBelt(0);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
		end();
	}
}

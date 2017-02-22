package org.usfirst.frc.team2374.robot.commands.belt;

import org.usfirst.frc.team2374.robot.Robot;
import org.usfirst.frc.team2374.robot.subsystems.Belt;

import edu.wpi.first.wpilibj.command.Command;

public class BeltWithJoystick extends Command {

	private static final Belt BELT = Robot.belt;

	public BeltWithJoystick() {
		requires(BELT);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		if (Robot.oi.getLeftBumper())
			BELT.setBelt(-Belt.MAX_BELT_SPEED);
		else if (Robot.oi.getRightBumper())
			BELT.setBelt(Belt.MAX_BELT_SPEED);
		else
			BELT.setBelt(0);
		/*
		 * waiting to be implemented if (BELT.isAtLimit() &&
		 * (Robot.oi.getLeftBumper() || Robot.oi.getRightBumper()))
		 * Robot.oi.setRumble(true); else Robot.oi.setRumble(false);
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
		BELT.setBelt(0);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
		end();
	}
}

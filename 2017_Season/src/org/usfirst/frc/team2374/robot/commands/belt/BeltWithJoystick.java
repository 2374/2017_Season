package org.usfirst.frc.team2374.robot.commands.belt;

import org.usfirst.frc.team2374.robot.Robot;
import org.usfirst.frc.team2374.robot.subsystems.Belt;

import edu.wpi.first.wpilibj.command.Command;

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
		double left = Robot.oi.getLeftTrigger();
		double right = Robot.oi.getRightTrigger();

		if (left != 0 && right == 0)
			belt.setBelt(-left);
		else if (left == 0 && right != 0)
			belt.setBelt(right);
		else
			belt.setBelt(0);
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

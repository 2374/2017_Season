package org.usfirst.frc.team2374.robot.commands.belt;

import org.usfirst.frc.team2374.robot.Robot;
import org.usfirst.frc.team2374.robot.subsystems.Belt;

import edu.wpi.first.wpilibj.command.Command;

public class BeltWithJoystick extends Command {

	private static final Belt BELT = Robot.belt;

	private boolean rumbling;

	public BeltWithJoystick() {
		requires(BELT);
		rumbling = false;
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		if ((BELT.isAtLeftLimit() && Robot.oi.getLeftBumper())
				|| (BELT.isAtRightLimit() && Robot.oi.getRightBumper())) {
			BELT.setBelt(0);
			setRumble(true);
		} else {
			setRumble(false);
			if (Robot.oi.getLeftBumper())
				BELT.setBelt(-Belt.MAX_BELT_SPEED);
			else if (Robot.oi.getRightBumper())
				BELT.setBelt(Belt.MAX_BELT_SPEED);
			else
				BELT.setBelt(0);
		}
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

	private void setRumble(boolean b) {
		if (b) {
			Robot.oi.setRumble(true);
			rumbling = true;
		} else if (!b && rumbling) {
			Robot.oi.setRumble(false);
			rumbling = false;
		}
	}
}

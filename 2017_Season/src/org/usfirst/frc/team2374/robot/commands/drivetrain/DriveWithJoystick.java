package org.usfirst.frc.team2374.robot.commands.drivetrain;

import org.usfirst.frc.team2374.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class DriveWithJoystick extends Command {

	public DriveWithJoystick() {
		requires(Robot.drivetrain);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		double leftTrigger = Robot.oi.getLeftTrigger();
		double rightTrigger = Robot.oi.getRightTrigger();
		if (rightTrigger != 0)
			Robot.drivetrain.arcadeDrive(rightTrigger, 0);
		else if (leftTrigger != 0)
			Robot.drivetrain.arcadeDrive(-leftTrigger, 0);
		else {
			// TODO: (Code review) Don't bother creating variables for this, just pass
			// getDriver*Y() directly to tankDrive()
			double leftJoy = Robot.oi.getDriverLeftY();
			double rightJoy = Robot.oi.getDriverRightY();
			Robot.drivetrain.tankDrive(leftJoy, rightJoy);
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
		Robot.drivetrain.tankDrive(0, 0);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
		end();
	}
}

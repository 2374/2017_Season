package org.usfirst.frc.team2374.robot.commands.drivetrain;

import org.usfirst.frc.team2374.robot.OI;
import org.usfirst.frc.team2374.robot.Robot;
import org.usfirst.frc.team2374.robot.subsystems.Drivetrain;

import edu.wpi.first.wpilibj.command.Command;

public class DriveWithJoystick extends Command {

	private static final Drivetrain DRIVE = Robot.drivetrain;

	public DriveWithJoystick() {
		requires(DRIVE);
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
		double leftJoystick = Robot.oi.getDriverLeftY();
		double rightJoystick = Robot.oi.getDriverRightY();
		if (rightTrigger != 0)
			DRIVE.tankDrive(-rightTrigger + OI.deadZone(leftJoystick, 0.2),
					-rightTrigger + OI.deadZone(rightJoystick, 0.2));
		else if (leftTrigger != 0)
			DRIVE.tankDrive(leftTrigger + OI.deadZone(leftJoystick, 0.2),
					leftTrigger + OI.deadZone(rightJoystick, 0.2));
		else
			DRIVE.tankDrive(leftJoystick, rightJoystick);
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

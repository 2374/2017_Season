package org.usfirst.frc.team2374.robot.commands.drivetrain;

import org.usfirst.frc.team2374.robot.Robot;
import org.usfirst.frc.team2374.robot.subsystems.Drivetrain;
import org.usfirst.frc.team2374.robot.subsystems.Vision;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

// TODO: (Code review) COFFEEPAPER
// TODO: (Code review) This looks like it could benefit from the use of inheritance
// Most of the code is similar to other DriveTo*.java
public class DriveToTarget extends Command {

	private double currentDistance, travelDistance;
	private Drivetrain drive = Robot.drivetrain;
	private Vision camera = Robot.camera;

	private final double targetDistance;
	private final double offset = 1.0;

	// TODO: (Code review) Try to have full variable names when possible, particularly
	// for function arguments
	public DriveToTarget(double tar) {
		requires(drive);
		requires(camera);
		targetDistance = tar;
	}

	@Override
	protected void initialize() {
		currentDistance = camera.distanceToTargetInches();
		travelDistance = currentDistance - targetDistance;
		drive.resetEncoders();
		drive.resetGyro();
		if (travelDistance < 50)
			drive.setShortPID();
		else
			drive.setLongPID();
		drive.setLongPID();
		Timer.delay(0.1);
		drive.setDrivePIDSetPoint(travelDistance);
		drive.setGyroPIDSetPoint(0);
		drive.enableDrivePID(true);
		drive.enableGyroPID(true);
	}

	@Override
	protected void execute() {
		drive.arcadeDrive(drive.getDrivePIDOutput(), drive.getGyroPIDOutput());
	}

	@Override
	protected boolean isFinished() {
		double currentDistance = (drive.getLeftDistanceInches() + drive.getRightDistanceInches()) / 2;
		if (travelDistance < 0)
			return currentDistance <= travelDistance + offset;
		return currentDistance >= travelDistance - offset;
	}

	@Override
	protected void end() {
		drive.enableDrivePID(false);
		drive.enableGyroPID(false);
		drive.arcadeDrive(0, 0);
	}

	@Override
	protected void interrupted() {
		end();
	}

}

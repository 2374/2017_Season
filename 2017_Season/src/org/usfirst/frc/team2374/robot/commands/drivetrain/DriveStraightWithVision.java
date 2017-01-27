package org.usfirst.frc.team2374.robot.commands.drivetrain;

import org.usfirst.frc.team2374.robot.Robot;
import org.usfirst.frc.team2374.robot.subsystems.*;

import edu.wpi.first.wpilibj.command.Command;

public class DriveStraightWithVision extends Command{
	
	private double targetDistance;
	private Drivetrain drive = Robot.drivetrain;
	private Vision camera = Robot.camera;
	
	public DriveStraightWithVision(){
		requires(drive);
		requires(camera);
	}

	protected void initialize(){
		targetDistance = camera.distanceToTargetInches();
		drive.resetEncoders();
    	drive.setDrivePIDSetPoint(targetDistance);
    	drive.setDrivePIDSpeed(drive.MAX_AUTO_SPEED);
    	drive.enableDrivePID(true);
	}
	
	protected void execute(){
		drive.arcadeDrive(drive.getDrivePIDOutput(), 0);
	}

	@Override
	protected boolean isFinished() {
    	double currentDistance = (drive.getLeftDistanceInches() + drive.getRightDistanceInches()) / 2;
    	return currentDistance >= targetDistance;
	}
	
	protected void end(){
    	drive.enableDrivePID(false);
		drive.arcadeDrive(0, 0);
	}
	
	protected void interrupted(){
		end();
	}

}

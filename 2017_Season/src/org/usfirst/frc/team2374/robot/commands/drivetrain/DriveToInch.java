package org.usfirst.frc.team2374.robot.commands.drivetrain;

import org.usfirst.frc.team2374.robot.Robot;
import org.usfirst.frc.team2374.robot.subsystems.Drivetrain;

import edu.wpi.first.wpilibj.command.Command;

public class DriveToInch extends Command {
	
	private Drivetrain drive = Robot.drivetrain;
	private double wantedDistance;
	private double speed;

    public DriveToInch(double inches, double speed) {
        requires(drive);
        wantedDistance = inches;
        this.speed = speed;
    }
    
    public DriveToInch(double inches) {
    	this(inches, Drivetrain.MAX_AUTO_SPEED);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	drive.resetEncoders();
    	drive.resetGyro();
    	drive.setDrivePIDSetPoint(wantedDistance);
    	drive.setDrivePIDSpeed(speed);
    	drive.setGyroPIDSetPoint(0);
    	drive.setGyroPIDSpeed(speed);
    	drive.enableDrivePID(true);
    	drive.enableGyroPID(true);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	drive.arcadeDrive(drive.getDrivePIDOutput(), drive.getGyroPIDOutput());
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	double currentDistance = (drive.getLeftDistanceInches() + drive.getRightDistanceInches()) / 2;
    	return currentDistance >= wantedDistance;
    }

    // Called once after isFinished returns true
    protected void end() {
    	drive.enableDrivePID(false);
    	drive.enableGyroPID(false);
    	drive.arcadeDrive(0, 0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}

package org.usfirst.frc.team2374.robot.commands.drivetrain;

import org.usfirst.frc.team2374.robot.Robot;
import org.usfirst.frc.team2374.robot.subsystems.Drivetrain;
import org.usfirst.frc.team2374.robot.subsystems.Vision;

import edu.wpi.first.wpilibj.command.*;

public class CorrectForAngle extends TimedCommand {
	
	private Drivetrain drive = Robot.drivetrain;
	private Vision camera = Robot.camera;
	private double targetOffset;
	
	public CorrectForAngle(double time) {
		super(time);
		requires(drive);
		requires(camera);
	}
	
	protected void Initialize() {
		targetOffset = camera.compareAreas();
    	drive.resetEncoders();
    	if(targetOffset < 0) drive.setDrivePIDSetPoint(-25);
    	else if(targetOffset > 0) drive.setDrivePIDSetPoint(25);
    	drive.setDrivePIDSpeed(drive.MAX_AUTO_SPEED);
    	drive.enableDrivePID(true);
	}
	
	protected void Execute() {
		if(targetOffset < 0) drive.tankDrive(0, drive.getDrivePIDOutput());//turn left
		else if(targetOffset > 0) drive.tankDrive(drive.getDrivePIDOutput(), 0);//turn right
	}

	@Override
	protected boolean isFinished() {
		return camera.compareAreas() < -20 || camera.compareAreas() > 20 || this.isTimedOut();//ends when we are parallel or when one second has passed
	}
	
	protected void end(){
    	drive.enableDrivePID(false);
		drive.arcadeDrive(0, 0);
	}
	
	protected void interrupted(){
		end();
	}

}

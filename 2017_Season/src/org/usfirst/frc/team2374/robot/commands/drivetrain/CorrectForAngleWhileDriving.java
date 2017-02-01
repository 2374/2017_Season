package org.usfirst.frc.team2374.robot.commands.drivetrain;

import org.usfirst.frc.team2374.robot.Robot;
import org.usfirst.frc.team2374.robot.subsystems.Drivetrain;
import org.usfirst.frc.team2374.robot.subsystems.Vision;

import edu.wpi.first.wpilibj.command.*;

public class CorrectForAngleWhileDriving extends TimedCommand {
	
	private Drivetrain drive = Robot.drivetrain;
	private Vision camera = Robot.camera;
	
	public CorrectForAngleWhileDriving(double time) {
		super(time);
		requires(drive);
		requires(camera);
	}
	
	protected void Initialize() {
		double initialError = camera.compareAreas(); //percent difference between rectangle areas
    	drive.resetEncoders();
    	drive.setDrivePIDSpeed(Drivetrain.MAX_AUTO_SPEED);
    	drive.setDrivePIDInputs(-100, 100);
    	if(initialError < 0) drive.setDrivePIDSetPoint(-25);
    	else drive.setDrivePIDSetPoint(25);
    	drive.enableDrivePID(true);
	}
	
	protected void Execute() {
		double currentError = camera.compareAreas();
		dr
		if(initialError < 0) drive.tankDrive(0, drive.getDrivePIDOutput());//turn left
		else if(initialError > 0) drive.tankDrive(drive.getDrivePIDOutput(), 0);//turn right
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

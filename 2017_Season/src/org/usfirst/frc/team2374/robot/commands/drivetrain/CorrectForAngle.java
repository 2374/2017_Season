package org.usfirst.frc.team2374.robot.commands.drivetrain;

import org.usfirst.frc.team2374.robot.Robot;
import org.usfirst.frc.team2374.robot.subsystems.Drivetrain;
import org.usfirst.frc.team2374.robot.subsystems.Vision;

import edu.wpi.first.wpilibj.command.*;

public class CorrectForAngle extends TimedCommand {
	
	private Drivetrain drive = Robot.drivetrain;
	private Vision camera = Robot.camera;
	private double initialError;
	
	private final double MAX_TURN_SPEED = 0.40;
	
	public CorrectForAngle(double time) {
		super(time);
		requires(drive);
		requires(camera);
	}
	
	protected void Initialize() {
		initialError = camera.compareAreas(); //percent difference between rectangle areas
		drive.setCameraPIDSpeed(MAX_TURN_SPEED);
    	if(initialError < 0) 
    		drive.setCameraPIDSetPoint(-25);
    	else 
    		drive.setCameraPIDSetPoint(25);
    	drive.enableCameraPID(true);
	}
	
	protected void Execute() {
		drive.arcadeDrive(0, drive.getCameraPIDOutput());
	}

	@Override
	protected boolean isFinished() {
		return camera.compareAreas() < -20 || camera.compareAreas() > 20 || this.isTimedOut();//ends when we are parallel or when one second has passed
	}
	
	protected void end(){
    	drive.enableCameraPID(false);
		drive.arcadeDrive(0, 0);
	}
	
	protected void interrupted(){
		end();
	}

}

package org.usfirst.frc.team2374.robot.commands.drivetrain;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import org.usfirst.frc.team2374.robot.Robot;
import org.usfirst.frc.team2374.robot.subsystems.Drivetrain;
import org.usfirst.frc.team2374.robot.subsystems.Vision;

import edu.wpi.first.wpilibj.command.Command;

public class CorrectForAngle extends Command {
	
	private Drivetrain drive = Robot.drivetrain;
	private Vision camera = Robot.camera;
	private double targetOffset;
	private ActionListener e;
	private Timer t = new Timer(1, e);
	private int time = 0;
	
	public CorrectForAngle() {
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
    	t.restart();
	}
	
	protected void Execute() {
		if(targetOffset < 0) drive.tankDrive(0, drive.getDrivePIDOutput());//turn left
		else if(targetOffset > 0) drive.tankDrive(drive.getDrivePIDOutput(), 0);//turn right
	}
	
	public void actionPerformed(ActionEvent e) {
	    	time++;
	    }

	@Override
	protected boolean isFinished() {
		return camera.compareAreas() < -20 || camera.compareAreas() > 20 || time >= 1000;//ends when we are parallel or when one second has passed
	}
	
	protected void end(){
    	drive.enableDrivePID(false);
		drive.arcadeDrive(0, 0);
	}
	
	protected void interrupted(){
		end();
	}

}

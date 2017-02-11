package org.usfirst.frc.team2374.robot.commands.belt;

import org.usfirst.frc.team2374.robot.Robot;
import org.usfirst.frc.team2374.robot.subsystems.Belt;

import edu.wpi.first.wpilibj.command.Command;

public class CenterBelt extends Command{
	
	private double initialPos;
	
	public CenterBelt(){
		requires(Robot.belt);
		requires(Robot.camera);
	}
	
	protected void initialize(){
		initialPos = Robot.camera.pixelsToCenter();
		if (initialPos > 0) Robot.belt.setBelt(-Belt.MAX_BELT_SPEED);
		else Robot.belt.setBelt(Belt.MAX_BELT_SPEED);
	}
	
	protected void execute() {
	}
	
	@Override
	protected boolean isFinished() {
		if (initialPos > 0)
			return Robot.camera.pixelsToCenter() <= 0;
		return Robot.camera.pixelsToCenter() >= 0;
	}
	
	protected void end() {
		Robot.belt.setBelt(0);
	}
	
	protected void interrupted(){
		end();
	}

}

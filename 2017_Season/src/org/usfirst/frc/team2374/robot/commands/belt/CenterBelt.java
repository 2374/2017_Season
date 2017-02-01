package org.usfirst.frc.team2374.robot.commands.belt;

import org.usfirst.frc.team2374.robot.Robot;
import org.usfirst.frc.team2374.robot.subsystems.Belt;

import edu.wpi.first.wpilibj.command.Command;
// passed peer review
// need to account for offset
public class CenterBelt extends Command{
	
	private double offset;
	private double initialPos;
	
	public CenterBelt(){
		requires(Robot.belt);
		requires(Robot.camera);
	}
	
	protected void initialize(){
		offset = 0;
		initialPos = Robot.camera.pixelsToCenter();
		if (initialPos > offset) Robot.belt.setBelt(-Belt.MAX_BELT_SPEED);
		else Robot.belt.setBelt(Belt.MAX_BELT_SPEED);
	}
	
	protected void execute() {
	}
	
	@Override
	protected boolean isFinished() {
		if (Robot.belt.isAtLimit())
			return true;
		if (initialPos > offset)
			return Robot.camera.pixelsToCenter() <= offset;
		return Robot.camera.pixelsToCenter() >= offset;
	}
	
	protected void end() {
		Robot.belt.setBelt(0);
	}
	
	protected void interrupted(){
		end();
	}

}

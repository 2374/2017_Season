package org.usfirst.frc.team2374.robot.commands.auto;

import org.usfirst.frc.team2374.robot.Robot;
import org.usfirst.frc.team2374.robot.commands.belt.*;
import org.usfirst.frc.team2374.robot.commands.drivetrain.*;
import org.usfirst.frc.team2374.robot.commands.grabber.*;
import org.usfirst.frc.team2374.robot.subsystems.*;

import edu.wpi.first.wpilibj.command.CommandGroup;


public class Right extends CommandGroup {
	
	private Drivetrain drive = Robot.drivetrain;
	private Belt belt = Robot.belt;
	private Vision camera = Robot.camera;
	private Grabber grabber = Robot.grabber;
	private double inchesToTarget = 60;//CHANGE THIS LATER
	private int feetToEnd = 45;//CHANGE THIS LATER
	private int correctionInches = 4;//CHANGE THIS LATER
	
    public Right() {
    	requires(drive);
    	requires(belt);
    	requires(camera);
    	requires(grabber);
    	
    	//drive forward
    	addSequential(new DriveToInch(inchesToTarget, drive.MAX_AUTO_SPEED));
    	//turn left 60 degrees
    	addSequential(new TurnToDegree(-60));
    	if(camera.isReal()) {
    		//correct for angle
    		for(int i = 0; i < 1; i+=0){
    			addSequential(new CorrectForAngle());
    			addSequential(new DriveToInch(correctionInches));
    			if(Math.abs(camera.compareAreas()) <= 20) i++;
    		}
    		//align gear with peg
    		addSequential(new CenterBelt());
    		//drive to target
    		addSequential(new DriveStraightWithVision());
    		//release gear
    		addSequential(new OpenGrabber());
    		//back up
    		addSequential(new DriveToInch(-2 * 12));
    		//turn left
    		addSequential(new TurnToDegree(60));
    		//drive to other end of field, close grabber and center belt
    		addParallel(new DriveToInch(feetToEnd * 12));
    		addParallel(new CloseGrabber());
    		addSequential(new MoveBeltToPoint(0, belt.MAX_BELT_SPEED));
    	}
    	//if not just cross the base line, don't drop the gear, and stay in the same general area as the target
    	else {
    		addSequential(new TurnToDegree(0));
    		addSequential(new DriveToInch(3 * 12, drive.MAX_AUTO_SPEED));
    	}
    }
}

package org.usfirst.frc.team2374.robot.commands.auto;

import org.usfirst.frc.team2374.robot.Robot;
import org.usfirst.frc.team2374.robot.commands.belt.*;
import org.usfirst.frc.team2374.robot.commands.drivetrain.*;
import org.usfirst.frc.team2374.robot.commands.grabber.*;
import org.usfirst.frc.team2374.robot.subsystems.*;

import edu.wpi.first.wpilibj.command.CommandGroup;


public class Center extends CommandGroup {
	
	private Drivetrain drive = Robot.drivetrain;
	private Belt belt = Robot.belt;
	private Vision camera = Robot.camera;
	private Grabber grabber = Robot.grabber;
	private int feetToEnd = 45;//CHANGE THIS LATER
	private final int INCHES_TO_FEET = 12;
	
    public Center() {
    	requires(drive);
    	requires(belt);
    	requires(camera);
    	requires(grabber);
    	
    	//do we have a valid target?
    	if(camera.isReal()) {
    		//align gear with peg
    		addSequential(new CenterBelt());
    		//drive to target
    		addSequential(new DriveStraightWithVision());
    		//release gear
    		addSequential(new OpenGrabber());
    		//back up
    		addSequential(new DriveToInch(-2 * INCHES_TO_FEET));
    		//turn right
    		addSequential(new TurnToDegree(90));
    		//drive forward
    		addSequential(new DriveToInch(3 * INCHES_TO_FEET));
    		//turn left
    		addSequential(new TurnToDegree(-90));
    		//drive to other end of field, close grabber and center belt
    		addParallel(new DriveToInch(feetToEnd * INCHES_TO_FEET));
    		addParallel(new CloseGrabber());
    		addSequential(new MoveBeltToPoint(0, Belt.MAX_BELT_SPEED));
    	}
    	//if not just cross the base line, don't drop the gear, and stay in the same general area as the target
    	else {
    		addSequential(new DriveToInch(1 * INCHES_TO_FEET));
    		addSequential(new TurnToDegree(90));
    		addSequential(new DriveToInch(3 * INCHES_TO_FEET));
    		addSequential(new TurnToDegree(-90));
    		addSequential(new DriveToInch(6 * INCHES_TO_FEET));
    	}
    }
}

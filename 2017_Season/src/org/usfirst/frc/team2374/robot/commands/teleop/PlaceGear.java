package org.usfirst.frc.team2374.robot.commands.teleop;

import org.usfirst.frc.team2374.robot.Robot;
import org.usfirst.frc.team2374.robot.commands.belt.*;
import org.usfirst.frc.team2374.robot.commands.drivetrain.*;
import org.usfirst.frc.team2374.robot.commands.grabber.*;
import org.usfirst.frc.team2374.robot.subsystems.*;

import edu.wpi.first.wpilibj.command.CommandGroup;


public class PlaceGear extends CommandGroup {
	
	private Drivetrain drive = Robot.drivetrain;
	private Belt belt = Robot.belt;
	private Vision camera = Robot.camera;
	private Grabber grabber = Robot.grabber;
	private int correctionInches = 4;//CHANGE THIS LATER

    public PlaceGear() {	
    	requires(drive);
    	requires(belt);
    	requires(camera);
    	requires(grabber);
    	
    	//correct for angle
    	for(int i = 0; i < 1; i+=0){
    		addSequential(new CorrectForAngleWhileDriving(1));
    		addSequential(new DriveToInch(correctionInches));
    		if(Math.abs(camera.compareAreas()) <= 20) i++;
    	}
    	//align gear with peg
    	addSequential(new CenterBelt());
    	//drive to target
    	addSequential(new DriveStraightWithVision());
    	//release gear
    	addSequential(new OpenGrabber());
    }
}

package org.usfirst.frc.team2374.robot.commands.auto;

import org.usfirst.frc.team2374.robot.Robot;
import org.usfirst.frc.team2374.robot.commands.belt.CenterBeltOnTarget;
import org.usfirst.frc.team2374.robot.commands.drivetrain.DriveToInchShort;
import org.usfirst.frc.team2374.robot.commands.drivetrain.DriveToTarget;
import org.usfirst.frc.team2374.robot.subsystems.Belt;
import org.usfirst.frc.team2374.robot.subsystems.Drivetrain;
import org.usfirst.frc.team2374.robot.subsystems.Grabber;
import org.usfirst.frc.team2374.robot.subsystems.Vision;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class Tester extends CommandGroup {

	private Drivetrain drive = Robot.drivetrain;
	private Belt belt = Robot.belt;
	private Vision camera = Robot.camera;
	private Grabber grabber = Robot.grabber;

	public Tester() {

		requires(drive);
		requires(belt);
		requires(camera);
		requires(grabber);

		addSequential(new DriveToTarget(30));
		addSequential(new CenterBeltOnTarget());
		addSequential(new DriveToInchShort(22)); // LEAVE 23
	}
}

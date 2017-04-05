package org.usfirst.frc.team2374.robot.commands.auto;

import org.usfirst.frc.team2374.robot.Robot;
import org.usfirst.frc.team2374.robot.commands.drivetrain.DriveToInch;
import org.usfirst.frc.team2374.robot.commands.drivetrain.DriveToInch.DriveToType;
import org.usfirst.frc.team2374.robot.commands.drivetrain.TurnToDegree;
import org.usfirst.frc.team2374.robot.subsystems.Belt;
import org.usfirst.frc.team2374.robot.subsystems.Drivetrain;
import org.usfirst.frc.team2374.robot.subsystems.Grabber;
import org.usfirst.frc.team2374.robot.subsystems.Vision;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class LeftRed extends CommandGroup {

	private Drivetrain drive = Robot.drivetrain;
	private Belt belt = Robot.belt;
	private Vision camera = Robot.camera;
	private Grabber grabber = Robot.grabber;

	public LeftRed() {
		requires(drive);
		requires(belt);
		requires(camera);
		requires(grabber);

		addSequential(new DriveToInch(AutoConstants.LEFT_FORWARD_RED, DriveToType.LONG));
		addSequential(new TurnToDegree(AutoConstants.LEFT_TURN, AutoConstants.LEFT_TURN_TIMEOUT));
		addSequential(new LoadGearAuto());
	}
}

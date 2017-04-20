package org.usfirst.frc.team2374.robot.commands.auto;

import org.usfirst.frc.team2374.robot.Robot;
import org.usfirst.frc.team2374.robot.commands.drivetrain.DriveToInch;
import org.usfirst.frc.team2374.robot.commands.drivetrain.DriveToInch.DriveToType;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class BaseLineCenter extends CommandGroup {

	public BaseLineCenter() {
		requires(Robot.drivetrain);

		addSequential(new DriveToInch(AutoConstants.BASE_LINE_CENTER, DriveToType.LONG));
	}
}

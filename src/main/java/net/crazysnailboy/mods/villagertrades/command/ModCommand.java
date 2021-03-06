package net.crazysnailboy.mods.villagertrades.command;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import net.crazysnailboy.mods.villagertrades.common.registry.VillagerRegistryHelper;
import net.crazysnailboy.mods.villagertrades.common.registry.VillagerRegistryHelper.VTTVillagerCareer;
import net.crazysnailboy.mods.villagertrades.common.registry.VillagerRegistryHelper.VTTVillagerProfession;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.VillagerRegistry.VillagerCareer;
import net.minecraftforge.fml.common.registry.VillagerRegistry.VillagerProfession;


public class ModCommand implements ICommand
{

	@Override
	public int compareTo(ICommand arg0)
	{
		return 0;
	}

	@Override
	public String getName()
	{
		return "vtt";
	}

	@Override
	public String getUsage(ICommandSender sender)
	{
		return null;
	}

	@Override
	public List<String> getAliases()
	{
		return Collections.singletonList("vtt");
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
	{
		World world = sender.getEntityWorld();
		if (!world.isRemote)
		{

			if (args.length == 0)
			{
				for (Map.Entry<Integer, String> kvp : VillagerRegistryHelper.getProfessionIdsAndNamesSortedById())
				{
					sender.sendMessage(new TextComponentString(kvp.getKey() + ": " + kvp.getValue()));
				}
				return;
			}

			if (args.length == 2 && args[0].equals("profession"))
			{
				VillagerProfession profession = VillagerRegistryHelper.getProfession(args[1]);
				if (profession != null)
				{
					for (VillagerCareer career : new VTTVillagerProfession(profession).getCareers())
					{
						VTTVillagerCareer vttCareer = new VTTVillagerCareer(career);
						sender.sendMessage(new TextComponentString(vttCareer.getId() + ": " + vttCareer.getName()));
						sender.sendMessage(new TextComponentString("   " + vttCareer.getCareerLevels() + " levels"));
					}
				}
			}

		}
	}

	@Override
	public boolean checkPermission(MinecraftServer server, ICommandSender sender)
	{
		return sender.canUseCommand(2, getName());
	}

	@Override
	public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, BlockPos targetPos)
	{
		return null;
	}

	@Override
	public boolean isUsernameIndex(String[] args, int index)
	{
		return false;
	}

}

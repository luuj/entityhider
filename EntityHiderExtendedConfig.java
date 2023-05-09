package net.runelite.client.plugins.entityhiderextended;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;
import net.runelite.client.config.ConfigSection;

@ConfigGroup("entityhiderextended")
public interface EntityHiderExtendedConfig extends Config
{
	//Config Sections
	@ConfigSection(
		name = "NPCs",
		description = "NPCs",
		position = 0
	)
	String npcsSection = "NPCs";

	@ConfigItem(
		position = 1,
		keyName = "hideNPCsID",
		name = "Hide NPCs (ID)",
		description = "Configures which NPCs by ID are hidden",
		section = npcsSection
	)
	default String hideNPCsID()
	{
		return "";
	}


}
package net.runelite.client.plugins.entityhiderextended;
import com.google.common.base.Splitter;
import javax.inject.Inject;
import com.google.common.annotations.VisibleForTesting;
import com.google.inject.Provides;
import net.runelite.api.Client;
import net.runelite.api.NPC;
import net.runelite.api.Renderable;
import net.runelite.client.callback.Hooks;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.events.ConfigChanged;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;


@PluginDescriptor(
		name = "<html><font color=#b82584>[J] Entity Hider Ext.",
		enabledByDefault = false,
		description = "Specify which NPCs to hide",
		tags = {"npcs"}
)
public class EntityHiderExtendedPlugin extends Plugin
{
	@Inject
	private Client client;

	@Inject
	private EntityHiderExtendedConfig config;

	@Inject
	private Hooks hooks;

	private final Hooks.RenderableDrawListener drawListener = this::shouldDraw;

	private static final Splitter SPLITTER = Splitter.on("\n").omitEmptyStrings().trimResults();


	@Provides
	EntityHiderExtendedConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(EntityHiderExtendedConfig.class);
	}

	private Set<Integer> hideNPCsID;

	@Override
	protected void startUp()
	{
		hooks.registerRenderableDrawListener(drawListener);
		updateConfig();
	}

	@Override
	protected void shutDown()
	{
		hooks.unregisterRenderableDrawListener(drawListener);
	}

	@VisibleForTesting
	boolean shouldDraw(Renderable renderable, boolean drawingUI)
	{
		if (renderable instanceof NPC)
		{
			NPC npc = (NPC) renderable;

			if (npc.getName() != null && hideNPCsID.contains(npc.getId()))
			{
				return false;
			}
		}

		return true;
	}

	@Subscribe
	public void onConfigChanged(ConfigChanged event)
	{
		if (!event.getGroup().equals("entityhiderextended"))
		{
			return;
		}

		updateConfig();
	}

	private void updateConfig()
	{
		hideNPCsID = new HashSet<>();

		List<String> strList = SPLITTER.splitToList(this.config.hideNPCsID());
		Iterator var4 = strList.iterator();

		while(var4.hasNext()) {
			String str = (String)var4.next();
			try
			{
				hideNPCsID.add(Integer.parseInt(str));
			}
			catch (NumberFormatException ignored)
			{
			}

		}
	}
}
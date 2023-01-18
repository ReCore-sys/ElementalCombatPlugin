package net.elementalcombat.ElementalCombatPlugin;

import org.bukkit.event.player.PlayerJoinEvent;
import org.junit.jupiter.api.Test;

public class TemplatePluginTests extends BuildArenaBase {

    @Test
    public void shouldFirePlayerJoinEvent() {
        server.addPlayer();

        server.getPluginManager().assertEventFired(PlayerJoinEvent.class);
    }
}

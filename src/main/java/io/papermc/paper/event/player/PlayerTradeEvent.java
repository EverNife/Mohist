package io.papermc.paper.event.player;

import org.bukkit.entity.AbstractVillager;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.inventory.MerchantRecipe;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Called when a player trades with a villager or wandering trader
 */
public class PlayerTradeEvent extends PlayerEvent implements Cancellable {

    private static final HandlerList handlers = new HandlerList();
    private boolean cancelled;

    private boolean increaseTradeUses;
    private boolean rewardExp;
    private final AbstractVillager villager;
    private MerchantRecipe trade;

    public PlayerTradeEvent(@NotNull Player player, @NotNull AbstractVillager villager, @NotNull MerchantRecipe trade, boolean rewardExp, boolean increaseTradeUses) {
        super(player);
        this.villager = villager;
        this.trade = trade;
        this.rewardExp = rewardExp;
        this.increaseTradeUses = increaseTradeUses;
    }

    /**
     * Gets the Villager or Wandering trader associated with this event
     * @return the villager or wandering trader
     */
    @NotNull
    public AbstractVillager getVillager() {
        return villager;
    }

    /**
     * Gets the associated trade with this event
     * @return the trade
     */
    @NotNull
    public MerchantRecipe getTrade() {
        return trade;
    }

    /**
     * Sets the trade. This is then used to determine the next prices
     * @param trade the trade to use
     */
    public void setTrade(@Nullable MerchantRecipe trade) {
        this.trade = trade;
    }

    /**
     * @return will trade try to reward exp
     */
    public boolean isRewardingExp() {
        return rewardExp;
    }

    /**
     * Sets whether the trade will try to reward exp
     * @param rewardExp try to reward exp
     */
    public void setRewardExp(boolean rewardExp) {
        this.rewardExp = rewardExp;
    }

    /**
     * @return whether or not the trade will count as a use of the trade
     */
    public boolean willIncreaseTradeUses() {
        return increaseTradeUses;
    }

    /**
     * Sets whether or not the trade will count as a use
     * @param increaseTradeUses true to count/false to not count
     */
    public void setIncreaseTradeUses(boolean increaseTradeUses) {
        this.increaseTradeUses = increaseTradeUses;
    }

    /**
     * Gets the cancellation state of this event. A cancelled event will not
     * be executed in the server, but will still pass to other plugins
     *
     * @return true if this event is cancelled
     */
    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }

    /**
     * Sets the cancellation state of this event. A cancelled event will not
     * be executed in the server, but will still pass to other plugins.
     *
     * @param cancel true if you wish to cancel this event
     */
    @Override
    public void setCancelled(boolean cancel) {
        this.cancelled = cancel;
    }

    @NotNull
    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    @NotNull
    public static HandlerList getHandlerList() {
        return handlers;
    }
}

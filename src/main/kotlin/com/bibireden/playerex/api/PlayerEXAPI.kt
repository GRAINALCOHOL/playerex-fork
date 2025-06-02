package com.bibireden.playerex.api

import com.bibireden.playerex.api.attribute.PlayerEXAttributes
import com.bibireden.playerex.api.attribute.TradeSkillAttributes
import com.bibireden.playerex.api.damage.DamageFunction
import com.bibireden.playerex.api.damage.DamagePredicate
import com.bibireden.playerex.registry.*
import net.minecraft.resources.ResourceLocation

@Suppress("UNUSED")
object PlayerEXAPI {
    /** Contains the attribute ids that are "skills", and are present on the main screens first page. */
    @JvmField
    val PRIMARY_ATTRIBUTE_IDS: Collection<ResourceLocation> = PlayerEXAttributes.PRIMARY_ATTRIBUTE_IDS
    
    /** Passive trade skill ids. */
    @JvmField
    val TRADE_SKILL_IDS: Collection<ResourceLocation> = TradeSkillAttributes.IDS

    /**
     * Registers a damage modification condition that is applied to living entities
     * under specific circumstances.
     *
     * @param predicate Using the incoming damage conditions, determines whether the
     *                  damage modification function
     *                  should be applied.
     * @param function  Using the incoming damage conditions, modifies the incoming
     *                  damage before it actually damages.
     */
    @JvmStatic
    fun registerDamageModification(predicate: DamagePredicate, function: DamageFunction) {
        DamageModificationRegistry.register(predicate, function)
    }

    /**
     * Registers a refund condition. Refund conditions tell the game what can be
     * refunded and what the maximum number of
     * refund points are for a given circumstance.
     *
     * @param condition
     */
    @JvmStatic
    fun registerRefundCondition(condition: RefundCondition) {
        RefundConditionRegistry.register(condition)
    }

    /**
     * @return Returns all the registered refund conditions. Note that while this is
     *         mutable and backed by the original registry,
     *         you should avoid modification and treat as read-only!
     * @since 3.5.0
     */
    @JvmStatic
    val refundConditions: List<RefundCondition>
        get() = RefundConditionRegistry.get()
}
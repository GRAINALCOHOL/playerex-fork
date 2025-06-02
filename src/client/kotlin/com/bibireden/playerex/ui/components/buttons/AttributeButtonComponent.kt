package com.bibireden.playerex.ui.components.buttons

import com.bibireden.data_attributes.api.DataAttributesAPI
import com.bibireden.data_attributes.api.attribute.IEntityAttribute
import com.bibireden.data_attributes.ui.renderers.ButtonRenderers
import com.bibireden.playerex.components.player.IPlayerDataComponent
import com.bibireden.playerex.ext.id
import com.bibireden.playerex.networking.NetworkingChannels
import com.bibireden.playerex.networking.NetworkingPackets
import com.bibireden.playerex.ui.PlayerEXScreen
import com.bibireden.playerex.ui.childById
import io.wispforest.owo.ui.component.ButtonComponent
import io.wispforest.owo.ui.component.TextBoxComponent
import io.wispforest.owo.ui.core.Insets
import io.wispforest.owo.ui.core.Sizing
import net.minecraft.world.entity.ai.attributes.Attribute
import net.minecraft.world.entity.player.Player
import net.minecraft.network.chat.Component

class AttributeButtonComponent(val attribute: Attribute, private val player: Player, private val component: IPlayerDataComponent, val type: PlayerEXScreen.ButtonType) : ButtonComponent(
    Component.literal(type.symbol),
    {
        // reference text-box to get the necessary value to send to server (ik it's bad, we fix later)
        it.parent()?.parent()?.parent()?.childById(TextBoxComponent::class, "input")?.let { box ->
            val amount = box.value.toDoubleOrNull() ?: return@let
            val points = type.getPointsFromComponent(component)

            if (points < amount) return@let // invalid, not enough points.

            DataAttributesAPI.getValue(attribute, player).ifPresent {
                NetworkingChannels.MODIFY.clientHandle().send(NetworkingPackets.Update(type.packet, attribute.id, amount.toInt()))
            }
        }
    }
) {
    init {
        sizing(Sizing.content(), Sizing.fixed(9))
        renderer(ButtonRenderers.STANDARD)
        refresh()
    }

    fun refresh() {
       DataAttributesAPI.getValue(attribute, player).ifPresent { value ->
           val max = (attribute as IEntityAttribute).`data_attributes$max`()
           this.active(when (type) {
               PlayerEXScreen.ButtonType.Add -> component.skillPoints > 0 && max > value
               PlayerEXScreen.ButtonType.Remove -> component.refundablePoints > 0 && value > 0
           })
       }
    }
}
package com.keletu.ancienttweaks.packet;

import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

public class ModNetwork {

    private static final String PROTOCOL_VERSION = "1";

    public static void register(final RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar(PROTOCOL_VERSION);

        registrar.playToClient(PacketBubbleShield.TYPE, PacketBubbleShield.CODEC, PacketBubbleShield::handle);
        registrar.playToClient(TanookiJumpClient.TYPE, TanookiJumpClient.CODEC, TanookiJumpClient::handle);
        registrar.playToServer(TanookiJumpPayload.TYPE, TanookiJumpPayload.CODEC, TanookiJumpPayload::handle);
        registrar.playToServer(TanookiRolling.TYPE, TanookiRolling.CODEC, TanookiRolling::handle);
        registrar.playToServer(TanookiStatuePayload.TYPE, TanookiStatuePayload.CODEC, TanookiStatuePayload::handle);
    }
}
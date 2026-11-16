package com.keletu.calamitycurios.packet;

import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

public class ModNetwork {

    private static final String PROTOCOL_VERSION = "1";

    public static void register(final RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar(PROTOCOL_VERSION);

        registrar.playToClient(PacketBubbleShield.TYPE, PacketBubbleShield.CODEC, PacketBubbleShield::handle);
        registrar.playToClient(CommunityProgressSyncPayload.TYPE, CommunityProgressSyncPayload.CODEC, CommunityProgressSyncPayload::handle);
        registrar.playToClient(MechanicalHeartSyncPayload.TYPE, MechanicalHeartSyncPayload.CODEC, MechanicalHeartSyncPayload::handle);
        registrar.playToClient(TanookiJumpClient.TYPE, TanookiJumpClient.CODEC, TanookiJumpClient::handle);
        registrar.playToClient(TanookiStatueSyncPayload.TYPE, TanookiStatueSyncPayload.CODEC, TanookiStatueSyncPayload::handle);
        registrar.playToServer(TanookiJumpPayload.TYPE, TanookiJumpPayload.CODEC, TanookiJumpPayload::handle);
        registrar.playToServer(TanookiInputPayload.TYPE, TanookiInputPayload.CODEC, TanookiInputPayload::handle);
        registrar.playToServer(MechanicalHeartActivatePayload.TYPE, MechanicalHeartActivatePayload.CODEC, MechanicalHeartActivatePayload::handle);
    }
}

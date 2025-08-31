package com.spaceresilience.capture;

import org.pcap4j.core.*;
import org.pcap4j.packet.Packet;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class PacketCapture {

    private PcapHandle handle;
    private Connection conn;

    public PacketCapture() throws Exception {
        // Setup SQLite
        conn = DriverManager.getConnection("jdbc:sqlite:data/packets.db");
        conn.createStatement().execute(
            "CREATE TABLE IF NOT EXISTS packets (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "srcIp TEXT, dstIp TEXT, protocol TEXT, length INTEGER)"
        );
    }

    public void startCapture() throws Exception {
        PcapNetworkInterface nif = Pcaps.findAllDevs().get(0); // first interface
        handle = nif.openLive(65536, PcapNetworkInterface.PromiscuousMode.PROMISCUOUS, 10);

        PacketListener listener = new PacketListener() {
            @Override
            public void gotPacket(Packet packet) {
                storePacket(packet);
            }
        };

        handle.loop(-1, listener);
    }

    private void storePacket(Packet packet) {
        try {
            PreparedStatement stmt = conn.prepareStatement(
                "INSERT INTO packets (srcIp,dstIp,protocol,length) VALUES (?,?,?,?)"
            );
            stmt.setString(1, "SRC"); // placeholder
            stmt.setString(2, "DST"); // placeholder
            stmt.setString(3, packet.getClass().getSimpleName());
            stmt.setInt(4, packet.length());
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stopCapture() {
        if(handle != null) handle.close();
    }
}


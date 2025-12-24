//package com.nayra.logcollector;
//
//import java.io.IOException;
//import java.net.DatagramPacket;
//import java.net.DatagramSocket;
//import java.util.Arrays;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//
//import com.nayra.kafka.RawLogKafkaProducer;
//
//public class RawLogCollector implements CommandLineRunner {
//
//	@Autowired
//	RawLogKafkaProducer producer;
//
//	@Override
//	public void run(String... args) throws Exception {
//		new Thread(() -> {
//			try (DatagramSocket socket = new DatagramSocket(9999)) {
//				byte[] buffer = new byte[1024];
//				DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
//				while (true) {
//					socket.receive(packet);
//					byte[] rawLog = Arrays.copyOf(packet.getData(), packet.getLength());
////					producer.sendRawLog(rawLog);
//				}
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}).start();
//	}
//
//}

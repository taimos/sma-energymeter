package de.taimos.sma.energymeter;

import java.math.BigInteger;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketTimeoutException;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Thread that receives data from an SMA energy meter by listening to the Multicast Stream<br>
 * <br>
 * 
 * Copyright 2014 Taimos GmbH<br>
 * <br>
 * 
 * @author Thorsten Hoeger
 * 
 */
public class SMAReader extends Thread {
	
	private static final int RECEIVE_TIMEOUT = 5000;
	
	private static final Logger logger = LoggerFactory.getLogger(SMAReader.class);
	
	
	/**
	 * The SMA Reader callback
	 * 
	 * Copyright 2014 Taimos GmbH<br>
	 * <br>
	 * 
	 * @author Thorsten Hoeger
	 * 
	 */
	public static interface SMACallback {
		
		/**
		 * This method is called upon reception of a new data packet
		 * 
		 * @param data the received data packet
		 */
		void dataReceived(SMAData data);
		
		/**
		 * This method is called on any error
		 * 
		 * @param e the error that occured
		 */
		void error(Exception e);
		
		/**
		 * This method is called if no data is received within <code>SMAReader.RECEIVE_TIMEOUT</code> milliseconds
		 */
		void timeout();
		
	}
	
	
	private static final char[] hexArray = "0123456789ABCDEF".toCharArray();
	
	private final String group;
	private final int port;
	private final SMACallback cb;
	
	private final AtomicBoolean running = new AtomicBoolean(true);
	
	
	/**
	 * Construct a new reader with SMA default values
	 * 
	 * @param cb the value callback
	 */
	public SMAReader(SMACallback cb) {
		// use SMA default values
		this("239.12.255.254", 9522, cb);
	}
	
	/**
	 * Construct a new reader with given values
	 * 
	 * @param group the multicast group
	 * @param port the UDP port
	 * @param cb the value callback
	 */
	public SMAReader(String group, int port, SMACallback cb) {
		this.group = group;
		this.port = port;
		this.cb = cb;
	}
	
	/**
	 * Shut down the receiver
	 */
	public void shutdown() {
		this.running.set(false);
	}
	
	@Override
	public void run() {
		byte[] b = new byte[1024];
		DatagramPacket dgram = new DatagramPacket(b, b.length);
		try (MulticastSocket socket = new MulticastSocket(this.port)) {
			SMAReader.logger.debug("Start socket on group {} and port {}", this.group, this.port);
			socket.joinGroup(InetAddress.getByName(this.group));
			
			SMAReader.logger.debug("Setting receive timeout to {} milliseconds", SMAReader.RECEIVE_TIMEOUT);
			socket.setSoTimeout(SMAReader.RECEIVE_TIMEOUT);
			
			while (this.running.get()) {
				try {
					SMAReader.logger.debug("Waiting for next datagram");
					socket.receive(dgram);
					
					// format data
					byte[] data = Arrays.copyOfRange(dgram.getData(), 0, dgram.getLength());
					dgram.setLength(b.length); // must reset length field!
					SMAReader.logger.debug("Received SMA data from {}", dgram.getAddress().toString());
					
					// parse and process data
					this.cb.dataReceived(this.parse(data));
				} catch (SocketTimeoutException e) {
					SMAReader.logger.warn("Didn't receive data within {} milliseconds", SMAReader.RECEIVE_TIMEOUT);
					this.cb.timeout();
				} catch (Exception e) {
					this.cb.error(e);
				}
			}
		} catch (Exception e) {
			this.cb.error(e);
		}
	}
	
	private SMAData parse(byte[] data) {
		if (data.length != 600) {
			SMAReader.logger.warn("Invalid datagram length: {}", data.length);
			throw new RuntimeException("invalid length");
		}
		
		// read serial number of energymeter
		byte[] serial = Arrays.copyOfRange(data, 20, 24);
		SMAData sma = new SMAData(new BigInteger(serial).toString());
		
		// cut body from datagram
		byte[] body = Arrays.copyOfRange(data, 28, 588);
		
		int i = 0;
		while (i < body.length) {
			// first 4 bytes := 00 <addr> <len> 00
			if ((body[i] != 0) || (body[i + 3] != 0)) {
				String head = this.byteToHex(body[i]) + this.byteToHex(body[i + 1]) + this.byteToHex(body[i + 2]) + this.byteToHex(body[i + 3]);
				SMAReader.logger.warn("Invalid field header found: {}", head);
				throw new RuntimeException("parse error");
			}
			String address = this.byteToHex(body[i + 1]);
			int len = body[i + 2];
			int dataStart = i + 4;
			int dataEnd = i + 4 + len;
			
			byte[] dataBytes = Arrays.copyOfRange(body, dataStart, dataEnd);
			// use <addr><len> as key (eg 2A4)
			sma.add(address + len, new BigInteger(dataBytes));
			i = dataEnd;
		}
		
		return sma;
	}
	
	private String byteToHex(byte bytes) {
		char[] hexChars = new char[2];
		int v = bytes & 0xFF;
		hexChars[0] = SMAReader.hexArray[v >>> 4];
		hexChars[1] = SMAReader.hexArray[v & 0x0F];
		return new String(hexChars);
	}
	
}

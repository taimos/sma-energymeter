package de.taimos.sma.energymeter;

import de.taimos.sma.energymeter.SMAReader.SMACallback;

public class MulticastTest {
	
	public static void main(String[] args) {
		SMACallback cb = new SMACallback() {
			
			@Override
			public void error(Exception e) {
				e.printStackTrace();
			}
			
			@Override
			public void timeout() {
				System.err.println("Data timeout");
			}
			
			@Override
			public void dataReceived(SMAData data) {
				System.out.println("#############################");
				System.out.println("DATA BLOCK " + data.getSerial());
				System.out.println("#############################");
				SMAField[] fields = SMAField.values();
				for (SMAField f : fields) {
					System.out.println(f.name() + ": " + data.getValueString(f));
				}
			}
		};
		
		SMAReader reader = new SMAReader(cb);
		reader.start();
		
		try {
			Thread.sleep(15000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		
		reader.shutdown();
		
	}
	
}

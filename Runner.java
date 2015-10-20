public class Runner {
		public static void main(String[] args) {
				new Thread() {
						public void run() {
								System.out.println("hello, from elsewhere.");
						}
				}.start();
				try { Thread.sleep(1); } catch (InterruptedException ex) {}
				System.out.println("hello, from runner.");
		}
}

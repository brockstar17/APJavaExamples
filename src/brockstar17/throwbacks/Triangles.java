package brockstar17.throwbacks;

public class Triangles
{
	public static void main(String[] args) {
		System.out.println("Triangle 1");
		drawTriangle1();
		System.out.println("Triangle 2");
		drawTri2();
		System.out.println("Triangle 3");
		drawTri3();
		System.out.println("Triangle 4");
		drawTri4();
	}

	private static void drawTriangle1() {
		for (int i = 1; i <= 5; i++) {
			String out = "";
			for (int l = 0; l < i; l++) {
				out += "* ";
			}
			System.out.println(out);
		}
	}

	private static void drawTri2() {
		int space = 0;
		for (int i = 5; i > 0; i--) {
			String out = "";

			for (int l = 0; l < space; l++) {
				out += "  ";

			}

			for (int l = 0; l < i; l++) {
				out += "* ";
			}

			space++;

			System.out.println(out);
		}
	}

	private static void drawTri3() {
		int space = 5;
		for (int i = 1; i <= 5; i++) {
			String out = "";

			for (int l = 0; l < space; l++) {
				out += "  ";

			}

			for (int l = 0; l < i; l++) {
				out += "* ";
			}

			space--;

			System.out.println(out);
		}
	}

	private static void drawTri4() {
		for (int i = 5; i > 0; i--) {
			String out = "";
			for (int l = 0; l < i; l++) {
				out += "* ";
			}
			System.out.println(out);
		}
	}

}

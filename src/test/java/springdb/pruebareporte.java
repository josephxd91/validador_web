package springdb;

public class pruebareporte {
	
	
	public static void main(String[] args) {
		
		String text = "lorem//deee";
		int cantidad = text.split("//").length;
		String nombre = text.split("//")[cantidad-1];
		System.out.println(cantidad);
		System.out.println("GGG: " + nombre);
	}

}

public class Teste {
    public static void main(String[] args) {
        String oi = "\"Ola, tudo bom\"";
        System.out.println(oi);
        String oi2 = oi.replaceAll(", ",",");
        System.out.println(oi2);
    }
}

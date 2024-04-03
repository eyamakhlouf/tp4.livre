package dao;
import java.util.List;
import lecture.Livre;
public class TestDao {
public static void main(String[] args) {
LivreDaoImpl pdao= new LivreDaoImpl();
Livre liv= pdao.save(new Livre("iphone 8 plus","aaa",2800,"fffff",200));
System.out.println(liv);
List<Livre> livs =pdao.livresParMC("Ca");
for (Livre l : livs)
System.out.println(l);
}
}
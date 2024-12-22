import java.util.Vector;
import java.lang.Object;

public class Main {
    public static void main(String[] args) {
        @SuppressWarnings({ "rawtypes", "unchecked" })
        Vector<Object> vec=new Vector();
        vec.add("e");
        vec.add("r");
        Element element=new Element(vec);

        @SuppressWarnings({ "rawtypes", "unchecked" })
        Vector<Object> vec2=new Vector();
        vec2.add("e");
        vec2.add("p");
        vec2.add("r");
        vec2.add("k");
        Element element2=new Element(vec2);
        // Element union=new Element(element.Difference(element2).elements);
        // for (int i = 0; i < union.elements.size(); i++) {
        //     System.out.println(union.elements.get(i));
        // }
        
        // System.out.println(element.Card());
        // System.out.println(element.Appartenance("e"));
        // for (int i = 0; i < element.elements.size(); i++) {
        //     System.out.println(element.elements.get(i));
        // }
    }
}

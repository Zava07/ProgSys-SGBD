import java.util.Vector;
import java.lang.Object;

@SuppressWarnings({"unchecked", "rawtypes"})
public class Element {
    Vector<Object> elements = new Vector<>();

    public Element(Vector<Object> ens) {
        elements = Doublon(ens);
    }

    public Vector Doublon(Vector<Object> vec) {
        for (int i = 0; i < vec.size(); i++) {
            for (int j = i + 1; j < vec.size(); j++) {
                if (vec.get(i).equals(vec.get(j))) {
                    vec.remove(j);
                }
            }
        }
        return vec;
    }

    public Vector getElements() {
        return this.elements;
    }

    public boolean Appartenance(Object obj) {
        if (elements.contains(obj)) {
            return true;
        }
        return false;
    }

    public int Card() {
        return elements.size();
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public Element Union(Element el) {
        Vector vec = new Vector();
        vec.addAll(el.elements);
        vec.addAll(elements);
        vec = Doublon(vec);
        Element union = new Element(vec);
        return union;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public Element Intersection(Element el) {
        Vector vec = new Vector();
        for (int i = 0; i < elements.size(); i++) {
            for (int j = 0; j < el.elements.size(); j++) {
                if (elements.get(i).equals(el.elements.get(j))) {
                    vec.add(elements.get(i));
                }
            }
        }
        vec = Doublon(vec);
        Element intersection = new Element(vec);
        return intersection;
    }

    public Element Difference(Element el) {
        Element union = Union(el);
        Element intersection = Intersection(el);
        union.elements.removeAll(intersection.elements);
        Element difference = union;
        return difference;
    }
}
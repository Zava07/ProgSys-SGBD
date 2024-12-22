public class Attribut{
    String name;
    Class domaine;
    Element ensDomaine;

    public Attribut(String n,Class d){
        this.name=n;
        this.domaine=d;
    }

    public Attribut(String n,Element e){
        this.name=n;
        this.ensDomaine=e;
    }

    public String getName()
    {
        return this.name;
    }

    public Class getDomaine()
    {
        return this.domaine;
    }

    public Class setDomaine(Class domaine)
    {
        this.domaine=domaine;
        return this.domaine;
    }

    public Element setDomaine(Element domaine)
    {
        this.ensDomaine=domaine;
        return this.ensDomaine;
    }

    public String toString(){
        return (name);
    }
}

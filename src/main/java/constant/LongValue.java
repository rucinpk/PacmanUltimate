package constant;


/**
 * klasa potrzebna do obslugi klasy anonimowej animator czyli glownej petli programu
 */
public class LongValue {
    private long value;

    public LongValue(long i)
    {
        value = i;
    }
    public void setValue(long val){
        this.value=val;
    }

    public long getValue() {
        return value;
    }
}

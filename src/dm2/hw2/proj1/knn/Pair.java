package dm2.hw2.proj1.knn;
@SuppressWarnings("rawtypes")
public class Pair<K, V extends Comparable> implements Comparable<Pair<K, V>> {

	public K key;
	public V value;
	
	public Pair(K _key, V _value) {
		this.key = _key;
		this.value = _value;
	}
	
	@SuppressWarnings("unchecked")
	public int compareTo(Pair<K, V> _p) {
		return this.value.compareTo(_p.value);
	}
	
	@Override
	public String toString() {
		return "Key: " + this.key + ", Value: " + this.value;
	}
}
import java.util.*;

public class HashMapCode{
    static class CustomHashMap<K,V> {
        private class Node{  //defining linked list in buckets
            K key;
            V value;

            public Node(K key,V value){
                this.key=key;
                this.value=value;
            }
        }
        private int n;// nodes
        private int N;// buckets
        private LinkedList<Node> buckets[];

        @SuppressWarnings("unchecked")
        public CustomHashMap(){
            this.N=4;
            this.buckets=new LinkedList[4];
            for(int i=0;i<4;i++){
                this.buckets[i]=new LinkedList<>();
            }
        }
        private int hashFunction(K key){
            int bi=key.hashCode();  //hashCode() can produce negative codes also..hence we consider only +ve ones
            return Math.abs(bi)%N;  //bi should be in 0 to N-1 thus we use %N
        }
        private int searchinLL(K key,int bi){
            LinkedList<Node> ll=buckets[bi];
            for(int i=0;i<ll.size();i++){
                if(ll.get(i).key==key){
                    return i;
                }
            }
            return -1;
        }
        @SuppressWarnings("unchecked")
        private void rehash(){
            LinkedList<Node> old[]=buckets;
            buckets=new LinkedList[N*2];
            for(int i=0;i<N*2;i++){
                buckets[i]=new LinkedList<>();
            }
            for(int i=0;i<old.length;i++){
                LinkedList<Node> ll=old[i];
                for(int j=0;j<ll.size();j++){
                    Node node=ll.get(j);
                    put(node.key,node.value);
                }
            }
        }
        public boolean containsKey(K key){
            int bi=hashFunction(key);
            int di=searchinLL(key, bi);
            if(di<0)
                return false;
            return true;
        }
        public V get(K key){
            int bi=hashFunction(key);
            int di=searchinLL(key, bi);
            if(di<0)
                return null;
            Node node=buckets[bi].get(di);
            return node.value;
        }
        public V remove(K key){
            int bi=hashFunction(key);
            int di=searchinLL(key, bi);
            if(di<0)
                return null;
            Node node=buckets[bi].remove(di);
            n--;
            return node.value;
        }
        public void put(K key,V value){
            int bi=hashFunction(key);
            int di=searchinLL(key,bi);
            if(di<0){
                buckets[bi].add(new Node(key,value));//adding new node
                n++;
            }
            else{
                Node temp=buckets[bi].get(di);//changing the existing value
                temp.value=value;
            }
            double lambda=(double)(n/N);
            if(lambda > 2.0){
                rehash();
            }
        }
        public ArrayList<K> keySet(){
            ArrayList<K> arr=new ArrayList<>();
            for(int i=0;i<buckets.length;i++){
                LinkedList<Node> ll=buckets[i];
                for(int j=0;j<ll.size();j++){
                    arr.add(ll.get(j).key);
                }
            }
            return arr;
        }
        public boolean IsEmpty(){
            return n==0;
        }
    }
    public static void main(String []args){
        CustomHashMap<String,Integer> hm=new CustomHashMap<>();
        hm.put("Vikas",1);
        hm.put("Vikky",2);
        hm.put("Vikas",5);
        hm.remove("Vikas");
        ArrayList<String> keys=hm.keySet();
        for(String key: keys){
            System.out.println(key+" "+hm.get(key));
        }
    }
}

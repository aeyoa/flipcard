import java.util.ArrayList;

/**
 * Created by arsenykogan on 27/06/14.
 */
public class Sandbox {
    public static void main(String[] args) {
        final ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(i + "go go");
        }
        for (String s : list) {
            System.out.println(s);
        }
    }
}

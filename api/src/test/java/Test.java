import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by DELL on 2018/9/14.
 */
public class Test {
    public static List<Object> testWrap(){
        List<Object> list = new ArrayList<>();
        try{
            list.add("try");
            System.out.println("try block");
            return list;
        }catch(Exception e){
            list.add("catch");
            System.out.println("catch block");
            return list;
        }finally{
            list.add("finally");
            System.out.println("finally block ");
        }
    }

    public static void main(String[] args) {
        System.out.println("main:"+ testWrap());
    }
}
